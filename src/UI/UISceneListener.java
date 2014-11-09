package UI;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//@author A0111824R
/**
 * Handles all Scene Events triggered by users.
 * 
 * @author Tan Young Sing
 */
public class UISceneListener implements EventHandler<KeyEvent>{
	
	private UICmdInputBox cmdInputBox;
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UISceneListener(UICmdInputBox cmdInputBox) {
		this.cmdInputBox = cmdInputBox;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private boolean isValidFocusCharacter(KeyCode keyCode) {
		if(keyCode.isDigitKey() || keyCode.isLetterKey()) {
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
     public void handle(KeyEvent ke) { 
         String currentText = cmdInputBox.getText();
         
         if((!cmdInputBox.isFocused() && isValidFocusCharacter(ke.getCode()))) {
             cmdInputBox.focusCommandInputBox();
             cmdInputBox.setText(currentText);
             cmdInputBox.resetPositionCaret();
         }
     } 
}
