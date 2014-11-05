package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ListIterator;

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
    
    public void addComparator(Comparator<Task> comparator) {
        this.additionalComparators.add(comparator);
    }
    
    public void addFilter(TaskFilter filter) {
        this.additionalFilter.add(filter);
    }
    
    public void replaceFilter(TaskListFilter filter) {
        this.additionalFilter = filter;
    }
    
    public void replaceComparators(ArrayList<Comparator<Task>> comparators) { 
        this.additionalComparators = comparators;
    }
    
    private static void runComparators(ArrayList<Comparator<Task>> comparators, ArrayList<Task> taskList) {
        ListIterator<Comparator<Task>> li = comparators.listIterator();
        while (li.hasNext()) {
            Collections.sort(taskList, li.next());
        }
    }
    
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