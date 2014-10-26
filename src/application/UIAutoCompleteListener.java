package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

/**
 * UIAutoCompleteListener: ActionHandler acts as a middle man between UIAutoComplete & UIComponent.
 * 
 * @author Tan Young Sing
 */
public class UIAutoCompleteListener implements EventHandler<KeyEvent> {

    final private String MSG_DEFAULT_PROMPT = "Ask WaveWave to do something ?";
    
    private UICmdInputBox cmdInputBox;
    private UIAutoComplete uiAutoComplete;
    private String nextPossibleCommand;
    
    private String ADD_COMMAND = "ADD";
    
    public UIAutoCompleteListener(UICmdInputBox cmdInputBox) {
        this.uiAutoComplete = new UIAutoComplete(cmdInputBox, this);
        this.cmdInputBox = cmdInputBox;
        this.nextPossibleCommand = "";
    }
    
    public void setNextPossibleCmd(String cmd) {
        this.nextPossibleCommand = cmd;
    }
    
    private boolean isAddCommand(String next) {
    	if(next.trim().equals(ADD_COMMAND)) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void handle(KeyEvent event) {
        TextField inputBox = cmdInputBox.getCmdInputBox();
        this.uiAutoComplete.runAutoComplete(inputBox.getText().trim());  
        
        if(event.getCode().equals(KeyCode.SPACE)) {
            if(nextPossibleCommand.length() != 0) {
            	
            	if(isAddCommand(nextPossibleCommand)) {
            		inputBox.setText(nextPossibleCommand + "[]");
            	} else {
            		inputBox.setText(nextPossibleCommand);
            	}
            	
                inputBox.positionCaret(inputBox.getText().length());

            	if(isAddCommand(nextPossibleCommand)) {
            		 inputBox.positionCaret(inputBox.getText().length()-1);
            	}
            	
                cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
                nextPossibleCommand = "";
            }
        }   
    }
}
