package UI;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Abstracted TextField Class.
 * 
 * @author Tan Young Sing
 */
public class UICmdInputBox {

    private TextField cmdInputBox;
    private Text suggestionText;
    private Text guideMsgText;
    
    private final int CMDINPUT_HEIGHT = 35;
    private final String CMDINPUT_PROMPT_TEXT = "Ask WaveWave to do something ?";
    
    public UICmdInputBox(Text suggestionText, Text guideMsgText) {
        this.suggestionText = suggestionText;
        this.guideMsgText = guideMsgText;
        this.cmdInputBox = new TextField();
        
        setInputBoxProperty();
        addKeyPressedListener();
        addKeyTypedListener();
        addKeyReleasedListener();
    }
    
    private void setInputBoxProperty() {
        cmdInputBox.setPrefHeight(CMDINPUT_HEIGHT);
        cmdInputBox.setPromptText(CMDINPUT_PROMPT_TEXT);
    }
    
    private void addKeyPressedListener() {
        cmdInputBox.setOnKeyPressed(new UIControllerListener(this));
    }
    
    private void addKeyReleasedListener() {
        cmdInputBox.setOnKeyReleased(new UIAutoCompleteListener(this));
    }
    
    private void addKeyTypedListener() {
    	cmdInputBox.setOnKeyTyped(new UIGuideMessage(this));
    }
    
    public void setSuggestionText(String output) {
        suggestionText.setText("\u2022 " + output);
    }
    
    public void setGuideMsgText(String output) {
    	guideMsgText.setText(output);
    }
    
    public void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
    
    public String getText() {
    	return cmdInputBox.getText();
    }
    
    public void setText(String text) {
    	cmdInputBox.setText(text);
    }
    
    public void resetPositionCaret() {
    	cmdInputBox.positionCaret(this.getText().length());
    }
    
    public boolean isFocused() {
    	return cmdInputBox.isFocused();
    }
    
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}
