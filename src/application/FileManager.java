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
import java.util.Date;

public class FileManager {
	
	private static final String floatingFilename = "reminders.json";
	private static final String deadlineFilename = "tasks.json";
	private static final String timedFilename = "events.json";
	
	private JSONArray floatingTasks = new JSONArray();
	private JSONArray deadlineTasks = new JSONArray();
	private JSONArray timedTasks = new JSONArray();
	
	public FileManager() {
	}
	
	public void initiateFile() {
		initiateFloatingFile();
		initiateDeadlineFile();
		initiateTimedFile();
	}
	
	public void initiateFloatingFile() {
		File file = new File(floatingFilename);
		try {
			if(!file.exists()) {
				FileWriter createFile = new FileWriter(floatingFilename);
				createFile.close();
			}
		} catch (IOException e) {
			
		}
	}
	
	public void initiateDeadlineFile() {
		File file = new File(deadlineFilename);
		try {
			if(!file.exists()) {
				FileWriter createFile = new FileWriter(deadlineFilename);
				createFile.close();
			}
		} catch (IOException e) {
			
		}
		
	}
	
	public void initiateTimedFile() {
		File file = new File(timedFilename);
		try {
			if(!file.exists()) {
				FileWriter createFile = new FileWriter(timedFilename);
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
		floatingTasks.clear();
		JSONParser parser = new JSONParser();
		try {
			Object filereader = parser.parse(new FileReader(floatingFilename));
			floatingTasks = (JSONArray) filereader;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
	}
	
	public void retrieveDeadline() {
		deadlineTasks.clear();
		JSONParser parser = new JSONParser();
		try {
			Object filereader = parser.parse(new FileReader(deadlineFilename));
			deadlineTasks = (JSONArray) filereader;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
		
	}
	
	public void retrieveTimed() {
		timedTasks.clear();
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
	
	public void convertFloatingArrayListToJSONArray(ArrayList<FloatingTask> list) {
		floatingTasks.clear();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Description", list.get(i).getDescription());
			floatingTasks.add(obj);
		}
	}
	
	public void convertDeadlineArrayListToJSONArray(ArrayList<DeadlineTask> list) {
		deadlineTasks.clear();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Description", list.get(i).getDescription());
			obj.put("Deadline", list.get(i).getDeadline());
			deadlineTasks.add(obj);
		}
	}
	
	public void convertTimedArrayListToJSONArray(ArrayList<TimedTask> list) {
		timedTasks.clear();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Description", list.get(i).getDescription());
			timedTasks.add(obj);
		}
	}
	
	public ArrayList<FloatingTask> convertFloatingJSONArrayToArrayList() {
		ArrayList<FloatingTask> list = new ArrayList<FloatingTask>();
		for (int i = 0; i < floatingTasks.size(); i++) {
			FloatingTask task = new FloatingTask();
			JSONObject obj = (JSONObject) floatingTasks.get(i);
			task.setDescription((String) obj.get("Description")); 
			list.add(task);
		}
		return list;
	}
	
	public ArrayList<DeadlineTask> convertDeadlineJSONArrayToArrayList() {
		ArrayList<DeadlineTask> list = new ArrayList<DeadlineTask>();
		for (int i = 0; i < deadlineTasks.size(); i++) {
			DeadlineTask task = new DeadlineTask();
			JSONObject obj = (JSONObject) deadlineTasks.get(i);
			task.setDescription((String) obj.get("Description")); 
			task.setDeadline((Date) obj.get("Deadline"));
			list.add(task);
		}
		return list;
	}
	
	public ArrayList<TimedTask> convertTimedJSONArrayToArrayList() {
		ArrayList<TimedTask> list = new ArrayList<TimedTask>();
		for (int i = 0; i < timedTasks.size(); i++) {
			TimedTask task = new TimedTask();
			JSONObject obj = (JSONObject) timedTasks.get(i);
			task.setDescription((String) obj.get("Description")); 
			list.add(task);
		}
		return list;
	}

}
