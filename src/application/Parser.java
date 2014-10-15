package application;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu
 * @version 2.0
 */
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    
    private static Logger logger = Logger.getLogger("Foo");


    private static Command cmd;
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
    public static Command getCommand(String userInput) {
        logger.log(Level.INFO, "going to return a Command object to Controller");
        assert ( userInput != null );
        String commandType = parseCommandType(userInput);
        String taskID = parseTaskID(userInput);
        int priority = parsePriority(userInput);

        return cmd;
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

    private static String parseTaskID(String input) {
        String command = parseCommandType(input); 
        String taskID = null;
        if ((command.equalsIgnoreCase("edit")) || (command.equalsIgnoreCase("complete")) || (command.equalsIgnoreCase("delete"))) {
            assert (input.trim().split("\\s+").length>1);
            taskID = input.trim().split("\\s+")[1];
        }
        return taskID;
    }
    private static int parsePriority(String input){
        int priority = StringUtils.countMatches(input, "!");
        return priority;
    }

    public Date parseTime(String input) {
        List<Date> dates = new PrettyTimeParser().parse(input);
        Date taskTime;
        if (dates.size()>0) {
            taskTime =  new PrettyTimeParser().parse(input).get(0);
        }
        else {
            taskTime = null;
        }
    //    System.out.println(taskTime.toString());

        return taskTime;
    }
}








