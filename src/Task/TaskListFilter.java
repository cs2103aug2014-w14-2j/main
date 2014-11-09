package Task;

import java.util.ArrayList;
import java.util.ListIterator;

import org.joda.time.DateTime;

//@author A0110546R
interface TaskFilter {
    public boolean apply(Task t);
}

/**
 * Filter to keep completed tasks in the list. 
 * @author Sun Wang Jun
 */
class KeepTasksCompleted implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.isCompleted();
    }
}

/**
 * Filter to keep tasks which are completed in the past 24 hours. 
 * @author Sun Wang Jun
 */
class KeepTasksCompletedToday implements TaskFilter {
    private DateTime oneDayAgo;
    public KeepTasksCompletedToday() {
        this.oneDayAgo = new DateTime().minusDays(1);
    }
    
    @Override
    public boolean apply(Task t) {
        return t.getCompletedAt() != null && t.getCompletedAt().isAfter(this.oneDayAgo);
    }
}

/**
 * Filter to keep tasks which are over due. 
 * @author Sun Wang Jun
 */
class KeepTasksOutstanding implements TaskFilter {
    private DateTime now;
    public KeepTasksOutstanding() {
        this.now = new DateTime();
    }
    
    @Override
    public boolean apply(Task t) {
        if (!t.isCompleted()) {
            if (t.getEndDate() != null && t.getEndDate().isBefore(this.now)) {
                return true;
            }
            // There is no end date but only start date, and it is before now.
            else if (t.getEndDate() == null &&
                    t.getDate() != null && t.getDate().isBefore(this.now)) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Filter to keep tasks between a start and end time. 
 * @author Sun Wang Jun
 */
class KeepTasksBetween implements TaskFilter {
    private DateTime start, end;
    /**
     * This constructor uses two specific date times.
     * @param start start Datetime
     * @param end end DateTime
     * @author Sun Wang Jun
     */
    public KeepTasksBetween(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }
    /**
     * This constructor accepts a number to use the current time and number of days later.
     * @param numDays the number of days from the current time to number of days later.
     * @author Sun Wang Jun
     */
    public KeepTasksBetween(int numDays) {
        this.start = new DateTime();
        this.end = new DateTime().plusDays(numDays);
    }
    
    @Override
    public boolean apply(Task t) {
        // There is start date,
        if (t.getDate() != null) {
            // Start date is between requested start and end,
            if (t.getDate().isAfter(this.start) && t.getDate().isBefore(this.end)) {
                return true;
            }
        }
        
        // There is end date,
        if (t.getEndDate() != null) {
            // End date is between requested start and end,
            if (t.getEndDate().isAfter(this.start) && t.getEndDate().isBefore(this.end)) {
                return true;
            }
        }
        
        return false;
    }   
}

/**
 * Filter to keep tasks that contain the keyword. 
 * @author Sun Wang Jun
 */
class KeepTasksWithKeyword implements TaskFilter {
    private String keyword;

    /**
     * @param keyword the String keyword. 
     * @author Sun Wang Jun
     */
    public KeepTasksWithKeyword(String keyword) {
        this.keyword = keyword.toUpperCase();
    }
    
    @Override
    public boolean apply(Task t) {
        String uppercaseDescription = t.getDescription().toUpperCase();
        return uppercaseDescription.indexOf(this.keyword) > -1;        
    }
}

/**
 * Filter to keep tasks that have priority. 
 * @author Sun Wang Jun
 */
class KeepTasksWithPriority implements TaskFilter {    
    @Override
    public boolean apply(Task t) {
         return t.getPriority() > 0;
    }
}

/**
 * Filter to keep events.
 * @author Sun Wang Jun
 */
class KeepTasksWithStartDate implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.getDate() != null; // True if there is start date.
    }
}

/**
 * Filter to keep tasks. 
 * @author Sun Wang Jun
 */
class KeepTasksWithoutStartDate implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.getDate() == null; // True if there is no start date.
    }
}

/**
 * Filter to keep reminders (tasks without start and end time).
 * @author Sun Wang Jun
 */
class KeepReminders implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.getDate() == null && t.getEndDate() == null;
    }
}

/**
 * Filter to ignore (soft-)deleted tasks. 
 * @author Sun Wang Jun
 */
class IgnoreTasksDeleted implements TaskFilter { // Keep tasks which are not deleted.
    @Override
    public boolean apply(Task t) {
        return !t.isDeleted(); // True if not deleted. 
    }
}

/**
 * Filter to keep uncompleted tasks. 
 * @author Sun Wang Jun
 */
class KeepTasksNotCompleted implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return !t.isCompleted();
    }
}

/**
 * The main class to store and run the filters. 
 * @author Sun Wang Jun
 */
public class TaskListFilter {
    private ArrayList<TaskFilter> filters;
    private boolean strongFilter; // true for AND/&&, false for OR/||.

    /**
     * @param strongFilter true to use &&/AND filtering, false to use ||/OR filtering. 
     * @author Sun Wang Jun
     */
    public TaskListFilter(boolean strongFilter) {
        this.filters = new ArrayList<TaskFilter>();
        this.strongFilter = strongFilter;
    }

    /**
     * @param filter adds the filter into the list. 
     * @author Sun Wang Jun
     */
    public void add(TaskFilter filter) {
        this.filters.add(filter);
    }

    /**
     * Runs the filters.
     * @param taskList the ArrayList to filter.
     * @return the filtered ArrayList of tasks.
     * @author Sun Wang Jun
     */
    public ArrayList<Task> apply(ArrayList<Task> taskList) {
        ArrayList<Task> filteredTaskList = new ArrayList<Task>();
        ListIterator<Task> taskI = taskList.listIterator();
        ListIterator<TaskFilter> filterI;
        Task task;
        TaskFilter filter;
        boolean keep = this.strongFilter;
        
        while (taskI.hasNext()) {
            task = taskI.next();
            keep = this.strongFilter;
            filterI = this.filters.listIterator();
            while (filterI.hasNext()) {
                filter = filterI.next();
                if (this.strongFilter) { // Strong filter is on, do &&.
                    if (!filter.apply(task)) { // Once false,
                        keep = false; // Do not keep.
                        break; // Get out of the while loop.
                    }
                }
                else { // Strong filter is off, do ||.
                    if (filter.apply(task)) { // Once true,
                        filteredTaskList.add(task);
                        break; // Get out of the while loop.
                    }
                }
            }
            // Only add at this point if it is a strong filter and kept.
            if (this.strongFilter && keep) { filteredTaskList.add(task); }
        }
        
        return filteredTaskList;
    }
}



