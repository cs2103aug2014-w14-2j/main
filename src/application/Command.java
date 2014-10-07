package application;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Date;
import java.util.List;

public class Command {

    private String commandType;
    private String taskID;
    private String keyword;
    private String taskDesc;
    private String taskType;
    private Date taskTime;

    Command(String commandType) {  // for undo command
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        taskDesc = null;
        taskType = null;
        taskTime = null;      
    }
    Command(String commandType, String taskID) {   // for delete, complete and search 
        this.commandType = commandType;
        this.taskID = taskID;
        this.keyword = taskID;
        taskDesc = null;
        if (String.valueOf(taskID.charAt(0)).equals("F")) {
            taskType = "floating";
        }
        else {
            taskType = "deadline";
        }
        taskTime = null;
    }
    Command(String commandType, String taskDesc, Date taskTime) {   // add command
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;
        if (taskTime.toString().equals("")) {
            taskType = "floating";
        }
        else {
            taskType = "deadline";
        }
    }
    Command(String commandType, String taskID, String taskDesc, Date taskTime) {  // edit 
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;
        if (taskTime.toString().equals("")) {
            taskType = "floating";
        }
        else {
            taskType = "deadline";
        }
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
    public String getTaskDesc(){
        return taskDesc;
    }
    public String getKeyword(){
        return keyword;
    }
    public Date getTaskTime(){
        return taskTime;
    }
    public String getTaskType(){
        return taskType;
    }
}
