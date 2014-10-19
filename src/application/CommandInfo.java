package application;

import java.util.Date;
import java.util.List;

/** This class stores all information that a Command object needs to execute a command 
 * 
 * @author Jinyu  A0090971Y
 */

public class CommandInfo {

    private String commandType;
    private int taskID;
    private String taskDesc;
    private Date startDateTime;
    private Date endDateTime;
    private int priority;
    
    /**
     * constructor for CommandInfo class
     * @param commandType
     * @param taskID
     * @param taskDesc
     * @param startDateTime
     * @param endDateTime
     * @param priority
     */
    CommandInfo(String commandType, int taskID, String taskDesc, Date startDateTime,Date endDateTime, int priority) {  // edit 
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priority = priority;
    }
    
    // not implemented yet
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
     * This returns the start date time of the task
     * @return the start date time of a task with the type Date, null if there is no start date time
     */
    public Date getStartDateTime() {
        return startDateTime;
    }
    
    /**
     * This returns the end date time of the task
     * @return the start date time of a task with the type Date, null if there is no end date time
     */
    public Date getEndDateTime() {
        return endDateTime;
    }
    
    
    /**
     * 
     * @return an integer indicate the level of priority, the larger the integer, the higher the priority
     */
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
