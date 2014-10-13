package application;

import java.util.ArrayList;

public class UIAutoComplete {
    
    final private String MSG_COMMAND_SUGGESTION = "Do you mean : %s. You can enter <space> key to complete.";
    final private String MSG_DEFAULT_PROMPT = "Ask WaveWave to do something ?";
    
    final private String ADD_COMMAND = "ADD";
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
            if(suggestedCmd.length() == 0) {
                cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
            } else {
                cmdInputBox.setSuggestionText(String.format(MSG_COMMAND_SUGGESTION, suggestedCmd));
                this.acListener.setNextPossibleCmd(suggestedCmd);
            }
        } else {
            cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
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
