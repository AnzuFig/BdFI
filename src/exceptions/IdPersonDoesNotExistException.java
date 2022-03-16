package exceptions;

public class IdPersonDoesNotExistException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idPerson does not exist.";

    public IdPersonDoesNotExistException() {
        super(MSG);
    }


}
