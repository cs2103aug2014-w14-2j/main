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
            uiComponent.setSuggestionText(messageManager.getMessage(feedback));
            logger.log(messageManager.getMessage(feedback));
            return;
        }

        // Really should command pattern this now!
        // Run the command.
        try {            
            switch (commandInfo.getCommandType()) {
                case "add":
                    taskManager.add(commandInfo);
                    // feedback = new MessageNotifyAdd(taskManager.getLastModifiedTask().getID() + "");
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
                    taskManager.undo(commandInfo, dataStorage.getPastVersion());
                    feedback = new MessageNotifyUndo();
                    break;
                case "complete":
                    taskManager.complete(commandInfo);
                    feedback = new MessageNotifyComplete(commandInfo.getTaskIDs());
                    break;
                case "home":
                    break;
                case "search":
                case "display":
                case "show":
                    taskManager.clearIDMapping();
                    uiComponent.updateRightPanel(taskManager.getSearchedTasks(commandInfo), "Tasks search results");
                    uiComponent.updateLeftPanel(taskManager.getSearchedEvents(commandInfo), "Events search reuslts");
                    return;
                case "quit":
                case "exit":
                    Platform.exit();
                    break;
                	
            }
        } catch (MismatchedCommandException e) {
            logger.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
        
        taskManager.clearIDMapping();
        uiComponent.updateRightPanel(taskManager.getTasks(), "Tasks");
        uiComponent.updateLeftPanel(taskManager.getReminders(), "Events");
        
        dataStorage.saveTasks(taskManager.getList());
        
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
        uiComponent.updateLeftPanel(taskManager.getReminders(), "Events");
    }
    
    //@author A0110546R
    private static void setup() {
        taskManager = new TaskManager();
        dataStorage = new DataStorage();
        messageManager = new MessageManager();
        
        dataStorage.initiateFile();
        
        taskManager.initializeList(dataStorage.retrieveTasks());
    }
    
    //@author A0110546R
    public static void main(String[] args) {
        setup();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiComponent = new UIComponent();
        uiComponent.showStage(primaryStage);
    }
}
