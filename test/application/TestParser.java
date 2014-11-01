package application;


import java.util.ArrayList;

import org.joda.time.DateTime;


public class TestParser {
    public static void main(String[] args) {
    Parser parser = new Parser();

    CommandInfo cmd = parser.getCommandInfo("search [go] 6pm to 7pm ");

  //  System.out.println(cmd.getCommandType());

    try { 
    cmd.validateUserInput();
    }
    catch (MismatchedCommandException e) {
        System.out.println(e);
    }
    System.out.println(cmd.getMessage());
    
  //  ArrayList<String> IDs = new ArrayList<String>();
  //  IDs = cmd.getTaskIDs();
  //  for (int i = 0; i<IDs.size();i++) {
  //      System.out.println(IDs.get(i));
  //  }
    
    System.out.println(cmd.getTaskDesc());
    System.out.println(cmd.isCompleted());
  //  System.out.println(cmd.getPriority());
    System.out.println("start time is " + cmd.getStartDateTime());
    System.out.println("end time is " + cmd.getEndDateTime());
  //  System.out.println("keyword is "+cmd.getTaskDesc());

 
}
}
