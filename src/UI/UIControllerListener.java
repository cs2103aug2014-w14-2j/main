package UI;

import java.util.ArrayList;

import application.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Middle man between UIComponent and Controller.
 * 
 * @author Tan Young Sing
 */
public class UIControllerListener implements EventHandler<KeyEvent> {
    
    private UICmdInputBox cmdInputBox;
    private ArrayList<String> cmdHistory;
    private static int cmdIndex = 0;
    
    public UIControllerListener(UICmdInputBox cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
        this.cmdHistory = new ArrayList<String>();
    }
    
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) { 
            Controller.runCommandInput(cmdInputBox.getText());
            cmdHistory.add(0, cmdInputBox.getText());
            cmdInputBox.setText("");
            
            cmdInputBox.setGuideMsgText(cmdInputBox.getToolTip());
            UICmdInputBox.toolTipCounter++;
            if(UICmdInputBox.toolTipCounter > 3) {
            	UICmdInputBox.toolTipCounter = 0;
            } 
        } 
        
        if (event.getCode().equals(KeyCode.ESCAPE)) {
        	cmdInputBox.setText("");
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
        	} else if (event.getCode().toString().equals("DOWN")) {
        		cmdInputBox.setText(cmdHistory.get(cmdIndex));
        		cmdInputBox.resetPositionCaret();
        		cmdIndex--;
        	}
        }
    }
}
