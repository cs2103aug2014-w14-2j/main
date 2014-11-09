package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;

import Parser.CommandInfo;
import Parser.Parser;
import Task.Task;
import Task.TaskManager;
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
    private static ConfigManager configManager;
    private static UIComponent uiComponent;
    private static MessageManager messageManager;
    private static Backup backup;
    
    //@author A0110546R
    /**
     * Executes the command entered.
     * 
     * @param input The entire command input.
     */
    public static void runCommandInput(String input) {
        logger.log(Level.FINE, "runCommandInput(input: {0} )", input);

        CommandInfo commandInfo = null;
        
        // Ensures valid command input.
        try {
            commandInfo = (new Parser()).getCommandInfo(input);
        } catch (MismatchedCommandException e) { // Need to change exception type.
            uiComponent.setSuggestionText("Command is invalid");
            return;
        }
        
        Message feedback = null;
        
        // Check for invalid IDs.
        ArrayList<String> invalidIDs = taskManager.getInvalidDisplayIDs(commandInfo.getTaskIDs());
        if (invalidIDs != null) {
            feedback = new MessageWarningInvalidID(invalidIDs);
            uiComponent.setSuggestionText(messageManager.getMessage(feedback));
            logger.log(messageManager.getMessage(feedback));
            return;
        }

        // Run the command.         
            switch (commandInfo.getCommandType()) {
            case "add":
                taskManager.add(commandInfo);
                break;
            case "delete":
                taskManager.delete(commandInfo);
                feedback = new MessageNotifyDelete(commandInfo.getTaskIDs());
                break;
            case "edit":
                taskManager.edit(commandInfo);
                feedback = new MessageNotifyEdit(commandInfo.getTaskIDs().get(0));
                break;
            case "undo":
                taskManager.undo(commandInfo, backup.getPastVersion());
                feedback = new MessageNotifyUndo();
                break;
            case "complete":
                taskManager.complete(commandInfo);
                feedback = new MessageNotifyComplete(commandInfo.getTaskIDs());
                break;
            case "home":
                break;
            case "show":
                taskManager.setDaysToDisplay(commandInfo, configManager);
                // continues:
            case "search":
                taskManager.clearIDMapping();
                uiComponent.updateRightPanel(taskManager.getSearchedTasks(commandInfo), "Tasks search results");
                uiComponent.updateLeftPanel(taskManager.getSearchedEvents(commandInfo), "Events search results");
                return;
            case "quit":
            case "exit":
                Platform.exit();
                return;
            	
        }
        
        taskManager.clearIDMapping();
        uiComponent.updateRightPanel(taskManager.getTasks(), "Tasks");
        uiComponent.updateLeftPanel(taskManager.getEvents(), "Events");
        
        backup.storeBackup(taskManager.getFullList());
        dataStorage.saveTasks(taskManager.getSanitizedList());
        
        // Can only get display id after displaying.
        if ("add".equals(commandInfo.getCommandType())) {
            feedback = new MessageNotifyAdd(taskManager.getLastModifiedTask().getDisplayID());
        }
        
        if (feedback != null) {
            uiComponent.setSuggestionText(messageManager.getMessage(feedback));
            logger.log(messageManager.getMessage(feedback));
        }
    }
    

    //@author A0110546R
    /**
     * For the UI to retrieve the list of tasks after it is initialized.
     */
    public static void getTasks() {
        uiComponent.updateRightPanel(taskManager.getTasks(), "Tasks");
        uiComponent.updateLeftPanel(taskManager.getEvents(), "Events");
    }
    
    //@author A0110546R
    /**
     * For the UI to retrieve the Task given the display ID.
     * @param displayID the displayID of the task.
     */
    public static Task getTaskFromDisplayID(String displayID) {
        try {
            return taskManager.getTaskFromDisplayID(displayID.toUpperCase());
        } catch (IllegalArgumentException e) {
            uiComponent.setSuggestionText(e.getMessage());
            // There is no need to log this.
        }
        return null;
    }
    
    //@author A0110546R
    private static void setup() {
        dataStorage = new DataStorage();
        dataStorage.initiateFile();
        
        taskManager = new TaskManager();
        taskManager.initializeList(dataStorage.retrieveTasks());
        
        messageManager = new MessageManager();
        
        configManager = new ConfigManager();
        taskManager.setDaysToDisplay(configManager.getHomeViewType());
        
        backup = new Backup();
        backup.storeBackup(taskManager.getFullList());
    }
    
    //@author A0110546R
    public static void main(String[] args) {
        setup();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiComponent = new UIComponent();
        primaryStage.getIcons().add(new Image("/UI/wavewave.png"));
        uiComponent.showStage(primaryStage);
    }
}
