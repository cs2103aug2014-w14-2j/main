package UI;

import org.joda.time.DateTime;

import task.Task;

//@author A0111824R
/**
 *
 
 */
public class UIRightTaskListItem extends UITaskListItem {

	private final String LISTITEM_HEADER_TITLE_DEADLINE =  "DEADLINES";
	private final String LISTITEM_HEADER_TITLE_REMINDER =  "REMINDERS";
	
    //@author A0111824R
    /**
     *
     
     */
	public UIRightTaskListItem (Task task, DateTime date, boolean isLeftPane) {
		super(task, date, isLeftPane);
		init(task, date);
	}
	
    //@author A0111824R
    /**
     *
     
     */
	private void init(Task task, DateTime date) {
		if (task == null && date != null) {
			super.setType(LISTITEM_SEPARATOR);
			super.setSeparatorTitle(LISTITEM_HEADER_TITLE_DEADLINE);
		} else if(task == null && date == null) { 
			super.setType(LISTITEM_SEPARATOR);
			super.setSeparatorTitle(LISTITEM_HEADER_TITLE_REMINDER);
		}else {
			super.setType(LISTITEM_DEFAULT);
		}
	}
}
