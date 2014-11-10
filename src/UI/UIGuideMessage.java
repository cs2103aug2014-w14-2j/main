package UI;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

//@author A0090971Y
/**
 * Middle man between UIComponent and Controller.
 * 
 */
public class UIGuideMessage implements EventHandler<KeyEvent> {

    private UICmdInputBox cmdInputBox;
    private String guideMessage; 
    private static String TOOLTIP_BULLET = "\u2022 ";

    public UIGuideMessage(UICmdInputBox cmdInputBox) {
        this.cmdInputBox = cmdInputBox;
    }
    
    //@author A0090971Y
    @Override
    public void handle(KeyEvent event) {
        String input = this.cmdInputBox.getText();
        if((input.equalsIgnoreCase("a"))||(input.equalsIgnoreCase("ad"))
               || (input.equalsIgnoreCase("add"))) {
            setGuideMessage("Enter task description inside the square brackets.");
        }
        else if (input.equalsIgnoreCase("add []")) {
            setGuideMessage("Press space twice once you are done writing the description to get out of the square brackets.");
        } 
        else if ((input.indexOf("] ")>=0)&& (input.indexOf("ADD")>=0)) {
            setGuideMessage("Enter a time for the task. (Optional)");
        }
        else if(input.equalsIgnoreCase("e")) {
            setGuideMessage("Enter task ID and press Space.");
        }
        else if ((input.indexOf("]")>=0)&& (input.indexOf("EDIT")>=0)) {
            setGuideMessage("You can edit the task.");
        }
        else if (input.equalsIgnoreCase("u")) {
            setGuideMessage("To undo, press Enter.");
        }
        else if (input.equalsIgnoreCase("co")) {
            setGuideMessage("Enter task ID(s) (seperated by a space) to complete them.");
        }
        else if (input.equalsIgnoreCase("del")) {
            setGuideMessage("Enter task ID(s) (seperated by a space) to delete them.");
        }
        else if (input.equalsIgnoreCase("se")) {
            setGuideMessage("Enter the search keyword inside the square brackets.");
        }
        else if (input.equalsIgnoreCase("search []")) {
            setGuideMessage("Press space twice once you are done writing the keyword to get out of the square brackets.");
        } 
        else if ((input.indexOf("] ")>=0)&& (input.indexOf("SEARCH")>=0)) {
            setGuideMessage("SEARCH [KEYWORD] time(optional) ! (priority optional) ");
        }
        else if (input.equalsIgnoreCase("sh")) {
            setGuideMessage("Enter a specific time or a period to show tasks within this time range.");
        }
        else if (input.equalsIgnoreCase("q")) {
            setGuideMessage("Press Enter to quit the program. All tasks are saved automatically.");
        }
    }
    
    //@author A0090971Y
    private void setGuideMessage(String message) {
        this.guideMessage = message;
        cmdInputBox.setGuideMsgText(TOOLTIP_BULLET + message);
    }
    
    //@author A0090971Y
    /**
     * 
     * @return the guide message to be displayed 
     */
    public String getGuideMessage() {
        return this.guideMessage;
    } 
}
