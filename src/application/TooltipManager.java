package application;

import java.util.Random;
import java.util.ArrayList;

public class TooltipManager {
    
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
    
    private static String TOOLTIP_MISC1 = "Typing the first letter of your command and <space> will auto-complete the command.";
    private static String TOOLTIP_MISC2 = "Enter QUIT command to terminate WaveWave.";
    private static String TOOLTIP_MISC3 = "After undoing, you can revert by entering UNDO again.";
    
    private ArrayList<String> tooltipsAdd = new ArrayList<String>();
    private ArrayList<String> tooltipsEdit = new ArrayList<String>();
    private ArrayList<String> tooltipsDelete = new ArrayList<String>();
    private ArrayList<String> tooltipsComplete = new ArrayList<String>();
    private ArrayList<String> tooltipsSearch = new ArrayList<String>();
    private ArrayList<String> tooltipsMisc = new ArrayList<String>();
    
    
    public TooltipManager() {
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
    }
    
    private Integer getRandomInteger(int size) {
        return rand.nextInt(size);
    }
    
    public String getTooltipsAdd() {
        return tooltipsAdd.get(getRandomInteger(tooltipsAdd.size()));
    }
    
    public String getTooltipsEdit() {
        return tooltipsEdit.get(getRandomInteger(tooltipsEdit.size()));
    }
    
    public String getTooltipsDelete() {
        return tooltipsDelete.get(getRandomInteger(tooltipsDelete.size()));
    }
    
    public String getTooltipSearch() {
        return tooltipsSearch.get(getRandomInteger(tooltipsSearch.size()));
    }
    
    public String getTooltipMisc() {
        return tooltipsMisc.get(getRandomInteger(tooltipsMisc.size()));
    }
    
    
    
}