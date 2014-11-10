package application;

import java.util.ArrayList;
import java.util.logging.Level;

import Task.Task;

//@author A0115864B
/**
 * This component stores a past version of the tasks list as a backup
 * so that undo feature can be supported.
 * 
 * @author Kim Hyung Jon
 *
 */
public class Backup {
    
    private ArrayList<ArrayList<Task>> undoArrayList;
    private static final Integer UNDOARRAYLIST_MAX_SIZE = 2;
    private static WaveLogger logger = new WaveLogger("Backup");
    
    //@author A0115864B
    /**
     * Constructor
     * Prepares an ArrayList of ArrayList of Task objects to store a past version
     */
    public Backup() {
        undoArrayList = new ArrayList<ArrayList<Task>>();
    }
    
    //@author A0115864B
    /**
     * Stores the tasks list as backup
     * 
     * @param tasks ArrayList of all Task objects
     */
    public void storeBackup(ArrayList<Task> tasks) {
        undoArrayList.add(copyTasks(tasks));
        manageUndoArrayListSize();
    }

    //@author A0115864B
    /**
     * Maintains the number of stored backup versions to 2
     * by removing any excess version(s)
     */
    public void manageUndoArrayListSize() {
        if (undoArrayList.size() > UNDOARRAYLIST_MAX_SIZE) {
            logger.log(Level.INFO, "Deleting older saved versions");
            for (int i = undoArrayList.size() ; i > UNDOARRAYLIST_MAX_SIZE ; i--) {
                undoArrayList.remove(0);
            }
        }
    }
    
    //@author A0115864B
    /**
     * Returns a past saved version when "undo" command is received
     * 
     * @return backup ArrayList of tasks that was saved before last operation
     */
    public ArrayList<Task> getPastVersion() {
        ArrayList<Task> pastVersion = undoArrayList.remove(0);
        logger.log(Level.INFO, "Backup version retrieved");
        return pastVersion;
    }
    
    //@author A0115864B
    /**
     * Stores a copy of the tasks list in current state
     * Clones each Task object by copying all encapsulated information
     * 
     * @param originalTasks ArrayList of all Task objects
     * @return              copied ArrayList of all copied Task objects
     */
    public ArrayList<Task> copyTasks(ArrayList<Task> originalTasks) {
        ArrayList<Task> backup = new ArrayList<Task>();
        for (int i = 0 ; i < originalTasks.size() ; i++) {
            backup.add(new Task(originalTasks.get(i), i));
        }
        return backup;
    }

}
