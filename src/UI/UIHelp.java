package UI;

import java.util.ArrayList;

import application.InputCommands;
import edu.emory.mathcs.backport.java.util.Collections;

public class UIHelp { 
	private ArrayList<String> commandList;
	private int midPoint;
	
	private final String UI_HELP_NOTICE = "Note: both uppercase and lowercase commands are supported.";
	private final String UI_HELP_ADD =  "add [enter task description here] time/period (optional) ! (use exclamation mark to set priority to high (optional)";
	private final String UI_HELP_COMPLETE = "complete task ID(s): to indicate a task or multiple tasks are completed, task IDs are separated by a blank space.";
	private final String UI_HELP_DELETE = "delete task ID(s): to indicate a task or multiple tasks are completed, task IDs are separated by a blank space.";
	private final String UI_HELP_EDIT = "edit task ID : press Space key after task ID is entered, task description and the corresponding time are auto-completed here. Task can then be edited.";
	private final String UI_HELP_HOME = "home: go to the home view where only today and the next day tasks are displayed.";
	private final String UI_HELP_SEARCH = "A combination to search keyword, time, priority and completed tasks are supported. search [enter keyword here] time/period(optional) !(optional) complete(optional)";
	private final String UI_HELP_SHOW = "show time/period: to show tasks at a specific time or within a period of time.";
	private final String UI_HELP_UNDO = "undo: to undo a previous operation";
	private final String UI_HELP_QUIT = "quit: to quit the program.";
	
	public UIHelp() {
		commandList = InputCommands.getCommandList();
		Collections.sort(commandList);
		this.midPoint = this.commandList.size()/2;
	}
	
	public ArrayList<UITaskListItem> generateLeftHelpList() {
		ArrayList<UITaskListItem> helpList = new ArrayList<UITaskListItem>();
		
		for(int i = 0; i < midPoint; i++) {
			String cmd = commandList.get(i);
			String description = getHelpText(cmd);
			
			if(description.length() != 0) {
				UITaskListItem titleListItem = new UIHelpListItem(cmd, null, null, true, true);
				helpList.add(titleListItem);
			
				UITaskListItem helpDescriptionItem = new UIHelpListItem(description, null, null, true, false);
				helpList.add(helpDescriptionItem);
			}
		}	
		return helpList;
	}
	
	public ArrayList<UITaskListItem> generateRightHelpList() {
		ArrayList<UITaskListItem> helpList = new ArrayList<UITaskListItem>();
		
		for (int i= midPoint; i < commandList.size(); i++) {
			String cmd = commandList.get(i);
			String description = getHelpText(cmd);
			
			if(description.length() != 0) {
				UITaskListItem titleListItem = new UIHelpListItem(cmd, null, null, true, true);
				helpList.add(titleListItem);
			
				UITaskListItem helpDescriptionItem = new UIHelpListItem(description, null, null, true, false);
				helpList.add(helpDescriptionItem);
			}
		}
		return helpList;
	}
	
	private String getHelpText(String cmd) {
		switch(cmd.toUpperCase()) {
			case "ADD": return UI_HELP_ADD;
			case "COMPLETE": return UI_HELP_COMPLETE;
			case "DELETE": return UI_HELP_DELETE;
			case "EDIT": return UI_HELP_EDIT;
			case "HOME": return UI_HELP_HOME;
			case "SEARCH": return UI_HELP_SEARCH;
			case "SHOW": return UI_HELP_SHOW;
			case "UNDO": return UI_HELP_UNDO;
			case "QUIT": return UI_HELP_QUIT;
			default: return "";
		}
	}
	
	
	
    
}
