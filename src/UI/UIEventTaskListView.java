package UI;

import java.util.ArrayList;

import org.joda.time.DateTime;

import task.Task;

//@author A0111824R
/**
 *
 
 */
public class UIEventTaskListView extends UITaskListView {
	
	private final String CMD_DELETE_EVENT_TASK = "DELETE %s";
	
	//@author A0111824R
    /**
     * @param cmdInputBox - the inputbox object on the UI
     
     */
	public UIEventTaskListView(UICmdInputBox cmdInputBox) {
		super(cmdInputBox);
		initializeListener();
	}
	
	//@author A0111824R
    /**
     *
     
     */
	private void initializeListener() {
		taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_EVENT_TASK, cmdInputBox, this));
	}
	
	//@author A0111824R
    /**
     * @param items - ArrayList of task to be displayed on the ListView
     * @param isLeftPane - indicate if it is for the left panel
     
     * @return convert the arraylist of tasks into UITaskListItem
     */
	protected ArrayList<UITaskListItem> generateEmptyList(ArrayList<Task> items, boolean isLeftPane) {
		ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
	    UITaskListItem item = new UIEmptyTaskListItem(null, null, true);
	    listItems.add(item);
	    return listItems;
	}
			
	//@author A0111824R
    /**
     * @param takes in an arrayList of Tasks object to be displayed on the Listview
     
     * @return convert the arrayList of task to UITaskListItem
     */
    protected ArrayList<UITaskListItem> generateListItems(ArrayList<Task> items) {
    	DateTime currentDate = null;
    	ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
    	UITaskListItem currentHeader = null;
    	
    	for(int i = 0; i<items.size(); i++) {
    		Task t = items.get(i);
    		if(currentHeader == null) {
        		currentDate = t.getDate();
        		currentHeader = new UILeftTaskListItem(null, t.getDate(), true);
        		listItems.add(currentHeader);
    		} else {
    			if(currentDate.toString("y").equals(t.getDate().toString("y"))) {
    				if(!currentDate.toString("D").equals(t.getDate().toString("D"))) {
    					currentDate = t.getDate();
    					currentHeader = new UILeftTaskListItem(null, t.getDate(), true);
    					listItems.add(currentHeader);
    				}
    			} else {
    				currentDate = t.getDate();
    				currentHeader = new UILeftTaskListItem(null, t.getDate(), true);
    				listItems.add(currentHeader);
    			}
    		}	
    		listItems.add(new UILeftTaskListItem(t, t.getDate(), true));
    		currentHeader.incrementNumOfTask();
    	}

    	return listItems;
    }
}
