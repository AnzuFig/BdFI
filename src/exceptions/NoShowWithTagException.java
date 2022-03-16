package exceptions;

public class NoShowWithTagException extends Exception {
    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No shows with tag.";

    public NoShowWithTagException() {
        super(MSG);
    }
}
