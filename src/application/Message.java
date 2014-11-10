package application;

import java.util.ArrayList;

/**
 * The class Message and its subclasses are objects passed by Controller to UI
 * to display to the user what Controller did when user operation was accepted.
 * 
 * @author Kim Hyung Jon
 *
 */

public class Message {

    protected static final String NOTIFY_ADDED = "Added as ";
    protected static final String NOTIFY_DELETED = "Deleted ";
    protected static final String NOTIFY_EDITED = "edited successfully";
    protected static final String NOTIFY_COMPLETED = "finished ";
    protected static final String NOTIFY_UNDO = "Change unmade";

    protected static final String WARNING_INVALID_ID = "Cannot find tasks ";
    protected static final String WARNING_INVALID_DATE = "Cannot understand time ";
    protected static final String WARNING_PASSED_DATE = "Task %s has already begun";
    protected static final String WARNING_PASSED_END_DATE = "Task %s has already passed";
    protected static final String WARNING_IS_THIS_MISTAKE = "If this is a mistake, please edit";

    protected String outputMsg = "";
    
    //@author A0115864B
    /**
     * Returns the message encapsulated by the selected Message object
     * 
     * @return message constructed by the Message object
     */
    public String getMessage() {
        return outputMsg;
    }
    
    @Override
    public String toString() {
        return outputMsg;
    }

}

class MessageNotifyAdd extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when a task has been added successfully
     * 
     * @param ID Task ID assigned to the new task
     */
    MessageNotifyAdd(String ID) {
        outputMsg += NOTIFY_ADDED + ID;
    }
}

class MessageNotifyDelete extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when tasks have been deleted successfully
     * 
     * @param deletedTaskIDs ArrayList of deleted task IDs
     */
    MessageNotifyDelete(ArrayList<String> deletedTaskIDs) {
        assert deletedTaskIDs.size() > 0;
        outputMsg += NOTIFY_DELETED;
        
        for (int i = 0 ; i < deletedTaskIDs.size() ; i++) {
            outputMsg += deletedTaskIDs.get(i);
            if (i < deletedTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageNotifyEdit extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when a task has been edited successfully
     * 
     * @param ID Task ID of edited task
     */
    MessageNotifyEdit(String ID) {
        outputMsg += ID + " " + NOTIFY_EDITED;
    }
}

class MessageNotifyComplete extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when tasks have been marked as complete
     * 
     * @param completedTaskIDs ArrayList of completed task IDs
     */
    MessageNotifyComplete(ArrayList<String> completedTaskIDs) {
        assert completedTaskIDs.size() > 0;
        outputMsg += NOTIFY_COMPLETED;
        
        for (int i = 0 ; i < completedTaskIDs.size() ; i++) {
            outputMsg += completedTaskIDs.get(i);
            if (i < completedTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageNotifyUndo extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when a user operation has been undone
     */
    MessageNotifyUndo() {
        outputMsg += NOTIFY_UNDO;
    }
}

class MessageWarningInvalidID extends Message {
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when specified tasks cannot be found
     * and hence cannot be deleted or completed
     * 
     * @param invalidTaskIDs ArrayList of task IDs that couldn't be found
     */
    MessageWarningInvalidID(ArrayList<String> invalidTaskIDs) {
        assert invalidTaskIDs.size() > 0;
        outputMsg += WARNING_INVALID_ID;
        for (int i = 0 ; i < invalidTaskIDs.size() ; i++) {
            outputMsg += invalidTaskIDs.get(i);
            if (i < invalidTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageWarningInvalidDate extends Message {
    
    //@author A0115864B
    /**
     * Constructor used when the date entered when adding a task is invalid
     * 
     * @param invalidDates ArrayList of either one or both date/times that were invalid
     */
    MessageWarningInvalidDate(ArrayList<String> invalidDates) {
        outputMsg += WARNING_INVALID_DATE;
        for (int i = 0 ; i < invalidDates.size() ; i++) {
            outputMsg += invalidDates.get(i);
            if (i < invalidDates.size() - 1) {
                outputMsg += " and ";
            }
        }
    }
}

class MessageWarningDatePassed extends Message {
    
    MessageWarningDatePassed(String taskID) {
        outputMsg = String.format(WARNING_PASSED_DATE + WARNING_IS_THIS_MISTAKE, taskID);
    }
}

class MessageWarningEndDatePassed extends Message {
    
    MessageWarningEndDatePassed(String taskID) {
        outputMsg = String.format(WARNING_PASSED_END_DATE + WARNING_IS_THIS_MISTAKE, taskID);
    }
}