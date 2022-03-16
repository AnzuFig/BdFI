package exceptions;

public class InvalidYearException extends Exception {

	/**
	 * Serial Version UID of the Class
	 */
	static final long serialVersionUID = 0L;

	private static final String MSG = "Invalid year."; 
	public InvalidYearException() {
		super(MSG);
	}
}
