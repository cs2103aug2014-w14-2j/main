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
import org.joda.time.DateTime;

public class DataStorage {
	
	private final String filename = "Todo.json";
	private JSONArray tasks = new JSONArray();
	private ArrayList<Task> backup;
	
	public DataStorage() {
		initiateFile();
	}
	
	/**
	 * 
	 */
	public void initiateFile() {
		File file = new File(filename);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * 
	 */
	public void retrieveTasks() {
		tasks.clear();
		JSONParser parser = new JSONParser();
		try {
			Object fileRead = parser.parse(new FileReader(filename));
			tasks = (JSONArray)fileRead;
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
	}
	
	public void saveTasks() {
		try {
			FileWriter fw = new FileWriter(filename, false);
			fw.write(tasks.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Task> convertJSONArrayToArrayList() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = new Task();
			JSONObject obj = (JSONObject) tasks.get(i);
			task.setDescription((String) obj.get("Description"));
			try {
				if(obj.containsKey("Date")) {
					DateTime date = (DateTime)obj.get("Date");
					task.setDate(date);
				}
				if(obj.containsKey("End date")) {
					DateTime end = (DateTime)obj.get("End date");
					task.setEndDate(end);
				}
			} catch (Exception e) {
				
			}
			list.add(task);
		}
		backup = new ArrayList<Task>(list);
		return list;
		
	}
	
	/**
	 * 
	 * @param list
	 */
	public void convertArrayListToJSONArray(ArrayList<Task> list) {
		tasks.clear();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Description", list.get(i).getDescription());
			try {
				if (list.get(i).getDate() != null) {
					obj.put("Date", list.get(i).getDate());
				}
				if (list.get(i).getEndDate() != null) {
					obj.put("End date", list.get(i).getEndDate());
				}
			} catch (Exception e) {
				
			}
			tasks.add(obj);
		}
	}

}
