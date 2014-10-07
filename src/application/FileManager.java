package application;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileManager {
	
	private static final String floatingFilename = "reminders.json";
	private static final String deadlineFilename = "tasks.json";
	private static final String timedFilename = "events.json";
	
	private JSONArray floatingTasks = new JSONArray();
	private JSONArray deadlineTasks = new JSONArray();
	private JSONArray timedTasks = new JSONArray();
	
	public void initiateFile(String filename) {
		File file = new File(filename);
		try {
			if(!file.exists()) {
				FileWriter createFile = new FileWriter(filename);
				createFile.close();
			}
		} catch (IOException e) {
			
		}
	}
	
	public void retrieveLists() {
		retrieveFloating();
		retrieveDeadline();
		retrieveTimed();
	}
	
	public void retrieveFloating() {
		JSONParser parser = new JSONParser();
		try {
			Object filereader = parser.parse(new FileReader(floatingFilename));
			floatingTasks = (JSONArray) filereader;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
	}
	
	public void retrieveDeadline() {
		JSONParser parser = new JSONParser();
		try {
			Object filereader = parser.parse(new FileReader(deadlineFilename));
			deadlineTasks = (JSONArray) filereader;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
		
	}
	
	public void retrieveTimed() {
		JSONParser parser = new JSONParser();
		try {
			Object filereader = parser.parse(new FileReader(timedFilename));
			timedTasks = (JSONArray) filereader;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
		
	}
	
	public void saveToFiles() {
		saveFloatingToFiles();
		saveDeadlineToFiles();
		saveTimedToFiles();
	}
	
	public void saveFloatingToFiles() {
		try {
			FileWriter file = new FileWriter(floatingFilename, false);
			file.write(floatingTasks.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			
		}
	}
	
	public void saveDeadlineToFiles() {
		try {
			FileWriter file = new FileWriter(deadlineFilename, false);
			file.write(deadlineTasks.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			
		}
		
	}
	
	public void saveTimedToFiles() {
		try {
			FileWriter file = new FileWriter(timedFilename, false);
			file.write(timedTasks.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			
		}
		
	}
	
	public void buildArrayLists() {
		buildFloatingArrayList();
		buildDeadlineArrayList();
		buildTimedArrayList();
		
	}
	
	public void buildFloatingArrayList() {
		ArrayList<String> floatingArrayList = new ArrayList<String>();
		for (int i = 0; i < floatingTasks.size(); i++) {
			JSONObject obj = (JSONObject) floatingTasks.get(i);
		}
	}
	
	public void buildDeadlineArrayList() {
		
	}
	
	public void buildTimedArrayList() {
		
	}
	
	public void convertToJSON() {
		
	}

	public static void main(String[] args) {
		

	}

}
