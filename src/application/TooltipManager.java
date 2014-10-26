package application;

import java.util.Random;
import java.util.ArrayList;

/**
 * 
 * @author Kim Hyung Jon
 *
 */

public class TooltipManager {
    
    private final String TOOLTIP_ADD = "ADD [what to do]";
    private final String TOOLTIP_EDIT = "";
    private final String TOOLTIP_DELETE = "DELETE <task ID>";
    private final String TOOLTIP_COMPLETE = "COMPLETE <task ID>";
    
    private final String TOOLTIP_AUTOCOMPLETE_ADD = "A �� ADD: press <space> to auto-complete";
    private final String TOOLTIP_AUTOCOMPLETE_DELETE = "D �� DELETE: press <space> to auto-complete";
    private final String TOOLTIP_AUTOCOMPLETE_EDIT = "E �� EDIT: press <space> to auto-complete";
    private final String TOOLTIP_AUTOCOMPLETE_COMPLETE = "C �� COMPLETE: press <space> to auto-complete";
    private final String TOOLTIP_AUTOCOMPLETE_QUIT = "Q �� QUIT: press <space> to auto-complete";
    
    ArrayList<String> Tooltips = new ArrayList<String>();
    
    
    
    public int randomNumber(int min, int max) {
        Random rand = new Random(System.currentTimeMillis());
        int num = rand.nextInt((max - min) + 1) + min;
        return num;
    }
    
    public String randomTooltipGen() {
        return Tooltips.get(randomNumber(0, Tooltips.size())-1);
    }

    public String inputTooltipGen() {
        
    }

}
