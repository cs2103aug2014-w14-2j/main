package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;

import UI.UIComponent;

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
    private static MessageManager messageManager;
    
    //@author A0110546R
    /**
     * Executes the command entered.
     * 
     * @param input The entire command input.
     */
    public static void runCommandInput(String input) {
        logger.log(Level.FINE, "runCommandInput(input: {0} )", input);

        CommandInfo commandInfo = (new Parser()).getCommandInfo(input);
        Message feedback = null;
        
        // Check for invalid IDs.
        ArrayList<String> invalidIDs = taskManager.getInvalidDisplayIDs(commandInfo.getTaskIDs());
        if (invalidIDs != null) {
            feedback = new MessageWarningInvalidID(invalidIDs);
            logger.log(messageManager.getMessage(feedback));
            return;
        }

        // Really should command pattern this now!
        // Run the command.
        try {            
            switch (commandInfo.getCommandType()) {
                case "add":
                    taskManager.add(commandInfo);
                    feedback = new MessageNotifyAdd(taskManager.getLastModifiedTask().getID() + "");
                    break;
                case "delete":
                    taskManager.delete(commandInfo);
                    feedback = new MessageNotifyDelete(commandInfo.getTaskIDs());
                    break;
                case "edit":
                    taskManager.edit(commandInfo);
                    feedback = new MessageNotifyEdit(taskManager.getLastModifiedTask().getID() + "");
                    break;
                case "undo":
                    taskManager.undo(commandInfo, dataStorage.getPastVersion());
                    feedback = new MessageNotifyUndo();
                    break;
                case "complete":
                    taskManager.complete(commandInfo);
                    feedback = new MessageNotifyComplete(commandInfo.getTaskIDs());
                    break;
                case "search complete": // Temporary.
                    taskManager.clearIDMapping();
                    uiComponent.updateTaskList(taskManager.getCompletedTasks());
                    uiComponent.updateReminderList(taskManager.getCompletedReminders());
                    return;
                case "search":
                case "display":
                case "show":
                    break;
                case "quit":
                case "exit":
                    Platform.exit();
                    break;
                	
            }
        } catch (MismatchedCommandException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
        
        if (feedback != null) {
            logger.log(messageManager.getMessage(feedback));
        }
        
        taskManager.clearIDMapping();
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
        messageManager = new MessageManager();
        
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
