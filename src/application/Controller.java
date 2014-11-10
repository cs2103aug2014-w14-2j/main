package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;


import task.Task;
import task.TaskManager;

import data.ConfigManager;
import data.DataStorage;

import Parser.CommandInfo;
import Parser.DateTimeParser;
import Parser.Parser;
import UI.UIComponent;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    private static final String NAME_TODOS = "To-dos";
    private static final String NAME_EVENTS = "Events";
    private static final String NAME_SEARCH_RESULTS = "search results";
    
    private static final WaveLogger logger = new WaveLogger("Controller");
    
    private static DataStorage dataStorage;    
    private static TaskManager taskManager;
    private static ConfigManager configManager;
    private static UIComponent uiComponent;
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
        } catch (InvalidCommandException e) {
            uiComponent.setSuggestionText(e.getMessage());
            return;
        }
        assert(commandInfo != null);
        
        Message feedback = null;
        
        // Check for invalid IDs.
        ArrayList<String> invalidIDs = taskManager.getInvalidDisplayIDs(commandInfo.getTaskIDs());
        if (invalidIDs != null) {
            feedback = new MessageWarningInvalidID(invalidIDs);
            uiComponent.setSuggestionText(feedback.getMessage());
            logger.log(feedback.getMessage());
            return;
        }

        // Run the command.         
        switch (commandInfo.getCommandType()) {
            case InputCommands.ADD:
                taskManager.add(commandInfo);
                break;
            case InputCommands.DELETE:
                taskManager.delete(commandInfo);
                feedback = new MessageNotifyDelete(commandInfo.getTaskIDs());
                break;
            case InputCommands.EDIT:
                taskManager.edit(commandInfo);
                feedback = new MessageNotifyEdit(commandInfo.getTaskIDs().get(0));
                break;
            case InputCommands.UNDO:
                taskManager.undo(commandInfo, backup.getPastVersion());
                feedback = new MessageNotifyUndo();
                break;
            case InputCommands.COMPLETE:
                taskManager.complete(commandInfo);
                feedback = new MessageNotifyComplete(commandInfo.getTaskIDs());
                break;
            case InputCommands.HOME:
                break;
            case InputCommands.SHOW:
                taskManager.setDaysToDisplay(commandInfo, configManager);
                // continues:
            case InputCommands.SEARCH:
                taskManager.clearIDMapping();
                uiComponent.updateRightPanel(taskManager.getSearchedTasks(commandInfo), NAME_TODOS + " " + NAME_SEARCH_RESULTS);
                uiComponent.updateLeftPanel(taskManager.getSearchedEvents(commandInfo), NAME_EVENTS + " " + NAME_SEARCH_RESULTS);
                return;
            case InputCommands.QUIT:
            case InputCommands.EXIT:
                Platform.exit();
                return;
            	
        }
        
        taskManager.clearIDMapping();
        uiComponent.updateRightPanel(taskManager.getTasks(), NAME_TODOS);
        uiComponent.updateLeftPanel(taskManager.getEvents(), NAME_EVENTS);
        
        backup.storeBackup(taskManager.getFullList());
        dataStorage.saveTasks(taskManager.getSanitizedList());
        
        // Can only get display id after displaying.
        if ("add".equals(commandInfo.getCommandType())) {
            feedback = new MessageNotifyAdd(taskManager.getLastModifiedTask().getDisplayID());
        }
        
        if (feedback != null) {
            uiComponent.setSuggestionText(feedback.getMessage());
            logger.log(feedback.getMessage());
        }
    }
    

    //@author A0110546R
    /**
     * For the UI to retrieve the list of tasks after it is initialized.
     */
    public static void getTasks() {
        uiComponent.updateRightPanel(taskManager.getTasks(), NAME_TODOS);
        uiComponent.updateLeftPanel(taskManager.getEvents(), NAME_EVENTS);
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
        
        configManager = new ConfigManager();
        taskManager.setDaysToDisplay(configManager.getHomeViewType());
        
        backup = new Backup();
        backup.storeBackup(taskManager.getFullList());
        
        new DateTimeParser("start now"); // To reduce lag on adding the first event.
    }
    
    //@author A0110546R
    public static void main(String[] args) {
        setup();
        launch(args);
    }

    //@author A0110546R
    @Override
    public void start(Stage primaryStage) throws Exception {
        uiComponent = new UIComponent();
        primaryStage.getIcons().add(new Image("/UI/wavewave.png"));
        uiComponent.showStage(primaryStage);
    }
}
