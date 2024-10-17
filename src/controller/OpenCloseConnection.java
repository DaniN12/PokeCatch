package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import classes.MyException;

/**
 * 
 * The OpenCloseConnection class manages the opening and closing of database
 * connections. It retrieves the database configuration information from the
 * Config.properties file, opens a connection to the database using JDBC, and
 * provides a method for closing the connection and prepared statement object.
 * The class throws a custom exception MyException when there is an error
 * connecting to or closing the connection to the database.
 */

public class OpenCloseConnection {
	private ResourceBundle configFile; // Resource bundle for storing the database configuration parameters.
	private String url; // The URL for the database.
	private String user; // The username for the database.
	private String pass; // The password for the database.

	/**
	 * Constructor that initializes the database connection parameters by reading
	 * them from a configuration file.
	 */
	public OpenCloseConnection() {
		configFile = ResourceBundle.getBundle("resources.Config"); // Load the configuration file.
		url = configFile.getString("URL"); // Get the URL from the configuration file.
		user = configFile.getString("USER"); // Get the username from the configuration file.
		pass = configFile.getString("PASSWORD"); // Get the password from the configuration file.
	}

	/**
	 * Opens a connection to the database using the database connection parameters.
	 *
	 * @return a Connection object representing the database connection.
	 * @throws MyException if there is an error connecting to the database.
	 */
	public Connection openConnection() throws MyException {
		Connection con = null; // Initialize the connection to null.
		try {
			con = DriverManager.getConnection(url, user, pass); // Establish the database connection using the
																// parameters from the configuration file.
		} catch (SQLException e) {
			String error = "Error connecting to the database"; // Define the error message.
			MyException er = new MyException(error); // Create a new exception with the error message.
			throw er; // Throw the exception.
		}
		return con; // Return the established database connection.
	}

	/**
	 * Closes a database connection and a prepared statement.
	 *
	 * @param stmt the PreparedStatement to close.
	 * @param con  the Connection to close.
	 * @throws MyException if there is an error closing the statement or connection.
	 */
	public void closeConnection(PreparedStatement stmt, Connection con) throws MyException {
		if (stmt != null) { // Check if the prepared statement is not null.
			try {
				stmt.close(); // Close the prepared statement.
			} catch (SQLException e) {
				String error = "Error closing statement"; // Define the error message.
				MyException er = new MyException(error); // Create a new exception with the error message.
				throw er; // Throw the exception.
			}
		}
		if (con != null) { // Check if the connection is not null.
			try {
				con.close(); // Close the connection.
			} catch (SQLException e) {
				String error = "Error closing the flux to the database"; // Define the error message.
				MyException er = new MyException(error); // Create a new exception with the error message.
				throw er; // Throw the exception.
			}
		}
	}
}