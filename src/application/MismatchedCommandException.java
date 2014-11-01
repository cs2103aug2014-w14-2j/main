package application;

//author A0110546R
public class MismatchedCommandException extends Exception {
    private static final long serialVersionUID = 2673862145114002300L; // Eclipse wants to have this.

    public MismatchedCommandException() {}
    
    public MismatchedCommandException(String message) {
        super(message);
    }
}

