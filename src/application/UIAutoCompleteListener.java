package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

public class UIAutoCompleteListener implements EventHandler<KeyEvent> {

    private TextField cmdInputBox;
    private UIAutoComplete uiAutoComplete;
    
    public UIAutoCompleteListener(TextField cmdInputBox) {
        this.uiAutoComplete = new UIAutoComplete();
        this.cmdInputBox = cmdInputBox;
    }
    
    @Override
    public void handle(KeyEvent event) {
        this.uiAutoComplete.runAutoComplete(cmdInputBox.getText());
        
    }
}
