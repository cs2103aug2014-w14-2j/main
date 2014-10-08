package application;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

abstract class TaskManager<E> {
    protected ArrayList<E> list;
    protected E task; // Maybe this can act as "last modified task".
    
    public abstract ArrayList<E> add(Command command);
    public abstract ArrayList<E> edit(Command command);
    public abstract ArrayList<E> delete(Command command);
    public abstract void updateUi(UiComponent uiComponent);
    
    public ArrayList<E> getList() {
        return this.list;
    }
    
    public ArrayList<E> initializeList(ArrayList<E> storedList) {
        list = storedList;
        return this.list;
    }
    
}

class FloatingTaskManager extends TaskManager<FloatingTask> {    
    public ArrayList<FloatingTask> add(Command command) {
        this.task = new FloatingTask();
        this.task.setDescription(command.getTaskDesc());
        this.list.add(this.task);
        return this.list;
    }
    
    public ArrayList<FloatingTask> edit(Command command) {
        return this.list;
    }
    
    public ArrayList<FloatingTask> delete(Command command) {
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);
        
        return this.list;        
    }
    
    public void updateUi(UiComponent uiComponent) {
        uiComponent.updateFloatingTasks(this.list);
    }
}

class TimedTaskManager extends TaskManager<TimedTask> {    
    public ArrayList<TimedTask> add(Command command) {
        this.task = new TimedTask();
        this.task.setDescription(command.getTaskDesc());
        this.list.add(this.task);
        return this.list;
    }
    
    public ArrayList<TimedTask> edit(Command command) {
        return this.list;
    }
    
    public ArrayList<TimedTask> delete(Command command) {
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);
        
        return this.list;        
    }
    
    
    public void updateUi(UiComponent uiComponent) {
        //uiComponent.updateFloatingTasks(this.list);
    }
}

class DeadlineTaskManager extends TaskManager<DeadlineTask> {    
    public ArrayList<DeadlineTask> add(Command command) {
        this.task = new DeadlineTask();
        this.task.setDescription(command.getTaskDesc());
        this.task.setDeadline(command.getTaskTime());
        this.list.add(this.task);
        return list;
    }
    
    public ArrayList<DeadlineTask> edit(Command command) {
        return this.list;
    }
    
    public ArrayList<DeadlineTask> delete(Command command) {
        // Temporary hack to remove via ArrayList index.
        int taskId = Integer.parseInt(command.getTaskID().substring(1)) - 1;
        this.list.remove(taskId);
        
        return this.list;        
    }
    
    
    public void updateUi(UiComponent uiComponent) {
        uiComponent.updateDeadlineTasks(this.list);
    }
}

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    private static FileManager fileManage;
    
    private static FloatingTaskManager floatingTasks = new FloatingTaskManager();
    private static TimedTaskManager timedTasks = new TimedTaskManager();
    private static DeadlineTaskManager deadlineTasks = new DeadlineTaskManager();

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
    	floatingTasks.initializeList(fileManage.convertFloatingJSONArrayToArrayList());
    	deadlineTasks.initializeList(fileManage.convertDeadlineJSONArrayToArrayList());
    	timedTasks.initializeList(fileManage.convertTimedJSONArrayToArrayList());
    	
        Command command = (new Parser(input)).getCmd();
        TaskManager<?> taskManager = null; // Wild card to avoid warning.
        
        switch (command.getTaskType()) {
            case "floating": taskManager = floatingTasks; break;
            case "timed": taskManager = timedTasks; break;
            case "deadline": taskManager = deadlineTasks; break;
            default: break; // Throw exception here?
        }
        
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
        
        fileManage.convertFloatingArrayListToJSONArray(floatingTasks.getList());
        fileManage.convertDeadlineArrayListToJSONArray(deadlineTasks.getList());
        fileManage.convertTimedArrayListToJSONArray(timedTasks.getList());
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
        floatingTasks = new FloatingTaskManager();
        timedTasks = new TimedTaskManager();
        deadlineTasks = new DeadlineTaskManager();
        fileManage = new FileManager();
        fileManage.initiateFile();
        launch(args);
    }
}
