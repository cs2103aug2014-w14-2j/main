package Task;

import java.util.Comparator;

import org.joda.time.LocalDate;

//@author A0110546R
/**
 * The comparator class used to sort Tasks by their date.
 * 
 * @author Sun Wang Jun
 */
class DateComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getDate().isAfter(b.getDate())) {
            return 1; // a is after b, so a comes after b.
        } else if (a.getDate().isBefore(b.getDate())) {
            return -1; // a is before b, so a comes before b.
        } else {
            if (a.getEndDate() == null && b.getEndDate() == null) { // Untested.
                return 0; // both a and b has no end date, do nothing.
            } else if (a.getEndDate() == null) { // Untested.
                return 1; // a has no end date, b has end date, so a comes after b. 
            } else if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, so a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;
    }
}

//@author A0110546R
/**
 * The comparator class used to sort Tasks by their end date.
 * @author Sun Wang Jun
 *
 */
class EndDateComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getEndDate() == null && b.getEndDate() == null) {
            return 0;
        } else if (a.getEndDate() == null) {
            return 1; // b has end date, so a comes after b.
        } else if (b.getEndDate() == null) {
            return -1; // a has end date, so a comes before b.
        } else { // Both a and b has end date,
            if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;
    }
}

//@author A0110546R
/**
 * The comparator class used to sort Tasks by their completed at date.
 * @author Sun Wang Jun
 * 
 */
class CompletedAtComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getCompletedAt() == null && b.getCompletedAt() == null) {
            return 0;
        } else if (a.getCompletedAt() == null) {
            return -1; // b has completed date, so a comes before b.
        } else if (b.getCompletedAt() == null) {
            return 1; // a has completed date, so a comes after b.
        } else { // Both a and b has end date,
            if (a.getCompletedAt().isAfter(b.getCompletedAt())) {
                return 1; // a is after b, a comes after b.
            } else if (a.getCompletedAt().isBefore(b.getCompletedAt())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;
    }
}

//@author A0110546R
/**
 * The comparator class used to sort Tasks by their created at date.
 * 
 * @author Sun Wang Jun
 */
class ModifiedAtComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        if (a.getModifiedAt().isAfter(b.getModifiedAt())) {
            return -1; // a is after b, so a comes after b.
        } else if (a.getModifiedAt().isBefore(b.getModifiedAt())) {
            return 1; // a is before b, so a comes before b.
        }
        return 0;
    }
}

//@author A0110546R
/**
 * The comparator class used to sort Tasks by their priority.
 * 
 * @author Sun Wang Jun
 */
class PriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        // a is greater priority, a should be before b.
        return b.getPriority() - a.getPriority();
    }
}

//@author A0110546R
/**
 * The comparator class used to sort Tasks by days and within each day, by priority.
 * 
 * @author Sun Wang Jun
 */
class DayPriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task a, Task b) {
        LocalDate ldtA = a.getDate().toLocalDate();
        LocalDate ldtB = b.getDate().toLocalDate();
        
        if (ldtA.isAfter(ldtB)) {
            return 1; // a is after b, so a comes after b.
        } else if (ldtA.isBefore(ldtB)) {
            return -1; // a is before b, so a comes before b.
        }

        else { // Same day, so sort by priority:
            
            if (a.getPriority() > b.getPriority()) {
                return -1; // a has greater priority, so a comes before b.
            } else if (a.getPriority() < b.getPriority()) {
                return 1; // a has smaller priority, so a comes after b.
            }
            
            // Else, priority is equal, so sort by end date:
            else if (a.getEndDate() == null && b.getEndDate() == null) {
                return 0; // both a and b has no end date, do nothing.
            } else if (a.getEndDate() == null) { // Untested.
                return 1; // a has no end date, b has end date, so a comes after b. 
            } else if (a.getEndDate().isAfter(b.getEndDate())) {
                return 1; // a is after b, so a comes after b.
            } else if (a.getEndDate().isBefore(b.getEndDate())) {
                return -1; // a is before b, so a comes before b.
            }
        }
        return 0;        
    }
}
