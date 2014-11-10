package application;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import Task.Task;
import data.DataStorage;

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
        
    }
    
}
