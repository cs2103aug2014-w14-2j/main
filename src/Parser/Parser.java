package Parser;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import application.InputCommands;
import application.InvalidCommandException;

//@author A0090971Y
/** This class implements a Parser to parse an input string
 */
public class Parser {
    private DateTimeParser parser;
    private static String[] timePrepositions = new String[] {"BY","BEFORE","DUE","TILL","UNTIL"};

    //@author A0090971Y
    /**
     * This constructs a parser object with an user input 
     * @param userInput   the one line command statement the user inputs
     */
    public Parser(){
    }

    //@author A0090971Y
    /**
     * return the object of CommandInfo class
     * @return the object of CommandInfo class 
     */
    public CommandInfo getCommandInfo(String userInput) throws InvalidCommandException {
        String commandType = parseCommandType(userInput);
        ArrayList<String> taskIDs = new ArrayList<String>();
        taskIDs = parseTaskIDs(userInput);

        String taskDesc = parseTaskDesc(userInput,commandType);
        int priority = parsePriority(userInput,taskDesc);
        String content = parseContent(userInput,taskDesc).toUpperCase();
        parser = new DateTimeParser(content);
        DateTime startDateTime =changeToDateTime(parser.getStartDateTime());
        DateTime endDateTime = changeToDateTime(parser.getEndDateTime());
        if (isDeadline(content,startDateTime,endDateTime)) {
            endDateTime = startDateTime;
            startDateTime = null;          
        }
        startDateTime = formatStartDateTime(startDateTime,content);
        endDateTime = formatEndDateTime(endDateTime,content);
        boolean completed = getComplete(content);
        try {
            CommandInfo cmdInfo = new CommandInfo(commandType, taskIDs, taskDesc,startDateTime,endDateTime, priority,completed);
            return cmdInfo;
        }
        catch (InvalidCommandException e) {
            throw e;
        }
    }

    //@author A0090971Y
    /**
     * format the end time to 23:59 if the user only enters the end date which does not contain digits
     * @param dt
     * @param content
     * @return
     */
    private DateTime formatEndDateTime(DateTime dt,String content) {  
        if (dt == null) {
            return dt;
        }
        if(!content.matches(".*\\d.*")){
            int year = Integer.parseInt(dt.toString().substring(0, 4));
            int month =Integer.parseInt(dt.toString().substring(5, 7));
            int day = Integer.parseInt(dt.toString().substring(8,10));       
            dt = new DateTime(year,month, day,23,59,59);
        }
        return dt;
    }

    //@author A0090971Y
    /**
     * format the start time to 12:00am if the user only enters the start date which does not contain digits
     * @param dt
     * @param content
     * @return
     */
    private DateTime formatStartDateTime(DateTime dt,String content) {
        if (dt == null) {
            return dt;
        }
        DateTime currentDT = new DateTime();
        int result = DateTimeComparator.getInstance().compare(currentDT,dt);
        if ((result == -1)&&(!content.matches(".*\\d.*"))) {   //currentDT is after dt
            int year = Integer.parseInt(dt.toString().substring(0, 4));
            int month =Integer.parseInt(dt.toString().substring(5, 7));
            int day = Integer.parseInt(dt.toString().substring(8,10));       
            dt = new DateTime(year,month, day,0,0,0);
        }
        return dt;
    }

    //@author A0090971Y
    private DateTime changeToDateTime(Date date) {
        DateTime dateTime = null;
        if (date == null) {
            return dateTime;
        }
        return (new DateTime(date));
    }

    //@author A0090971Y
    /**
     * 
     * @param input
     * @return the input by removing the command type word and the taskID.
     */
    private String parseTaskDesc(String input,String cmdType) {
        String desc = null;
        if ((cmdType.equalsIgnoreCase(InputCommands.ADD)) || (cmdType.equalsIgnoreCase(InputCommands.EDIT))
                || (cmdType.equalsIgnoreCase(InputCommands.SEARCH)) || (cmdType.equalsIgnoreCase(InputCommands.SHOW))){
            int startIndex = input.indexOf("[");
            int endIndex = input.indexOf("]");
            if ((startIndex>0) && (endIndex>0)) {
                desc = input.substring(startIndex+1, endIndex);
            }
            else desc = "";
        }
        else {
            cmdType = input.trim().split("\\s+")[0];
            desc = input.replaceFirst(cmdType, "").trim();
            desc = desc.replace("[", "");
            desc = desc.replace("]", "");
        }
        return desc;
    }
    
    //@author A0090971Y
    private boolean isDeadline(String content,DateTime startDT, DateTime endDT) {
        for (int i = 0; i<timePrepositions.length;i++) {
            if (content.indexOf(timePrepositions[i])>=0){
                if (endDT == null)
                    return true;
            }
        }
        return false;
    }

    //@author A0090971Y
    private String parseContent(String input,String desc) {
        String content;
        String firstWord = input.trim().split("\\s+")[0];
        content = input.replace(firstWord,"").trim();
        if (parseTaskIDs(input).size()!=0) {
            content = content.replace(parseTaskIDs(input).get(0),"").trim();
        }
        if (input.indexOf("]")>=0) {
            int index = input.lastIndexOf("]");
            content = input.substring(index+1);
        }
        return content;
    }

    //@author A0090971Y
    /**
     * 
     * @param input
     * @return the command type , all letters capitalized
     */
    private String parseCommandType(String input) {
        String[] array = input.trim().split("\\s+");
        String command = array[0].toUpperCase();
        return command;      
    }

    //@author A0090971Y
    /**
     * 
     * @param input
     * @return null when taskID is not required, otherwise taskID as an ArrayList of String for edit,complete or delete command keyword
     */
    private ArrayList<String> parseTaskIDs(String input) {
        String command = parseCommandType(input);
        ArrayList<String> IDs = new ArrayList<String>();
        String taskID = null;
        if (command.equalsIgnoreCase(InputCommands.EDIT)) {
            taskID = input.trim().split("\\s+")[1];
            IDs.add(taskID);
        }
        else if ((command.equalsIgnoreCase(InputCommands.COMPLETE)) || (command.equalsIgnoreCase(InputCommands.DELETE))) {
            String[] array = input.trim().split("\\s+");
            for (int i = 1; i<array.length; i++) {
                taskID = array[i];
                IDs.add(taskID);
            }
        }
        return IDs;
    }

    //@author A0090971Y
    /**
     * 
     * @param input
     * @return the priority of the task by counting the number of exclamation marks in user input
     */
    private int parsePriority(String input,String desc){
        desc = desc.replace("[", "");
        desc = desc.replace("]", "");
        input = input.replaceAll(desc, "");
        int priority = StringUtils.countMatches(input,"!");
        if (priority>0) {
            priority = 1;
        }
        return priority;
    }

    //@author A0090971Y
    private boolean getComplete(String content) {
        if (content.indexOf(InputCommands.COMPLETE)>=0) {
            return true; 
        }
        return false;
    }
}
