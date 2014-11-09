package UI;

import java.util.ArrayList;

import application.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//@author A0111824R
/**
 * Middle man between UIComponent and Controller.
 * 
 * @author Tan Young Sing
 */
public class UIControllerListener implements EventHandler<KeyEvent> {
    
    private UICmdInputBox cmdInputBox;
    private ArrayList<String> cmdHistory;
    private static int cmdIndex = 0;
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public UIControllerListener(UICmdInputBox cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
        this.cmdHistory = new ArrayList<String>();
        this.cmdHistory.add("");
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    @Override
    public void handle(KeyEvent event) {
    	if (event.getCode().equals(KeyCode.ENTER)) { 
            Controller.runCommandInput(cmdInputBox.getText());
            cmdHistory.add(0, cmdInputBox.getText());
            cmdInputBox.setText("");
            cmdIndex = 0;
            
            cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        } 
        
        if (event.getCode().equals(KeyCode.ESCAPE)) {
        	cmdInputBox.setText("");
        	cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        }
        
        if (event.getCode().isArrowKey() && !cmdHistory.isEmpty()) {
        	if (cmdIndex >= cmdHistory.size()) {
        		cmdIndex = 0;
        	} else if (cmdIndex < 0) {
        		cmdIndex = cmdHistory.size()-1;
        	}
        	
        	if (event.getCode().toString().equals("UP")) {
        		cmdInputBox.setText(cmdHistory.get(cmdIndex));
        		cmdInputBox.resetPositionCaret();
        		cmdIndex++;
        		cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        	} else if (event.getCode().toString().equals("DOWN")) {
        		cmdInputBox.setText(cmdHistory.get(cmdIndex));
        		cmdInputBox.resetPositionCaret();
        		cmdIndex--;
        		cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        	}
        }
    }
}
