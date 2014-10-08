package application;

import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;


/**
 * The task object!
 * @author Sun Wang Jun
 *
 */
public class Task {
    private String id;
    private String description;
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
