package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {

    private static DataStorage storage;
    
    private static TaskManager taskManager;

    private static UiComponent uiComponent;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {

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
        try {
            showStage(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showStage(Stage primaryStage) {
        uiComponent = new UiComponent();
        primaryStage.setScene(uiComponent.getScene());
        primaryStage.setResizable(false);
        primaryStage.setTitle("WaveWave[0.1]");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // floatingTasks = new FloatingTaskManager();
        // timedTasks = new TimedTaskManager();
        // deadlineTasks = new DeadlineTaskManager();
        storage = new DataStorage();
        storage.initiateFile();
        
        taskManager = new TaskManager();
        
        launch(args);
    }
}
