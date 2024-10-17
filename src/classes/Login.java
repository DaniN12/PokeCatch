package classes;

/**
 * 
 * The Login class represents a user's login information, including their
 * username and password.
 * 
 * @author Alexander Epelde
 */
public class Login {

	/**
	 * The user's username.
	 */
	private String username;

	/**
	 * The user's password.
	 */
	private String password;

	/**
	 * Gets the user's username.
	 * 
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user's username.
	 * 
	 * @param username the username to set for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 * 
	 * @param password the password to set for the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns a string representation of the Login object, including the username
	 * and password.
	 * 
	 * @return a string representation of the Login object
	 */
	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + "]";
	}

}
