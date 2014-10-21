package application;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu   A0090971
 * @version 3.0
 */

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

public class Parser {
    
    private static Logger logger = Logger.getLogger("Foo");
    private DateTimeParser parser;
    
    /**
     * This constructs a parser object with an user input 
     * @param userInput   the one line command statement the user inputs
     */
    Parser(){
    }

    /**
     * return the object of CommandInfo class
     * @return the object of CommandInfo class 
     */
    public CommandInfo getCommandInfo(String userInput) {
        logger.log(Level.INFO, "going to return a CommandInfo object to Controller");
        
        String commandType = parseCommandType(userInput);
        int taskID = parseTaskID(userInput);
        int priority = parsePriority(userInput);
        
        parser = new DateTimeParser(parseContent(userInput));
        Date startDateTime = parser.getStartDateTime();
        Date endDateTime = parser.getEndDateTime();
        String taskDesc = parseTaskDesc(parseContent(userInput));
        
        CommandInfo cmdInfo = new CommandInfo(commandType, taskID, taskDesc,startDateTime,endDateTime, priority);
        return cmdInfo;
    }
    
    /**
     * 
     * @param input
     * @return the input by removing the command type word and the taskID.
     */
    private String parseContent(String input) {
        String content;
        String firstWord = input.trim().split("\\s+")[0];
        content = input.replace(firstWord,"").trim();
        if (parseTaskID(input) != 0) {
            content = content.replace(String.valueOf(parseTaskID(input))+" ","").trim();
        }
        return content;
    }
    
    private String parseTaskDesc(String input){
        String taskDesc = parser.removeDateTime(input);
        return taskDesc;
    }

    /**
     * 
     * @param input
     * @return the command type , all letters capitalized
     */
    private String parseCommandType(String input) {
        String command = input.trim().split("\\s+")[0].toUpperCase();
        logger.log(Level.INFO, "command keyword parsed");
        return command;      
    }
    
    /**
     * 
     * @param input
     * @return 0 when taskID is not required, otherwise taskID as integer for edit,complete or delete command keyword
     */
    private int parseTaskID(String input) {
        String command = parseCommandType(input); 
        int taskID = 0;
        if ((command.equalsIgnoreCase("edit")) || (command.equalsIgnoreCase("complete")) || (command.equalsIgnoreCase("delete"))) {
            taskID = Integer.parseInt(input.trim().split("\\s+")[1]);
        }
        return taskID;
    }

    private int parsePriority(String input){
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
}








