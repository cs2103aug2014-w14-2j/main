package UI;

<<<<<<< HEAD
import java.util.ArrayList;

=======
import application.TooltipManager;
>>>>>>> 66c2cb3a4ab17f7d04b11c97c5b4d0c92dae98b9
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
    
    private final int CMDINPUT_HEIGHT = 35;
    private final String CMDINPUT_PROMPT_TEXT = "Ask WaveWave to do something.";
    private UITooltips toolTipManage;
    private UIAutoCompleteListener autoCompleteListener;
    private UIComponent ui;

    
    //@author A0111824R
    /**
     * @param suggestionText - the text label used to display suggestion text
     * @param guideMsgText - the text label used to display guide messages
     * @author Tan Young Sing
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
     * @author Tan Young Sing
     */
    public String getToolTip() {
    	return toolTipManage.getToolTips(autoCompleteListener.getCurrentCmd(cmdInputBox.getText()));
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
        cmdInputBox.setOnKeyPressed(new UIControllerListener(this, ui));
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void addKeyReleasedListener() {
        cmdInputBox.setOnKeyReleased(autoCompleteListener);
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
        suggestionText.setText(output);
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void setGuideMsgText(String output) {
    	guideMsgText.setText(output);
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
     * @param text to be displayed on the input box
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
     * @return if the inputbox is focused.
     */
    public boolean isFocused() {
    	return cmdInputBox.isFocused();
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @return return the inputbox node
     */
    public TextField getCmdInputBox() {
        return cmdInputBox;
    }
}
