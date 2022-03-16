package exceptions;

public class InvalidRatingException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "Invalid Rating.";
    public InvalidRatingException() {
        super(MSG);
    }

}
