package exceptions;

public class NoRatedProductionsException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No rated productions.";

    public NoRatedProductionsException() {
        super(MSG);
    }
}
