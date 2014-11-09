package UI;

public class UIHelp {
    public static void main(String[] args) {
    String Note ="Note: both uppercase and lowercase commands are supported.\n\n";
    String Add = "ADD:\n"+
            "add [enter task description here] time/period (optional) ! (use exclamation mark to set priority to high (optional)\n\n";
    String Complete = "COMPLETE:\n"+
            "complete task ID(s): to indicate a task or multiple tasks are completed, task IDs are separated by a blank space.\n\n";
    String Delete = "DELETE:\n"+
            "delete task ID(s): to indicate a task or multiple tasks are completed, task IDs are separated by a blank space.\n\n"; 
    String Edit = "EDIT:\n"+
            "edit task ID : press Space key after task ID is entered, task description and\n"+
            "the corresponding time are auto-completed here. Task can then be edited.\n\n";
    String Home = "HOME:\n" +
            "home: go to the home view where only today and the next day’s tasks are displayed.\n\n";
    String Search = "SEARCH:\n"+
            "A combination to search keyword, time, priority and completed tasks are supported.\n"+
            "search [enter keyword here] time/period(optional) !(optional) complete(optional)\n";
    String Show = "SHOW:\n"+
            "show time/period: to show tasks at a specific time or within a period of time.\n\n"; 
    String Undo = "UNDO:\n"+
            "undo: to undo a previous operation\n\n";
    String Quit = "QUIT:\n"+
            "quit: to quit the program.";
    
    String content = Note+Add+Complete+Delete+Edit+Home+Search+Show+Undo+Quit;
    System.out.println(content);

    }
    
    
}
