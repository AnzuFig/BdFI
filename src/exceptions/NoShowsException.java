package exceptions;

public class NoShowsException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No shows.";

    public NoShowsException() {
        super(MSG);

    }
}
