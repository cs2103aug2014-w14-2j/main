package application;

public class Command {

    private String commandType;
    private String taskID;
    private String keyword;
    private String taskDesc;
    private String taskType;
    private String taskTime;

    Command(String commandType) {
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        taskDesc = null;
        taskType = null;
        taskTime = null;      
    }
    Command(String commandType, String taskID) {
        this.commandType = commandType;
        this.taskID = taskID;
        this.keyword = taskID;
        taskDesc = null;
        taskType = null;
        taskTime = null;
    }
    Command(String commandType, String taskDesc, String taskTime) {
        this.commandType = commandType;
        taskID = null;
        keyword = null;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;
        taskType = null;
    }
    Command(String commandType, String taskID, String taskDesc, String taskTime) {
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.taskTime = taskTime;
        taskType = null;
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
    public String getTaskTime(){
        return taskTime;
    }
    public String getTaskType(){
        return taskType;
    }
}
