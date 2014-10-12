package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

public class UIAutoCompleteListener implements EventHandler<KeyEvent> {

    private TextField cmdInputBox;
    private UIAutoComplete uiAutoComplete;
    private String nextPossibleCommand;
    
    public UIAutoCompleteListener(UICmdInputBox cmdInputBox) {
        this.uiAutoComplete = new UIAutoComplete(cmdInputBox, this);
        this.cmdInputBox = cmdInputBox.getCmdInputBox();
        this.nextPossibleCommand = "";
    }
    
    public void setNextPossibleCmd(String cmd) {
        this.nextPossibleCommand = cmd;
    }
    
    @Override
    public void handle(KeyEvent event) {
        this.uiAutoComplete.runAutoComplete(cmdInputBox.getText().trim());  
        
        if(event.getCode().equals(KeyCode.SPACE)) {
            if(nextPossibleCommand.length() != 0) {
                cmdInputBox.setText(nextPossibleCommand);
                cmdInputBox.positionCaret(cmdInputBox.getText().length());
                nextPossibleCommand = "";
            }
        }
        
    }
}
