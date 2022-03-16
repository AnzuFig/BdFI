package exceptions;

public class PersonHasNoShowsException extends Exception {

    static final long serialVersionUID = 0L;

    private static final String MSG ="idPerson has no shows.";
    public PersonHasNoShowsException(){
        super(MSG);
    }

}
