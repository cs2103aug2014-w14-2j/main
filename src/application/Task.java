package application;

import java.util.Comparator;

import org.joda.time.DateTime;

/**
 * The task object!
 * 
 * @author Sun Wang Jun
 */
public class Task {
    private String id;
    private String description;

    private DateTime date;
    private DateTime endDate;
    private boolean completed;
    
    private int priority;
    
    /**
     * Constructor that creates a Task based on CommandInfo.
     */
    public Task(CommandInfo command) {
        this.description = command.getTaskDesc();
        if (command.getStartDateTime() != null) {
            // Temporarily casting to DateTime.
            this.date = new DateTime(command.getStartDateTime());
        }
        if (command.getEndDateTime() != null) {
            // Temporarily casting to DateTime.
            this.date = new DateTime(command.getEndDateTime());
        }
        if (command.getPriority() != 0) {
            this.priority = command.getPriority();
        }
    }
    
    /**
     * Public default constructor.
     */
    public Task() {
        
    }

    /**
     * Returns the id of the Task.
     * 
     * @return the id of the Task.
     */
    public String getID() {
        return this.id;
    }

    // We may not want to expose any setting of ids.
    // public void setId(String id) { this.id = id; }

    /**
     * Returns the description of the Task.
     * 
     * @return the description of the Task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the Task.
     * 
     * @param description
     *            overwrites the description of the Task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the (start) date of the Task. Returns null if there is no date.
     * 
     * @return the (start) date of the Task. Returns null if there is no date.
     */
    public DateTime getDate() {
        return this.date;
    }

    /**
     * Sets the (start) date of the Task. Set as null to remove the date.
     * 
     * @param date
     *            sets the (start) date of the Task. Set to null to remove the
     *            date.
     */
    public void setDate(DateTime date) {
        // We should probably set endDate to null if date is null.
        this.date = date;
    }

    /**
     * Removes the (start) date of the Task.
     */
    public void removeDate() {
        this.setDate(null);
    }

    /**
     * Returns the end date of the Task. Returns null if there is no end date.
     * 
     * @return the end date of the Task. Returns null if there is no end date.
     */
    public DateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date of the Task. Set as null to remove the date.
     * 
     * @param endDate
     *            sets the end date of the Task. Set to null to remove the date.
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Removes the end date of the Task.
     */
    public void removeEndDate() {
        this.setEndDate(null);
    }

    /**
     * Returns whether Task is completed.
     * 
     * @return boolean whether Task is completed.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completed status of the Task.
     * 
     * @param completed
     *            overwrites the completed status of the Task.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Completes the task.
     */
    public void complete() {
        this.setCompleted(true);
    }

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
    }
}

/**
 * The comparator class used to sort Tasks by their date.
 * 
 * @author Sun Wang Jun
 *
 */
class DateComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getDate().isAfter(b.getDate())) {
            return 1; // a is after b, so a comes after b.
        } else if (a.getDate().isBefore(b.getDate())) {
            return -1; // a is before b, so a comes before b.
        } else {
            if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, so a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;
    }
}

/**
 * The comparator class used to sort Tasks by their priority.
 * 
 * @author Sun Wang Jun
 *
 */
class PriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        // a is greater priority, a should be before b.
        return b.getPriority() - a.getPriority();
    }
}