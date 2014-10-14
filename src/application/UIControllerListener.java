package application;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UIControllerListener implements EventHandler<KeyEvent> {
    
    private TextField cmdInputBox;
    
    //TO-BE-DELETED
    UiComponent component;
    java.util.ArrayList<Task> temp = new  java.util.ArrayList<Task>();
    
    public UIControllerListener(TextField cmdInputBox, UiComponent component) {
        this.cmdInputBox = cmdInputBox;
        this.component = component;
    }
    
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) { 
            //Controller.runCommandInput(cmdInputBox.getText()); 
            
            //TO-BE-DELETED
            Task t = new Task();
            t.setDescription("for testing"); 
            temp.add(t);
            component.updateTaskList(temp);  
        } 
    }

}
