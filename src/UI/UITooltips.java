package UI;

import java.util.Random;
import java.util.ArrayList;

//@author A0115864B
/**
 * Stores and returns random tooltips
 * @author Kim Hyung Jon
 *
 */
public class UITooltips {
    
    Random rand = new Random();
    
    private static String TOOLTIP_ADD1 = "A task can have a start time, end time, both or neither.";
    private static String TOOLTIP_ADD2 = "The square brackets contain task description. Other information is outside.";
    private static String TOOLTIP_EDIT1 = "To edit, specify task ID and press space to view the syntax.";
    private static String TOOLTIP_DELETE1 = "Multiple tasks can be deleted together.";
    private static String TOOLTIP_COMPLETE1 = "Multiple tasks can be completed together.";
    private static String TOOLTIP_COMPLETE2 = "Completed tasks can be reviewed by the command SHOW complete";
    private static String TOOLTIP_SEARCH1 = "Search supports keyword, start and end times, priority.";
    private static String TOOLTIP_SEARCH2 = "Tasks can be searched based on keywords, inserted between square brackets when searching.";
    private static String TOOLTIP_SEARCH3 = "Return to default view by typing HOME";
    
    private static String TOOLTIP_MISC1 = "Typing the first letter of your command and <space> will auto-complete the command type.";
    private static String TOOLTIP_MISC2 = "Enter QUIT command to terminate WaveWave.";
    private static String TOOLTIP_MISC3 = "After undoing, you can revert by entering UNDO again.";

    private static String TOOLTIP_BULLET = "\u2022 ";

    private static String TOOLTIP_MISC4 = "All tasks are stored in an external file. You can directly modify it as well.";
    private static String TOOLTIP_MISC5 = "\"by <day>\" is understood as 23:59pm of that day.";
    private static String TOOLTIP_MISC6 = "Overdue tasks disappear after the day is passed. They can be viewed using SHOW command.";
    private static String TOOLTIP_MISC7 = "WaveWave can understand most natural language input for date and time.";
    private static String TOOLTIP_MISC8 = "All tasks are saved after each operation, so there is no need to manually save.";
    private static String TOOLTIP_MISC9 = "Undoing twice will revert to the state before the first undo.";
    private static String TOOLTIP_MISC10 = "Tasks with red icons are important ones.";
    private static String TOOLTIP_MISC11 = "Deadlines can be indicated with different words like until, by, etc";
    private static String TOOLTIP_MISC12 = "After typing description, press space twice to move out of square brackets and enter other details.";
    private static String TOOLTIP_MISC13 = "If you don't know when an event will end, just indicate its start time and it will be registered as an event.";
    private static String TOOLTIP_MISC14 = "Tasks with gray icons are the ones you completed.";

    private ArrayList<String> tooltipsAdd = new ArrayList<String>();
    private ArrayList<String> tooltipsEdit = new ArrayList<String>();
    private ArrayList<String> tooltipsDelete = new ArrayList<String>();
    private ArrayList<String> tooltipsComplete = new ArrayList<String>();
    private ArrayList<String> tooltipsSearch = new ArrayList<String>();
    private ArrayList<String> tooltipsMisc = new ArrayList<String>();
    
    //@author A0115864B
    /**
     * Constructor.
     * Adds all tooltips and guide messages to respective ArrayLists.
     */
    public UITooltips() {
        tooltipsAdd.add(TOOLTIP_ADD1);
        tooltipsAdd.add(TOOLTIP_ADD2);
        tooltipsEdit.add(TOOLTIP_EDIT1);
        tooltipsDelete.add(TOOLTIP_DELETE1);
        tooltipsComplete.add(TOOLTIP_COMPLETE1);
        tooltipsComplete.add(TOOLTIP_COMPLETE2);
        tooltipsSearch.add(TOOLTIP_SEARCH1);
        tooltipsSearch.add(TOOLTIP_SEARCH2);
        tooltipsSearch.add(TOOLTIP_SEARCH3);
        tooltipsMisc.add(TOOLTIP_MISC1);
        tooltipsMisc.add(TOOLTIP_MISC2);
        tooltipsMisc.add(TOOLTIP_MISC3);
        tooltipsMisc.add(TOOLTIP_MISC4);
        tooltipsMisc.add(TOOLTIP_MISC5);
        tooltipsMisc.add(TOOLTIP_MISC6);
        tooltipsMisc.add(TOOLTIP_MISC7);
        tooltipsMisc.add(TOOLTIP_MISC8);
        tooltipsMisc.add(TOOLTIP_MISC9);
        tooltipsMisc.add(TOOLTIP_MISC10);
        tooltipsMisc.add(TOOLTIP_MISC11);
        tooltipsMisc.add(TOOLTIP_MISC12);
        tooltipsMisc.add(TOOLTIP_MISC13);
        tooltipsMisc.add(TOOLTIP_MISC14);
    } 
    
    public String getToolTips(String command) {
    	switch (command.toUpperCase()) {
    		case "ADD" : return TOOLTIP_BULLET + getTooltipsAdd();
    		case "EDIT" : return  TOOLTIP_BULLET + getTooltipsEdit();
    		case "COMPLETE" : return  TOOLTIP_BULLET + getTooltipsComplete();
    		case "DELETE" : return  TOOLTIP_BULLET + getTooltipsDelete();
    		case "SEARCH": return  TOOLTIP_BULLET + getTooltipSearch();
    		default: return  TOOLTIP_BULLET + getTooltipMisc();
    	}
    }
    
    //@author A0115864B
    /**
     * Returns a random index from 0 to the size of the ArrayList from which a message will be retrieved
     * 
     * @param size
     * @return
     */
    private Integer getRandomInteger(int size) {
        return rand.nextInt(size);
    }
    
    //@author A0115864B
    /**
     * Retrieves a random message related to ADD command
     * 
     * @return String containing the message to display
     */
    public String getTooltipsAdd() {
        return tooltipsAdd.get(getRandomInteger(tooltipsAdd.size()));
    }
    
    //@author A0115864B
    /**
     * Retrieves a random message related to EDIT command
     * 
     * @returnString containing the message to display
     */
    public String getTooltipsEdit() {
        return tooltipsEdit.get(getRandomInteger(tooltipsEdit.size()));
    }
    
    //@author A0115864B
    /**
     * Retrieves a random message related to DELETE command
     * 
     * @return String containing the message to display
     */
    public String getTooltipsDelete() {
        return tooltipsDelete.get(getRandomInteger(tooltipsDelete.size()));
    }
    
    //@author A0115864B
    /**
     * Retrieves a random message related to COMPLETE command
     * 
     * @return String containing the message to display
     */
    private String getTooltipsComplete() {
        return tooltipsComplete.get(getRandomInteger(tooltipsComplete.size()));
    }
    
    //@author A0115864B
    /**
     * Retrieves a random message related to SEARCH command
     * 
     * @return String containing the message to display
     */
    public String getTooltipSearch() {
        return tooltipsSearch.get(getRandomInteger(tooltipsSearch.size()));
    }
    
    //@author A0115864B
    /**
     * Retrieves a random tooltip
     * 
     * @return String containing the tooltip to display
     */
    public String getTooltipMisc() {
        return tooltipsMisc.get(getRandomInteger(tooltipsMisc.size()));
    }
}