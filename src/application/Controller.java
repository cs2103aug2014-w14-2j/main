package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller extends Application {
    private static ArrayList<FloatingTask> floatingTasks;
    private static ArrayList<TimedTask> timedTasks;
    private static ArrayList<DeadlineTask> deadlineTasks;
    
	
	// Actually do all these console outputs even work?

	/**
	 * Returns an ArrayList of tasks of a specific type.
	 * @param type "events" (timed), "reminders" (floating), "deadlines" (deadlines)
	 */
	public static void getTasks(String type) {
		System.out.println("getTasks(type: " + type + ") called");
	}

	/**
	 * Executes the command entered.
	 * @param input The entire command input.
	 */
	public static void runCommandInput(String input) {
	    Command command = (new Parser(input)).getCmd();
		
	    String commandType = command.getCommandType();
	    String taskType = command.getTaskType();
	    String taskID, taskDesc;
	    int id;
	    switch(commandType) {
	        case "add":
	            taskDesc = command.getTaskDesc();
	            switch (taskType) {
	                case "floating":
	                    FloatingTask newFT = new FloatingTask();
	                    newFT.setDescription(taskDesc);
	                    floatingTasks.add(newFT);
	                    break;
	                case "timed":
	                    TimedTask newTT = new TimedTask();
	                    newTT.setDescription(taskDesc);
	                    timedTasks.add(newTT);
	                    break;
	                case "deadline":
	                    DeadlineTask newDT = new DeadlineTask();
	                    newDT.setDescription(taskDesc);
	                    deadlineTasks.add(newDT);
	                    break;
	                    
	            }
	            break;
	        case "delete":
	            taskID = command.getTaskID().substring(1); // Strip first letter.
	            id = Integer.parseInt(taskID) - 1; // Woo magic numbers.
	            switch (taskType) {
	                case "floating":
	                    floatingTasks.remove(id);
	                    break;
                    case "timed":
                        timedTasks.remove(id);
                        break;
	                case "deadline":
                        deadlineTasks.remove(id);
	                    break;	                
	            }
	            break;
	        case "edit":
	            taskID = command.getTaskID().substring(1); // Strip first letter.
                id = Integer.parseInt(taskID) - 1; // Woo magic numbers.
	            taskDesc = command.getTaskDesc();
	            switch (taskType) {
	                case "floating":
	                    floatingTasks.get(id).setDescription(taskDesc);
	                    break;
	                case "timed":
                        timedTasks.get(id).setDescription(taskDesc);
	                    break;
	                case "deadline":
                        deadlineTasks.get(id).setDescription(taskDesc);
	                    break;
	            }
	    }
		// find out what type of command it is, switch case maybe,
		// then call the appropriate method.
	    
	    // Handle errors here!
	    
		System.out.println("runCommandInput(input: " + input + ") called");
	}

	/**
	 * Adds a new task.
	 * @param task Task object.
	 */
	public static void addTask(Task task) {
	    // Maybe we should expect an id?
	    // And return something to runCommandInput probably.
	    System.out.println("addTask(task: " + task.toString() + ") called");
	}
	/**
	 * Edits a given task object.
	 * @param id id of the task to edit.
	 * @param task new task information.
	 */
	public static void editTask(int id, Task task) { // This is very interesting.
	    // Not sure how the parameters should work in this case.
	    // Probably an EditTask object?
        System.out.println("editTask(id: " + id + "\n"
                + "\ttask: " + task.toString() + ") called");
	}
	
	// Shit we may need to overload the delete and complete tasks if
	// there are multiple ids!
	
	/**
	 * Deletes a given task object.
	 * @param id
	 */
	public static void deleteTask(int id) {
	    System.out.println("deleteTask(id: " + id + ") called");
	}
	/**
	 * Marks a given task object as complete.
	 * @param id
	 */
	public static void completeTask(int id) {
	    System.out.println("completeTask(id: " + id + ") called");
	}
	
	/**
	 * Searches tasks.
	 */
	public static void searchTasks() { // I have no clue what to pass in here!
	    System.out.println("searchTasks() called");
	}
	
    @Override
    public void start(Stage primaryStage) {
        try {
            showStage(primaryStage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void showStage(Stage primaryStage) {
        UiComponent uiComponent = new UiComponent();
        primaryStage.setScene(uiComponent.getScene());
        primaryStage.setResizable(false);
        primaryStage.setTitle("WaveWave[0.1]");
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        floatingTasks = new ArrayList<FloatingTask>();
        timedTasks = new ArrayList<TimedTask>();
        deadlineTasks = new ArrayList<DeadlineTask>();
        launch(args);
    }
}

