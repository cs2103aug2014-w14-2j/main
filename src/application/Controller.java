package application;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

class TaskManager {
    private ArrayList<Task> list;
    private Task task; // Maybe this can act as "last modified task".
    
    public ArrayList<Task> add(Command command) {
        this.task = new Task();
        this.task.setDescription(command.getTaskDesc());
        this.list.add(this.task);
        return this.list;
    }
    
    public ArrayList<Task> edit(Command command) {
        return this.list;
    }
    
    public ArrayList<Task> delete(Command command) {
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);
        
        return this.list;        
    }
    
    public void updateUi(UiComponent uiComponent) {
        //uiComponent.updateFloatingTasks(this.list);
    }
    
    public ArrayList<Task> getList() {
        return this.list;
    }
    
    public ArrayList<Task> initializeList(ArrayList<Task> storedList) {
        list = storedList;
        return this.list;
    }
    
}

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    private static FileManager fileManage;
    
    private static TaskManager taskManager = new TaskManager();

    private static UiComponent uiComponent;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        System.out.println("runCommandInput(input: " + input + ") called");
        
    	fileManage.retrieveLists();
//    	floatingTasks.initializeList(fileManage.convertFloatingJSONArrayToArrayList());
//    	deadlineTasks.initializeList(fileManage.convertDeadlineJSONArrayToArrayList());
//    	timedTasks.initializeList(fileManage.convertTimedJSONArrayToArrayList());
    	
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
        
//        fileManage.convertFloatingArrayListToJSONArray(floatingTasks.getList());
//        fileManage.convertDeadlineArrayListToJSONArray(deadlineTasks.getList());
//        fileManage.convertTimedArrayListToJSONArray(timedTasks.getList());
        fileManage.saveToFiles();
    }

    @Override
    // What is this sia?
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
//        floatingTasks = new FloatingTaskManager();
//        timedTasks = new TimedTaskManager();
//        deadlineTasks = new DeadlineTaskManager();
        fileManage = new FileManager();
        fileManage.initiateFile();
        launch(args);
    }
}
