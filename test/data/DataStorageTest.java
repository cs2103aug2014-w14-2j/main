package data;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import Task.Task;
import data.DataStorage;

//@author A0115864B
/**
 * 
 * @author Kim Hyung Jon
 *
 */
public class DataStorageTest {
    
    DataStorage test;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

    //@author A0115864B
    @Test
    public void testInitiateFile() {
        test = new DataStorage("testInitiateFile.json");
        test.initiateFile();
        boolean fileExists = false;
        File storage = new File("testInitiateFile.json");
        if(storage.isFile() && storage.exists()) {
            fileExists = true;
        }
        // After initiateFile() is called, external storage must exist
        assertEquals(fileExists, true);
    }
    
    //@author A0115864B
    @Test
    public void testRetrieveTasks() {
        test = new DataStorage("testRetrieveTasks.json");
        test.initiateFile();
        ArrayList<Task> tasks = test.retrieveTasks();
        assertEquals(tasks.size(), 1);
        
        Task task = tasks.get(0);
        assertEquals(task.getDescription(), "submit WaveWave v0.5");
        assertEquals(task.getEndDate().toString(), "2014-11-10T23:59:00.000+08:00");
        assertEquals(task.isCompleted(), false);
        assertEquals(task.getPriority(), 1);
        assertEquals(task.getDate(), null);
    }
    
    //@author A0115864B
    @Test
    public void testConvert1() {
        test = new DataStorage();
        
        Task task = new Task();
        task.setDescription("CS2103T lecture");
        DateTime date = formatter.parseDateTime("24/10/2014 14:00:00");
        task.setDate(date);
        DateTime end = formatter.parseDateTime("24/10/2014 16:00:00");
        task.setEndDate(end);
        task.setPriority(1);
        task.setCompleted(true);
        
        JSONObject obj = test.convertTaskToJSONObject(task);
        
        boolean isHighPriority = (boolean) obj.get("High-priority");
        int priority;
        if (isHighPriority) {
            priority = 1;
        } else {
            priority = 0;
        }
        
        assertEquals(obj.get("Description"), task.getDescription());
        assertEquals(obj.get("Date"), task.getDate().toString());
        assertEquals(obj.get("End date"), task.getEndDate().toString());
        assertEquals(priority, 1);
        assertEquals(obj.get("Completed"), true);
        
    }
    
    //@author A0115864B
    @Test
    public void testConvert2() {
        test = new DataStorage();
        
        JSONObject obj = new JSONObject();
        obj.put("Description", "Submit project manual");
        obj.put("End date", "2014-11-10T23:59:59.999+08:00");
        obj.put("High-priority", true);
        obj.put("Completed", true);
        
        Task task = test.convertJSONObjectToTask(obj);
        
        int priority = task.getPriority();
        boolean isHighPriority;
        if (priority == 1) {
            isHighPriority = true;
        } else {
            isHighPriority = false;
        }
        
        assertEquals(task.getDescription(), obj.get("Description"));
        assertEquals(task.getEndDate().toString(), obj.get("End date"));
        assertEquals(isHighPriority, true);
        assertEquals(task.isCompleted(), true);
        
    }
    
}
