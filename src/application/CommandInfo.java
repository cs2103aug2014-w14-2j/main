package application;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Date;
import java.util.List;

/** This class stores all information that a Command object needs to execute a command 
 * 
 * @author Jinyu
 * @version 2.0
 */
public class CommandInfo {

    private String commandType;
    private int taskID;
    private String taskDesc;
    private String startTime;
    private String startDate;
    private int priority;
    
    
    CommandInfo(String commandType, int taskID, String taskDesc, String startDate,String startTime, int priority) {  // edit 
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.startDate = startDate;
        this.startTime = startTime;
        this.priority = priority;
    }
    
    
    private boolean validateCommand(String command) {
        return true;        
    }
    /**
     * This returns the command type to be executed 
     * @return command type
     */
    public String getCommandType(){
        return commandType;
    }
    /**
     * This returns the task ID 
     * @return task ID
     */
    public int getTaskID(){
        return taskID;
    }
 
    /**
     * 
     * @return the start date in MM/DD/YYYY format
     */
    public String getStartDate() {
        return startDate;
    }
    
    /**
     * 
     * @return the start time in 24h, hh:mm:ss.ms format
     */
    public String getStartTime() {
        return startTime;
    }
    
    public int getPriority(){
        return priority;
    }
    /**
     * return the description of the Task
     * @return the description of the Task.
     */
  
    public String getTaskDesc(){
        return taskDesc;
    }
    /**
     * return the keyword to be searched
     * @return the keyword to be searched.
     */
    public String getKeyword(){
        return taskDesc;
    }
    
   
}
