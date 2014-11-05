package UI;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//@author A0111824R
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
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public UICmdInputBox(Text suggestionText, Text guideMsgText) {
        this.suggestionText = suggestionText;
        this.guideMsgText = guideMsgText;
        this.cmdInputBox = new TextField();
        
        setInputBoxProperty();
        addKeyPressedListener();
        addKeyTypedListener();
        addKeyReleasedListener();
        initializeToolTip();
        setGuideMsgText(this.getToolTip());
        toolTipCounter++;
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void initializeToolTip() {
    	toolTip.add(tooltip_one);
    	toolTip.add(tooltip_two);
    	toolTip.add(tooltip_three);
    	toolTip.add(tooltip_four);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public String getToolTip() {
    	return toolTip.get(toolTipCounter);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void setInputBoxProperty() {
        cmdInputBox.setPrefHeight(CMDINPUT_HEIGHT);
        cmdInputBox.setPromptText(CMDINPUT_PROMPT_TEXT);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void addKeyPressedListener() {
        cmdInputBox.setOnKeyPressed(new UIControllerListener(this));
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void addKeyReleasedListener() {
        cmdInputBox.setOnKeyReleased(new UIAutoCompleteListener(this));
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void addKeyTypedListener() {
    	cmdInputBox.setOnKeyTyped(new UIGuideMessage(this));
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void setSuggestionText(String output) {
        suggestionText.setText("\u2022 " + output);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void setGuideMsgText(String output) {
    	guideMsgText.setText("\u2022 " + output);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public String getText() {
    	return cmdInputBox.getText();
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void setText(String text) {
    	cmdInputBox.setText(text);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void resetPositionCaret() {
    	cmdInputBox.positionCaret(this.getText().length());
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public boolean isFocused() {
    	return cmdInputBox.isFocused();
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}
