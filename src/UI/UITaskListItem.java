package UI;

import org.joda.time.DateTime;

import application.Task;

//@author A0111824R
/**
 *
 * @author Tan Young Sing
 */
public class UITaskListItem {
	
	private final String LISTITEM_HEADER = "header";
	private final String LISTITEM_DEFAULT = "default";
	private final String LISTITEM_SEPARATOR = "float_separator";
	
	private Task task;
	private DateTime date;
	private String type;
	private int numOfTasks;
	private String separatorTitle;
	private String pane;

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UITaskListItem(Task task, DateTime date, String pane) {
		if(pane.equals("Left")) {
			if (task == null) {
				this.type = LISTITEM_HEADER;
				this.date = date;
			} else {
				this.type = LISTITEM_DEFAULT;
			}
		} else if(pane.equals("Right")) {
			if (task == null && date != null) {
				this.type = LISTITEM_SEPARATOR;
				this.separatorTitle = "DEADLINES";
			} else if(task == null && date == null) { 
				this.type = LISTITEM_SEPARATOR;
				this.separatorTitle = "REMINDERS";
			}else {
				this.type = LISTITEM_DEFAULT;
			}
		} else if(pane.equals("EMPTY")) {
			this.type = LISTITEM_SEPARATOR;
			this.separatorTitle = "No Scheduled Task";
		}
		
		this.pane = pane;
		this.numOfTasks = 0;
		this.task = task;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public String getPane() {
		return pane;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public String getSeparatorTitle() {
		return this.separatorTitle;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public int getNumberTask() {
		return this.numOfTasks;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public void incrementNumOfTask() {
		numOfTasks++;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public Task getTask() {
		return task;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public DateTime getDate() {
		return date;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public String getType() {
		return type;
	}
}
