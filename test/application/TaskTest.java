/**
 * 
 */
package application;

import static org.junit.Assert.*;

import org.joda.time.DateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sun Wang Jun
 *
 */
public class TaskTest {
    private Task task;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        task = new Task();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link application.Task#getId()}.
     */
    @Test
    public void testGetId() {
        assertNull(task.getID());
    }

    /**
     * Test method for {@link application.Task#description}.
     */
    @Test
    public void testDescription() {
        assertNull("has null description", task.getDescription());
        
        String description = "description";
        task.setDescription(description);
        assertEquals("has set description", task.getDescription(), description);
    }

    /**
     * Test method for {@link application.Task#date}.
     */
    @Test
    public void testDate() {
        assertNull("has null date", task.getDate());
        
        DateTime dateTime = new DateTime(2014, 10, 11, 12, 44, 0);
        task.setDate(dateTime);
        assertEquals("has set date", dateTime, task.getDate());
        
        task.setDate(null);
        assertNull("has set null date", task.getDate());
    }

    /**
     * Test method for {@link application.Task#endDate}.
     */
    @Test
    public void testEndDate() {
        assertNull("has null endDate", task.getEndDate());
        
        DateTime dateTime = new DateTime(2014, 10, 11, 12, 44, 0);
        task.setEndDate(dateTime);
        assertEquals("has set endDate", dateTime, task.getEndDate());
        
        task.setEndDate(null);
        assertNull("has set null endDate", task.getEndDate());
    }

    /**
     * Test method for {@link application.Task#completed}.
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
     * Test method for {@link application.Task#priority}.
     */
    @Test
    public void testPriority() {
        assertNull("has no priority", task.getPriority());
        
        int priority = 1;
        task.setPriority(priority);
        assertEquals("has priority", task.getPriority(), priority);        
    }

}
