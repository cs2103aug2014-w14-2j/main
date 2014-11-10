package data;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.WaveLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import task.Task;

/**
 * Data storage component that delivers data between Controller and external storage.
 * 
 * @author Kim Hyung Jon (matric number: A0115864B)
 * 
 */
public class DataStorage {

    private final String filename;
    private JSONArray tasks = new JSONArray();
    private DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_DATE = "Date";
    private static final String KEY_END = "End date";
    private static final String KEY_PRIORITY = "High-priority";
    private static final String KEY_ISCOMPLETED = "Completed";
    private static final String KEY_COMPLETED_DATE = "Completed date";
    private static final String KEY_CREATED_DATE = "Created date";
    private static final String KEY_LAST_MODIFIED_DATE = "Last modified date";
    
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
     * 
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
            if (!file.exists()) {
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
     * 
     * @return ArrayList of Task objects
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
        return convertJSONArrayToArrayList(tasks);
    }

    //@author A0115864B
    /**
     * Store tasks to external json file
     * 
     * @param array ArrayList of Task objects
     */
    public void saveTasks(ArrayList<Task> array) {
        convertArrayListToJSONArray(array);
        try {
            FileWriter fw = new FileWriter(filename, false);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String output = gson.toJson(tasks);
            fw.write(output);
            fw.flush();
            fw.close();
            logger.log(Level.INFO, "Tasks written to external file");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }

    //@author A0115864B
    /**
     * Converts JSONArray containing the tasks to a form that Controller can understand
     * 
     * @param array JSONArray of JSONObjects, each containing information of task details
     * @return      ArrayList of Task objects
     */
    public ArrayList<Task> convertJSONArrayToArrayList(JSONArray array) {
        ArrayList<Task> list = new ArrayList<Task>();
        for (int i = 0 ; i < array.size() ; i++) {
            JSONObject obj = (JSONObject) array.get(i);
            list.add(convertJSONObjectToTask(obj));
        }
        return list;
    }

    //@author A0115864B
    /**
     * Converts ArrayList to JSONArray that can be saved to external json file
     * 
     * @param list ArrayList of Task objects
     * @return     JSONArray of JSONObjects each corresponding to a particular task
     */
    public JSONArray convertArrayListToJSONArray(ArrayList<Task> list) {
        tasks.clear();
        for (int i = 0 ; i < list.size() ; i++) {
            Task task = list.get(i);
            tasks.add((JSONObject) convertTaskToJSONObject(task));
        }
        return tasks;
    }
    
    //@author A0115864B
    /**
     * Converts JSONObject to a Task object that Controller can operate on
     * 
     * @param obj JSONObject corresponding to a task saved in external file
     * @return    Task object that encapsulates the same data as parameter obj
     */
    public Task convertJSONObjectToTask(JSONObject obj) {
        Task task = new Task();
        try {
            task.setDescription((String) obj.get(KEY_DESCRIPTION));
            
            if (obj.containsKey(KEY_DATE)) {
                String dateString = (String) obj.get(KEY_DATE);
                DateTime date = fmt.parseDateTime(dateString);
                task.setDate(date);
            }
            
            if (obj.containsKey(KEY_END)) {
                String endString = (String) obj.get(KEY_END);
                DateTime end = fmt.parseDateTime(endString);
                task.setEndDate(end);
            }
            
            
            if (obj.containsKey(KEY_PRIORITY)) {
                boolean highPriority = (boolean)obj.get(KEY_PRIORITY);
                if (highPriority) {
                    task.setPriority(1);
                } else {
                    task.setPriority(0);
                }
            }
            
            boolean isCompleted = (boolean) obj.get(KEY_ISCOMPLETED);
            task.setCompleted(isCompleted);
            if (isCompleted) {
                String compString = (String) obj.get(KEY_COMPLETED_DATE);
                DateTime completedTime = fmt.parseDateTime(compString);
                task.setCompletedAt(completedTime);
            }
            
            String modString = (String) obj.get(KEY_LAST_MODIFIED_DATE);
            DateTime modTime = fmt.parseDateTime(modString);
            task.setModifiedAt(modTime);
            
            String createdString = (String) obj.get(KEY_CREATED_DATE);
            DateTime createdTime = fmt.parseDateTime(createdString);
            task.setCreatedAt(createdTime);
            
            logger.log(Level.INFO,
                    "JSONArray converted to ArrayList of tasks");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
        return task;
    }
    
    //@author A0115864B
    /**
     * Converts Task object to JSONObject that can be stored in external json file
     * 
     * @param task Task object to convert
     * @return     JSONObject corresponding to parameter task
     */
    public JSONObject convertTaskToJSONObject(Task task) {
        JSONObject obj = new JSONObject();
        try {
            obj.put(KEY_DESCRIPTION, task.getDescription());
            
            if (task.getPriority() == 1) {
                obj.put(KEY_PRIORITY, true);
            } else {
                obj.put(KEY_PRIORITY, false);
            }
            
            if (task.getDate() != null) {
                String date = fmt.print(task.getDate());
                obj.put(KEY_DATE, date);
            }
            
            if (task.getEndDate() != null) {
                String end = fmt.print(task.getEndDate());
                obj.put(KEY_END, end);
            }
            
            boolean isCompleted = task.isCompleted();
            obj.put(KEY_ISCOMPLETED, isCompleted);
            if (isCompleted) {
                String comp = fmt.print(task.getCompletedAt());
                obj.put(KEY_COMPLETED_DATE, comp);
            }
            
            String mod = fmt.print(task.getModifiedAt());
            obj.put(KEY_LAST_MODIFIED_DATE, mod);
            String cr = fmt.print(task.getCreatedAt());
            obj.put(KEY_CREATED_DATE, cr);
            
            logger.log(Level.INFO,
                    "ArrayList of tasks converted to JSONArray");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
        return obj;
    }

}
