package application;

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

import data.DataStorage;
import Task.Task;

/**
 * 
 * @author Kim Hyung Jon (matric number: A0115864B)
 * JUnit test file for test-driven development of DataStorage class
 * The test cases do not deal with all fields
 *
 */

public class DataTest {
	
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
		ArrayList<Task> arrayList = test.retrieveTasks();
		
		// Deadline task
		Task task1 = arrayList.get(0);
		assertEquals(task1.getDescription(), "Birthday party preparation");
		assertEquals(task1.getDate().toString(), "2015-01-16T01:34:23.000+08:00");
		
		// Timed task
		Task task2 = arrayList.get(1);
		assertEquals(task2.getDescription(), "CS2103T tutorial");
		assertEquals(task2.getDate().toString(), "2014-10-15T14:00:00.000+08:00");
		assertEquals(task2.getEndDate().toString(), "2014-10-15T15:00:00.000+08:00");
		
		// Floating task
		Task task3 = arrayList.get(2);
		assertEquals(task3.getDescription(), "Remember to feed dog");
		
	}
	
	//@author A0115864B
	@Test
	public void testSaveTasks() {
		test = new DataStorage("testSaveTasks.json");
		test.initiateFile();
		
		ArrayList<Task> testArrayList = new ArrayList<Task>();
		
		// Deadline task
		Task task1 = new Task();
		task1.setDescription("Birthday party preparation");
		DateTime date1 = formatter.parseDateTime("16/01/2015 01:34:23");
		task1.setDate(date1);
		
		// Timed task
		Task task2 = new Task();
		task2.setDescription("CS2103T tutorial");
		DateTime date2 = formatter.parseDateTime("15/10/2014 14:00:00");
		task2.setDate(date2);
		DateTime end2 = formatter.parseDateTime("15/10/2014 15:00:00");
		task2.setEndDate(end2);
		
		// Floating task
		Task task3 = new Task();
		task3.setDescription("Remember to feed dog");
		testArrayList.add(task1);
		testArrayList.add(task2);
		testArrayList.add(task3);
		
		test.saveTasks(testArrayList);
		// Comparing the contents of output file with those of expected output file
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
		
		// Original ArrayList of tasks
		ArrayList<Task> testArrayList = new ArrayList<Task>();
		Task task1 = new Task();
		task1.setDescription("Buy Civilization: Beyond Earth");
		DateTime date1 = formatter.parseDateTime("24/10/2014 13:23:04");
		task1.setDate(date1);
		Task task2 = new Task();
		task2.setDescription("MA2214 tutorial");
		DateTime date2 = formatter.parseDateTime("23/10/2014 14:00:00");
		task2.setDate(date2);
		DateTime end2 = formatter.parseDateTime("23/10/2014 15:00:00");
		task2.setEndDate(end2);
		Task task3 = new Task();
		task3.setDescription("Fix toilet pipe");
		testArrayList.add(task1);
		testArrayList.add(task2);
		testArrayList.add(task3);
		
		JSONArray jsonArray = test.convertArrayListToJSONArray(testArrayList);
		
		JSONObject obj1 = (JSONObject) jsonArray.get(0);
		assertEquals(obj1.get("Description"), task1.getDescription());
		assertEquals(obj1.get("Date"), task1.getDate().toString());
		
		JSONObject obj2 = (JSONObject) jsonArray.get(1);
		assertEquals(obj2.get("Description"), task2.getDescription());
		assertEquals(obj2.get("Date"), task2.getDate().toString());
		assertEquals(obj2.get("End date"), task2.getEndDate().toString());
		
		JSONObject obj3 = (JSONObject) jsonArray.get(2);
		assertEquals(obj3.get("Description"), task3.getDescription());
		
	}
	
	//@author A0115864B
	@Test
	public void testConvert2() {
		test = new DataStorage();
		
		// Original JSONArray of tasks
		JSONArray jsonArray = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("Description", "submit CS2103T code");
		obj1.put("Date", "2014-10-22T23:59:59.999+08:00");
		JSONObject obj2 = new JSONObject();
		obj2.put("Description", "CS2101 class");
		obj2.put("Date", "2014-10-23T12:00:00.000+08:00");
		obj2.put("End date", "2014-10-23T14:00:00.000+08:00");
		JSONObject obj3 = new JSONObject();
		obj3.put("Description", "Practice drawing");
		jsonArray.add(obj1);
		jsonArray.add(obj2);
		jsonArray.add(obj3);
		
		ArrayList<Task> arrayList = test.convertJSONArrayToArrayList(jsonArray);
		
		Task task1 = arrayList.get(0);
		assertEquals(task1.getDescription(), obj1.get("Description"));
		assertEquals(task1.getDate().toString(), obj1.get("Date"));
		
		Task task2 = arrayList.get(1);
		assertEquals(task2.getDescription(), obj2.get("Description"));
		assertEquals(task2.getDate().toString(), obj2.get("Date"));
		assertEquals(task2.getEndDate().toString(), obj2.get("End date"));
		
		Task task3 = arrayList.get(2);
		assertEquals(task3.getDescription(), obj3.get("Description"));
		
	}
	

}
