package application;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

/** This class stores all information that a Command object needs to execute a command 
 * 
 * @author A0090971Y
 */


public class CommandInfo {

    private boolean isValid;
    private boolean isValidID;
    private String commandType;
    private ArrayList<String> taskIDs = new ArrayList<String>();
    private String taskDesc;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private int priority;
    private static String[] validCommandTypes = new String[] {"add","complete","edit","delete","quit","search","search complete","undo"};

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

    CommandInfo(String commandType, ArrayList<String> taskIDs, String taskDesc, Date startDT,Date endDT, int priority) {  // edit 

        this.isValid = validateCommandType(commandType);
   //     this.isValidID = validateTaskID(taskID);
        this.commandType = commandType;
        this.taskIDs = taskIDs;
        this.taskDesc = taskDesc;
        this.startDateTime = getStartDateTime(startDT);
        this.endDateTime = getEndDateTime(endDT);
        this.startDateTime = adjustStartDateTime(startDateTime);
        this.endDateTime = adjustEndDateTime(endDateTime);
        this.priority = priority;


    }
    private DateTime adjustStartDateTime(DateTime dateTime) {
        if (dateTime != null ) {
            DateTime currentDT = new DateTime();
            int result = DateTimeComparator.getInstance().compare(currentDT,dateTime);
            if (result == 1) {   //currentDT is less than dateTime
                dateTime = dateTime.plusDays(1);
            }
        }
        return dateTime;
    }

    private DateTime adjustEndDateTime(DateTime dateTime) {
        if (dateTime != null ) {
            int result = DateTimeComparator.getInstance().compare(this.startDateTime,dateTime);
            if (result == 1) {   //currentDT is less than dateTime
                dateTime = dateTime.plusDays(1);
            }
        }
        return dateTime;
    }


    private boolean validateTaskID(String ID) {
        if (ID != null) {
            if ((Character.compare(ID.charAt(0),TaskManager.NORMAL_TASK_PREFIX)==0) || 
                    (Character.compare(ID.charAt(0),TaskManager.DATED_TASK_PREFIX)==0))
            {
                ID = ID.substring(1);
                if ((StringUtils.isNumeric(ID)) && (!ID.equals("0"))) {
                    return true;
                }
            }
        }
        return false;
    }

    //@author A0090971Y
    /**
     * 
     * @param command
     * @return true if the command type is valid, else return false
     */
    private boolean validateCommandType(String command) {
        for (int i = 0; i<CommandInfo.getValidCommandTypes().length; i++) {
            if (command.equalsIgnoreCase(CommandInfo.getValidCommandTypes()[i])) {
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
  /*  public String getTaskID(){
        return taskID;
    }
   */
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

    public DateTime getStartDateTime() {
        return this.startDateTime;
    }
    public DateTime getEndDateTime() {
        return this.endDateTime;
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

    public static String[] getValidCommandTypes() {
        return validCommandTypes;
    }

    public static void setValidCommandTypes(String[] validCommandTypes) {
        CommandInfo.validCommandTypes = validCommandTypes;
    }

    
  /*  public boolean getIsValidID() {



        return isValidID;
    }
   */
    public ArrayList<String> getTaskIDs() {
        return taskIDs;
    }
 

}
