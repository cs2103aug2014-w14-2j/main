package application;

import org.joda.time.DateTime;

public class TestParser {
    public static void main(String[] args) {
    Parser parser = new Parser();
    CommandInfo cmd = parser.getCommandInfo("ADD [apple] by 10am");
    System.out.println(cmd.getCommandType());
    boolean isValid = cmd.getIsValid();
    System.out.println(isValid);
    System.out.println(cmd.getTaskID());
    System.out.println(cmd.getTaskDesc());
    System.out.println(cmd.getPriority());
    System.out.println("start time is " + cmd.getStartDateTime());
    System.out.println("end time is " + cmd.getEndDateTime());
    System.out.println("keyword is "+cmd.getTaskDesc());
    System.out.println(cmd.getIsValidID());
    DateTime dt = new DateTime();
    System.out.println(dt);
}
}
