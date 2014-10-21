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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * @author A0115864B
 * 
 *
 */
public class DataStorage {
	
	private final String filename;
	private JSONArray tasks = new JSONArray();
	private ArrayList<Task> backup;
	private DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
	
	public DataStorage() {
		filename = "Todo.json";
		initiateFile();
	}
	
	public DataStorage(String name) {
		filename = name;
		initiateFile();
	}
	
	
	//@author A0115864B
	public void initiateFile() {
		File file = new File(filename);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
		}
	}
	
	//@author A0115864B
	public ArrayList<Task> retrieveTasks() {
		tasks.clear();
		JSONParser parser = new JSONParser();
		try {
			tasks = (JSONArray) parser.parse(new FileReader(filename));
		} catch (IOException e) {
			System.out.println("IOException");
		} catch (ParseException e) {
			System.out.println("ParseException");
		}
		System.out.println("File retrieved successfully");
		System.out.println("Number of tasks retrieved: " + tasks.size());
		for(Object obj : tasks) {
			JSONObject task = (JSONObject)obj;
			System.out.println(task.get("Description"));
		}
		System.out.println("These are the tasks retrieved\n");
		return convertJSONArrayToArrayList();
	}
	
	//@author A0115864B
	public void saveTasks(ArrayList<Task> array) {
		convertArrayListToJSONArray(array);
		try {
			FileWriter fw = new FileWriter(filename, false);
			fw.write(tasks.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			
		}
		System.out.println("One iteration finished\n");
	}
	
	//@author A0115864B
	public ArrayList<Task> convertJSONArrayToArrayList() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = new Task();
			JSONObject obj = (JSONObject) tasks.get(i);
			task.setDescription((String) obj.get("Description"));
			try {
				if(obj.containsKey("Date")) {
					String dateString = (String)obj.get("Date");
					DateTime date = fmt.parseDateTime(dateString);
					task.setDate(date);
				}
				if(obj.containsKey("End date")) {
					String endString = (String)obj.get("End date");
					DateTime end = fmt.parseDateTime(endString);
					task.setEndDate(end);
				}
			} catch (Exception e) {
				
			}
			list.add(task);
		}
		backup = new ArrayList<Task>(list);
		return list;
		
	}
	
	//@author A0115864B
	/**
	 * 
	 * @param list
	 */
	public void convertArrayListToJSONArray(ArrayList<Task> list) {
		tasks.clear();
		System.out.println("Converting these tasks to JSON");
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("Description", list.get(i).getDescription());
			System.out.println(list.get(i).getDescription());
			try {
				if (list.get(i).getDate() != null) {
					String date = fmt.print(list.get(i).getDate());
					obj.put("Date", date);
				}
				if (list.get(i).getEndDate() != null) {
					String end = fmt.print(list.get(i).getEndDate());
					obj.put("End date", end);
				}
			} catch (Exception e) {
				
			}
			tasks.add(obj);
		}
	}
	
	//@author A0115864B
	public ArrayList<Task> getPastVersion() {
		return backup;
	}

}
