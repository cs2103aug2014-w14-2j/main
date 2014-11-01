package application;

import java.util.ArrayList;
import java.util.logging.Level;

public class Backup {
    
    private ArrayList<ArrayList<Task>> undoQueue;
    private static final Integer undoQueue_MAX_SIZE = 2;
    private static WaveLogger logger = new WaveLogger("Backup");
    
    public Backup() {
        undoQueue = new ArrayList<ArrayList<Task>>();
    }
    
    public void storeBackup(ArrayList<Task> tasks) {
        undoQueue.add(backupTasks(tasks));
        manageUndoQueueSize();
    }

    /**
     * Maintain maximum undo queue size as 2
     */
    public void manageUndoQueueSize() {
        if (undoQueue.size() > undoQueue_MAX_SIZE) {
            logger.log(Level.INFO, "Deleting older saved versions");
            for (int i = undoQueue.size(); i > undoQueue_MAX_SIZE; i--) {
                undoQueue.remove(0);
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
        ArrayList<Task> pastVersion = undoQueue.remove(0);
        logger.log(Level.INFO, "Backup version retrieved");
        return pastVersion;
    }
    
    //@author A0115864B
    /**
     * Stores a copy of the ArrayList of Tasks in current state. Deep copies everything.
     * 
     * @param tasks
     * @return
     */
    public ArrayList<Task> backupTasks(ArrayList<Task> originalTasks) {
        ArrayList<Task> backup = new ArrayList<Task>();
        for (Task task : originalTasks) {
            backup.add(new Task(task));
        }
        return backup;
    }

}
