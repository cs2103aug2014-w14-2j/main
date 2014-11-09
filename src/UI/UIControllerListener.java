package UI;

import java.util.ArrayList;

import application.Controller;
import application.InputCommands;
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
    private UIHelp uiHelp;
    private UIComponent ui;
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public UIControllerListener(UICmdInputBox cmdInputBox, UIComponent ui) {
        this.cmdInputBox = cmdInputBox;
        this.cmdHistory = new ArrayList<String>();
        this.cmdHistory.add("");
        this.uiHelp = new UIHelp();
        this.ui = ui;
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void displayHelpLists() {
    	ui.getLeftView().overwriteView(uiHelp.generateLeftHelpList());
    	ui.getRightView().overwriteView(uiHelp.generateRightHelpList());
    	ui.setLeftPanelTitle("COMMAND (A-E)");
    	ui.setRightPanelTitle("COMMAND (F-Z)");
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    @Override
    public void handle(KeyEvent event) {
    	if (event.getCode().equals(KeyCode.ENTER)) { 
    		
    		if(InputCommands.getHelpCommand().equals(cmdInputBox.getText().toUpperCase().trim())) {
    			displayHelpLists();
    		} else {
                Controller.runCommandInput(cmdInputBox.getText());
    		}
    	
            cmdHistory.add(0, cmdInputBox.getText());
            cmdInputBox.setText("");
            cmdIndex = 0;
        } 
        
        if (event.getCode().equals(KeyCode.ESCAPE)) {
        	cmdInputBox.setText("");
        	
        	if(cmdInputBox.getText().length() != 0) {
        		cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        	}
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
        		
        		if(cmdInputBox.getText().length() != 0) { 
        			cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        		}
        	} else if (event.getCode().toString().equals("DOWN")) {
        		cmdInputBox.setText(cmdHistory.get(cmdIndex));
        		cmdInputBox.resetPositionCaret();
        		cmdIndex--;
        		
        		if(cmdInputBox.getText().length() != 0) { 
        			cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
        		}
        	}
        }
    }
}
