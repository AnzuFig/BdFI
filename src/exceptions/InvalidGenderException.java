package exceptions;

public class InvalidGenderException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "Invalid gender information.";

    public InvalidGenderException() {
        super(MSG);
    }

}
