package application;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UICmdInputBox {

    private TextField cmdInputBox;
    private Text suggestionText;
    
    private final int CMDINPUT_HEIGHT = 35;
    private final String CMDINPUT_PROMPT_TEXT = "Ask WaveWave to do something ?";
    private UiComponent component;
    
    public UICmdInputBox(Text suggestionText, UiComponent component) {
        this.suggestionText = suggestionText;
        this.cmdInputBox = new TextField();
        
        //TO-BE-DELETED
        this.component = component;
        
        setInputBoxProperty();
        addKeyPressedListener();
        addKeyTypedListener();
    }
    
    private void setInputBoxProperty() {
        cmdInputBox.setPrefHeight(CMDINPUT_HEIGHT);
        cmdInputBox.setPromptText(CMDINPUT_PROMPT_TEXT);
    }
    
    private void addKeyPressedListener() {
        //TO-BE-DELETED
        cmdInputBox.setOnKeyPressed(new UIControllerListener(this.cmdInputBox, component));
    }
    
    private void addKeyTypedListener() {
        cmdInputBox.setOnKeyReleased(new UIAutoCompleteListener(this));
    }
    
    public void setSuggestionText(String output) {
        suggestionText.setText(output);
    }
    
    public void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
    
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}