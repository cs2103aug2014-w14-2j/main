package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.ListIterator;

//@author A0110546R
/**
 * The manager that manipulates and contains the array list of Tasks.
 * 
 * @author Sun Wang Jun
 */
class TaskManager {
    public static final char NORMAL_TASK_PREFIX = 'T';
    public static final char DATED_TASK_PREFIX = 'E';
    
    private ArrayList<Task> list;
    private Task task; // Maybe this can act as "last modified task".
    private Hashtable<String, Integer> idMapping;
    
    public TaskManager() { // Maybe singleton this.
        this.idMapping = new Hashtable<String, Integer>();
    }
    
    /**
     * Checks if the task displayID entered is valid.
     * 
     * @param displayID the task displayID the user entered.
     * @return whether it exists in idMapping
     */
    public boolean ensureValidDisplayID(String displayID) {
        return this.idMapping.containsKey(displayID);
    }
    
    /**
     * Used to map displayID to actual taskID.
     * 
     * @param displayID
     * @return taskID
     */
    private int mapDisplayIDtoActualID(String displayID) {
        assert(this.idMapping.containsKey(displayID) == true);
        return this.idMapping.get(displayID);
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
        int taskId = this.mapDisplayIDtoActualID(commandInfo.getTaskIDs().get(0));       
        this.task = new Task(commandInfo, taskId);
        
        this.list.set(taskId, this.task); // Replaces the task.
        
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
        
        ListIterator<String> li = commandInfo.getTaskIDs().listIterator();
        while (li.hasNext()) {
            String displayID = li.next();
            int taskId = this.mapDisplayIDtoActualID(displayID);
            this.list.set(taskId, null); // "Soft-delete" in the ArrayList.
        }

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
        
        ListIterator<String> li = commandInfo.getTaskIDs().listIterator();
        while (li.hasNext()) {
            String displayID = li.next();
            int taskId = this.mapDisplayIDtoActualID(displayID);
            this.list.get(taskId).complete(); // "Soft-delete" in the ArrayList.
        }
        
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
        return TaskListFilter.filterOutNullTasks(this.list); // Need to filter out nulls, because they are soft-deleted.
    }
    
    /**
     * Returns the tasks without start dates.
     * 
     * @return the tasks without start dates.
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = TaskListFilter.filterOutTasksWithStartDates(this.list); // Kick out tasks with start dates.
        tasks = TaskListFilter.filterOutCompletedTasks(tasks); // Kick out completed tasks.
        
        // Sort by modified at date first, then priority.
        Collections.sort(tasks, new ModifiedAtComparator());
        Collections.sort(tasks, new PriorityComparator());
        
        int i = 1;
        ListIterator<Task> li = tasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = NORMAL_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return tasks;
    }
    
    /**
     * Returns the tasks with start dates.
     * 
     * @return the tasks with start dates.
     */
    public ArrayList<Task> getReminders() {
        ArrayList<Task> tasks = TaskListFilter.filterOutTasksWithoutStartDates(this.list); // Kick out tasks without start dates.
        tasks = TaskListFilter.filterOutCompletedTasks(tasks); // Kick out completed tasks.

        Collections.sort(tasks, new DayPriorityComparator());
        
        int i = 1;
        ListIterator<Task> li = tasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = DATED_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
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
    
    /**
     * Returns the last task modified.
     * @return the last task modified.
     */    
    public Task getLastModifiedTask() {
        return this.task;
    }
    
    
    /**
     * Returns a list of invalid display IDs.
     * @return a list of invalid display IDs, or null if none.
     */
    public ArrayList<String> getInvalidDisplayIDs(ArrayList<String> taskIDs) {
        if (taskIDs == null) { return null; }
        
        ArrayList<String> invalidIDs = new ArrayList<String>();

        ListIterator<String> li = taskIDs.listIterator();
        while (li.hasNext()) {
            String taskID = li.next();
            if (!this.idMapping.containsKey(taskID)) { // Invalid id...
                invalidIDs.add(taskID);
            }
        }
        
        if (invalidIDs.size() == 0) { return null; }
        return invalidIDs;
    }
    
    /**
     * Clears the ID mapping hashtable.
     */
    public void clearIDMapping() { this.idMapping.clear(); }
    
}
