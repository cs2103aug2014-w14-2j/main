package Parser;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu   A0090971
 * @version 3.0
 */

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import application.MismatchedCommandException;

public class Parser {

    private DateTimeParser parser;
    private static String[] timePrepositions = new String[] {"BY","DUE","TILL","UNTIL"};

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
    public CommandInfo getCommandInfo(String userInput) throws MismatchedCommandException {

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
            endDateTime = formatEndDateTime(endDateTime,content);
        }
        startDateTime = formatStartDateTime(startDateTime,content);
        boolean completed = getComplete(content);
        String input = parseInput(userInput);
        try {
            CommandInfo cmdInfo = new CommandInfo(commandType, taskIDs, taskDesc,startDateTime,endDateTime, priority,completed,input);
            return cmdInfo;
        }
        catch (MismatchedCommandException e) {
            throw e;
        }
    }

    //@author A0090971Y
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
        if ((cmdType.equalsIgnoreCase("add")) || (cmdType.equalsIgnoreCase("edit"))
                || (cmdType.equalsIgnoreCase("search")) || (cmdType.equalsIgnoreCase("show"))){
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

    private boolean isDeadline(String content,DateTime startDT, DateTime endDT) {
        for (int i = 0; i<timePrepositions.length;i++) {
            if (content.indexOf(timePrepositions[i])>=0){
                if (endDT == null)
                    return true;
            }
        }
        return false;
    }

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
        if (command.equalsIgnoreCase("edit")) {
            taskID = input.trim().split("\\s+")[1];
            IDs.add(taskID);
        }
        else if ((command.equalsIgnoreCase("complete")) || (command.equalsIgnoreCase("delete"))) {
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
        return priority;
    }

    private boolean getComplete(String content) {
        if (content.indexOf("complete")>=0) {
            return true; 
        }
        return false;
    }

    private String parseInput(String userInput){
        if (parseCommandType(userInput).equalsIgnoreCase("add")){
            String command = userInput.trim().split("\\s+")[0];
            userInput = userInput.replace(command,"").trim();
        }
        else if (parseCommandType(userInput).equalsIgnoreCase("edit")){
            String command = userInput.trim().split("\\s+")[0];
            String index = userInput.trim().split("\\s+")[1];
            userInput = userInput.replaceAll(command, "");
            userInput = userInput.replaceAll(index, "");
            userInput = userInput.trim();
        }
        else {
            userInput = null;
        }

        return userInput;
    }
}








