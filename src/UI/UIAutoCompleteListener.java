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
    
    private static KeyCode previousKey;
    private boolean isDouble;
    
    private UICmdInputBox cmdInputBox;
    private UIAutoComplete uiAutoComplete;
    private String nextPossibleCommand;
    
    final public String ADD_COMMAND = "ADD";
    final private String EDIT_COMMAND = "EDIT";
    final private String SEARCH_COMMAND = "SEARCH";
    
    final private int EDIT_COMMAND_STRUCTURE = 3;
     
    //@author A0111824R
    /**
     * @param An inputbox object 
     * @author Tan Young Sing
     */
    public UIAutoCompleteListener(UICmdInputBox cmdInputBox) {
        this.uiAutoComplete = new UIAutoComplete(cmdInputBox, this);
        this.cmdInputBox = cmdInputBox;
        this.nextPossibleCommand = "";
        this.isDouble = false;
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public String getCurrentCmd(String cmd) {
    	String[] cmdRetrieval = cmd.split(" ");
    	return cmdRetrieval[0].toUpperCase();
    }
    
    //@author A0111824R
    /**
     * @param the next possible command to be autocompleted.
     * @author Tan Young Sing
     */
    public void setNextPossibleCmd(String cmd) {
        this.nextPossibleCommand = cmd;
    }
    
    //@author A0111824R
    /**
     * @param the current command on the textbox
     * @author Tan Young Sing
     * @return if the current command is an ADD command
     */
    private boolean isAddCommand(String next) {
    	if(next.trim().equalsIgnoreCase(ADD_COMMAND)) {
    		return true;
    	}
    	return false;
    }
    
    //@author A0111824R
    /**
     * @param the current command on the textbox
     * @author Tan Young Sing
     * @return if the current command is an SEARCH command
     */
    private boolean isSearchCommand(String next) {
    	if(next.trim().equalsIgnoreCase(SEARCH_COMMAND)) {
    		return true;
    	}
    	return false;
    }
    
    //@author A0111824R
    /**
     * @param the current command on the textbox
     * @author Tan Young Sing
     * @return if the current command is an EDIT command
     */
    private boolean isEditCommand(String next) {
    	String[] cmdRetrieval = next.split(" ");
    	
    	if(cmdRetrieval[0].equalsIgnoreCase(EDIT_COMMAND) && cmdRetrieval.length == EDIT_COMMAND_STRUCTURE) {
    		return true;
    	}
    	return false;
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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
            		inputBox.setText(nextPossibleCommand + "[ ]");
            	} else {
            		inputBox.setText(nextPossibleCommand);
            	}
            	
                inputBox.positionCaret(inputBox.getText().length());

            	if(isAddCommand(nextPossibleCommand) || isEditCommand(nextPossibleCommand) || isSearchCommand(nextPossibleCommand)) {
            		 inputBox.positionCaret(inputBox.getText().length()-1); 
            	}
            	
                nextPossibleCommand = "";
            	cmdInputBox.setSuggestionText(cmdInputBox.getToolTip());
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
