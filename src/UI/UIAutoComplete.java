package UI;

import java.util.ArrayList;

import task.Task;
import application.Controller;
import application.InputCommands;

//@author A0111824R
/**
 * UIAutoComplete: Responsible for all autocomplete operations.
 * 
 * @author Tan Young Sing
 */
public class UIAutoComplete {
    
    final private String MSG_COMMAND_SUGGESTION = "\u2022 Do you mean : %s. You can enter <space> key to complete.";
    final private String MSG_COMMAND_NOT_SUPPORTED = "\u2022 WaveWave has only these set of commands : <add> <delete> <edit> <search> <show> <complete> <home>";
    final private String EDIT_TASKID_FOUND = "\u2022 Task ID : %s found, use the <space> key to autocomplete.";
    
    private final String UI_DATETIMEFORMAT = "dd MMM yyy, h:mm a";
    
    final private int FIRST_WORD_IN_CMD = 1;
    final private int SECOND_WORD_IN_CMD = 2;
    final private int EDIT_INDEX_POSITION = 1;
    
    private UICmdInputBox cmdInputBox;
    private UIAutoCompleteListener acListener;
    private ArrayList<String> commandList;
    
    
    //@author A0111824R
    /**
     * UIAutoComplete: Constructor
     * Takes in CmdInputBox Component to detect user input 
     *
     * @author Tan Young Sing
     */
    public UIAutoComplete(UICmdInputBox cmdInputBox, UIAutoCompleteListener acListener) {
        this.cmdInputBox = cmdInputBox;
        this.commandList = InputCommands.getCommandList();
        this.acListener = acListener;
    }
    
    //@author A0111824R
    /**
     * UIAutoComplete: Constructor
     * Takes in CmdInputBox Component to detect user input 
     *
     * @author Tan Young Sing
     */
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
        } else if (isTheNWord(command, SECOND_WORD_IN_CMD) && cmdUsed.equalsIgnoreCase(InputCommands.getEditCommand())) {
        	 String taskID = getEditCommandIndex(command).toUpperCase();
        	 Task selectedTask = Controller.getTaskFromDisplayID(taskID);
        	 
        	 if(selectedTask != null) {
        		 cmdInputBox.setSuggestionText(String.format(EDIT_TASKID_FOUND, taskID));
            	 String taskDetails = parseEditText(selectedTask);
            	 String suggestedCmd = cmdInputBox.getText() + taskDetails;
            	 this.acListener.setNextPossibleCmd(suggestedCmd);
        	 } else {
        		 this.acListener.setNextPossibleCmd("");
        	 }
        } else {
            this.acListener.setNextPossibleCmd("");
        }
    }
    
    //@author A0111824R
    /** 
     *
     * @author Tan Young Sing
     */
    private String parseEditText(Task selectedTask) {
    	String editText = "[ " + selectedTask.getDescription() + " ] ";
    	
    	if(selectedTask.getDate() != null && selectedTask.getEndDate() == null) {
    		editText += selectedTask.getDate().toString(UI_DATETIMEFORMAT);
    	} else if(selectedTask.getDate() != null && selectedTask.getEndDate() != null) {
    		editText += selectedTask.getDate().toString(UI_DATETIMEFORMAT) + " TO " + selectedTask.getEndDate().toString(UI_DATETIMEFORMAT);
    	} else if(selectedTask.getDate() == null && selectedTask.getEndDate() != null) {
    		editText += "BY " + selectedTask.getEndDate().toString(UI_DATETIMEFORMAT);
    	}
    	
    	if(selectedTask.getPriority() == 1) {
    		editText += " !";
    	}
    
    	return editText;
    }
    
    //@author A0111824R
    /** 
     *
     * @author Tan Young Sing
     */
    private String getEditCommandIndex(String cmd) {
    	String[] indexRetrieval = cmd.split(" ");
    	return indexRetrieval[EDIT_INDEX_POSITION];
    }
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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
    
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private String getCommandText(String cmd) {
    	String[] cmdRetrieval = cmd.split(" ");
    	return cmdRetrieval[0];
    }

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private boolean isTheNWord(String cmd, int n) {
        String[] oneWordChecker = cmd.split(" ");
        return (oneWordChecker.length == n);
    }
    
}
