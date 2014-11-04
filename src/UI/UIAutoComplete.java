package UI;

import java.util.ArrayList;

/**
 * UIAutoComplete: Responsible for all autocomplete operations.
 * 
 * @author Tan Young Sing
 */
public class UIAutoComplete {
    
    final private String MSG_COMMAND_SUGGESTION = "Do you mean : %s. You can enter <space> key to complete.";
    final private String MSG_COMMAND_NOT_SUPPORTED = "WaveWave would only support these sets of command <add> <delete> <edit>";
    
    final public String ADD_COMMAND = "ADD";
    final private String DELETE_COMMAND = "DELETE";
    final private String EDIT_COMMAND = "EDIT";
    final private String UNDO_COMMAND = "UNDO";
    final private String QUIT_COMMAND = "QUIT";
    final private String EXIT_COMMAND = "EXIT";
    final private String COMPLETE_COMMAND = "COMPLETE";
    final private String SEARCH_COMMAND = "SEARCH";
    final private String SHOW_COMMAND = "SHOW";
    final private String DISPLAY_COMMAND = "DISPLAY";
    final private String HOME_COMMAND = "HOME";
    
    final private int FIRST_WORD_IN_CMD = 1;
    final private int SECOND_WORD_IN_CMD = 2;
    
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
        cmdList.add(UNDO_COMMAND);
        cmdList.add(COMPLETE_COMMAND);
        cmdList.add(QUIT_COMMAND);
        cmdList.add(DISPLAY_COMMAND);
        cmdList.add(SHOW_COMMAND);
        cmdList.add(SEARCH_COMMAND);
        cmdList.add(EXIT_COMMAND);
        cmdList.add(HOME_COMMAND);
        return cmdList;
    }
    
    public void runAutoComplete(String command) {
    	String cmdUsed = getCommandText(command).trim();
    	if (isTheNWord(command, FIRST_WORD_IN_CMD)) {       
            String suggestedCmd = getSuggestions(command.toUpperCase());
            
            //case 1 : suggestedCmd returns empty strings 
            if(suggestedCmd.length() == 0) {
            	//case 1a : the command typed in is not supported by wavewave
            	if(!suggestedCmd.equalsIgnoreCase(command)) {
                    cmdInputBox.setSuggestionText(MSG_COMMAND_NOT_SUPPORTED);
                    this.acListener.setNextPossibleCmd("");
            	} else {
            		//case 1b : the command typed in is the right command so do nothing as of now
                    //cmdInputBox.setSuggestionText(MSG_DEFAULT_PROMPT);
                    this.acListener.setNextPossibleCmd("");
            	}
            } else {
            	//case 2 : autocomplete found a close match and had gave a suggestions
                cmdInputBox.setSuggestionText(String.format(MSG_COMMAND_SUGGESTION, suggestedCmd));
                this.acListener.setNextPossibleCmd(suggestedCmd);
            }
        } else if (isTheNWord(command, SECOND_WORD_IN_CMD) && cmdUsed.equalsIgnoreCase(EDIT_COMMAND)) {
        	 String suggestedCmd = cmdInputBox.getText() + "[]";
        	 this.acListener.setNextPossibleCmd(suggestedCmd);
        } else {
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
               break;
           }
        }
        return output;
    }
    
    private String getCommandText(String cmd) {
    	String[] cmdRetrieval = cmd.split(" ");
    	return cmdRetrieval[0];
    }

    
    private boolean isTheNWord(String cmd, int n) {
        String[] oneWordChecker = cmd.split(" ");
        return (oneWordChecker.length == n);
    }
    
}
