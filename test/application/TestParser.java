package application;


import java.util.ArrayList;

import org.joda.time.DateTime;


public class TestParser {
    public static void main(String[] args) {
    Parser parser = new Parser();

    CommandInfo cmd = parser.getCommandInfo("Add [drink tea]");

  //  System.out.println(cmd.getCommandType());
  //  boolean isValid = cmd.getIsValid();
  //  System.out.println(isValid);
  //  ArrayList<String> IDs = new ArrayList<String>();
  //  IDs = cmd.getTaskIDs();
  //  for (int i = 0; i<IDs.size();i++) {
  //      System.out.println(IDs.get(i));
  //  }
    System.out.println(cmd.getTaskDesc());
  //  System.out.println(cmd.getPriority());
  //  System.out.println("start time is " + cmd.getStartDateTime());
  //  System.out.println("end time is " + cmd.getEndDateTime());
  //  System.out.println("keyword is "+cmd.getTaskDesc());

 
}
}
