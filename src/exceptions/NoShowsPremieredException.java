package exceptions;

public class NoShowsPremieredException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No finished productions.";

    public NoShowsPremieredException() {
        super(MSG);
    }
}