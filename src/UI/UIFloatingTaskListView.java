package UI;

import java.util.ArrayList;

import application.Task;

//@author A0111824R
/**
 *
 * @author Tan Young Sing
 */
public class UIFloatingTaskListView extends UITaskListView {
	
	private final String CMD_DELETE_FLOATING_TASK = "DELETE %s";
	private final String UI_SEPARATOR_REMINDER = "REMINDERS";
	private final String UI_SEPARATOR_DEADLINE = "DEADLINES";
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UIFloatingTaskListView(UICmdInputBox cmdInputBox) {
		super(cmdInputBox);
		initializeListener();
	}
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void initializeListener() {
		taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_FLOATING_TASK, cmdInputBox, this));
	}
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    protected ArrayList<UITaskListItem> generateEmptyList(ArrayList<Task> items, boolean isLeftPane) {
    	ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
    	UITaskListItem item = new UIEmptyTaskListItem(null, null, false);
    	listItems.add(item);
    	return listItems;
    }
	
    protected ArrayList<UITaskListItem> generateListItems(ArrayList<Task> items) {
    	ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
    	UITaskListItem currentHeader = null;
    	
    	for(int i =0; i<items.size(); i++) {
    		Task listItem = items.get(i);
    		
    		if(currentHeader == null) {
    			if(listItem.getEndDate() == null) {
    				currentHeader = new UIRightTaskListItem(null, null, false);
    				listItems.add(currentHeader);
    			} else if(listItem.getEndDate() != null) {
    				currentHeader = new UIRightTaskListItem(null, listItem.getEndDate(), false);
    				listItems.add(currentHeader);
    			} 
    		} else {
    			if(listItem.getEndDate() != null && !currentHeader.getSeparatorTitle().equalsIgnoreCase(UI_SEPARATOR_DEADLINE)) {
    				currentHeader = new UIRightTaskListItem(null, listItem.getEndDate(), false);
    				listItems.add(currentHeader);
    			} else if(listItem.getEndDate() == null && !currentHeader.getSeparatorTitle().equalsIgnoreCase(UI_SEPARATOR_REMINDER)){
    				currentHeader = new UIRightTaskListItem(null, null, false);
    				listItems.add(currentHeader);
    			}
    		}
    	
    		listItems.add(new UIRightTaskListItem(listItem, listItem.getDate(), false));
    		currentHeader.incrementNumOfTask();
    	}
    
    	return listItems;
    }
}
