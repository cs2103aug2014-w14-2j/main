package UI;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//@author A0111824R
/**
 * ListView Event Handler.
 * 
 
 */
public class UITaskListViewListener implements EventHandler<KeyEvent> {

	private String msg;
	private UICmdInputBox cmdInput;
	private UITaskListView taskList;
	
	//@author A0111824R
    /**
     * @param taskList - ListView from the UI
     
     */
	public UITaskListViewListener(String msg, UICmdInputBox cmdInput, UITaskListView lv) {
		this.cmdInput = cmdInput;
		this.msg = msg;
		this.taskList = lv;
	}

	
	//@author A0111824R
    /**
     *
     
     */
	private String formatIndexIntoCmd(ObservableList<UITaskListItem> items) {
		String output = "";
		for (UITaskListItem item : items) {
			output += item.getTask().getDisplayID() + " ";
		}
		return output.trim();
	}
	
	//@author A0111824R
    /**
     * @param KeyEvent triggered only on keyboard event
     
     */
	@Override
	public void handle(KeyEvent ke) {
    	if(ke.getCode() == KeyCode.DELETE) {
    		String cmdOutput = formatIndexIntoCmd(taskList.getSelectedItem());		
     		cmdInput.getCmdInputBox().setText(String.format(msg, cmdOutput));
     		cmdInput.focusCommandInputBox();
     		cmdInput.resetPositionCaret();
    	}
	}
}
