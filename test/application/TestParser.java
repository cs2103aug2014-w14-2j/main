package application;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import com.joestelmach.natty.DateGroup;

public class TestParser {
    private static Logger logger = Logger.getLogger("Foo");
    public static void main(String[] args) {
       
        String input = "Add eat apple now"; 
        Parser p = new Parser();
        CommandInfo cmdInfo = p.getCommandInfo(input);
        DateTime sd = cmdInfo.getStartDateTime();
        DateTime ed = cmdInfo.getEndDateTime();
        String taskD = cmdInfo.getTaskDesc();
        System.out.println(sd);
        System.out.println(ed);
        System.out.println(taskD);
    }
}
