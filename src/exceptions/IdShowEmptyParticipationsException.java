package exceptions;

public class IdShowEmptyParticipationsException extends Exception{

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idShow has no participations.";
    public IdShowEmptyParticipationsException() {
        super(MSG);
    }

}
