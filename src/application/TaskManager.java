package application;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The manager that manipulates and contains the array list of Tasks.
 * 
 * @author Sun Wang Jun
 */
class TaskManager {
    private ArrayList<Task> list;
    private Task task; // Maybe this can act as "last modified task".
    
    public TaskManager() { // Maybe singleton this.
        this.initializeList(new ArrayList<Task>()); // Temporary solution.
    }
    
    /**
     * Adds a task to the list.
     * 
     * @param command
     *            of type "add".
     * @return the updated list of tasks.
     */
    public ArrayList<Task> add(CommandInfo command) throws MismatchedCommandException {
        if (!"add".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        this.task = new Task();
        this.task.setDescription(command.getTaskDesc());
        this.list.add(this.task);
        return this.list;
    }

    /**
     * Edits a task in the list.
     * 
     * @param command
     *            of type "edit" and contains task id.
     * @return the updated list of tasks.
     */
    public ArrayList<Task> edit(CommandInfo command) throws MismatchedCommandException {
        if (!"edit".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        return this.list;
    }

    /**
     * Deletes a task in the list.
     * 
     * @param command
     *            of type "delete" and contains task id.
     * @return the updated list of tasks.
     */
    public ArrayList<Task> delete(CommandInfo command) throws MismatchedCommandException {
        if (!"delete".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        // Temporary hack to remove via ArrayList index.
        int taskId = command.getTaskID() - 1;
        this.list.remove(taskId);

        return this.list;
    }
    
    public ArrayList<Task> undo(CommandInfo command, ArrayList<Task> backup) 
            throws MismatchedCommandException {
        if (!"undo".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        list = backup;
        return list;
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
        ListIterator<Task> li = this.list.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t.getDate() == null) { // There is no date.
                tasks.add(t);
            }
        }
        return tasks;
    }
    
    /**
     * Returns the tasks with dates.
     * 
     * @return the tasks with dates.
     */
    public ArrayList<Task> getReminders() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        ListIterator<Task> li = this.list.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t.getDate() != null) { // There is a date.
                tasks.add(t);
            }
        }
        return tasks;
    }

    /**
     * Initializes the list of tasks from storage.
     * 
     * @param storedList
     *            ArrayList of tasks.
     * @return the initialized list of tasks.
     */
    public ArrayList<Task> initializeList(ArrayList<Task> storedList) {
        list = storedList;
        return this.list;
    }
}