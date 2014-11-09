package Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ListIterator;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import application.ConfigManager;
import Parser.CommandInfo;

//@author A0110546R
/**
 * The manager that manipulates and contains the array list of Tasks.
 * 
 * @author Sun Wang Jun
 */
public class TaskManager {
    public static final char NORMAL_TASK_PREFIX = 'T';
    public static final char DATED_TASK_PREFIX = 'E';
    public static final int DEFAULT_DAYS_TO_DISPLAY = 3;
    
    private ArrayList<Task> list;
    private Task lastModifiedTask;
    private Hashtable<String, Integer> idMapping;
    
    private ListDisplay eventsDisplay;
    private ListDisplay tasksDisplay;
    
    private int daysToDisplay = DEFAULT_DAYS_TO_DISPLAY;
    
    /**
     * Initializes and sets up the task manager.
     */
    public TaskManager() {
        this.idMapping = new Hashtable<String, Integer>();
        this.eventsDisplay = new EventListDisplay();
        this.tasksDisplay = new TaskListDisplay();
    }
    
    /**
     * Sets the number of days to display in the default view.
     * @param days number of days.
     */
    public void setDaysToDisplay(int days) {
        this.daysToDisplay = days;
    }
    
    /**
     * Sets the number of days to display in the default view.
     * @param commandInfo Use start and end time to determine the number of days.
     * @param configManager The config manager to update and save the information to.
     */
    public void setDaysToDisplay(CommandInfo commandInfo, ConfigManager configManager) {
        LocalDate today = new LocalDate();
        LocalDate startDay = new LocalDate();
        if (commandInfo.getStartDateTime() != null) {
            startDay = commandInfo.getStartDateTime().toLocalDate();
        }
        if (startDay.equals(today) && commandInfo.getEndDateTime() != null) {
            LocalDate endDay = commandInfo.getEndDateTime().toLocalDate();
            this.daysToDisplay = Days.daysBetween(startDay, endDay).getDays();
            configManager.setHomeViewType(this.daysToDisplay);
        }
    }
    
    /**
     * Checks if the task displayID entered is valid.
     * 
     * @param displayID the task displayID the user entered.
     * @return whether it exists in idMapping.
     */
    public boolean ensureValidDisplayID(String displayID) {
        return this.idMapping.containsKey(displayID);
    }
    
    /**
     * Used to map displayID to actual taskID.
     * 
     * @param displayID the display ID that is shown in the list view.
     * @return taskID the internal ID of the Task.
     */
    private int mapDisplayIDtoActualID(String displayID) {
        assert(this.idMapping.containsKey(displayID) == true);
        return this.idMapping.get(displayID);
    }
    

    /**
     * Initializes the list of tasks from storage.
     * 
     * @param storedList ArrayList of tasks.
     * @return the initialized list of tasks.
     */
    public ArrayList<Task> initializeList(ArrayList<Task> storedList) {
        list = new ArrayList<Task>(storedList);
        Task.resetIDCounter(list.size());
        return this.list;
    }
    
    /**
     * Returns the last task modified.
     * @return the last task modified.
     */    
    public Task getLastModifiedTask() {
        return this.lastModifiedTask;
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
    
    /**
     * Returns the Task associated with this displayID.
     * @param displayID the displayID of the task.
     * @throws IllegalArgumentException if the displayID is not displayed in the views.
     */
    public Task getTaskFromDisplayID(String displayID) throws IllegalArgumentException {
        if (!this.ensureValidDisplayID(displayID)) {
            throw new IllegalArgumentException("There is no such display ID.");
        }
        return this.list.get(this.mapDisplayIDtoActualID(displayID));
    }
    
    /**
     * Adds a task to the list.
     * 
     * @param commandInfo of type "add".
     */
    public void add(CommandInfo commandInfo) {
        assert(commandInfo.getCommandType() == "add");
        this.lastModifiedTask = new Task(commandInfo);
        this.list.add(this.lastModifiedTask);
    }

    /**
     * Edits a task in the list.
     * 
     * @param commandInfo of type "edit" and contains task id.
     */
    public void edit(CommandInfo commandInfo) {  
        assert(commandInfo.getCommandType() == "edit");
        int taskId = this.mapDisplayIDtoActualID(commandInfo.getTaskIDs().get(0));       
        this.lastModifiedTask = new Task(commandInfo, taskId);
        
        this.list.set(taskId, this.lastModifiedTask); // Replaces the task.
    }

    /**
     * Deletes a task in the list.
     * 
     * @param commandInfo of type "delete" and contains task id(s).
     */
    public void delete(CommandInfo commandInfo) {
        assert(commandInfo.getCommandType() == "delete");
        ListIterator<String> li = commandInfo.getTaskIDs().listIterator();
        while (li.hasNext()) {
            String displayID = li.next();
            int taskId = this.mapDisplayIDtoActualID(displayID);
            this.list.get(taskId).setDeleted(true);
        }
    }
    
    /**
     * Completes a task in the list.
     * 
     * @param commandInfo of type "complete" and contains task id(s).
     */
    public void complete(CommandInfo commandInfo) {   
        assert(commandInfo.getCommandType() == "complete");
        ListIterator<String> li = commandInfo.getTaskIDs().listIterator();
        while (li.hasNext()) {
            String displayID = li.next();
            int taskId = this.mapDisplayIDtoActualID(displayID);
            this.list.get(taskId).complete(); // "Soft-delete" in the ArrayList.
        }
    }

    /**
     * Undos one previous commandInfo.
     * 
     * @param commandInfo of type "undo".
     * @param backup the backup task list.
     */
    public void undo(CommandInfo commandInfo, ArrayList<Task> backup) {
        assert(commandInfo.getCommandType() == "undo");
        this.list = new ArrayList<Task>(backup);
    }
    
    /**
     * Returns the full list of tasks, including the deleted tasks.
     * @return the full list of tasks, including the deleted tasks.
     */
    public ArrayList<Task> getFullList() {
        return this.list;
    }

    /**
     * Returns the full list of tasks, ignoring the deleted tasks.
     * 
     * @return the full list of tasks, ignoring the deleted tasks.
     */
    public ArrayList<Task> getSanitizedList() {
        TaskListFilter filter = new TaskListFilter(true); // Does a AND/&& filtering.
        filter.add(new IgnoreTasksDeleted());
        return filter.apply(this.list);
    }
    
    /**
     * Returns the tasks without start dates.
     * 
     * @return the tasks without start dates.
     */
    public ArrayList<Task> getTasks() {
        TaskListFilter filter = new TaskListFilter(false); // Does a OR/|| filtering.
        filter.add(new KeepTasksCompletedToday()); // or,
        filter.add(new KeepReminders());
        filter.add(new KeepTasksBetween(this.daysToDisplay));
        this.tasksDisplay.replaceFilter(filter);
        
        return this.tasksDisplay.display(this.list, this.idMapping);
    }
    
    /**
     * Returns the tasks with start dates.
     * 
     * @return the tasks with start dates.
     */
    public ArrayList<Task> getEvents() {
        TaskListFilter filter = new TaskListFilter(false); // Does a OR/|| filtering.
        filter.add(new KeepTasksCompletedToday()); // or,
        filter.add(new KeepTasksOutstanding());
        filter.add(new KeepTasksBetween(this.daysToDisplay));
        this.eventsDisplay.replaceFilter(filter);
        
        return this.eventsDisplay.display(this.list, this.idMapping);
    }
    
    /**
     * Returns the tasks given in the search parameters.
     * 
     * @return the tasks given in the search parameters.
     */
    public ArrayList<Task> getSearchedTasks(CommandInfo commandInfo) {        
        ArrayList<Comparator<Task>> comparators = new ArrayList<Comparator<Task>>();        
        TaskListFilter filter = new TaskListFilter(true); // AND filter.
        
        // Filtering of dates:
        DateTime start = commandInfo.getStartDateTime();
        DateTime end = commandInfo.getEndDateTime();
        if (start != null && end != null) {
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new EndDateComparator());
        }
        else if (start != null) { // end is null, not possible here but whatever,
            end = start.withTimeAtStartOfDay().plusDays(1);
            start = start.withTimeAtStartOfDay().minusMillis(1); // Millisecond before today.
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new EndDateComparator());
        }
        else if (end != null) { // start is null,
            start = new DateTime();
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new EndDateComparator());
        }

        // Whether to show priority:
        if (commandInfo.getPriority() > 0) {
            filter.add(new KeepTasksWithPriority());
        }
        
        // Whether to show completed only:
        if (commandInfo.isCompleted()) { // For completed tasks only.
            filter.add(new KeepTasksCompleted());
            comparators.add(new CompletedAtComparator());
        }
        
        // Searching by keywords:
        if (commandInfo.getTaskDesc() != null) {
            filter.add(new KeepTasksWithKeyword(commandInfo.getTaskDesc()));
        }
        
        this.tasksDisplay.replaceFilter(filter);
        this.tasksDisplay.replaceComparators(comparators);
        
        return this.tasksDisplay.display(this.list, this.idMapping);
    }
    
    /**
     * Returns the events given in the search parameters.
     * 
     * @return the events given in the search parameters.
     */
    public ArrayList<Task> getSearchedEvents(CommandInfo commandInfo) {
        ArrayList<Comparator<Task>> comparators = new ArrayList<Comparator<Task>>();
        TaskListFilter filter = new TaskListFilter(true); // AND filter.

        // Whether to show completed only:
        if (commandInfo.isCompleted()) { // For completed tasks only.
            filter.add(new KeepTasksCompleted());
            comparators.add(new CompletedAtComparator());
        }
        
        // Whether to show priority, inclusive:
        if (commandInfo.getPriority() > 0) {
            filter.add(new KeepTasksWithPriority());
        }
        
        // Filtering of dates:
        DateTime start = commandInfo.getStartDateTime();
        DateTime end = commandInfo.getEndDateTime();
        if (start != null && end != null) {
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new DateComparator());
        }
        else if (start != null) { // end is null,
            end = start.withTimeAtStartOfDay().plusDays(1);
            start = start.withTimeAtStartOfDay().minusMillis(1); // Millisecond before today.
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new DateComparator());
        }
        else if (end != null) { // start is null, not possible here but whatever,
            start = new DateTime();
            filter.add(new KeepTasksBetween(start, end));
            comparators.add(new DateComparator());
        }
        
        // Searching by keywords:
        if (commandInfo.getTaskDesc() != null) {
            filter.add(new KeepTasksWithKeyword(commandInfo.getTaskDesc()));
        }
        
        this.eventsDisplay.replaceFilter(filter);
        this.eventsDisplay.replaceComparators(comparators);
        
        return this.eventsDisplay.display(this.list, this.idMapping);
    }
    
}
