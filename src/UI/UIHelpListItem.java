package UI;

import org.joda.time.DateTime;

import Task.Task;

public class UIHelpListItem extends UITaskListItem {
	
	private String description;
	private boolean isTitle;
	
	public UIHelpListItem(String description, Task task, DateTime date, boolean isLeftPane, boolean isTitle) {
		super(task, date, isLeftPane);
		this.isLeftPane = isLeftPane;
		this.description = description;
		this.isTitle = isTitle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isTitle() {
		return isTitle;
	}
}
