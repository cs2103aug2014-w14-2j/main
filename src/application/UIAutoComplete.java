package application;

import java.util.ArrayList;

/**
 * UIAutoComplete: Responsible for all autocomplete operations.
 * 
 * @author Tan Young Sing
 */
public class UIAutoComplete {
    
    final private String MSG_COMMAND_SUGGESTION = "Do you mean : %s. You can enter <space> key to complete.";
    final private String MSG_COMMAND_NOT_SUPPORTED = "WaveWave would only support these sets of command <add> <delete> <edit>";
    final private String MSG_DEFAULT_PROMPT = "Ask WaveWave to do something ?";
    
    final public String ADD_COMMAND = "ADD";
    final private String DELETE_COMMAND = "DELETE";
    final private String EDIT_COMMAND = "EDIT";
    
    final private int SPACE_CHAR_NOT_FOUND = 1;
    
    private UICmdInputBox cmdInputBox;
    private ArrayList<String> commandList;
    private UIAutoCompleteListener acListener;
    
    public UIAutoComplete(UICmdInputBox cmdInputBox, UIAutoCompleteListener acListener) {
        this.cmdInputBox = cmdInputBox;
        this.commandList = getCommandList();
        this.acListener = acListener;
    }
    
    private ArrayList<String> getCommandList() {
        ArrayList<String> cmdList = new ArrayList<String>();
        cmdList.add(ADD_COMMAND);
        cmdList.add(DELETE_COMMAND);
        cmdList.add(EDIT_COMMAND);
        return cmdList;
    }
    
    public void runAutoComplete(String command) {
    	if (isTheFirstWord(command)) {       
            String suggestedCmd = getSuggestions(command.toUpperCase());
            
            //case 1 : suggestedCmd returns empty strings 
            if(suggestedCmd.length() == 0) {
            	//case 1a : the command typed in is not supported by wavewave
            	if(!suggestedCmd.equalsIgnoreCase(command)) {
                    cmdInputBox.setSuggestionText(MSG_COMMAND_NOT_SUPPORTED);
                    this.acListener.setNextPossibleCmd("");
            	} else {
            		//case 1b : the command typed in is the right command so do nothing as of now
                    cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
                    this.acListener.setNextPossibleCmd("");
            	}
            } else {
            	//case 2 : autocomplete found a close match and had gave a suggestions
                cmdInputBox.setSuggestionText(String.format(MSG_COMMAND_SUGGESTION, suggestedCmd));
                this.acListener.setNextPossibleCmd(suggestedCmd);
            }
        } else {
            cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
            this.acListener.setNextPossibleCmd("");
        }
    }

    private String getSuggestions(String word) {
        if(word.trim().length() == 0) {
            return "";
        }
        
        String output = "";
        for (String command : commandList) {
           if(command.startsWith(word)) {
               output += command + " ";
           }
        }
        return output;
    }
    
    private boolean isTheFirstWord(String cmd) {
        String[] oneWordChecker = cmd.split(" ");
        return (oneWordChecker.length == SPACE_CHAR_NOT_FOUND);
    }
    
}
