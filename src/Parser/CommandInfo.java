package Parser;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import application.MismatchedCommandException;
import application.TaskManager;

/** This class stores all information that a Command object needs to execute a command 
 * 
 * @author A0090971Y
 */

public class CommandInfo {

    private String commandType;
    private ArrayList<String> taskIDs = new ArrayList<String>();
    private String taskDesc;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private int priority;
    private static String[] validCommandTypes = new String[] {"add","complete","edit","delete","home","quit","search","show","undo","exit"};
    private String message = null;
    private boolean completed;
    private String input;
    
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

    CommandInfo(String commandType, ArrayList<String> taskIDs, String taskDesc, Date startDT,Date endDT, int priority,boolean isCompleted,String input) 
    throws MismatchedCommandException {  // edit
        try {
        this.commandType = commandType;
        this.taskIDs = upperCaseIDs(taskIDs);
        this.taskDesc = taskDesc;
        this.startDateTime = getStartDateTime(startDT);
        this.endDateTime = getEndDateTime(endDT);
        this.priority = priority;
        this.completed = isCompleted;
        this.input = input;
        checkStartDateTime();
        checkEndDateTime();
        validateUserInput();
        }
        catch (MismatchedCommandException e) {
            throw e;
        }
    }

    private ArrayList<String> upperCaseIDs(ArrayList<String> IDs) {
        for (int i = 0; i<IDs.size();i++) {
            String ID = IDs.get(i).toUpperCase();
            IDs.set(i,ID);
        }
        return IDs;
        
    }
    private void checkStartDateTime() {
        if ((this.startDateTime != null ) && ((this.commandType.equalsIgnoreCase("add"))
                || (this.commandType.equalsIgnoreCase("edit")))){
            DateTime currentDT = new DateTime();
            int result = DateTimeComparator.getInstance().compare(currentDT,this.startDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                setMessage("The Time Specified is before the Current Time.");
            }
        }
    }

    private void checkEndDateTime() {
        if ((this.endDateTime != null ) && ((this.commandType.equalsIgnoreCase("add"))
                || (this.commandType.equalsIgnoreCase("edit")))){
            DateTime currentDT = new DateTime();
            int result = DateTimeComparator.getInstance().compare(currentDT,this.endDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                setMessage("The End Time Specified is before the Current Time.");
            }
            else {
                String m = null;
                setMessage(m);
            }
        }
    }

    //@author A0090971Y
    /**
     * throws MismatchedCommandException when the user input entered is invalid
     * @throws MismatchedCommandException
     */
    private void validateUserInput() throws MismatchedCommandException {
        boolean isValid = false;
        for (int i = 0; i<CommandInfo.getValidCommandTypes().length; i++) {
            if (this.commandType.equalsIgnoreCase(CommandInfo.getValidCommandTypes()[i])) {
                isValid = true;
            }
        }
        if (isValid == false) {
            throw new MismatchedCommandException("Invalid Command Type Entered.");
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
                        throw new MismatchedCommandException("Invalid Task ID Entered.");
                    }
                }
                else {
                    throw new MismatchedCommandException("Invalid Task ID Entered.");}
            }
        }
        if (((this.commandType.equalsIgnoreCase("add")) || (this.commandType.equalsIgnoreCase("edit"))) 
                && (this.taskDesc.equalsIgnoreCase(""))) {
            throw new MismatchedCommandException("Empty Task Description.");        
        }
        if ((startDateTime != null) && (endDateTime!=null)){
            int result = DateTimeComparator.getInstance().compare(this.startDateTime,this.endDateTime);
            if (result == 1) {   //currentDT is less than dateTime
                throw new MismatchedCommandException("The End Time is before the Start Time.");
            }
        }
    }

    //@author A0090971Y
    /**
     * This returns the start date time of the task
     * @return the start date time of a task with the type Date, null if there is no start date time
     */
    public DateTime getStartDateTime(Date startDT) {
        DateTime dateTime = null;
        if (startDT == null) {
            return dateTime;
        }
        return (new DateTime(startDT));
    }

    //@author A0090971Y
    /**
     * This returns the end date time of the task
     * @return the start date time of a task with the type Date, null if there is no end date time
     */
    private DateTime getEndDateTime(Date endDT) {
        DateTime dateTime = null;
        if (endDT == null) {
            return dateTime;
        }
        return (new DateTime(endDT));
    }

    private static String[] getValidCommandTypes() {
        return validCommandTypes;
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
     * 
     * @return an ArrayList of task IDs
     */
    public ArrayList<String> getTaskIDs() {
        return taskIDs;
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
    public DateTime getStartDateTime() {
        return this.startDateTime;
    }
    public DateTime getEndDateTime() {
        return this.endDateTime;
    }

    private void setMessage(String m) {
        this.message = m;
    }

    //@authour A0090971Y
    /**
     * 
     * @return a message when there is one otherwise return null
     */
    public String getMessage() {
        return this.message;
    }
    //@author A0090971Y
    /**
     * 
     * @return a boolean to indicate if the user searches a completed task, true for searching completed tasks, false for searching not completed tasks
     */
    public boolean isCompleted(){
        return completed;
    }
    //@author A0090971Y
    /**
     * 
     * @return the user input after the add command
     */
    public String getInput() {
        return this.input;
    }
}
