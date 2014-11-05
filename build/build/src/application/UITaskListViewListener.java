package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UITaskListViewListener implements EventHandler<KeyEvent> {

	private String msg;
	private UICmdInputBox cmdInput;
	private UITaskListView taskList;
	
	public UITaskListViewListener(String msg, UICmdInputBox cmdInput, UITaskListView lv) {
		this.cmdInput = cmdInput;
		this.msg = msg;
		this.taskList = lv;
	}

	@Override
	public void handle(KeyEvent ke) {
    	if(ke.getCode() == KeyCode.DELETE) {
    		int index = taskList.getSelectedItemIndex()+1;			
     		cmdInput.getCmdInputBox().setText(String.format(msg, index));
     		cmdInput.focusCommandInputBox();
     		cmdInput.resetPositionCaret();
    	}
	}
}
