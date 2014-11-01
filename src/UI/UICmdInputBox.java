package UI;

import java.util.ArrayList;

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
    
    private String tooltip_one = "You can add a new task by using ADD [description]";
    private String tooltip_two = "You can complete a task by using COMPLETE [description]";
    private String tooltip_three = "You can edit a task by using EDIT ID [description]";
    private String tooltip_four = "You can delete a task by using DELETE ID";
    private ArrayList<String> toolTip = new ArrayList<String>();
    public static int toolTipCounter = 0;
    
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
        initializeToolTip();
        guideMsgText.setText(this.getToolTip());
        toolTipCounter++;
    }
    
    private void initializeToolTip() {
    	toolTip.add(tooltip_one);
    	toolTip.add(tooltip_two);
    	toolTip.add(tooltip_three);
    	toolTip.add(tooltip_four);
    }
    
    public String getToolTip() {
    	return "\u2022 " + toolTip.get(toolTipCounter);
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
