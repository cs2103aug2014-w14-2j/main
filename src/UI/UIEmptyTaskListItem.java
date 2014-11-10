package UI;

import org.joda.time.DateTime;

import task.Task;

//@author A0111824R
/**
 *
 * @author Tan Young Sing
 */
public class UIEmptyTaskListItem extends UITaskListItem {
    
	private final String LISTITEM_HEADER_TITLE_RIGHT =  "No Scheduled Task";
	private final String LISTITEM_HEADER_TITLE_LEFT =  "No Scheduled Event";
	
	//@author A0111824R
    /**
     * @param Task object to be displayed
     * @param DateTime from JodaTime library
     * @param isLeftPane to indicate the item to be positioned in which panel on the UI
     * @author Tan Young Sing
     */
	public UIEmptyTaskListItem (Task task, DateTime date, boolean isLeftPane) {
		super(task, date, isLeftPane);
		this.isLeftPane = isLeftPane;
		init(task, date);
	}
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void init(Task task, DateTime date) {
		super.setType(LISTITEM_SEPARATOR);
		
		if(isLeftPane) {
			super.setSeparatorTitle(LISTITEM_HEADER_TITLE_LEFT);
		} else {
			super.setSeparatorTitle(LISTITEM_HEADER_TITLE_RIGHT);
		}
	}

}
