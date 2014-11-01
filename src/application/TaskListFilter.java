package application;

import java.util.ArrayList;
import java.util.ListIterator;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

//@author A0110546R
interface TaskFilter {
    public boolean apply(Task t);
}

class KeepTasksCompleted implements TaskFilter {
    public boolean apply(Task t) {
        return t.isCompleted();
    }
}

class KeepTasksCompletedToday implements TaskFilter {
    private DateTime oneDayAgo;
    public KeepTasksCompletedToday() {
        this.oneDayAgo = new DateTime().minusDays(1);
    }
    
    public boolean apply(Task t) {
        return t.getCompletedAt() != null && t.getCompletedAt().isAfter(this.oneDayAgo);
    }
}

class KeepTasksToShowToday implements TaskFilter {
    private LocalDate today;
    public KeepTasksToShowToday() {
        this.today = new LocalDate();
    }
    
    public boolean apply(Task t) {
        LocalDate date = new LocalDate(t.getDate());
        LocalDate endDate = new LocalDate(t.getEndDate());
        
        if (date.equals(this.today)) {
            return true;
        }
        // If start date is before today, and end date is today or after,
        else if (date.isBefore(this.today) &&
                (endDate.equals(this.today) || endDate.isAfter(this.today))) {
            return true;
        }
        return false;
    }
}

class KeepTasksWithStartDate implements TaskFilter {
    public boolean apply(Task t) {
        return t.getDate() != null; // True if there is start date.
    }
}

class KeepTasksWithoutStartDate implements TaskFilter {
    public boolean apply(Task t) {
        return t.getDate() == null; // True if there is no start date.
    }
}

class IgnoreTasksDeleted implements TaskFilter { // Keep tasks which are not deleted.
    public boolean apply(Task t) {
        return !t.isDeleted(); // True if not deleted. 
    }
}

class KeepTasksNotCompleted implements TaskFilter {
    public boolean apply(Task t) {
        return !t.isCompleted();
    }
}

public class TaskListFilter {
    private ArrayList<TaskFilter> filters;
    private ArrayList<Task> taskList;
    private boolean strongFilter; // true for AND/&&, false for OR/||.
    
    public TaskListFilter(ArrayList<Task> taskList, boolean strongFilter) {
        this.filters = new ArrayList<TaskFilter>();
        this.taskList = taskList;
        this.strongFilter = strongFilter;
    }
    
    public void add(TaskFilter filter) {
        this.filters.add(filter);
    }
    
    public ArrayList<Task> apply() {
        ArrayList<Task> filteredTaskList = new ArrayList<Task>();
        ListIterator<Task> taskI = this.taskList.listIterator();
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



