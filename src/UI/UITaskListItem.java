package UI;

import org.joda.time.DateTime;

import application.Task;

public class UITaskListItem {
	
	private final String LISTITEM_DATE = "date";
	private final String LISTITEM_DEFAULT = "default";
	
	private Task task;
	private DateTime date;
	private String type;

	public UITaskListItem(Task task, DateTime date) {
		if (task == null) {
			this.type = LISTITEM_DATE;
			this.date = date;
		} else {
			this.type = LISTITEM_DEFAULT;
		}
		
		this.task = task;
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
