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
public class UIGuideMessage implements EventHandler<KeyEvent> {
    
    private UICmdInputBox cmdInputBox;
    
    public UIGuideMessage(UICmdInputBox cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
    }
    
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) { 
           // do something
        } 
    }
}
