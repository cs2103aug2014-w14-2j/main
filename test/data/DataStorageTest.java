package data;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import task.Task;
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
        assertEquals(true, fileExists);
    }
    
    //@author A0115864B
    @Test
    public void testRetrieveTasks() {
        test = new DataStorage("testRetrieveTasks.json");
        test.initiateFile();
        ArrayList<Task> tasks = test.retrieveTasks();
        assertEquals(1, tasks.size());
        
        Task task = tasks.get(0);
        assertEquals("submit WaveWave v0.5", task.getDescription());
        assertEquals("2014-11-10T23:59:00.000+08:00", task.getEndDate().toString());
        assertEquals(false, task.isCompleted());
        assertEquals(1, task.getPriority());
        assertEquals(null, task.getDate());
    }
    
    //@author A0115864B
    @Test
    public void testSaveTasks() {
        test = new DataStorage("testSaveTasks.json");
        Task task = new Task();
        task.setDescription("CS2103T software demo");
        DateTime date = formatter.parseDateTime("12/11/2014 14:00:00");
        task.setDate(date);
        task.setPriority(1);
        task.setCompleted(false);
        
        ArrayList<Task> list = new ArrayList<Task>();
        list.add(task);
        test.saveTasks(list);
        
        byte b1 = 0;
        byte b2 = 1;
        try {
            FileInputStream f1 = new FileInputStream("testSaveTasks.json");
            FileInputStream f2 = new FileInputStream("testSaveTasksExpected.json");
            DataInputStream d1 = new DataInputStream(f1);
            DataInputStream d2 = new DataInputStream(f2);
            b1 = d1.readByte();
            b2 = d2.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertEquals(b1, b2);
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
        
        assertEquals(task.getDescription(), obj.get("Description"));
        assertEquals(task.getDate().toString(), obj.get("Date"));
        assertEquals(task.getEndDate().toString(), obj.get("End date"));
        assertEquals(1, priority);
        assertEquals(true, obj.get("Completed"));
        
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
        
        assertEquals(obj.get("Description"), task.getDescription());
        assertEquals(obj.get("End date"), task.getEndDate().toString());
        assertEquals(true, isHighPriority);
        assertEquals(true, task.isCompleted());
        
    }
    
}
