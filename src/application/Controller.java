package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.logging.Level;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    
    private static final WaveLogger logger = new WaveLogger("Controller");
    
    private static DataStorage dataStorage;    
    private static TaskManager taskManager;
    private static UIComponent uiComponent;
    
    //@author A0110546R
    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        logger.log(Level.FINE, "runCommandInput(input: {0} )", input);

        CommandInfo commandInfo = (new Parser()).getCommandInfo(input);
        
        try {
            switch (commandInfo.getCommandType()) {
                case "add":
                    taskManager.add(commandInfo);
                    break;
                case "delete":
                    taskManager.delete(commandInfo);
                    break;
                case "edit":
                    taskManager.edit(commandInfo);
                    break;
                case "undo":
                	taskManager.undo(commandInfo, dataStorage.getPastVersion());
                	break;
                case "complete":
                    taskManager.complete(commandInfo);
                    break;
                case "quit":
                    Platform.exit();
                    break;
                	
            }
        } catch (MismatchedCommandException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }

        uiComponent.updateTaskList(taskManager.getTasks());
        uiComponent.updateReminderList(taskManager.getReminders());
        
        dataStorage.saveTasks(taskManager.getList());
    }
    

    //@author A0110546R
    /**
     * For the UI to retrieve the list of tasks after it is initialized.
     */
    public static void getTasks() {
        uiComponent.updateTaskList(taskManager.getTasks());
        uiComponent.updateReminderList(taskManager.getReminders());
    }
    
    //@author A0110546R
    public static void main(String[] args) {
        taskManager = new TaskManager();
        dataStorage = new DataStorage();
        dataStorage.initiateFile();
        
        taskManager.initializeList(dataStorage.retrieveTasks());
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiComponent = new UIComponent();
        uiComponent.showStage(primaryStage);
    }
}
