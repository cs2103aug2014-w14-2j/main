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

<<<<<<< HEAD
    private static DataStorage storage;
=======
    private static DataStorage dataStorage;
>>>>>>> 26c93c410698c57447d473a58439fcea98e99c0e
    
    private static TaskManager taskManager;

    private static UIComponent uiComponent;
    
    private static Parser parser;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        logger.log(Level.FINE, "runCommandInput(input: {0} )", input);

<<<<<<< HEAD
        System.out.println("runCommandInput(input: " + input + ") called");

        storage.retrieveTasks();
        // floatingTasks.initializeList(storage.convertFloatingJSONArrayToArrayList());
        // deadlineTasks.initializeList(storage.convertDeadlineJSONArrayToArrayList());
        // timedTasks.initializeList(storage.convertTimedJSONArrayToArrayList());

        Command command = (new Parser(input)).getCmd();

        switch (command.getCommandType()) {
        case "add":
            taskManager.add(command);
            taskManager.updateUi(uiComponent);
            break;
        case "delete":
            taskManager.delete(command);
            taskManager.updateUi(uiComponent);
            break;
        case "edit":
            taskManager.edit(command);
            taskManager.updateUi(uiComponent);
        }

        // storage.convertFloatingArrayListToJSONArray(floatingTasks.getList());
        // storage.convertDeadlineArrayListToJSONArray(deadlineTasks.getList());
        // storage.convertTimedArrayListToJSONArray(timedTasks.getList());
        storage.saveTasks();
    }

    @Override
    public void start(Stage primaryStage) {
=======
        dataStorage.retrieveTasks();
        taskManager.initializeList(dataStorage.convertJSONArrayToArrayList());

        Command command = Parser.getCommand(input);
        
>>>>>>> 26c93c410698c57447d473a58439fcea98e99c0e
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
<<<<<<< HEAD
        // floatingTasks = new FloatingTaskManager();
        // timedTasks = new TimedTaskManager();
        // deadlineTasks = new DeadlineTaskManager();
        storage = new DataStorage();
        storage.initiateFile();
        
=======
>>>>>>> 26c93c410698c57447d473a58439fcea98e99c0e
        taskManager = new TaskManager();
        dataStorage = new DataStorage();
        dataStorage.initiateFile();
        dataStorage.retrieveTasks();
        taskManager.initializeList(dataStorage.convertJSONArrayToArrayList());
        parser = Parser.getInstance();
        
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
