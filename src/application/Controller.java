package application;

/**
 * The controller logic that integrates UI, Storage and Parser.
 * 
 * @author Sun Wang Jun
 */
public class Controller {

    private static FileManager fileManage;
    
    private static TaskManager taskManager;

    private static UiComponent uiComponent;

    /**
     * Executes the command entered.
     * 
     * @param input
     *            The entire command input.
     */
    public static void runCommandInput(String input) {
        // Replace with logger later.
        System.out.println("runCommandInput(input: " + input + ") called");

        fileManage.retrieveLists();

        Command command = (new Parser(input)).getCmd();

        switch (command.getCommandType()) {
        case "add":
            taskManager.add(command);
            taskManager.updateUi(uiComponent);
            break;
        case "delete":
            taskManager.delete(command);
            taskManager.updateUi(uiComponent);
            break;
        case "edit":
            taskManager.edit(command);
            taskManager.updateUi(uiComponent);
        }

        fileManage.saveToFiles();
    }

    public static void main(String[] args) {
        fileManage = new FileManager();
        fileManage.initiateFile();
        
        taskManager = new TaskManager();
        
        UiComponent.launchApplication(args);
    }
}
