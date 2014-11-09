package UI;

import java.util.ArrayList;

import application.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Middle man between UIComponent and Controller.
 * 
 * @author 
 */
public class UIGuideMessage implements EventHandler<KeyEvent> {

    private UICmdInputBox cmdInputBox;
    private String guideMessage;
    
    private static String TOOLTIP_BULLET = "\u2022 ";

    public UIGuideMessage(UICmdInputBox cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
    }

    @Override
    public void handle(KeyEvent event) {
        String input = this.cmdInputBox.getText();
        if((input.equalsIgnoreCase("a"))||(input.equalsIgnoreCase("ad"))
               || (input.equalsIgnoreCase("add"))) {
            setGuideMessage("Enter task description inside square brackets.");
        }
        else if (input.equalsIgnoreCase("add []")) {
            setGuideMessage("Type double space to get out of the square brackets.");
        } 
        
        else if ((input.indexOf("] ")>=0)&& (input.indexOf("ADD")>=0)) {
            setGuideMessage("(Optional)Enter a time for the task.");
        }
        
        else if(input.equalsIgnoreCase("e")) {
            setGuideMessage("Enter task ID and press Space.");
        }
        else if (input.equalsIgnoreCase("add []")) {
            setGuideMessage("Type double space to get out of the square brackets.");
        } 
        else if ((input.indexOf("]")>=0)&& (input.indexOf("EDIT")>=0)) {
            setGuideMessage("You can edit the task.");
        }
        else if (input.equalsIgnoreCase("u")) {
            setGuideMessage("To undo, press Enter.");
        }
        
        else if (input.equalsIgnoreCase("co")) {
            setGuideMessage("Enter task ID(s)(seperated by space) to complete them.");
        }
        else if (input.equalsIgnoreCase("del")) {
            setGuideMessage("Enter task ID(s)(seperated by space) to delete them.");
        }
        else if (input.equalsIgnoreCase("se")) {
            setGuideMessage("Enter a search keyword inside the square brackets.");
        }
    
        else if (input.equalsIgnoreCase("search []")) {
            setGuideMessage("Type double space to get out of the square brackets.");
        } 
       
        else if ((input.indexOf("] ")>=0)&& (input.indexOf("SEARCH")>=0)) {
            setGuideMessage("SEARCH [KEYWORD] !(use \"!\" to search priotiry task) (enter a time or period) complete(use \"complete\" to search completed tasks)");
        }
        
        else if (input.equalsIgnoreCase("sh")) {
            setGuideMessage("Enter a specific time or a period to show tasks within this time range.");
        }
        
        else if (input.equalsIgnoreCase("q")) {
            setGuideMessage("Press Enter to quit the program. All tasks are saved automatically.");
        }
    }
    
    private void setGuideMessage(String message) {
        this.guideMessage = message;
        cmdInputBox.setGuideMsgText(TOOLTIP_BULLET + message);
    }
    public String getGuideMessage() {
        return this.guideMessage;
    } 
}
