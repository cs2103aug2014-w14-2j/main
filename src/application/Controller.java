package application;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {

    private static DataStorage dataStorage;
    
    private static TaskManager taskManager;

    private static UiComponent uiComponent;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        // Replace with logger later.
        System.out.println("runCommandInput(input: " + input + ") called");

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
            e.printStackTrace();
        }
        uiComponent.updateTaskList(taskManager.getList());

        dataStorage.convertArrayListToJSONArray(taskManager.getList());
        dataStorage.saveTasks();
    }

    public static void main(String[] args) {
        dataStorage = new DataStorage();
        dataStorage.initiateFile();
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        taskManager = new TaskManager();
        uiComponent = new UiComponent();
        uiComponent.showStage(primaryStage);
    }
}
