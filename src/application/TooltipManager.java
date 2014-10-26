package application;

import java.util.Random;
import java.util.ArrayList;

public class TooltipManager {
    
    private final String TOOLTIP_ADD = "";
    private final String TOOLTIP_EDIT = "";
    
    ArrayList<String> Tooltips = new ArrayList<String>();
    
    public int randomNumber(int min, int max) {
        Random rand = new Random(System.currentTimeMillis());
        int num = rand.nextInt((max - min) + 1) + min;
        return num;
    }
    
    public String randomTooltipGen() {
        return Tooltips.get(randomNumber(0, Tooltips.size())-1);
    }

    public static void main(String[] args) {
        

    }

}
