package application;

import java.util.ArrayList;

/**
 * The manager that contains the array list of Tasks.
 * 
 * @author Sun Wang Jun
 */
class TaskManager {
    private ArrayList<Task> list;
    private Task task; // Maybe this can act as "last modified task".

    /**
     * @param uiComponent
     *            the view to update
     */
    public void updateUi(UiComponent uiComponent) {
        //uiComponent.updateFloatingTasks(this.list);
    }

    /**
     * Adds a task to the list.
     * 
     * @param command
     *            of type "add".
     * @return the updated list of tasks.
     */
    public ArrayList<Task> add(Command command) {
        // Throw exception if incorrect command type.
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
    public ArrayList<Task> edit(Command command) {
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
    public ArrayList<Task> delete(Command command) {
        // Throw exception if incorrect command type.
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);

        return this.list;
    }

    /**
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