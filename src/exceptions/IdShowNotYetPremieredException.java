package exceptions;

public class IdShowNotYetPremieredException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idShow is in production.";
    public IdShowNotYetPremieredException() {
        super(MSG);
    }
}
