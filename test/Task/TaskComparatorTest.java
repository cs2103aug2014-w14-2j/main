package Task;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Task.DateComparator;
import Task.ModifiedAtComparator;
import Task.PriorityComparator;
import Task.Task;

//@author A0110546R
public class TaskComparatorTest {

   ArrayList<Task> tasks;
   Task a, b;
   
    @Before
    public void setUp() throws Exception {
        tasks = new ArrayList<Task>();
        a = new Task();
        b = new Task();
        
        tasks.add(a);
        tasks.add(b);
        
        // a comes before b at the moment.
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testOrderInArrayList() {
        assertEquals("a is in front", a, tasks.get(0));
        assertEquals("b is behind", b, tasks.get(1));
    }

    /**
     * Tests that the start date comparator works as it should.
     */
    @Test
    public void testStartDateComparator() {
        DateTime date = new DateTime(2014, 10, 30, 12, 44, 0); // 30 October 2014, 12:44:00
        a.setDate(date);
        date = date.minusDays(1);
        b.setDate(date);
        
        Collections.sort(tasks, new DateComparator());
        
        assertEquals("b is now in front", b, tasks.get(0));
        assertEquals("a is now behind", a, tasks.get(1));
    }
    
    /**
     * Tests that the end date comparator works as it should. 
     */
    @Test
    public void testEndDateComparator() {
        DateTime date = new DateTime(2014, 10, 30, 12, 44, 0); // 30 October 2014, 12:44:00
        a.setDate(date);
        b.setDate(date);

        date = date.plusDays(3);
        a.setEndDate(date);
        date = date.minusDays(1);
        b.setEndDate(date);
        
        Collections.sort(tasks, new DateComparator());

        assertEquals("a is now behind", a, tasks.get(1));
        assertEquals("b is now in front", b, tasks.get(0));
    }
    
    /**
     * Tests that the priority comparator works as it should.
     */
    @Test
    public void testPriorityComparator() {
        a.setPriority(0);
        b.setPriority(1);
        
        Collections.sort(tasks, new PriorityComparator());
        
        assertEquals("b is now in front", b, tasks.get(0));
        assertEquals("a is now behind", a, tasks.get(1));        
    }
    
    /**
     * Tests that the modified at comparator works as it should.
     */
    @Test
    public void testModifiedAtComparator() {
        try {
            Thread.sleep(100); // So that modified at time is significantly different.
        } catch (Exception e) {}
        
        b.setPriority(0); // So that b is "updated".
        
        Collections.sort(tasks, new ModifiedAtComparator());
        
        assertEquals("b is now in front", b, tasks.get(0));
        assertEquals("a is now behind", a, tasks.get(1));        
    }

}
