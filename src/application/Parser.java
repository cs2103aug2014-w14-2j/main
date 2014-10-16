package application;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu
 * @version 3.0
 */
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    
    private static Logger logger = Logger.getLogger("Foo");


    private static CommandInfo cmdInfo;
    private static Parser theOne;
    /**
     * This constructs a parser object with an user input 
     * @param userInput   the one line command statement the user inputs
     */
    Parser(){

    }

    public static Parser getInstance() {
        if (theOne == null) {
            theOne = new Parser();
        }
        return theOne;
    }

    /**
     * return the object of Command class
     * @return the object of Command class 
     */
    public static CommandInfo getCommandInfo(String userInput) {
        logger.log(Level.INFO, "going to return a Command object to Controller");
        assert ( userInput != null );
        String commandType = parseCommandType(userInput);
        int taskID = parseTaskID(userInput);
        int priority = parsePriority(userInput);
        String startDate= extractDate(parseDateTime(userInput).toString());
        String startTime = extractTime(parseDateTime(userInput).toString());
        String taskDesc = parseTaskDesc(userInput);
        
        CommandInfo cmdInfo = new CommandInfo(commandType, taskID, taskDesc,startDate,startTime, priority);
        return cmdInfo;
    }
    
    private static String parseTaskDesc(String input){
        String taskDesc;
        taskDesc = input.replace(parseCommandType(input), "").trim();
        if (parseTaskID(input) != 0) {
            taskDesc = input.replace(String.valueOf(parseTaskID(input)),"").trim();
        }
        System.out.printf("taskDesc is %s",taskDesc);
        return taskDesc;
        
    }

    /**
     * 
     * @param input
     * @return the command type , all letters capitalized
     */
    private static String parseCommandType(String input) {
        String command = input.trim().split("\\s+")[0].toUpperCase();
        logger.log(Level.INFO, "command keyword parsed");
        return command;      
    }
    /**
     * 
     * @param input
     * @return 0 when taskID is not required, otherwise taskID as integer for edit,complete or delete command keyword
     */

    private static int parseTaskID(String input) {
        String command = parseCommandType(input); 
        int taskID = 0;
        if ((command.equalsIgnoreCase("edit")) || (command.equalsIgnoreCase("complete")) || (command.equalsIgnoreCase("delete"))) {
            assert (input.trim().split("\\s+").length>1);
            taskID = Integer.parseInt(input.trim().split("\\s+")[1]);
            
            Integer.parseInt("1234");
        }
        return taskID;
    }
    private static int parsePriority(String input){
        int priority = StringUtils.countMatches(input, "!");
        return priority;
    }

    public static Date parseDateTime(String input) {
        List<Date> dates = new PrettyTimeParser().parse(input);
        Date taskTime;
        if (dates.size()>0) {
            taskTime =  new PrettyTimeParser().parse(input).get(0);
        }
        else {
            taskTime = null;
        }
        System.out.println(taskTime.toString());

        return taskTime;
    }

    public static String extractTime(String dateTime){
        String time =dateTime.trim().split("\\s+")[3];
        return time;
    }
    
    public static String extractDate(String dateTime){
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
    
    private static int matchMonth(String month) {
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
}








