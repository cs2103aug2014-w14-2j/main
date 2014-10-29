package UI;

import org.joda.time.DateTime;

import application.Task;

public class UITaskListItem {
	
	private String TYPE_HEADING = "date";
	private String TYPE_DEFAULT = "default";
	
	private Task task;
	private String type;
	

	public UITaskListItem(Task task, DateTime date) {
		if(task == null) {
			this.task = null;
			type =  TYPE_HEADING;
		} else {
			this.task = task;
			type = TYPE_DEFAULT;
		}
	}
	
	public String getType() {
		return type;
	}
	
	public Task getTask() {
		return task;
	}
}
