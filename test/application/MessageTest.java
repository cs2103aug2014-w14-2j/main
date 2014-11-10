package application;

import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

//@author A0115864B
/**
 * Unit test for class Message and selected subclasses
 *
 */
public class MessageTest {
    
    Message message;

    //@author A0115864B
    @Test
    public void testNotifyAdd() {
        message = new MessageNotifyAdd("T2");
        assertEquals("Added as T2", message.getMessage());
    }

    //@author A0115864B
    @Test
    public void testNotifyDelete() {
        ArrayList<String> deleted = new ArrayList<String>();
        deleted.add("T1");
        deleted.add("E1");
        deleted.add("E4");
        message = new MessageNotifyDelete(deleted);
        assertEquals("Deleted T1, E1, E4", message.getMessage());
    }

    //@author A0115864B
    @Test
    public void testNotifyUndo() {
        message = new MessageNotifyUndo();
        assertEquals("Change unmade", message.getMessage());
    }

    //@author A0115864B
    @Test
    public void testWarningInvalidID() {
        ArrayList<String> failed = new ArrayList<String>();
        failed.add("T3");
        failed.add("E3");
        failed.add("E5");
        failed.add("T12");
        message = new MessageWarningInvalidID(failed);
        assertEquals("Cannot find tasks T3, E3, E5, T12", message.getMessage());
    }

}
