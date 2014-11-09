package application;

import java.util.ArrayList;


public class Message {

    protected final String NOTIFY_ADDED = "Added as ";
    protected final String NOTIFY_DELETED = "Deleted ";
    protected final String NOTIFY_EDITED = "edited successfully";
    protected final String NOTIFY_COMPLETED = "finished ";
    protected final String NOTIFY_UNDO = "Change unmade";

    protected final String WARNING_INVALID_ID = "Cannot find tasks ";
    protected final String WARNING_INVALID_DATE = "Cannot understand time ";
    protected final String WARNING_PASSED_DATE = "Task %s has already begun";
    protected final String WARNING_PASSED_END_DATE = "Task %s has already passed";
    protected final String WARNING_IS_THIS_MISTAKE = "If this is a mistake, please edit";

    protected String outputMsg = "";
    
    public String getMessage() {
        return outputMsg;
    }
    
    @Override
    public String toString() {
        return outputMsg;
    }

}

class MessageNotifyAdd extends Message {
    
    /**
     * 
     * @param ID Task ID assigned to the new task
     */
    MessageNotifyAdd(String ID) {
        outputMsg += NOTIFY_ADDED + ID;
    }
}

class MessageNotifyDelete extends Message {
    
    /**
     * 
     * @param deletedTaskIDs ArrayList of deleted task IDs
     */
    MessageNotifyDelete(ArrayList<String> deletedTaskIDs) {
        outputMsg += NOTIFY_DELETED;
        
        for (int i = 0; i < deletedTaskIDs.size(); i++) {
            outputMsg += deletedTaskIDs.get(i);
            if (i < deletedTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageNotifyEdit extends Message {
    
    /**
     * 
     * @param ID Task ID of edited task
     */
    MessageNotifyEdit(String ID) {
        outputMsg += ID + " " + NOTIFY_EDITED;
    }
}

class MessageNotifyComplete extends Message {
    
    /**
     * 
     * @param completedTaskIDs ArrayList of completed task IDs
     */
    MessageNotifyComplete(ArrayList<String> completedTaskIDs) {
        outputMsg += NOTIFY_COMPLETED;
        
        for (int i = 0; i < completedTaskIDs.size(); i++) {
            outputMsg += completedTaskIDs.get(i);
            if (i < completedTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageNotifyUndo extends Message {
    
    /**
     * 
     */
    MessageNotifyUndo() {
        outputMsg += NOTIFY_UNDO;
    }
}

class MessageWarningAdd extends Message {
    
}

class MessageWarningInvalidID extends Message {
    
    /**
     * 
     * @param invalidTaskIDs ArrayList of task IDs that couldn't be found
     */
    MessageWarningInvalidID(ArrayList<String> invalidTaskIDs) {
        outputMsg += WARNING_INVALID_ID;
        for (int i = 0; i < invalidTaskIDs.size(); i++) {
            outputMsg += invalidTaskIDs.get(i);
            if (i < invalidTaskIDs.size() - 1) {
                outputMsg += ", ";
            }
        }
    }
}

class MessageWarningInvalidDate extends Message {
    
    /**
     * 
     * @param invalidDates ArrayList of either one or two date/times that were invalid
     */
    MessageWarningInvalidDate(ArrayList<String> invalidDates) {
        outputMsg += WARNING_INVALID_DATE;
        for (int i = 0; i < invalidDates.size(); i++) {
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