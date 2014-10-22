package application;

public class TestParser {
    public static void main(String[] args) {
    Parser parser = new Parser();
    CommandInfo cmd = parser.getCommandInfo("exac one");
    System.out.println(cmd.getCommandType());
    boolean isValid = cmd.getIsValid();
    System.out.println(isValid);
    /*
    if (isValid) {
        System.out.println("true");
    }
    else {
        System.out.println("false");
    }
    */
}
}
