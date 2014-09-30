package application;

class Task {
    // For skeleton purposes.
    // Move to separate file later.
}

public class Controller {
    // To be singleton or not to be?
	public Controller() {
		
	}
	
	// Actually do all these console outputs even work?

	// Find tasks of a specific type,
	// and return an array (ArrayList?) of tasks.
	public static void getTasks(String type) {
		System.out.println("getTasks(type: " + type + ") called");
	}

	// The main point of command input entry.
	public static void runCommandInput(String input) {
		// Call the parser in here,
		// find out what type of command it is, switch case maybe,
		// then call the appropriate method.
	    
	    // Handle errors here!
	    
		System.out.println("runCommandInput(input: " + input + ") called");
	}
	
	// Maybe the following tasks can be wrapped in another class? Not sure.
	public static void addTask(Task task) {
	    // Maybe we should expect an id?
	    // And return something to runCommandInput probably.
	    System.out.println("addTask(task: " + task.toString() + ") called");
	}
	public static void editTask(int id, Task task) { // This is very interesting.
	    // Not sure how the parameters should work in this case.
	    // Probably an EditTask object?
        System.out.println("editTask(id: " + id + "\n"
                + "\ttask: " + task.toString() + ") called");
	}
	
	// Shit we may need to overload the delete and complete tasks if
	// there are multiple ids!
	public static void deleteTask(int id) {
	    System.out.println("deleteTask(id: " + id + ") called");
	}
	public static void completeTask(int id) {
	    System.out.println("completeTask(id: " + id + ") called");
	}
	
	public static void searchTasks() { // I have no clue what to pass in here!
	    System.out.println("searchTasks() called");
	}
}
