package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Middle man between UIComponent and Controller.
 * 
 * @author Tan Young Sing
 */
public class UIControllerListener implements EventHandler<KeyEvent> {
    
    private TextField cmdInputBox;
    
    public UIControllerListener(TextField cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
    }
    
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) { 
            Controller.runCommandInput(cmdInputBox.getText()); 
            cmdInputBox.setText("");
        } 
    }

}
