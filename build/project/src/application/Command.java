package application;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Date;
import java.util.List;

public class Command {

    private String commandType;
    private String taskID;
    private String keyword;
    private String taskDesc;
    private Date taskTime;

    Command(String commandType) {  // for undo command
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        taskDesc = null;
        taskTime = null;      
    }
    Command(String commandType, String taskID) {   // for delete, complete and search 
        this.commandType = commandType;
        this.taskID = taskID;
        this.keyword = taskID;
        taskDesc = null;
        taskTime = null;
    }
    Command(String commandType, String taskDesc, Date taskTime) {   // add command
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;    
    }
    Command(String commandType, String taskID, String taskDesc, Date taskTime) {  // edit 
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;
        keyword = null;
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
    public String getTaskID(){
        return taskID;
    }
    /**
     * @return the description of the Task.
     */
    public String getTaskDesc(){
        return taskDesc;
    }
    /**
     * @return the keyword to be searched.
     */
    public String getKeyword(){
        return keyword;
    }
    
    public Date getTaskTime(){
        return taskTime;
    }
    /**
     * @return the type of the Task.
     */
}
