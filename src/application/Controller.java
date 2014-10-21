package application;

import javafx.application.Application;
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

        CommandInfo command = (new Parser()).getCommandInfo(input);
        
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
                case "undo":
                	taskManager.undo(command, dataStorage.getPastVersion());
                	break;
                case "complete":
                    taskManager.complete(command);
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
