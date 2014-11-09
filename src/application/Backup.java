package application;

import java.util.ArrayList;
import java.util.logging.Level;

import Task.Task;

public class Backup {
    
    private ArrayList<ArrayList<Task>> undoArrayList;
    private static final Integer undoArrayList_MAX_SIZE = 2;
    private static WaveLogger logger = new WaveLogger("Backup");
    
    public Backup() {
        undoArrayList = new ArrayList<ArrayList<Task>>();
    }
    
    public void storeBackup(ArrayList<Task> tasks) {
        undoArrayList.add(copyTasks(tasks));
        manageUndoArrayListSize();
    }

    /**
     * Maintain maximum undo queue size as 2
     */
    public void manageUndoArrayListSize() {
        if (undoArrayList.size() > undoArrayList_MAX_SIZE) {
            logger.log(Level.INFO, "Deleting older saved versions");
            for (int i = undoArrayList.size(); i > undoArrayList_MAX_SIZE; i--) {
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
     * Stores a copy of the ArrayList of Tasks in current state. Deep copies everything.
     * 
     * @param tasks
     * @return
     */
    public ArrayList<Task> copyTasks(ArrayList<Task> originalTasks) {
        ArrayList<Task> backup = new ArrayList<Task>();
        for (int i = 0; i < originalTasks.size(); i++) {
            backup.add(new Task(originalTasks.get(i), i));
        }
        return backup;
    }

}
