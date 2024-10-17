package classes;
/* Custom exception class that extends the base Exception class.
 * 
 * This class represents an exception with a custom error message.
 * 
 * @author Alexander Epelde
 */
public class MyException extends Exception {

	/**
	 * The serial version UID for serialization and deserialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The custom error message for this exception.
	 */
	private String message;

	/**
	 * Constructs a new MyException object with the specified error message.
	 * 
	 * @param the error message for this exception
	 */
	public MyException(String message) {
		this.message = message;
	}

	/**
	 * Returns the error message for this exception.
	 * 
	 * @return the error message for this exception
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the error message for this exception.
	 * 
	 * @param message the error message for this exception
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
