package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class testNatty {
    public static void main(String[] args) {
        List<Date> dates = null;
        String matchingValue = null;
        Boolean isRecurring = false;
        Date recursUntil = null;
        Parser parser = new Parser();
        String userInput = "every tuesday celebration national day till November";
        List<DateGroup> groups = parser.parse(userInput);
        {
            for (DateGroup group : groups) {
                dates = group.getDates();
                int line = group.getLine();
                int column = group.getPosition();
                matchingValue = group.getText();
                String syntaxTree = group.getSyntaxTree().toStringTree();
                Map parseMap = group.getParseLocations();
                isRecurring = group.isRecurring();
                recursUntil = group.getRecursUntil();
            }
        }
        if (dates != null) {
        for (Date date: dates) {
    //        System.out.println(date);
            System.out.println(matchingValue);
            userInput = userInput.replaceAll(matchingValue, "");
            System.out.println(userInput);
        //    System.out.println(isRecurring);
            System.out.println(recursUntil.toString());
        }
        }
        else {
            System.out.println("no time indicator");
        }
    }
}