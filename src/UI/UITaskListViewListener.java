package UI;

import application.Task;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
	
	public UITaskListViewListener(String msg, UICmdInputBox cmdInput, UITaskListView lv) {
		this.cmdInput = cmdInput;
		this.msg = msg;
		this.taskList = lv;
	}

	private String formatIndexIntoCmd(ObservableList<Task> items) {
		String output = "";
		for (Task item : items) {
			if(taskList.type.equals(FLOATING)){
				output += item.getDisplayID() + " ";
			} else if(taskList.type.equals(EVENT)){
				output += item.getDisplayID() + " ";
			}
		}
		return output.trim();
	}
	
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
