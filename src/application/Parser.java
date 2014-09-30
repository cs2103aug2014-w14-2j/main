public class Parser {
	String command;
	String task_name;
	Parser(String user_input){
		command = user_input.trim().split("\\s+")[0];
		task_name =user_input.replace(getFirstWord(user_input), "").trim();
	}
	String getCommand(){
		return command;
	}
    
	String getTaskName(){
		return task_name;
	}
	private static String getFirstWord(String input) {
		String command_type = input.trim().split("\\s+")[0];
		return command_type;
	}
}
