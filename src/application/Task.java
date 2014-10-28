package application;

import java.util.Comparator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

//@author A0110546R
/**
 * The task object!
 * 
 * @author Sun Wang Jun
 */
public class Task {    
    private int id;
    private String displayID;
    
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
     * Constructor that edits an existing Task based on CommandInfo and assigns the same id.
     * 
     * @param commandInfo the CommandInfo object that contains parsed information.
     * @param id an existing Task id.
     */
    public Task(CommandInfo commandInfo, int id) {
        this(commandInfo); // Calls commandInfo constructor first.
        this.id = id;
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
     * Gets the created date of the Task.
     * 
     * @return the created date of the Task.
     */
    public DateTime getCreatedAt() { return this.createdAt; }
    
    /**
     * Gets the last modified date of the Task.
     * 
     * @return the last modified date of the Task.
     */
    public DateTime getModifiedAt() { return this.modifiedAt; }
    
    /**
     * Gets the completed date of the Task.
     * 
     * @return the last completed date of the Task.
     */
    public DateTime getCompletedAt() { return this.completedAt; }
    
    
}

/**
 * The comparator class used to sort Tasks by their date.
 * 
 * @author Sun Wang Jun
 */
class DateComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getDate().isAfter(b.getDate())) {
            return 1; // a is after b, so a comes after b.
        } else if (a.getDate().isBefore(b.getDate())) {
            return -1; // a is before b, so a comes before b.
        } else {
            if (a.getEndDate() == null && b.getEndDate() == null) { // Untested.
                return 0; // both a and b has no end date, do nothing.
            } else if (a.getEndDate() == null) { // Untested.
                return 1; // a has no end date, b has end date, so a comes after b. 
            } else if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, so a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;
    }
}

/**
 * The comparator class used to sort Tasks by their created at date.
 * 
 * @author Sun Wang Jun
 */
class ModifiedAtComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getModifiedAt().isAfter(b.getModifiedAt())) {
            return -1; // a is after b, so a comes after b.
        } else if (a.getModifiedAt().isBefore(b.getModifiedAt())) {
            return 1; // a is before b, so a comes before b.
        }
        return 0;
    }
}

/**
 * The comparator class used to sort Tasks by their priority.
 * 
 * @author Sun Wang Jun
 */
class PriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        // a is greater priority, a should be before b.
        return b.getPriority() - a.getPriority();
    }
}


/**
 * The comparator class used to sort Tasks by days and within each day, by priority.
 * 
 * @author Sun Wang Jun
 */
class DayPriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        LocalDate ldtA = a.getDate().toLocalDate();
        LocalDate ldtB = b.getDate().toLocalDate();
        
        if (ldtA.isAfter(ldtB)) {
            return 1; // a is after b, so a comes after b.
        } else if (ldtA.isBefore(ldtB)) {
            return -1; // a is before b, so a comes before b.
        }

        else { // Same day, so sort by priority:
            
            if (a.getPriority() > b.getPriority()) {
                return -1; // a has greater priority, so a comes before b.
            } else if (a.getPriority() < b.getPriority()) {
                return 1; // a has smaller priority, so a comes after b.
            }
            
            // Else, priority is equal, so sort by end date:
            else if (a.getEndDate() == null && b.getEndDate() == null) {
                return 0; // both a and b has no end date, do nothing.
            } else if (a.getEndDate() == null) { // Untested.
                return 1; // a has no end date, b has end date, so a comes after b. 
            } else if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, so a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;        
    }
}
