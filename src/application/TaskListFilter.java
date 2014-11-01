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
    @Override
    public boolean apply(Task t) {
        return t.isCompleted();
    }
}

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

class KeepTasksToShowToday implements TaskFilter {
    private LocalDate today;
    public KeepTasksToShowToday() {
        this.today = new LocalDate();
    }
    
    @Override
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

class KeepTasksToShowTheNextDay implements TaskFilter {
    private LocalDate today;
    private LocalDate nextDay;
    public KeepTasksToShowTheNextDay() {
        this.today = new LocalDate();
    }
    @Override
    public boolean apply(Task t) {
        LocalDate date = new LocalDate(t.getDate());
        if (date.isAfter(this.today) && this.nextDay == null) { // Different date,
            this.nextDay = date; // Set the next day's date.
        }
        if (this.nextDay != null && date.equals(this.nextDay)) { // Same as next day,
            return true; // Show the task/
        }
        return false;
    }
}

class KeepTasksWithStartDate implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.getDate() != null; // True if there is start date.
    }
}

class KeepTasksWithoutStartDate implements TaskFilter {
    @Override
    public boolean apply(Task t) {
        return t.getDate() == null; // True if there is no start date.
    }
}

class IgnoreTasksDeleted implements TaskFilter { // Keep tasks which are not deleted.
    @Override
    public boolean apply(Task t) {
        return !t.isDeleted(); // True if not deleted. 
    }
}

class KeepTasksNotCompleted implements TaskFilter {
    @Override
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



