package application;

import org.joda.time.DateTime;

import Parser.CommandInfo;

//@author A0110546R
/**
 * The task object!
 * 
 * @author Sun Wang Jun
 */
public class Task {
    /* The following fields are not stored. */
    private int id;
    private String displayID;
    private boolean deleted = false;
    
    /* The following attributes are stored. */
    private String description;
    private DateTime date;
    private DateTime endDate;
    private boolean completed;
    private int priority;    
    private DateTime createdAt;
    private DateTime modifiedAt;
    private DateTime completedAt;
    
    private static int idCounter = 0;
    
    /**
     * Constructor that creates a Task based on CommandInfo.
     * 
     * @param commandInfo the CommandInfo object that contains parsed information.
     */
    public Task(CommandInfo commandInfo) {
        this(); // Calls default constructor first.
        
        this.description = commandInfo.getTaskDesc();
        if (commandInfo.getStartDateTime() != null) {
            this.date = commandInfo.getStartDateTime();
        }
        if (commandInfo.getEndDateTime() != null) {
            this.endDate = commandInfo.getEndDateTime();
        }
        if (commandInfo.getPriority() != 0) {
            this.priority = commandInfo.getPriority();
        }
    }
    
    /**
     * Constructor for cloning task object, used when storing past versions
     * @param original
     */
    public Task(Task original, int originalID) {
        id = originalID;
        description = original.getDescription();
        displayID = original.getDisplayID();
        date = original.getDate();
        endDate = original.getEndDate();
        completed = original.isCompleted();
        priority = original.getPriority();
        createdAt = original.getCreatedAt();
        modifiedAt = original.getModifiedAt();
        completedAt = original.getCompletedAt();
        deleted = original.isDeleted();
        
    }

    /**
     * Constructor that edits an existing Task based on CommandInfo and assigns the same id.
     * 
     * @param commandInfo the CommandInfo object that contains parsed information.
     * @param id an existing Task id.
     */
    public Task(CommandInfo commandInfo, int id) {
        this(commandInfo); // Calls commandInfo constructor first.
        this.id = id;
        Task.idCounter--; // Because we are editing, so do not increment the counter.
    }
    
    /**
     * Public default constructor.
     */
    public Task() {
        this.id = Task.idCounter++;
        this.createdAt = new DateTime();
        this.modifiedAt = new DateTime();
    }

    /**
     * Returns the id of the Task.
     * 
     * @return the id of the Task.
     */
    public int getID() { return this.id; }

    // We may not want to expose any setting of ids.
    // public void setId(String id) { this.id = id; }

    /**
     * Returns the description of the Task.
     * 
     * @return the description of the Task.
     */
    public String getDescription() { return this.description; }

    /**
     * Sets the description of the Task.
     * 
     * @param description overwrites the description of the Task.
     */
    public void setDescription(String description) {
        this.description = description;
        this.modifiedAt = new DateTime();
    }    
    
    /**
     * Returns the displayID of the Task.
     * 
     * @return the displayID of the Task.
     */
    public String getDisplayID() { return this.displayID; }

    /**
     * Sets the displayID of the Task.
     * 
     * @param description overwrites the displayID of the Task.
     */
    public void setDisplayID(String displayID) {
        this.displayID = displayID;
        // this.modifiedAt = new DateTime(); // This does not modify the task. 
    }

    /**
     * Returns the (start) date of the Task. Returns null if there is no date.
     * 
     * @return the (start) date of the Task. Returns null if there is no date.
     */
    public DateTime getDate() { return this.date; }

    /**
     * Sets the (start) date of the Task. Set as null to remove the date.
     * 
     * @param date sets the (start) date of the Task.
     *     Set to null to remove the date.
     */
    public void setDate(DateTime date) {
        // We should probably set endDate to null if date is null.
        this.date = date;
        this.modifiedAt = new DateTime();
    }

    /**
     * Removes the (start) date of the Task.
     */
    public void removeDate() { this.setDate(null); }

    /**
     * Returns the end date of the Task. Returns null if there is no end date.
     * 
     * @return the end date of the Task. Returns null if there is no end date.
     */
    public DateTime getEndDate() { return this.endDate; }

    /**
     * Sets the end date of the Task. Set as null to remove the date.
     * 
     * @param endDate sets the end date of the Task. Set to null to remove the date.
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
        this.modifiedAt = new DateTime();
    }

    /**
     * Removes the end date of the Task.
     */
    public void removeEndDate() { this.setEndDate(null); }

    /**
     * Returns whether Task is completed.
     * 
     * @return boolean whether Task is completed.
     */
    public boolean isCompleted() { return completed; }

    /**
     * Sets the completed status of the Task.
     * 
     * @param completed overwrites the completed status of the Task.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.modifiedAt = new DateTime();
        if (completed) {
            this.completedAt = new DateTime(this.modifiedAt);
        }
        else {
            this.completedAt = null;
        }
    }

    /**
     * Completes the task.
     */
    public void complete() { this.setCompleted(true); }

    /**
     * Returns the priority of the Task.
     * 
     * @return int priority of the Task.
     */
    public int getPriority() { return this.priority; }

    /**
     * Sets the priority of the Task.
     * 
     * @param priority overwrites the priority of the Task.
     */
    public void setPriority(int priority) {
        // Check if priority is negative.
        this.priority = priority;
        this.modifiedAt = new DateTime();
    }
    
    /**
     * Returns the created date of the Task.
     * 
     * @return the created date of the Task.
     */
    public DateTime getCreatedAt() { return this.createdAt; }
    
    /**
     * Sets the created date of the Task.
     * Used only when retrieving tasks list from external file.
     * 
     * @param createdDate the created DateTime of the Task.
     */
    public void setCreatedAt(DateTime createdDate) {
        this.createdAt = createdDate;
    }
    
    /**
     * Returns the last modified date of the Task.
     * 
     * @return the last modified date of the Task.
     */
    public DateTime getModifiedAt() { return this.modifiedAt; }
    
    /**
     * Sets the last modified date of the Task. 
     * Used only when retrieving tasks list from external file.
     * 
     * @param modifiedDate the last modified DateTime of the Task.
     */
    public void setModifiedAt(DateTime modifiedDate) {
        this.modifiedAt = modifiedDate;
    }
    
    /**
     * Returns the completed date of the Task.
     * 
     * @return the completed date of the Task.
     */
    public DateTime getCompletedAt() { return this.completedAt; }
    
    /**
     * Sets the completed date of the Task.
     * Used only when retrieving tasks list from external file.
     * 
     * @param completedDate the completed DateTime of the Task.
     */
    public void setCompletedAt(DateTime completedDate) {
        this.completedAt = completedDate;
    }
    
    /**
     * Used to reset the internal ID counter back to 0.
     */
    public static void resetIDCounter() { idCounter = 0; }
    
    /**
     * Returns whether this task is deleted.
     * 
     * @return whether this task is deleted.
     */
    public boolean isDeleted() { return this.deleted; }
    
    /**
     * Sets whether the Task is deleted.
     * @param deleted boolean whether to delete the Task.
     */
    public void setDeleted(boolean deleted) { this.deleted = deleted; }   
    
}

