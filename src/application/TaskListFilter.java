package application;

import java.util.ArrayList;
import java.util.ListIterator;

public final class TaskListFilter {
    private static ArrayList<Task> filterTasksAboutStartDates(ArrayList<Task> tasks, boolean hasStartDate) {
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        
        ListIterator<Task> li = tasks.listIterator();
        boolean hasDate = false;
        while (li.hasNext()) {
            Task t = li.next();
            if (t == null) { continue; }
            hasDate = t.getDate() != null; // True if has date.
            if (hasDate == hasStartDate) { 
                // If has date, and we want dated, add, or
                // if has no date, and we want no date, add.
                filteredTasks.add(t);
            }
        }
        return filteredTasks;        
    }
    
    public static ArrayList<Task> filterOutTasksWithStartDates(ArrayList<Task> tasks) {
        return filterTasksAboutStartDates(tasks, false); // This keeps tasks without start dates.
    }
    
    public static ArrayList<Task> filterOutTasksWithoutStartDates(ArrayList<Task> tasks) {
        return filterTasksAboutStartDates(tasks, true); // This keeps tasks with start dates.
    }
    
    private static ArrayList<Task> filterTasksAboutEndDates(ArrayList<Task> tasks, boolean hasEndDate) {
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        
        ListIterator<Task> li = tasks.listIterator();
        boolean hasDate = false;
        while (li.hasNext()) {
            Task t = li.next();
            if (t == null) { continue; }
            hasDate = t.getEndDate() != null; // True if has date.
            if (hasDate == hasEndDate) { 
                // If has date, and we want dated, add, or
                // if has no date, and we want no date, add.
                filteredTasks.add(t);
            }
        }
        return filteredTasks;        
    }
    
    public static ArrayList<Task> filterOutTasksWithEndDates(ArrayList<Task> tasks) {
        return filterTasksAboutEndDates(tasks, false); // This keeps tasks without start dates.
    }
    
    public static ArrayList<Task> filterOutTasksWithoutEndDates(ArrayList<Task> tasks) {
        return filterTasksAboutEndDates(tasks, true); // This keeps tasks with start dates.
    }

    private static ArrayList<Task> filterTasksAboutCompleted(ArrayList<Task> tasks, boolean isCompleted) {
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        
        ListIterator<Task> li = tasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t == null) { continue; }
            if (t.isCompleted() == isCompleted) {
                // If is completed, we want completed, add, or
                // if not completed, and we want not completed, add.
                filteredTasks.add(t);
            }
        }
        return filteredTasks;        
    }
    
    public static ArrayList<Task> filterOutCompletedTasks(ArrayList<Task> tasks) {
        return filterTasksAboutCompleted(tasks, false); // This keeps tasks that are not completed.
    }
    
    public static ArrayList<Task> filterOutNotCompletedTasks(ArrayList<Task> tasks) {
        return filterTasksAboutCompleted(tasks, true); // This keeps tasks that are completed.
    }
    
    public static ArrayList<Task> filterOutNullTasks(ArrayList<Task> tasks) {
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        
        ListIterator<Task> li = tasks.listIterator();
        while (li.hasNext()) {
            Task t = li.next();
            if (t != null) { // Task is not null, we keep.
                filteredTasks.add(t);
            }
        }
        return filteredTasks;
    }
}