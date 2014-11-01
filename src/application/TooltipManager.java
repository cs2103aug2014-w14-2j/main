package application;

import java.util.Random;
import java.util.ArrayList;

public class TooltipManager {
    
    private final String TOOLTIP_RANDOM1 = "";
    
    private final String TOOLTIP_ADD = "";
    private final String TOOLTIP_EDIT = "";
    private final String TOOLTIP_DELETE = "";
    private final String TOOLTIP_COMPLETE = "";
    private final String TOOLTIP_UNDO = "";
    private final String TOOLTIP_QUIT = "";
    
    
    ArrayList<String> tooltips = new ArrayList<String>();
    
    public String getTooltip() {
        Random rand = new Random();
        int randomNum = rand.nextInt(tooltips.size()-1);
        
        return tooltips.get(randomNum);
    }

    public static void main(String[] args) {
        

    }

}
