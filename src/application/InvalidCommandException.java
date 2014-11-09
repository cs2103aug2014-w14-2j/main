package application;

//author A0110546R
public class InvalidCommandException extends Exception {
    private static final long serialVersionUID = 2673862145114002300L; // Eclipse wants to have this.

    public InvalidCommandException() {}
    
    public InvalidCommandException(String message) {
        super(message);
    }
}

