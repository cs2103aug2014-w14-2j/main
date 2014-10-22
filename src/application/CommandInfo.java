package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

/** This class stores all information that a Command object needs to execute a command 
 * 
 * @author A0090971Y
 */


public class CommandInfo {

    private boolean isValid;
    private String commandType;
    private int taskID;
    private String taskDesc;
    private Date startDateTime;
    private Date endDateTime;
    private int priority;
    private static String validCommandTypes[] = new String[] {"add","complete","edit","delete","quit","search","undo"};

    //@author A0090971Y
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
        this.isValid = validateCommandType(commandType);
        this.commandType = commandType;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.priority = priority;


    }

    //@author A0090971Y
    /**
     * 
     * @param command
     * @return true if the command type is valid, else return false
     */
    private boolean validateCommandType(String command) {
        for (int i = 0; i<this.validCommandTypes.length; i++) {
            if (command.equalsIgnoreCase(this.validCommandTypes[i])) {
                System.out.println("testing");
                return true;
            }
        }
        return false;        
    }

    //@author A0090971Y
    /**
     * 
     * @return the boolean value indicating the validity of the user input. True if the command is valid, false if invalid
     */
    public boolean getIsValid() {
        return this.isValid;
    }

    //@author A0090971Y
    /**
     * This returns the command type to be executed 
     * @return command type
     */
    public String getCommandType(){
        return commandType.toLowerCase();
    }

    //@author A0090971Y
    /**
     * This returns the task ID 
     * @return task ID
     */
    public int getTaskID(){
        return taskID;
    }

    //@author A0090971Y
    /**
     * This returns the start date time of the task
     * @return the start date time of a task with the type Date, null if there is no start date time
     */
    public DateTime getStartDateTime() {
        DateTime dateTime = null;
        if (startDateTime == null) {
            return dateTime;
        }
        return (new DateTime(startDateTime));
    }

    //@author A0090971Y
    /**
     * This returns the end date time of the task
     * @return the start date time of a task with the type Date, null if there is no end date time
     */
    public DateTime getEndDateTime() {
        DateTime dateTime = null;
        if (endDateTime == null) {
            return dateTime;
        }
        return (new DateTime(endDateTime));
    }

    //@author A0090971Y
    /**
     * 
     * @return an integer indicate the level of priority, the larger the integer, the higher the priority
     */
    public int getPriority(){
        return priority;
    }

    //@author A0090971Y
    /**
     * return the description of the Task
     * @return the description of the Task.
     */
    public String getTaskDesc(){
        return taskDesc;
    }

    //@author A0090971Y
    /**
     * return the keyword to be searched
     * @return the keyword to be searched.
     */
    public String getKeyword(){
        return taskDesc;
    }


}
