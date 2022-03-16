package exceptions;

public class IdShowAlreadyPremieredException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idShow has already completed production.";

    public IdShowAlreadyPremieredException() {
        super(MSG);
    }

}
