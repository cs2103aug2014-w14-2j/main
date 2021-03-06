package Parser;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import task.TaskManager;
import application.InputCommands;
import application.InvalidCommandException;
import application.Message;

//@author A0090971Y
/** This class stores all information that a Command object needs to execute a command 
 */
public class CommandInfo {
    private String commandType;
    private ArrayList<String> taskIDs = new ArrayList<String>();
    private String taskDesc;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private int priority;
    private String message = null;
    private boolean isCompleted;

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
    CommandInfo(String commandType, ArrayList<String> taskIDs, String taskDesc, DateTime startDateTime,DateTime endDateTime, int priority,boolean isCompleted) 
            throws InvalidCommandException {  
        try {
            this.commandType = commandType;
            this.taskIDs = upperCaseIDs(taskIDs);
            this.taskDesc = taskDesc;
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            this.priority = priority;
            this.isCompleted = isCompleted;
            checkStartDateTime();
            checkEndDateTime();
            validateUserInput();
        }
        catch (InvalidCommandException e) {
            throw e;
        }
    }

    //@author A0090971Y
    private ArrayList<String> upperCaseIDs(ArrayList<String> IDs) {
        for (int i = 0; i<IDs.size();i++) {
            String ID = IDs.get(i).toUpperCase();
            IDs.set(i,ID);
        }
        return IDs;
    }
    
    //@author A0090971Y
    private void checkStartDateTime() {
        if ((this.startDateTime != null ) && ((this.commandType.equalsIgnoreCase(InputCommands.ADD))
                || (this.commandType.equalsIgnoreCase(InputCommands.EDIT)))){
            DateTime currentDT = new DateTime();
            int result = DateTimeComparator.getInstance().compare(currentDT,this.startDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                setMessage("The time specified should not be before the current time.");
            }
        }
    }

    //@author A0090971Y
    private void checkEndDateTime() {
        if ((this.endDateTime != null ) && ((this.commandType.equalsIgnoreCase(InputCommands.ADD))
                || (this.commandType.equalsIgnoreCase(InputCommands.EDIT)))){
            DateTime currentDT = new DateTime();
            int result = DateTimeComparator.getInstance().compare(currentDT,this.endDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                setMessage("The end time specified should not be before the current time.");
            }
            else {
                String m = null;
                setMessage(m);
            }
        }
    }

    //@author A0090971Y
    /**
     * throws InvalidCommandException when the user input entered is invalid
     * @throws InvalidCommandException
     */
    private void validateUserInput() throws InvalidCommandException {
        boolean isValid = false;  
        for (int i = 0; i<InputCommands.getCommandList().size(); i++) {
            if (this.commandType.equalsIgnoreCase(InputCommands.getCommandList().get(i))) {
                isValid = true;
            }
        }
        if (isValid == false) {
            throw new InvalidCommandException("You have entered an invalid command.");
        }
        for (int i = 0; i<this.taskIDs.size();i++) {
            if (this.taskIDs.get(i) != null) {
                if ((Character.compare(this.taskIDs.get(i).charAt(0),TaskManager.NORMAL_TASK_PREFIX)==0) || 
                        (Character.compare(this.taskIDs.get(i).charAt(0),TaskManager.DATED_TASK_PREFIX)==0))
                {
                    String ID = this.taskIDs.get(i).substring(1);
                    if ((StringUtils.isNumeric(ID)) && (!ID.equals("0"))) {
                    }
                    else {
                        throw new InvalidCommandException("You have entered invalid task ID(s).");
                    }
                }
                else {
                    throw new InvalidCommandException("You have entered invalid task ID(s).");}
            }
        }
        if (((this.commandType.equalsIgnoreCase(InputCommands.ADD)) || (this.commandType.equalsIgnoreCase(InputCommands.EDIT))) 
                && (this.taskDesc.equalsIgnoreCase(""))) {
            throw new InvalidCommandException("Task description should not be empty..");        
        }
        if ((startDateTime != null) && (endDateTime!=null)){
            int result = DateTimeComparator.getInstance().compare(this.startDateTime,this.endDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                throw new InvalidCommandException("The end time specified should not be before the start time.");
            }
        }
    }

    //@author A0090971Y
    /**
     * This returns the command type to be executed 
     * @return command type
     */
    public String getCommandType(){
        return commandType.toUpperCase();
    }

    //@author A0090971Y
    /**
     * This returns the list of task IDs 
     * @return an ArrayList of task IDs
     */
    public ArrayList<String> getTaskIDs() {
        return taskIDs;
    }
    
    //@author A0090971Y
    /**
     * This returns the priority of the task, 1 indicates high, 0 indicates no priority
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
    
    //@author A0090971Y
    /**
     * 
     * @return the start time in Joda-Time DateTime format
     */
    public DateTime getStartDateTime() {
        return this.startDateTime;
    }
    
    //@author A0090971Y
    /**
     * This returns the end time of the task 
     * @return the end time in Joda-Time DateTime format
     */
    public DateTime getEndDateTime() {
        return this.endDateTime;
    }
    
    //@author A0090971Y
    private void setMessage(String m) {
        this.message = m;
    }

    //@author A0090971Y
    /**
     * This returns the start time of the task
     * @return a message when there is one otherwise return null
     */
    public String getMessage() {
        return this.message;
    }
    
    //@author A0090971Y
    /**
     * This returns the boolean to indicate if the user searches a completed task.
     * @return a boolean to indicate if the user searches a completed task, true for searching completed tasks, false for searching not completed tasks
     */
    public boolean isCompleted(){
        return this.isCompleted;
    }
}