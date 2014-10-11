package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class CmdInputBox {

    private TextField cmdInputBox;
    private Text suggestionText;
    
    private final int CMDINPUT_HEIGHT = 35;
    private final String CMDINPUT_PROMPT_TEXT = "Ask WaveWave to do something ?";
    
    public CmdInputBox(Text suggestionText) {
        this.suggestionText = suggestionText;
        cmdInputBox = new TextField();
        setInputBoxProperty();
        addEventListener();
    }
    
    private void setInputBoxProperty() {
        cmdInputBox.setPrefHeight(CMDINPUT_HEIGHT);
        cmdInputBox.setPromptText(CMDINPUT_PROMPT_TEXT);
    }
    
    private void addEventListener() {
        cmdInputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override 
            public void handle(KeyEvent ke) { 
                if (ke.getCode().equals(KeyCode.ENTER)) { 
                    Controller.runCommandInput(cmdInputBox.getText());  
                } 
            } 
        });
    }
    
    public void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
    
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}
