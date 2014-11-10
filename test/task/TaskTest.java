/**
 * 
 */
package task;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import task.Task;

//@author A0110546R
/**
 *
 */
public class TaskTest {
    private Task task;

    @Before
    public void setUp() {
        task = new Task();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test method for {@link task.Task#getID()}.
     */
    @Test
    public void testGetId() {
        assertNotNull(task.getID());
    }
    
    /**
     * Test method for {@link task.Task#displayID()}.
     */
    @Test
    public void testDisplayId() {
        assertNull("has null displayID", task.getDisplayID());
        
        String displayID = "F1";
        task.setDisplayID(displayID);
        assertEquals("has set displayID", displayID, task.getDisplayID());
    }

    /**
     * Test method for {@link task.Task#description}.
     */
    @Test
    public void testDescription() {
        assertNull("has null description", task.getDescription());
        
        String description = "description";
        task.setDescription(description);
        assertEquals("has set description", description, task.getDescription());
    }

    /**
     * Test method for {@link task.Task#date}.
     */
    @Test
    public void testDate() {
        assertNull("has null date", task.getDate());
        
        DateTime date = new DateTime(2014, 10, 11, 12, 44, 0);
        task.setDate(date);
        assertEquals("has set date", date, task.getDate());
        
        task.setDate(null);
        assertNull("has set null date", task.getDate());
    }

    /**
     * Test method for {@link task.Task#endDate}.
     */
    @Test
    public void testEndDate() {
        assertNull("has null endDate", task.getEndDate());
        
        DateTime endDate = new DateTime(2014, 10, 11, 12, 44, 0);
        task.setEndDate(endDate);
        assertEquals("has set endDate", endDate, task.getEndDate());
        
        task.setEndDate(null);
        assertNull("has set null endDate", task.getEndDate());
    }

    /**
     * Test method for {@link task.Task#completed}.
     */
    @Test
    public void testCompleted() {
        assertFalse("is not completed", task.isCompleted());
        
        task.complete();
        assertTrue("is completed", task.isCompleted());
        
        task.setCompleted(false);
        assertFalse("is not completed again", task.isCompleted());
    }

    /**
     * Test method for {@link task.Task#priority}.
     */
    @Test
    public void testPriority() {
        assertEquals("has no priority", task.getPriority(), 0);
        
        int priority = 1;
        task.setPriority(priority);
        assertEquals("has priority", priority, task.getPriority());        
    }

}
