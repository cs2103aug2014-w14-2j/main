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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_DATE = "Date";
    private static final String KEY_END = "End date";
    private static final String KEY_PRIORITY = "Priority";
    private static final String KEY_ISCOMPLETED = "Completed";
    private static final String KEY_COMPLETED_DATE = "Completed date";
    private static final String KEY_CREATED_DATE = "Created date";
    private static final String KEY_LAST_MODIFIED_DATE = "Last modified date";

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
        
        undoQueue.add(backupTasks(array));
        logger.log(Level.INFO, "Current version stored as backup");
        manageundoQueueSize();

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
                    task.setPriority(((Long) obj.get(KEY_PRIORITY))
                            .intValue());
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
            list.add(task);
        }
        undoQueue.add(list);
        return list;

    }

    /**
     * Maintain maximum undo queue size as 2
     */
    public void manageundoQueueSize() {
        if (undoQueue.size() > undoQueue_MAX_SIZE) {
            logger.log(Level.INFO, "Deleting older saved versions");
            for (int i = undoQueue.size(); i > undoQueue_MAX_SIZE; i--) {
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
            
            try {
                obj.put(KEY_DESCRIPTION, list.get(i).getDescription());
                obj.put(KEY_PRIORITY, list.get(i).getPriority());
                
                if (list.get(i).getDate() != null) {
                    String date = fmt.print(list.get(i).getDate());
                    obj.put(KEY_DATE, date);
                }
                
                if (list.get(i).getEndDate() != null) {
                    String end = fmt.print(list.get(i).getEndDate());
                    obj.put(KEY_END, end);
                }
                
                boolean isCompleted = list.get(i).isCompleted();
                obj.put(KEY_ISCOMPLETED, isCompleted);
                if (isCompleted) {
                    String comp = fmt.print(list.get(i).getCompletedAt());
                    obj.put(KEY_COMPLETED_DATE, comp);
                }
                
                String mod = fmt.print(list.get(i).getModifiedAt());
                obj.put(KEY_LAST_MODIFIED_DATE, mod);
                String cr = fmt.print(list.get(i).getCreatedAt());
                obj.put(KEY_CREATED_DATE, cr);
                
                logger.log(Level.INFO,
                        "ArrayList of tasks converted to JSONArray");
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString(), e);
            }
            tasks.add(obj);
        }
        return tasks;
    }

    //@author A0115864B
    /**
     * Returns a past saved version when "undo" command is received
     * 
     * @return backup ArrayList of tasks that was saved before last operation
     */
    public ArrayList<Task> getPastVersion() {
        ArrayList<Task> pastVersion = undoQueue.remove(0);
        logger.log(Level.INFO, "Backup version retrieved");
        return pastVersion;
    }
    
    //@author A0115864B
    /**
     * Stores a copy of the ArrayList of Tasks in current state. Deep copies everything.
     * 
     * @param tasks
     * @return
     */
    public ArrayList<Task> backupTasks(ArrayList<Task> originalTasks) {
        ArrayList<Task> backup = new ArrayList<Task>();
        for (Task task : originalTasks) {
            backup.add(new Task(task));
        }
        return backup;
    }

}
