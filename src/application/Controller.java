package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {

    private static FileManager fileManage;
    
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

        fileManage.retrieveLists();

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

        fileManage.saveToFiles();
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
        primaryStage.setTitle("WaveWave[0.2]");
        primaryStage.show();
    }

    public static void main(String[] args) {
        fileManage = new FileManager();
        fileManage.initiateFile();
        
        taskManager = new TaskManager();
        
        launch(args);
    }
}
