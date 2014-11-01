package application;

import java.util.Random;
import java.util.ArrayList;

public class TooltipManager {
    
    ArrayList<String> tooltips = new ArrayList<String>();
    
    public String getTooltip() {
        Random rand = new Random();
        int randomNum = rand.nextInt(tooltips.size()-1);
        
        return tooltips.get(randomNum);
    }

    public static void main(String[] args) {
        

    }

}
