package application;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private static FileHandler fileHandler = null;

    private static DataStorage dataStorage;
    
    private static TaskManager taskManager;

    private static UIComponent uiComponent;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        logger.log(Level.FINE, "runCommandInput(input: {0} )", input);

        dataStorage.retrieveTasks();
        taskManager.initializeList(dataStorage.convertJSONArrayToArrayList());

        Command command = (new Parser(input)).getCmd();
        
        try {
            switch (command.getCommandType()) {
                case "add":
                    taskManager.add(command);
                    break;
                case "delete":
                    taskManager.delete(command);
                    break;
                case "edit":
                    taskManager.edit(command);
                    break;
            }
        } catch (MismatchedCommandException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
        uiComponent.updateTaskList(taskManager.getList());

        dataStorage.convertArrayListToJSONArray(taskManager.getList());
        dataStorage.saveTasks();
    }
    
    /**
     * For the UI to retrieve the list of tasks after it is initialized.
     */
    public static void getTasks() {
        uiComponent.updateTaskList(taskManager.getList());       
    }

    public static void main(String[] args) {
        taskManager = new TaskManager();
        dataStorage = new DataStorage();
        dataStorage.initiateFile();
        dataStorage.retrieveTasks();
        taskManager.initializeList(dataStorage.convertJSONArrayToArrayList());
        
        // Temporary logging file handler.
        try {
            fileHandler = new FileHandler(Controller.class.getName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.FINEST);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        uiComponent = new UIComponent();
        uiComponent.showStage(primaryStage);
    }
}