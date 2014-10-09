package application;

import java.util.Date;

/**
 * The task object!
 * @author Sun Wang Jun
 *
 */
public class Task {
    private String id;
    private String description;
    private Date date;
    private Date endDate;
    private boolean completed;
    
    /**
     * @return the id of the Task.
     */
    public String getId() { return this.id; }
    
    // We may not want to expose any setting of ids.
    // public void setId(String id) { this.id = id; }
    
    /**
     * @return the description of the Task.
     */
    public String getDescription() { return this.description; }
    /**
     * @param description overwrites the description of the Task.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the (start) date of the Task. This returns null if there is no date.
     */
    public Date getDate() { return this.date; }
    /**
     * @param date sets the (start) date of the Task. Set to null to remove the date.
     */
    public void setDate(Date date) {
        // We should probably set endDate to null if date is null.
        this.date = date;
    }
    /**
     * Removes the (start) date of the Task.
     */
    public void removeDate() { this.setDate(null); }
    
    /**
     * @return the end date of the Task. This returns null if there is no date.
     */
    public Date getEndDate() { return this.endDate; }
    /**
     * @param endDate sets the end date of the Task. Set to null to remove the date.
     */
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    /**
     * Removes the end date of the Task.
     */
    public void removeEndDate() { this.setEndDate(null); }
    
    
    /**
     * @return boolean whether Task is completed.
     */
    public boolean isCompleted() { return completed; }
    /**
     * @param completed overwrites the completed status of the Task.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
