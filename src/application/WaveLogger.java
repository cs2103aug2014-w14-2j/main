package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

//@author A0110546R
/**
 * Our custom logging class that wraps Logger.
 * Control loggers that output to the console by adding their
 * names into consoleOutput.txt.
 * 
 *
 */
public class WaveLogger {
    private static final String consoleOutputList = "consoleOutput.txt";
    
    private final Logger logger;
    private FileHandler fileHandler;
    
    /**
     * Constructs a new logger.
     * Preferably use literal strings for the name of the logger.
     * e.g. "Controller" instead of Controller.class.getName()
     * 
     * @param name the name of the file, without extension.
     */
    public WaveLogger(String name) {
        this.logger = Logger.getLogger(name);
        
        try { // Creates the log directory if it does not exist.
            new File("log").mkdirs();
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        
        try {
            this.fileHandler = new FileHandler("log\\" + name + ".log");
            this.fileHandler.setFormatter(new SimpleFormatter());
            this.logger.addHandler(this.fileHandler);
        } catch (Exception e) {
            // This will log to the console.
            logger.log(Level.SEVERE, null, e);
        }
        
        this.logger.setLevel(Level.FINEST);
        
        // If not in console output list...
        if (!useConsoleOutput(name)) {
            // Prevents console output.
            this.logger.setUseParentHandlers(false);
        }
    }
    
    private static boolean useConsoleOutput(String name) {
        try {
            Scanner sc = new Scanner(new FileReader(consoleOutputList));
            while (sc.hasNext()) {
                String nameInList = sc.nextLine();
                
                if (name.equals(nameInList)) {
                    sc.close();
                    return true;
                }
            }
            sc.close();
            return false;  
        }
        catch (FileNotFoundException e) {
            return false;
        }      
    }
        
    /**
     * Wraps Logger log method with fine grained level control
     * and message parameters.
     * 
     * @param level the intensity of the message.
     * @param message the message.
     * @param param any objects to pass into message.
     */
    public void log(Level level, String message, Object param) {
        this.logger.log(level, message, param);
    }
    
    /**
     * Wraps Logger log method with fine grained level control.
     * 
     * @param level the intensity of the message.
     * @param message the message.
     */
    public void log(Level level, String message) {
        this.logger.log(level, message);
    }
    
    /**
     * Basic logging log without having to care about levels.
     * 
     * @param message simply the message.
     */
    public void log(String message) {
        this.logger.log(Level.INFO, message);
    }
}
