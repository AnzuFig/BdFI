package exceptions;

public class IdShowDoesNotExistException extends Exception{

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idShow does not exist.";

    public IdShowDoesNotExistException() {
        super(MSG);
    }
}
