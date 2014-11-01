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
     * @param commandInfo of type "delete" and contains task id(s).
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
            this.list.get(taskId).setDeleted(true);
        }

        return this.list;
    }
    
    /**
     * Completes a task in the list.
     * 
     * @param commandInfo of type "complete" and contains task id(s).
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
     * @param commandInfo of type "undo".
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
     * Displays completed tasks. (temporary)
     * 
     * @param commandInfo of type "search complete" and contains task id.
     * @return the updated list of tasks.
     * @throws MismatchedCommandException if not of type "search complete"
     */
    public ArrayList<Task> display(CommandInfo commandInfo) throws MismatchedCommandException {
        if (!"search complete".equals(commandInfo.getCommandType())) {
            throw new MismatchedCommandException();
        }
        
        return this.list;
    }

    /**
     * Returns the full list of tasks, ignoring the deleted tasks.
     * 
     * @return the full list of tasks, ignoring the deleted tasks.
     */
    public ArrayList<Task> getList() {
        TaskListFilter filter = new TaskListFilter(this.list, true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted());
        return filter.apply();
    }
    
    /**
     * Returns the tasks without start dates.
     * 
     * @return the tasks without start dates.
     */
    public ArrayList<Task> getTasks() {
        TaskListFilter filter = new TaskListFilter(this.list, true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted()); // and,
        filter.add(new KeepTasksWithoutStartDate());
        ArrayList<Task> filteredTasks = filter.apply();
        
        filter = new TaskListFilter(filteredTasks, false); // Does a OR/|| filtering.
        filter.add(new KeepTasksCompletedToday()); // or,
        filter.add(new KeepTasksNotCompleted());
        filteredTasks = filter.apply();
                
        // Sort by modified at date first, then priority.
        Collections.sort(filteredTasks, new ModifiedAtComparator());
        Collections.sort(filteredTasks, new EndDateComparator());
        Collections.sort(filteredTasks, new PriorityComparator());
        Collections.sort(filteredTasks, new CompletedAtComparator());
        
        int i = 1;
        ListIterator<Task> li = filteredTasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = NORMAL_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return filteredTasks;
    }
    
    /**
     * Returns the tasks with start dates.
     * 
     * @return the tasks with start dates.
     */
    public ArrayList<Task> getReminders() {
        TaskListFilter filter = new TaskListFilter(this.list, true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted()); // and,
        filter.add(new KeepTasksWithStartDate());
        ArrayList<Task> filteredTasks = filter.apply();
        
        filter = new TaskListFilter(filteredTasks, false); // Does a OR/|| filtering.
        filter.add(new KeepTasksCompletedToday()); // or,
        filter.add(new KeepTasksToShowToday()); // or,
        filter.add(new KeepTasksToShowTheNextDay());
        filteredTasks = filter.apply();

        Collections.sort(filteredTasks, new DayPriorityComparator());
        
        int i = 1;
        ListIterator<Task> li = filteredTasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = DATED_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return filteredTasks;
    }
    
    /**
     * Returns the completed tasks without start dates.
     * 
     * @return the completed tasks without start dates.
     */
    public ArrayList<Task> getCompletedTasks() {
        TaskListFilter filter = new TaskListFilter(this.list, true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted()); // and,
        filter.add(new KeepTasksWithoutStartDate()); // and,
        filter.add(new KeepTasksCompleted());
        ArrayList<Task> filteredTasks = filter.apply();
        
        // Sort by modified at date first, then priority.
        Collections.sort(filteredTasks, new ModifiedAtComparator());
        Collections.sort(filteredTasks, new PriorityComparator());
        
        int i = 1;
        ListIterator<Task> li = filteredTasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = NORMAL_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return filteredTasks;
    }
    
    /**
     * Returns the completed tasks with start dates.
     * 
     * @return the completed tasks with start dates.
     */
    public ArrayList<Task> getCompletedReminders() {
        TaskListFilter filter = new TaskListFilter(this.list, true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted()); // and, 
        filter.add(new KeepTasksWithStartDate()); // and,
        filter.add(new KeepTasksCompleted());
        ArrayList<Task> filteredTasks = filter.apply();

        Collections.sort(filteredTasks, new DayPriorityComparator());
        
        int i = 1;
        ListIterator<Task> li = filteredTasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = DATED_TASK_PREFIX + "" + i;
            this.idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return filteredTasks;
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
