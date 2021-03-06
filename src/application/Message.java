package application;

import java.util.ArrayList;

/**
 * The class Message and its subclasses are objects passed by Controller to UI
 * to display to the user what Controller did when user operation was accepted.

 *
 */

//@author A0115864B
public abstract class Message {

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
}

class MessageNotifyAdd extends Message {
    
    private static final String NOTIFY_ADDED = "Added as ";
    
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

    private static final String NOTIFY_DELETED = "Deleted ";
    
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

    private static final String NOTIFY_EDITED = "edited successfully";
    
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

    private static final String NOTIFY_COMPLETED = "Finished ";
    
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

    private static final String NOTIFY_UNDO = "Change unmade";
    
    //@author A0115864B
    /**
     * Constructor for message to be displayed when a user operation has been undone
     */
    MessageNotifyUndo() {
        outputMsg += NOTIFY_UNDO;
    }
}

class MessageWarningInvalidID extends Message {

    private static final String WARNING_INVALID_ID = "Cannot find tasks ";
    
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