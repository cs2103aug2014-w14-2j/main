package application;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
	private ArrayList<ArrayList<Task>> undoQueue = new ArrayList<ArrayList<Task>>();
	
	private static final String STRING_DESC = "Description";
	private static final String STRING_DATE = "Date";
	private static final String STRING_END = "End date";
	private static final String STRING_PRIORITY = "Priority";
	private static final String STRING_COMPLETED = "Completed";
	private static final String STATIC_COMPLETED_DATE = "Completed date";
	
	private static final Integer undoQueue_MAX_SIZE = 2;
	
	private static WaveLogger logger = new WaveLogger("DataStorage");
	
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
			logger.log(Level.INFO, "Storage file ready");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
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
			logger.log(Level.INFO, "Contents of storage file retrieved");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} catch (ParseException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return getTasks();
	}
	
	//@author A0115864B
	/**
	 * Store tasks to external json file
	 * @param array ArrayList of tasks
	 */
	public void saveTasks(ArrayList<Task> array) {

        undoQueue.add(new ArrayList<Task>(array));
        logger.log(Level.INFO, "Current version stored as backup");
        manageundoQueueSize();
	    
		convertArrayListToJSONArray(array);
		try {
			FileWriter fw = new FileWriter(filename, false);
			fw.write(tasks.toJSONString());
			fw.flush();
			fw.close();
			logger.log(Level.INFO, "Tasks written to external file");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
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
					task.setPriority(((Long)obj.get(STRING_PRIORITY)).intValue());
				}
				logger.log(Level.INFO, "JSONArray converted to ArrayList of tasks");
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
			list.add(task);
		}
		undoQueue.add(list);
		return list;
		
	}
	
	/**
	 * Maintain maximum undo queue size as 2
	 */
	public void manageundoQueueSize() {
	    if(undoQueue.size() > undoQueue_MAX_SIZE) {
	        logger.log(Level.INFO, "Deleting older saved versions");
	        for (int i = undoQueue.size() ; i > undoQueue_MAX_SIZE ; i--) {
	            undoQueue.remove(0);
	        }
	    }
	}
	
	//@author A0115864B
	/**
	 * Converts ArrayList to JSONArray that can be saved to external json file
	 * @param list ArrayList of Task objects
	 */
	public JSONArray convertArrayListToJSONArray(ArrayList<Task> list) {
		tasks.clear();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put(STRING_DESC, list.get(i).getDescription());
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
				logger.log(Level.INFO, "ArrayList of tasks converted to JSONArray");
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}
			tasks.add(obj);
		}
		return tasks;
	}
	
	//@author A0115864B
	/**
	 * Support for undo command
	 * @return backup ArrayList of tasks that was saved before last operation
	 */
	public ArrayList<Task> getPastVersion() {
	    ArrayList<Task> pastVersion = undoQueue.remove(0);
	    logger.log(Level.INFO, "Backup version retrieved");
		return pastVersion;
	}

}
