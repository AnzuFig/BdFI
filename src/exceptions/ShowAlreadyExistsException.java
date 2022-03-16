package exceptions;

public class ShowAlreadyExistsException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idShow exists.";

    public ShowAlreadyExistsException() {
        super(MSG);
    }

}
