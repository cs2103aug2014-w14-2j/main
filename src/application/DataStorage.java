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
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * @author Kim Hyung Jon (matric number: A0115864B)
 * 
 *
 */
public class DataStorage {
	
	private final String filename;
	private JSONArray tasks = new JSONArray();
	private ArrayList<Task> backup;
	private DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
	
	
	private static final String STRING_DESC = "Description";
	private static final String STRING_DATE = "Date";
	private static final String STRING_END = "End date";
	private static final String STRING_PRIORITY = "Priority";
	
	
	//@author A0115864B
	/**
	 * Constructor.
	 */
	public DataStorage() {
		filename = "Todo.json";
		initiateFile();
	}
	
	//@author A0115864B
	/**
	 * Constructor when filename for external json storage is provided. For unit testing.
	 * @param name name of the json file for storing the tasks
	 */
	public DataStorage(String name) {
		filename = name;
		initiateFile();
	}
	
	
	//@author A0115864B
	/**
	 * Confirm that the external json file exists. If not, create it.
	 */
	public void initiateFile() {
		File file = new File(filename);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//@author A0115864B
	/**
	 * Read json file. Retrieve all tasks and store them in JSONArray.
	 * @return ArrayList of tasks 
	 */
	public ArrayList<Task> retrieveTasks() {
		tasks.clear();
		JSONParser parser = new JSONParser();
		try {
			tasks = (JSONArray) parser.parse(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("File retrieved successfully");
		System.out.println("Number of tasks retrieved: " + tasks.size());
		for(Object obj : tasks) {
			JSONObject task = (JSONObject)obj;
			System.out.println(task.get("Description"));
		}
		System.out.println("These are the tasks retrieved\n");
		return getTasks();
	}
	
	//@author A0115864B
	/**
	 * Store tasks to external json file
	 * @param array ArrayList of tasks
	 */
	public String saveTasks(ArrayList<Task> array) {
		convertArrayListToJSONArray(array);
		try {
			FileWriter fw = new FileWriter(filename, false);
			fw.write(tasks.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("One iteration finished\n");
		return "Saved to" + filename;
	}
	
	//@author A0115864B
	/**
	 * 
	 * @return
	 */
	public ArrayList<Task> getTasks() {
		return convertJSONArrayToArrayList(tasks);
	}
	
	//@author A0115864B
	/**
	 * Converts JSONArray containing the tasks to a form that other components can understand
	 * @return ArrayList of Tasks objects
	 */
	public ArrayList<Task> convertJSONArrayToArrayList(JSONArray array) {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < array.size(); i++) {
			Task task = new Task();
			JSONObject obj = (JSONObject) array.get(i);
			task.setDescription((String) obj.get(STRING_DESC));
			try {
				if(obj.containsKey(STRING_DATE)) {
					String dateString = (String)obj.get(STRING_DATE);
					DateTime date = fmt.parseDateTime(dateString);
					task.setDate(date);
				}
				if(obj.containsKey(STRING_END)) {
					String endString = (String)obj.get(STRING_END);
					DateTime end = fmt.parseDateTime(endString);
					task.setEndDate(end);
				}
				// Currently priority is not fully supported. May have to update later.
				if(obj.containsKey(STRING_PRIORITY)) {
					task.setPriority(((Integer)obj.get(STRING_PRIORITY)));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(task);
		}
		// Before sending ArrayList of tasks to Controller,
		// keep a backup of the current state of ArrayLists
		// so that it can be returned if an undo command is given
		return list;
		
	}
	
	//@author A0115864B
	/**
	 * Converts ArrayList to JSONArray that can be saved to external json file
	 * @param list ArrayList of Task objects
	 */
	public JSONArray convertArrayListToJSONArray(ArrayList<Task> list) {
		tasks.clear();
		System.out.println("Converting these tasks to JSON");
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put(STRING_DESC, list.get(i).getDescription());
			System.out.println(list.get(i).getDescription());
			obj.put(STRING_PRIORITY, list.get(i).getPriority());
			try {
				if (list.get(i).getDate() != null) {
					String date = fmt.print(list.get(i).getDate());
					obj.put(STRING_DATE, date);
				}
				if (list.get(i).getEndDate() != null) {
					String end = fmt.print(list.get(i).getEndDate());
					obj.put(STRING_END, end);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			tasks.add(obj);
		}
		return tasks;
	}
	
	//@author A0115864B
	/**
	 * Support for undo command
	 * @return backup ArrayList of tasks that was saved before last operation
	 * (Currently not implemented)
	 */
	public ArrayList<Task> getPastVersion() {
		return backup;
	}

}
