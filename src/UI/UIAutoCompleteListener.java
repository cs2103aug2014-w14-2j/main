package UI;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

//@author A0111824R
/**
 * UIAutoCompleteListener: ActionHandler acts as a middle man between UIAutoComplete & UIComponent.
 * 
 * @author Tan Young Sing
 */
public class UIAutoCompleteListener implements EventHandler<KeyEvent> {

    final private String MSG_DEFAULT_PROMPT = "Ask WaveWave to do something ?";
    
    private static KeyCode previousKey;
    private boolean isDouble;
    
    private UICmdInputBox cmdInputBox;
    private UIAutoComplete uiAutoComplete;
    private String nextPossibleCommand;
    
    final public String ADD_COMMAND = "ADD";
    final private String EDIT_COMMAND = "EDIT";
    final private String SEARCH_COMMAND = "SEARCH";
     
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public UIAutoCompleteListener(UICmdInputBox cmdInputBox) {
        this.uiAutoComplete = new UIAutoComplete(cmdInputBox, this);
        this.cmdInputBox = cmdInputBox;
        this.nextPossibleCommand = "";
        this.isDouble = false;
    }
    
    public void setNextPossibleCmd(String cmd) {
        this.nextPossibleCommand = cmd;
    }
    
    private boolean isAddCommand(String next) {
    	if(next.trim().equalsIgnoreCase(ADD_COMMAND)) {
    		return true;
    	}
    	return false;
    }
    
    private boolean isSearchCommand(String next) {
    	if(next.trim().equalsIgnoreCase(SEARCH_COMMAND)) {
    		return true;
    	}
    	return false;
    }
    
    
    private boolean isEditCommand(String next) {
    	String[] cmdRetrieval = next.split(" ");
    	
    	if(cmdRetrieval[0].equalsIgnoreCase(EDIT_COMMAND) && cmdRetrieval.length == 3) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void handle(KeyEvent event) {
        TextField inputBox = cmdInputBox.getCmdInputBox();
        this.uiAutoComplete.runAutoComplete(inputBox.getText().trim());  
        
        if(event.getCode().equals(KeyCode.SPACE)) {
        	
        	if( previousKey != null ) {
        		if(previousKey.equals(KeyCode.SPACE)) {
        			int indexToShift = inputBox.getText().lastIndexOf("]");
        			if(indexToShift != -1) {
        				String currentText = inputBox.getText();
        				String extract = " [" + currentText.substring(currentText.indexOf("[") + 1, currentText.indexOf("]")).trim() + "]";
        				String front = currentText.substring(0, currentText.indexOf("[")-1);
        				String replaceString = front + extract + " ";
        				
        				inputBox.setText(replaceString);
        				inputBox.positionCaret(replaceString.length()+1);
        			}
        				
        			isDouble = true;
        		} 
        	}
        	
            if(nextPossibleCommand.length() != 0) {
            	if(isAddCommand(nextPossibleCommand) || isSearchCommand(nextPossibleCommand)) {
            		inputBox.setText(nextPossibleCommand + "[]");
            	} else {
            		inputBox.setText(nextPossibleCommand);
            	}
            	
                inputBox.positionCaret(inputBox.getText().length());

            	if(isAddCommand(nextPossibleCommand) || isEditCommand(nextPossibleCommand) || isSearchCommand(nextPossibleCommand)) {
            		 inputBox.positionCaret(inputBox.getText().length()-1); 
            	}
            	
                cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
                nextPossibleCommand = "";
                
                isDouble = true;
            }
        }
        
        if(!isDouble) {
        	previousKey = event.getCode();
        } else {
        	previousKey = null;
        	isDouble = false;
        }
    }
}
