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
    private static String[] timePrepositions = new String[] {"by","till","until"};
    private static String[] escapeSequences = new String[]{"\\"};
    private static String[] addSlashes = new String[] {"\\\\"};




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
    public CommandInfo getCommandInfo(String userInput) {

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

        CommandInfo cmdInfo = new CommandInfo(commandType, taskIDs, taskDesc,startDateTime,endDateTime, priority);
        return cmdInfo;
    }

    //@author A0090971Y
    /**
     * 
     * @param input
     * @return the input by removing the command type word and the taskID.
     */
    private String parseTaskDesc(String input,String cmdType) {
        String desc = null;
        if ((cmdType.equalsIgnoreCase("add")) || (cmdType.equalsIgnoreCase("edit"))){
            int startIndex = input.indexOf("[");
            int endIndex = input.indexOf("]");
            desc = input.substring(startIndex+1, endIndex);
            //      desc = dealEscapeSequences(desc);
        }
        else {
            cmdType = input.trim().split("\\s+")[0];
            desc = input.replaceFirst(cmdType+" ", "");
        }
        return desc;
    }

    //@author A0090971Y
    /**
     * 
     * @param desc
     * @return task description without the escape sequences
     */
    private String dealEscapeSequences(String desc) {
        for (int i = 0; i<escapeSequences.length;i++) {
            if (desc.indexOf(escapeSequences[i])>=0) { 
                System.out.println(escapeSequences[i]);
                desc = desc.replace(escapeSequences[i],addSlashes[i]);
                break;
            }
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
        if (parseTaskIDs(input) != null) {
            content = content.replace(parseTaskIDs(input).get(0)+" ","").trim();
        }
        content = content.replaceAll(desc, "");
        content = content.replace("[","");
        content = content.replace("]", "");
        content = content.replace("!", "");
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
        if ((command.equalsIgnoreCase("search")) || (array.length==2)){
            if (array[1].equals("complete")) {
                command = command+" "+array[1].toUpperCase();
            }
        }
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
        input = input.replaceAll(desc, "");
        int priority = StringUtils.countMatches(input,"!");
        return priority;
    }


    /*
    private String extractTime(String dateTime){
        String time =dateTime.trim().split("\\s+")[3];
        return time;
    }

    private String extractDate(String dateTime){
        logger.log(Level.INFO, "starting to extract date");
        int day,month,year;

        String[] dateTimes = dateTime.trim().split("\\s+");
        year =Integer.parseInt(dateTimes[dateTimes.length-1]);
        month = matchMonth(dateTimes[1]);
        day = Integer.parseInt(dateTimes[2]);

        LocalDate localDate = new LocalDate(year, month, day);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        String formattedDate = formatter.print(localDate);
        return formattedDate;

    }

    private int matchMonth(String month) {
        switch (month) {
            case "Jan" : return 1; 
            case "Feb" : return 2;
            case "Mar" : return 3; 
            case "Apr" : return 4; 
            case "May" : return 5;
            case "Jun" : return 6;
            case "Jul" : return 7;
            case "Aug" : return 8;
            case "Sep" : return 9;
            case "Oct" : return 10;
            case "Nov" : return 11; 
            case "Dec" : return 12;
            default: return 0;
        }
    }
     */

    //@author A0090971Y
    /**
     * 
     * @param input
     * @param taskDesc
     * @return a string of the task description by removing the prepositions in front of time
     */
    /*
    private String removePrepositions(String input, String taskDesc){
        String[] inputArray = input.trim().split("\\s+");
        String[] descArray = taskDesc.trim().split("\\s+");
        int indexInput = -1;
        String preposition = null;
        for (int j = 0; j<inputArray.length;j++)
            for (int i = 0; i<Parser.getTimePrepositions().length;i++) {
                if (inputArray[j].equals(Parser.getTimePrepositions()[i])) {
                    indexInput = j;
                    preposition = Parser.getTimePrepositions()[i];
                    break;
                }
            }

        for (int i = 0; i<descArray.length;i++){
            if (descArray[i].equals(preposition)) {
                if ((i+1)!= descArray.length){
                    String nextWordDesc = descArray[i+1];
                    String nextWordInput = inputArray[i+1];
                    if (!nextWordInput.equals(nextWordDesc)) {
                        taskDesc = taskDesc.replace(preposition, "");
                    }
                }
                else {
                    if ((indexInput+1)!=inputArray.length) {
                        taskDesc = taskDesc.replace(preposition,"");
                    }
                }
            }
        }

        return taskDesc;
    }
     public static String[] getTimePrepositions() {
        return timePrepositions;
    }

    public static void setTimePrepositions(String[] timePrepositions) {
        Parser.timePrepositions = timePrepositions;
    }

     */

}








