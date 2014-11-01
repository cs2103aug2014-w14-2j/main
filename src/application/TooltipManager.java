package application;

import java.util.Random;
import java.util.ArrayList;

public class TooltipManager {
    
    Random rand = new Random();
    
    private static String TOOLTIP_SEARCH = "";
    private static String TOOLTIP_SHOW1 = "";
    private static String TOOLTIP_SHOW2 = "Return to default view by typing \"HOME\"";
    private static String TOOLTIP_ADD1 = "A task can have a start time, end time, both or neither.";
    private static String TOOLTIP_ADD2 = "The square brackets contain task description. Other information is outside.";
    private static String TOOLTIP_DELETE = "Multiple tasks can be deleted together.";
    private static String TOOLTIP_COMPLETE = "Multiple tasks can be completed together.";
    
    private static String TOOLTIP_MISC1 = "Typing the first letter of your command and <space> will auto-complete the command.";
    private static String TOOLTIP_MISX2 = "";
    
    private ArrayList<String> tooltipsAdd = new ArrayList<String>();
    private ArrayList<String> tooltipsDelete = new ArrayList<String>();
    private ArrayList<String> tooltipsSearch = new ArrayList<String>();
    
    public TooltipManager() {
        tooltipsSearch.add(TOOLTIP_SEARCH);
    }
    
    private Integer getRandomInteger(int size) {
        return rand.nextInt(size);
    }
    
    public String getTooltipsAdd() {
        return tooltipsAdd.get(getRandomInteger(tooltipsAdd.size()));
    }
    
    public String getTooltipsDelete() {
        return tooltipsDelete.get(getRandomInteger(tooltipsDelete.size()));
    }
    
    public String getTooltipSearch() {
        return tooltipsSearch.get(getRandomInteger(tooltipsSearch.size()));
    }
    
    
}