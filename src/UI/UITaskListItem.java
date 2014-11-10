package UI;

import org.joda.time.DateTime;

import task.Task;

//@author A0111824R
/**
 * 
 
 */
public class UITaskListItem {
	
	protected final String LISTITEM_HEADER = "header";
	protected final String LISTITEM_DEFAULT = "default";
	protected final String LISTITEM_SEPARATOR = "float_separator";
	
	private Task task;
	private DateTime date;
	private String type;
	private int numOfTasks;
	private String separatorTitle;
	protected boolean isLeftPane;

    //@author A0111824R
    /**
     *
     
     */
	public UITaskListItem(Task task, DateTime date, boolean isLeftPane) {
		this.isLeftPane = isLeftPane;
		this.numOfTasks = 0;
		this.task = task;
	}
	
    //@author A0111824R
    /**
     *
     
     */
	public boolean isLeftPane() {
		return this.isLeftPane;
	}
	
    //@author A0111824R
    /**
     *
     
     */
	public String getSeparatorTitle() {
		return this.separatorTitle;
	}
	
    //@author A0111824R
    /**
     *
     
     */
	protected void setSeparatorTitle(String title) {
		this.separatorTitle = title;
	}
	
    //@author A0111824R
    /**
     *
     
     */
    protected void setType(String type) {
		this.type = type;
	}

	//@author A0111824R
    /**
     *
     
     */
	public int getNumberTask() {
		return this.numOfTasks;
	}
	
    //@author A0111824R
    /**
     *
     
     */
	public void incrementNumOfTask() {
		numOfTasks++;
	}

    //@author A0111824R
    /**
     *
     
     */
	public Task getTask() {
		return task;
	}

    //@author A0111824R
    /**
     *
     
     */
	public DateTime getDate() {
		return date;
	}

    //@author A0111824R
    /**
     *
     
     */
    protected void setDate(DateTime date) {
		this.date = date;
	}

	//@author A0111824R
    /**
     *
     
     */
	public String getType() {
		return type;
	}
}
