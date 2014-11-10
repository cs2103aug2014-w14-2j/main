package application;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ConfigManager stores the default view type for WaveWave into an external json file.
 * View type is stored separately so that the view is maintained for next sessions.
 * JSON is used because of possible future expansions that include more settings to offer.
 * 
 * @author Kim Hyung Jon
 */

public class ConfigManager {
    
    private static File file;
    private static final String KEY_HOME_VIEW_TYPE = "Home view setting";
    private static WaveLogger logger = new WaveLogger("Config");
    private static String filename;
    
    //@author A0115864B
    /**
     * Constructor
     */
    public ConfigManager() {
        filename = "Config.json";
        initiateFile();
    }
    
    //@author A0115864B
    /**
     * Confirm that the external json file exists. If not, create it.
     */
    public void initiateFile() {
        file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            logger.log(Level.INFO, "Config file ready");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }
    
    //@author A0115864B
    /**
     * Read json file to retrieve the setting for home view type
     * 
     * @return integer representing a particular view type
     */
    public int getHomeViewType() {
        int setting = task.TaskManager.DEFAULT_DAYS_TO_DISPLAY;
        try {
            JSONParser parser = new JSONParser();
            JSONObject homeSetting = (JSONObject) parser.parse(new FileReader(filename));
            setting = (int)(long) homeSetting.get(KEY_HOME_VIEW_TYPE);
            logger.log(Level.INFO, "Default home view setting retrieved");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
        return setting;
    }
    
    //@author A0115864B
    /**
     * Stores the setting for home view type to the json file
     * 
     * @param type integer representing a particular view type
     */
    public void setHomeViewType(int type) {
        JSONObject homeSetting = new JSONObject();
        homeSetting.put(KEY_HOME_VIEW_TYPE, type);
        try {
            FileWriter fw = new FileWriter(file, false);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String output = gson.toJson(homeSetting);
            fw.write(output);
            fw.flush();
            fw.close();
            logger.log(Level.INFO, "Default home view setting saved");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }

}
