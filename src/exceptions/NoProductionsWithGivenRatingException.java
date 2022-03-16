package exceptions;

public class NoProductionsWithGivenRatingException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "No productions with rating.";

    public NoProductionsWithGivenRatingException() {
        super(MSG);
    }
}
