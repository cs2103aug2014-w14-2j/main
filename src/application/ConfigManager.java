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

public class ConfigManager {
    
    private static File file;
    private static final String KEY_HOME_VIEW_TYPE = "Home view setting";
    private static WaveLogger logger = new WaveLogger("Config");
    private static String filename;
    
    public ConfigManager() {
        filename = "Config.json";
        initiateFile(filename);
    }
    
    public void initiateFile(String name) {
        file = new File(name);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            logger.log(Level.INFO, "Config file ready");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }
    
    public int getHomeViewType() {
        int setting = 0;
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
    
    public static void main(String[] args) {
        ConfigManager test = new ConfigManager();
        test.setHomeViewType(1);
        System.out.println(test.getHomeViewType());

    }

}
