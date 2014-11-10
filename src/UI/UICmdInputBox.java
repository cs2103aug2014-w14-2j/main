package UI;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//@author A0111824R
/**
 * Abstracted TextField Class.
 * 
 
 */
public class UICmdInputBox {

    private TextField cmdInputBox;
    private Text suggestionText;
    private Text guideMsgText;
    
    private final int CMDINPUT_HEIGHT = 35;
    private final String CMDINPUT_PROMPT_TEXT = "Ask WaveWave to do something.";
    private UITooltips toolTipManage;
    private UIAutoCompleteListener autoCompleteListener;
    private UIComponent ui;

    
    //@author A0111824R
    /**
     * @param suggestionText - the text label used to display suggestion text
     * @param guideMsgText - the text label used to display guide messages
     
     */
    public UICmdInputBox(Text suggestionText, Text guideMsgText, UIComponent ui) {
        this.suggestionText = suggestionText;
        this.guideMsgText = guideMsgText;
        this.cmdInputBox = new TextField();
        this.toolTipManage = new UITooltips();
        this.autoCompleteListener = new UIAutoCompleteListener(this);
        this.ui = ui;
        
        setInputBoxProperty();
        addKeyPressedListener();
        addKeyTypedListener();
        addKeyReleasedListener();
    }
    
    //@author A0111824R
    /**
     *
     
     */
    public String getToolTip() {
    	return toolTipManage.getToolTips(autoCompleteListener.getCurrentCmd(cmdInputBox.getText()));
    }
    
    //@author A0111824R
    /**
     *
     
     */
    private void setInputBoxProperty() {
        cmdInputBox.setPrefHeight(CMDINPUT_HEIGHT);
        cmdInputBox.setPromptText(CMDINPUT_PROMPT_TEXT);
    }
    
    //@author A0111824R
    /**
     *
     
     */
    private void addKeyPressedListener() {
        cmdInputBox.setOnKeyPressed(new UIControllerListener(this, ui));
    }
    
    //@author A0111824R
    /**
     *
     
     */
    private void addKeyReleasedListener() {
        cmdInputBox.setOnKeyReleased(autoCompleteListener);
    }
    
    //@author A0111824R
    /**
     *
     
     */
    private void addKeyTypedListener() {
    	cmdInputBox.setOnKeyTyped(new UIGuideMessage(this));
    }
    
    //@author A0111824R
    /**
     *
     
     */
    public void setSuggestionText(String output) {
        suggestionText.setText(output);
    }
    
    //@author A0111824R
    /**
     *
     
     */
    public void setGuideMsgText(String output) {
    	guideMsgText.setText(output);
    }
    
    //@author A0111824R
    /**
     *
     
     */
    public void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
    
    //@author A0111824R
    /**
     *
     
     */
    public String getText() {
    	return cmdInputBox.getText();
    }
    
    //@author A0111824R
    /**
     * @param text to be displayed on the input box
     
     */
    public void setText(String text) {
    	cmdInputBox.setText(text);
    }
    
    //@author A0111824R
    /**
     * 
     
     */
    public void resetPositionCaret() {
    	cmdInputBox.positionCaret(this.getText().length());
    }
    
    //@author A0111824R
    /**
     * 
     
     * @return if the inputbox is focused.
     */
    public boolean isFocused() {
    	return cmdInputBox.isFocused();
    }
    
    //@author A0111824R
    /**
     *
     
     * @return return the inputbox node
     */
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}
