package UI;

import org.joda.time.DateTime;

import application.Task;

public class UITaskListItem {
	
	private final String LISTITEM_DATE = "date";
	private final String LISTITEM_DEFAULT = "default";
	
	private Task task;
	private DateTime date;
	private String type;
	private int numOfTasks;

	public UITaskListItem(Task task, DateTime date) {
		if (task == null) {
			this.type = LISTITEM_DATE;
			this.date = date;
		} else {
			this.type = LISTITEM_DEFAULT;
		}
		
		this.numOfTasks = 0;
		this.task = task;
	}
	
	public int getNumberTask() {
		return this.numOfTasks;
	}
	
	public void incrementNumOfTask() {
		numOfTasks++;
	}

	public Task getTask() {
		return task;
	}

	public DateTime getDate() {
		return date;
	}

	public String getType() {
		return type;
	}
}
