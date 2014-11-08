package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ListIterator;

//@author A0110546R
/**
 * The list display class.
 * @author Sun Wang Jun
 *
 */
public class ListDisplay {
    protected char TASK_PREFIX = ' ';
    
    protected ArrayList<Comparator<Task>> defaultComparators;
    protected TaskListFilter defaultFilter;
    
    protected ArrayList<Comparator<Task>> additionalComparators;
    protected TaskListFilter additionalFilter;
    
    public ListDisplay() {
        this.defaultComparators = new ArrayList<Comparator<Task>>();
        this.defaultFilter = new TaskListFilter(true); // Does a AND/&& filtering.
        this.additionalComparators = new ArrayList<Comparator<Task>>();
        this.additionalFilter = new TaskListFilter(false);
    }
    
    /**
     * Adds a non-default comparator.
     * @param comparator Comparator<Task>
     */
    public void addComparator(Comparator<Task> comparator) {
        this.additionalComparators.add(comparator);
    }
    
    /**
     * Adds a non-default filter.
     * @param filter TaskFilter
     */
    public void addFilter(TaskFilter filter) {
        this.additionalFilter.add(filter);
    }
    
    /**
     * Replaces the entire task list filter.
     * @param filter TaskListFilter
     */
    public void replaceFilter(TaskListFilter filter) {
        this.additionalFilter = filter;
    }
    
    /**
     * Replaces the entire comparators list.
     * @param comparators ArrayList of Comparator<Task>
     */
    public void replaceComparators(ArrayList<Comparator<Task>> comparators) { 
        this.additionalComparators = comparators;
    }
    
    private static void runComparators(ArrayList<Comparator<Task>> comparators, ArrayList<Task> taskList) {
        ListIterator<Comparator<Task>> li = comparators.listIterator();
        while (li.hasNext()) {
            Collections.sort(taskList, li.next());
        }
    }
    
    /**
     * First filters and sorts using the default filters and comparators, if any.
     * Then filters and sorts using the additional filters and comparators, if any.
     * 
     * @param taskList the list to filter and sort.
     * @param idMapping the Hashtable that maps displayID to internal ID.
     * @return a filtered and sorted ArrayList<Task> 
     */
    public ArrayList<Task> display(ArrayList<Task> taskList, Hashtable<String, Integer> idMapping) {
        ArrayList<Task> filteredTasks = this.defaultFilter.apply(taskList);
        runComparators(this.defaultComparators, filteredTasks);
        
        filteredTasks = this.additionalFilter.apply(filteredTasks);
        runComparators(this.additionalComparators, filteredTasks);

        int i = 1;
        ListIterator<Task> li = filteredTasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            String displayID = this.TASK_PREFIX + "" + i;
            idMapping.put(displayID, t.getID());
            t.setDisplayID(displayID);
            i++;
        }
        
        return filteredTasks;
    }
    
}

/**
 * Events display filters to keep tasks with start dates.
 * Sorts primarily by day, then priority and completion status.
 * @author Sun Wang Jun
 */
class EventListDisplay extends ListDisplay {    
    public EventListDisplay() {
        super();
        this.TASK_PREFIX = TaskManager.DATED_TASK_PREFIX;
        
        this.defaultFilter.add(new IgnoreTasksDeleted()); // and,
        this.defaultFilter.add(new KeepTasksWithStartDate());
        
        this.defaultComparators.add(new CompletedAtComparator());
        this.defaultComparators.add(new DayPriorityComparator());
    }
}

/**
 * Tasks display filters to keep tasks without start dates.
 * Sorts primarily by completion status, then end dates, then priority and last modified.
 * @author Sun Wang Jun
 */
class TaskListDisplay extends ListDisplay {    
    public TaskListDisplay() {
        super();
        this.TASK_PREFIX = TaskManager.NORMAL_TASK_PREFIX;
        
        this.defaultFilter.add(new IgnoreTasksDeleted()); // and,
        this.defaultFilter.add(new KeepTasksWithoutStartDate());
        
        this.defaultComparators.add(new ModifiedAtComparator());
        this.defaultComparators.add(new PriorityComparator());
        this.defaultComparators.add(new EndDateComparator());
        this.defaultComparators.add(new CompletedAtComparator());
    }
}