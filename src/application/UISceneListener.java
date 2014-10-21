package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Handles all Scene Events triggered by users.
 * 
 * @author Tan Young Sing
 */
public class UISceneListener implements EventHandler<KeyEvent>{
	
	private UICmdInputBox cmdInputBox;
	
	public UISceneListener(UICmdInputBox cmdInputBox) {
		this.cmdInputBox = cmdInputBox;
	}
	
	private boolean isValidFocusCharacter(String keyCodeText) {
		if(keyCodeText.length() == 1 || keyCodeText.startsWith("DIGIT")) {
			return true;
		}
		return false;
	}
	
	 @Override 
     public void handle(KeyEvent ke) { 
         String currentText = cmdInputBox.getText();
         String keyCodeText = ke.getCode().toString();
         
         if((!cmdInputBox.isFocused() && isValidFocusCharacter(keyCodeText))) {
             cmdInputBox.focusCommandInputBox();
             cmdInputBox.setText(currentText);
             cmdInputBox.resetPositionCaret();
         }
     } 
}
