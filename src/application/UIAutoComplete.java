package application;

public class UIAutoComplete {
    
    final String ADD_COMMAND = "ADD";
    final String DELETE_COMMAND = "DELETE";
    final String EDIT_COMMAND = "EDIT";
    
    final static int SPACE_CHAR_NOT_FOUND = -1;
    
    public UIAutoComplete() {
        
    }

    public void runAutoComplete(String command) {
        command = command.trim();
        if(command.indexOf(" ") == SPACE_CHAR_NOT_FOUND){
            
        }
        
        
        System.out.println(command);
    }
    
}
