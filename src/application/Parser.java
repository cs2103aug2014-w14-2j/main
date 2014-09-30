package application;

/** This class implements a Parser to parse an input string
 * 
 * @author jinyu
 * @version 1.0
 */

public class Parser {
	private String command_type;
	private String task_name;
	
	/**
	 * This constructs a parser object with an user input 
	 * @param user_input   the one line command statement the user inputs
	 */
	
	Parser(String user_input){
		command_type = user_input.trim().split("\\s+")[0];
		task_name =user_input.replace(getFirstWord(user_input), "").trim();
	}
	
	/**
	 * This returns the command type to be executed 
	 * @return command type
	 */
	public String getCommand(){
		return command_type;
	}
    
	/**
	 * This returns the task ID 
	 * @return task ID
	 */
	public String getTaskName(){
		return task_name;
	}
	
	private static String getFirstWord(String input) {
		String command = input.trim().split("\\s+")[0];
		return command;
	}
}
