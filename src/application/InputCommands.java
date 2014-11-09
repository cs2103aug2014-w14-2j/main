package application;

import java.util.ArrayList;

public class InputCommands {
    public final static String ADD = "ADD";
    public final static String DELETE = "DELETE";
    public final static String EDIT = "EDIT";
    public final static String UNDO = "UNDO";

    public final static String QUIT = "QUIT";
    public final static String EXIT = "EXIT";

    public final static String COMPLETE = "COMPLETE";
    public final static String SEARCH = "SEARCH";
    public final static String SHOW = "SHOW";
    public final static String DISPLAY = "DISPLAY";
    public final static String HELP = "HELP";

    public final static String HOME = "HOME";

    //@author A0111824R
    public static ArrayList<String> getCommandList() {
        ArrayList<String> cmdList = new ArrayList<String>();
        cmdList.add(ADD);
        cmdList.add(DELETE);
        cmdList.add(EDIT);
        cmdList.add(UNDO);
        cmdList.add(COMPLETE);
        cmdList.add(QUIT);
        cmdList.add(DISPLAY);
        cmdList.add(SHOW);
        cmdList.add(SEARCH);
        cmdList.add(EXIT);
        cmdList.add(HOME);
        cmdList.add(HELP);
        return cmdList;
    }
    
    //@author A0111824R
    public static String getEditCommand() {
        return EDIT;
    }
    
    //@author A0111824R
    public static String getAddCommand() {
        return ADD;
    }
    
    //@author A0111824R
    public static String getSearchCommand() {
        return SEARCH;
    }   
    
    //@author A0111824R
    public static String getHelpCommand() {
        return HELP;
    }
}
