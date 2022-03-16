package exceptions;

public class PersonAlreadyExistException extends Exception {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private static final String MSG = "idPerson exists.";

    public PersonAlreadyExistException() {
        super(MSG);
    }
}
