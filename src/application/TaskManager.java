package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

//@author A0110546R
/**
 * The manager that manipulates and contains the array list of Tasks.
 * 
 * @author Sun Wang Jun
 */
class TaskManager {
    private ArrayList<Task> list;
    private Task task; // Maybe this can act as "last modified task".
    
    public TaskManager() { // Maybe singleton this.
        
    }
    
    /**
     * Adds a task to the list.
     * 
     * @param commandInfo of type "add".
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "add".
     */
    public ArrayList<Task> add(CommandInfo commandInfo) throws MismatchedCommandException {
        if (!"add".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        this.task = new Task(commandInfo);
        this.list.add(this.task);
        
        return this.list;
    }

    /**
     * Edits a task in the list.
     * 
     * @param commandInfo of type "edit" and contains task id.
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "edit".
     */
    public ArrayList<Task> edit(CommandInfo commandInfo) throws MismatchedCommandException {
        if (!"edit".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        // Waiting for proper sequence flow.
        int id = commandInfo.getTaskID(); // Temporary id use.        
        this.task = new Task(commandInfo, id);
        
        this.list.set(id - 1, this.task); // Replaces the task.
        
        return this.list;
    }

    /**
     * Deletes a task in the list.
     * 
     * @param commandInfo of type "delete" and contains task id.
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "delete".
     */
    public ArrayList<Task> delete(CommandInfo commandInfo) throws MismatchedCommandException {
        if (!"delete".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        // Temporary hack to remove via ArrayList index.
        int taskId = commandInfo.getTaskID() - 1;
        this.list.remove(taskId);

        return this.list;
    }
    
    /**
     * Completes a task in the list.
     * 
     * @param commandInfo of type "complete" and contains task id.
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "complete".
     */
    public ArrayList<Task> complete(CommandInfo commandInfo) throws MismatchedCommandException {
        if (!"complete".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        int taskId = commandInfo.getTaskID() - 1;
        this.list.get(taskId).complete();
        
        return this.list;
    }

    /**
     * Undos one previous commandInfo.
     * 
     * @param commandInfo of type "complete" and contains task id.
     * @param backup the backup task list.
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "undo".
     */
    public ArrayList<Task> undo(CommandInfo commandInfo, ArrayList<Task> backup) 
            throws MismatchedCommandException {
        if (!"undo".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        this.list = new ArrayList<Task>(backup);
        return this.list;
    }

    /**
     * Returns the full list of tasks.
     * 
     * @return the full list of tasks.
     */
    public ArrayList<Task> getList() {
        return this.list;
    }
    
    /**
     * Returns the tasks without dates.
     * 
     * @return the tasks without dates.
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        
        int i = 0;
        ListIterator<Task> li = this.list.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t.getDate() == null) { // There is no date.
                t.setDisplayID("" + i);
                tasks.add(t);
            }
            i++;
        }
        
        Collections.sort(tasks, new ModifiedAtComparator());
        return tasks;
    }
    
    /**
     * Returns the tasks with dates.
     * 
     * @return the tasks with dates.
     */
    public ArrayList<Task> getReminders() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        
        int i = 0;
        ListIterator<Task> li = this.list.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t.getDate() != null) { // There is a date.
                t.setDisplayID("" + i);
                tasks.add(t);
            }
            i++;
        }
        Collections.sort(tasks, new DateComparator());
        return tasks;
    }

    /**
     * Initializes the list of tasks from storage.
     * 
     * @param storedList ArrayList of tasks.
     * @return the initialized list of tasks.
     */
    public ArrayList<Task> initializeList(ArrayList<Task> storedList) {
        list = new ArrayList<Task>(storedList);
        return this.list;
    }
}