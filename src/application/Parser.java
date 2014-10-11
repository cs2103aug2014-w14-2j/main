package application;

/** This class implements a Parser to parse an input string
 * 
 * @author Jinyu
 * @version 2.0
 */
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Date;
import java.util.List;

public class Parser {


    private Command cmd;
    private String commandType;
    private String taskID;
    private String taskDesc;
    private Date taskTime;

    /**
     * This constructs a parser object with an user input 
     * @param userInput   the one line command statement the user inputs
     */

    Parser(String userInput){
        commandType = getFirstWord(userInput).toLowerCase();
        switch (commandType) {
        case "undo" : {
            cmd = new Command(commandType);
            break;
        }
        case "delete" : case "complete" : case "search" : 
        {
            taskID =userInput.replace(getFirstWord(userInput), "").trim();
            cmd = new Command(commandType,taskID);
            break;
        }
        case "add" : {
            taskDesc = userInput.replace(getFirstWord(userInput), "").trim();
            List<Date> dates = new PrettyTimeParser().parse(taskDesc);
            if (dates.size()>0) {
            taskTime =  new PrettyTimeParser().parse(taskDesc).get(0);
            }
            else {
                taskTime = null;
            }
            cmd = new Command(commandType,taskDesc,taskTime);
            break;
        }
        case "edit" : {
            taskID = userInput.trim().split("\\s+")[1];
            taskDesc = userInput.replace(getFirstWord(userInput)+" "+taskID,"").trim(); 
            List<Date> dates = new PrettyTimeParser().parse(taskDesc);
            if (dates.size()>0) {
            taskTime =  new PrettyTimeParser().parse(taskDesc).get(0);
            }
            else {
                taskTime = null;
            }
            cmd = new Command(commandType, taskID, taskDesc, taskTime);
            break;	    
        }
        default: 
            break;
        }
    }

    private String getFirstWord(String input) {
        String command = input.trim().split("\\s+")[0];
        return command;
    }
    /**
     * @return the object of Command class 
     */
    public Command getCmd() {
        return cmd;
    }
}
