package UI;

import Task.Task;

import org.joda.time.DateTime;

//@author A0111824R
/**
 *
 * @author Tan Young Sing
 */
public class UILeftTaskListItem extends UITaskListItem {
	
    //@author A0111824R
    /**
     * @param Task the task object to be displayed on the listview
     * @param date of the assigned task
     * @param isLeftPane to indicate if it is the listview on the left
     * @author Tan Young Sing
     */
	public UILeftTaskListItem (Task task, DateTime date, boolean isLeftPane) {
		super(task, date, isLeftPane);
		init(task, date);
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void init(Task task, DateTime date) {
		if (task == null) {
			super.setType(LISTITEM_HEADER);
			super.setDate(date);
		} else {
			super.setType(LISTITEM_DEFAULT);
		}
	}
}
