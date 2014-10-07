package application;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller {
    // To be singleton or not to be?
	public Controller() {
		
	}
	
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
		// Call the parser in here,
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
}
