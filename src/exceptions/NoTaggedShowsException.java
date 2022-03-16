package exceptions;

public class NoTaggedShowsException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No tagged productions.";

    public NoTaggedShowsException() {
        super(MSG);
    }

}
