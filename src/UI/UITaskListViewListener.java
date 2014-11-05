package UI;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//@author A0111824R
/**
 * ListView Event Handler.
 * 
 * @author Tan Young Sing
 */
public class UITaskListViewListener implements EventHandler<KeyEvent> {

	private String msg;
	private UICmdInputBox cmdInput;
	private UITaskListView taskList;
	
    private final String FLOATING = "Task";
    private final String EVENT = "Event";
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UITaskListViewListener(String msg, UICmdInputBox cmdInput, UITaskListView lv) {
		this.cmdInput = cmdInput;
		this.msg = msg;
		this.taskList = lv;
	}

	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private String formatIndexIntoCmd(ObservableList<UITaskListItem> items) {
		String output = "";
		for (UITaskListItem item : items) {
			if(taskList.type.equals(FLOATING)){
				output += item.getTask().getDisplayID() + " ";
			} else if(taskList.type.equals(EVENT)){
				output += item.getTask().getDisplayID() + " ";
			}
		}
		return output.trim();
	}
	
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
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
