package application;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu   A0090971
 * @version 3.0
 */

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class Parser {

    private DateTimeParser parser;
    private static String[] timePrepositions = new String[] {"by","due","till","until"};

    //@author A0090971Y
    /**
     * This constructs a parser object with an user input 
     * @param userInput   the one line command statement the user inputs
     */
    Parser(){
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
        String content = parseContent(userInput,taskDesc);
        parser = new DateTimeParser(content);
        Date startDateTime = parser.getStartDateTime();
        Date endDateTime = parser.getEndDateTime();
        if (isDeadline(content,startDateTime,endDateTime)) {
            endDateTime = startDateTime;
            startDateTime = null;
        }
        boolean completed = getComplete(content);
        try {
        CommandInfo cmdInfo = new CommandInfo(commandType, taskIDs, taskDesc,startDateTime,endDateTime, priority,completed);
        return cmdInfo;
        }
        catch (MismatchedCommandException e) {
            throw e;
        }
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

    private boolean isDeadline(String content,Date startDT, Date endDT) {
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
        
        content = content.replace("[","");
        content = content.replace("]", "");
        content = content.replace("!", "");
        if (!desc.equalsIgnoreCase("")) {
            content = content.replaceAll(desc, "");
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
            IDs.add(taskID.toUpperCase());
        }
        else if ((command.equalsIgnoreCase("complete")) || (command.equalsIgnoreCase("delete"))) {
            String[] array = input.trim().split("\\s+");
            for (int i = 1; i<array.length; i++) {
                taskID = array[i];
                IDs.add(taskID.toUpperCase());
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
}








