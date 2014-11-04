package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;

public class ConfigManager {
    
    private static File file;
    
    private static WaveLogger logger = new WaveLogger("Config");
    private static String filename;
    
    public ConfigManager() {
        filename = "Config.txt";
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
        String line = null;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            line = br.readLine();
            br.close();
            fr.close();
            logger.log(Level.INFO, "Default home view setting retrieved");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
        return Integer.parseInt(line);
    }
    
    public void setHomeViewType(int type) {
        try {
            FileWriter fw = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(type);
            pw.close();
            fw.close();
            logger.log(Level.INFO, "Default home view setting saved");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
    }
    
    

}
