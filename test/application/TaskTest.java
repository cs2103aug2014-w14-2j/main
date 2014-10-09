/**
 * 
 */
package application;

import static org.junit.Assert.*;

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
        assertNull(task.getId());
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
     * Test method for {@link application.Task#getDate()}.
     */
    @Test
    public void testGetDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#setDate(java.util.Date)}.
     */
    @Test
    public void testSetDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#removeDate()}.
     */
    @Test
    public void testRemoveDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#getEndDate()}.
     */
    @Test
    public void testGetEndDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#setEndDate(java.util.Date)}.
     */
    @Test
    public void testSetEndDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#removeEndDate()}.
     */
    @Test
    public void testRemoveEndDate() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#isCompleted()}.
     */
    @Test
    public void testIsCompleted() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.Task#setCompleted(boolean)}.
     */
    @Test
    public void testSetCompleted() {
        fail("Not yet implemented");
    }

}
