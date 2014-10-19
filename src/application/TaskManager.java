package application;

import java.util.ArrayList;

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
    public ArrayList<Task> add(Command command) throws MismatchedCommandException {
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
    public ArrayList<Task> edit(Command command) throws MismatchedCommandException {
        if (!"edit".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        // Throw exception if incorrect command type.
        return this.list;
    }

    /**
     * Deletes a task in the list.
     * 
     * @param command
     *            of type "delete" and contains task id.
     * @return the updated list of tasks.
     */
    public ArrayList<Task> delete(Command command) throws MismatchedCommandException {
        if (!"delete".equals(command.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);

        return this.list;
    }

    /**
     * Returns the list of tasks.
     * 
     * @return the list of tasks.
     */
    public ArrayList<Task> getList() {
        return this.list;
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