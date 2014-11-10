package UI;

import org.joda.time.DateTime;

import task.Task;

//@author A0111824R
/**
 *
 
 */
public class UILeftTaskListItem extends UITaskListItem {
	
    //@author A0111824R
    /**
     * @param Task the task object to be displayed on the listview
     * @param date of the assigned task
     * @param isLeftPane to indicate if it is the listview on the left
     
     */
	public UILeftTaskListItem (Task task, DateTime date, boolean isLeftPane) {
		super(task, date, isLeftPane);
		init(task, date);
	}
	
    //@author A0111824R
    /**
     *
     
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
