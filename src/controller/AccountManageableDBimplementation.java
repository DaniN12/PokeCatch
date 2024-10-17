package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import classes.Combat;
import classes.Login;
import classes.MyException;
import classes.Pokemon;
import classes.Professor;
import classes.Trainer;
import interfaces.AccountManageable;

/**
 * 
 * This class implements the AccountManageable interface and manages Trainer and
 * Combat objects in a database.
 * 
 * @author Alexander Epelde
 * 
 */
public class AccountManageableDBimplementation implements AccountManageable {

	/**
	 * Connection to the database
	 */
	private Connection con;

	/**
	 * Statement to execute a query
	 */
	private PreparedStatement stmt;

	/**
	 * Object to open and close the connection to the database
	 */
	private OpenCloseConnection occ = new OpenCloseConnection();

	/**
	 * String to store the query
	 */
	private String query = null;

	/**
	 * This method adds a new trainer to the database
	 * 
	 * @param trainer The trainer to be added
	 * @throws MyException if there is an error adding the trainer to the database
	 */
	@Override
	public void addTrainer(Trainer trainer) throws MyException {

		// SQL query to insert a new trainer into the database
		query = "insert into Trainer (trainer_name, birthdate, gender, city, badges, pokeball) values (?, ?, ?, ?, ?, ?)";

		// Open a database connection
		con = occ.openConnection();

		try {
			// Prepare a statement with the SQL query
			stmt = con.prepareStatement(query);
			// Set the parameters of the statement with data from the Trainer object
			stmt.setString(1, trainer.getName());
			stmt.setDate(2, trainer.getBirthdate());
			stmt.setString(3, trainer.getGender());
			stmt.setString(4, trainer.getOriginCity());
			stmt.setInt(5, trainer.getBadges());
			stmt.setInt(6, trainer.getPokeballs());
			// Execute the statement to insert the new trainer into the database
			stmt.executeUpdate();

		} catch (SQLException e) {
			// If an exception occurs, create a new MyException object with an error message
			String error = "Error adding new trainer";
			MyException er = new MyException(error);
			// Throw the exception to the calling method
			throw er;
		}

		// Close the database connection and the statement
		occ.closeConnection(stmt, con);
	}

	/**
	 * 
	 * This method deletes a Trainer from the Trainer table of a database based on
	 * their ID.
	 * 
	 * @param trainerID the ID of the Trainer to be deleted
	 * 
	 * @throws MyException if there is an error while attempting to delete the
	 *                     Trainer
	 */
	public void deleteTrainer(int trainerID) throws MyException {
		// SQL query to delete Trainer with given ID
		query = "delete from Trainer where trainer_id = ?";

		// Establish a database connection using a custom openConnection() method
		con = occ.openConnection();

		try {
			// Prepare the SQL statement with the given query and Trainer ID parameter
			stmt = con.prepareStatement(query);
			stmt.setInt(1, trainerID);
			// Execute the prepared statement to delete the Trainer
			stmt.executeUpdate();
		} catch (SQLException e) {
			// If an error occurs while executing the prepared statement, throw a custom
			// exception with a descriptive error message
			String error = "Error deleting trainer";
			MyException er = new MyException(error);
			throw er;
		}
		// Close the database connection using a custom closeConnection() method
		occ.closeConnection(stmt, con);
	}

	/**
	 * 
	 * Returns a LinkedHashSet of all the trainers in the database who are not
	 * professors, including their pokemon team and combat history.
	 * 
	 * @return LinkedHashSet of Trainer objects with their pokemon team and combat
	 *         history
	 * @throws MyException if there was an error getting the trainers from the
	 *                     database
	 */
	@Override
	public LinkedHashSet<Trainer> getTrainers() throws MyException {

		// Create a new LinkedHashSet to store the trainers
		LinkedHashSet<Trainer> trainers = new LinkedHashSet<>();

		// Declare some variables for ResultSet, Trainer, and query
		ResultSet rst;
		ResultSet rsp;
		ResultSet rsc;
		Trainer t = null;
		query = "SELECT * FROM Trainer WHERE trainer_id NOT IN (SELECT professor_id FROM Professor)";
		con = occ.openConnection();

		try {
			// Create a new prepared statement for the query and execute it
			stmt = con.prepareStatement(query);
			rst = stmt.executeQuery();

			// Loop through the results of the query
			while (rst.next()) {
				// Create new LinkedHashSet objects to store the pokemon and combat history of
				// each trainer
				LinkedHashSet<Pokemon> pok = new LinkedHashSet<>();
				LinkedHashSet<Combat> com = new LinkedHashSet<>();
				int id = 0;
				t = new Trainer();
				id = rst.getInt("trainer_id");
				t.setTrainerID(id);
				t.setName(rst.getString("trainer_name"));
				t.setGender(rst.getString("gender"));
				t.setBirthdate(rst.getDate("birthdate"));
				t.setOriginCity(rst.getString("city"));
				t.setBadges(rst.getInt("badges"));
				t.setPokeballs(rst.getInt("pokeball"));

				// Create a new query to get the pokemon for this trainer and execute it
				query = "Select pokedex_id, region, pokemon_name, nickname, type1, type2, pokemon_lvl, location from Pokemon_static join Pokemon on pokemon_id=pokedex_id where trainer_id=? and location=true";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				rsp = stmt.executeQuery();

				// Loop through the results of the pokemon query
				while (rsp.next()) {
					// Create a new Pokemon object and set its properties
					Pokemon p = null;
					p = new Pokemon();
					p.setPokedexID(rsp.getInt("pokedex_id"));
					p.setRegion(rsp.getString("region"));
					p.setName(rsp.getString("pokemon_name"));
					p.setNickname(rsp.getString("nickname"));
					p.setType1(rsp.getString("type1"));
					p.setType2(rsp.getString("type2"));
					p.setLevel(rsp.getInt("pokemon_lvl"));
					p.setTeam(rsp.getBoolean("location"));
					pok.add(p);
				}
				// Set the pokemon team for this trainer
				t.setTeam(pok);

				// Create a new query to get the combat history for this trainer and execute it
				query = "Select trainer_id1, trainer_id2, winner from Combat where trainer_id1=? or trainer_id2=?";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.setInt(2, id);
				rsc = stmt.executeQuery();

				// Loop through the results of the combat history query
				while (rsc.next()) {
					// Create a new Combat object and set its properties
					Combat c = new Combat();
					c.setTrainer1(rsc.getInt("trainer_id1"));
					c.setTrainer2(rsc.getInt("trainer_id2"));
					c.setWinnerTrainerID(rsc.getInt("winner"));
					com.add(c);
				}
				// Set the combat history for this trainer
				t.setCombatHistory(com);
				trainers.add(t);
			}
		} catch (SQLException e) {
			// If there's an SQL exception, create a new MyException with a custom error
			// message
			String error = "Error getting trainers";
			MyException er = new MyException(error);
			// throw it
			throw er;
		} finally {
			// close the connection to the database
			occ.closeConnection(stmt, con);
		}

		return trainers;
	}

	@Override
	/**
	 * 
	 * Updates the amount of Pokeballs a Trainer has in the database.
	 * 
	 * @param trainer The Trainer object whose Pokeballs will be updated.
	 * 
	 * @throws MyException If there is an error updating the Pokeball amount in the
	 *                     database.
	 */
	public void givePokeballs(Trainer trainer) throws MyException {

		// Define the SQL query to update the pokeball amount for a trainer
		query = "UPDATE Trainer SET pokeball = ? WHERE trainer_id = ?";

		// Open a connection to the database
		con = occ.openConnection();

		try {
			// Prepare the SQL statement with the query
			stmt = con.prepareStatement(query);

			// Set the values for the first and second parameters of the SQL query using the
			// Trainer object's methods
			stmt.setInt(1, trainer.getPokeballs());
			stmt.setInt(2, trainer.getTrainerID());

			// Execute the SQL query to update the number of pokeballs for the trainer
			stmt.executeUpdate();
		} catch (SQLException e) {
			// If an SQL error occurs, catch the exception and throw a custom exception
			// MyException with a custom error message
			String error = "Error updating the pokeball count";
			MyException er = new MyException(error);
			throw er;
		}

		// Close the database connection and statement
		occ.closeConnection(stmt, con);
	}

	/**
	 * 
	 * Modifies the data of an existing Trainer in the database.
	 * 
	 * @param trainer The Trainer object containing the updated information.
	 * 
	 * @throws MyException If there is an error modifying the Trainer's data in the
	 *                     database.
	 */
	@Override
	public void modifyTrainer(Trainer trainer) throws MyException {
		// Define the SQL query to update the Trainer table row with the corresponding
		// trainer ID.
		query = "update Trainer set trainer_name = ?, birthdate = ?, gender = ?, city = ?, badges = ?, pokeball = ? WHERE trainer_id = ?";

		// Open a connection to the database.
		con = occ.openConnection();

		try {
			// Prepare the SQL statement.
			stmt = con.prepareStatement(query);
			// Set the parameters for the statement based on the Trainer object.
			stmt.setString(1, trainer.getName());
			stmt.setDate(2, trainer.getBirthdate());
			stmt.setString(3, trainer.getGender());
			stmt.setString(4, trainer.getOriginCity());
			stmt.setInt(5, trainer.getBadges());
			stmt.setInt(6, trainer.getPokeballs());
			stmt.setInt(7, trainer.getTrainerID());

			// Execute the SQL statement and get the number of rows affected.
			int id = stmt.executeUpdate();
			if (id == 0) {
				// If no rows were updated, throw an exception with an appropriate message.
				MyException er = new MyException("Nothing updated");
				throw er;
			} else if (id == 1) {
				// If one row was updated, throw an exception with a success message.
				MyException success = new MyException("Trainer updated successfully");
				throw success;
			}

		} catch (SQLException e) {
			// If an SQL exception is caught, throw an exception with an error message.
			String error = "Error modifying trainer's data";
			MyException er = new MyException(error);
			throw er;

		}

		// Close the database connection and statement.
		occ.closeConnection(stmt, con);

	}

	/**
	 * 
	 * Upgrades a Trainer to a Professor in the database.
	 * 
	 * @param professor The Professor object to be added to the database.
	 * 
	 * @throws MyException If there is an error creating the Professor or upgrading
	 *                     the Trainer to Professor.
	 */
	@Override
	public void upgradeToProfessor(Professor professor) throws MyException {
		int id;
		// SQL query to insert new Professor into database
		query = "insert into Professor (professor_id, region) values (?, ?)";

		// Get an ArrayList of Pokemon from the professor object
		ArrayList<Pokemon> aux = professor.getInitialSelection();

		// Open a database connection
		con = occ.openConnection();

		// Try to execute the first SQL query
		try {
			// Prepare the SQL statement
			stmt = con.prepareStatement(query);
			// Set the values for the parameters in the SQL query
			stmt.setInt(1, professor.getTrainerID());
			stmt.setString(2, professor.getRegion());
			// Execute the SQL statement
			stmt.executeUpdate();
		} catch (SQLException e) {
			// If there's an exception, create a new MyException with an error message
			String error = "Error creating new professor";
			MyException er = new MyException(error);
			// Throw the exception
			throw er;
		}
		// Try to execute the second SQL query
		try {
			// Update the query to call a stored procedure
			query = "call upgradeToProfessor(?, ?, ?, ?)";
			// Prepare the SQL statement
			stmt = con.prepareStatement(query);
			// Set the values for the parameters in the SQL query
			stmt.setInt(1, professor.getTrainerID());
			stmt.setInt(2, aux.get(0).getPokedexID());
			stmt.setInt(3, aux.get(1).getPokedexID());
			stmt.setInt(4, aux.get(2).getPokedexID());
			// Execute the SQL statement
			id = stmt.executeUpdate();
			// If the update affects at least one row, create a new MyException with a
			// success message

		} catch (SQLException e) {
			// If there's an exception, create a new MyException with the error message from
			// the exception
			String error = e.getMessage();
			MyException er = new MyException(error);
			// Throw the exception
			throw er;
		}

		// Close the database connection
		occ.closeConnection(stmt, con);
		
		if (id == 0) {
			MyException er = new MyException("Trainer upgraded successfully");
			// Throw the exception
			throw er;
		}
	}

	/**
	 * 
	 * Sets the login information for the most recently added Trainer in the
	 * database.
	 * 
	 * @param log The Login object containing the username and password for the
	 *            Trainer's login information.
	 * 
	 * @throws MyException If there is an error setting the login information.
	 */
	@Override
	// Sets the login information for a trainer
	public void setLogin(Login log) throws MyException {

		// Defines a SQL query to retrieve the last inserted trainer_id from the Trainer
		// table
		query = "select trainer_id from Trainer order by trainer_id desc limit 1";

		// Opens a connection to the database
		con = occ.openConnection();

		int id = -1;

		try {
			// Prepares a statement to execute the first query
			stmt = con.prepareStatement(query);
			// Executes the query and retrieves the result set
			ResultSet rs = stmt.executeQuery();
			// If the result set is not empty, retrieves the trainer_id value from it
			if (rs.next()) {
				id = rs.getInt("trainer_id");
			}
			// Defines a SQL query to insert the login information in the Login table
			query = "insert into Login(user_id, username, passwd) values (?, ?, ?)";
			// Prepares a statement to execute the second query
			stmt = con.prepareStatement(query);
			// Binds the parameters to the statement
			stmt.setInt(1, id);
			stmt.setString(2, log.getUsername());
			stmt.setString(3, log.getPassword());
			// Executes the query and retrieves the number of affected rows
			id = stmt.executeUpdate();
			// If one row was affected, throws a custom exception with a success message
			if (id == 1) {
				MyException message = new MyException("Trainer added successfully");
				throw message;
			}

		} catch (SQLException e) {
			// If there's an SQL exception, throws a custom exception with an error message
			String error = "Error setting the login information";
			MyException er = new MyException(error);
			throw er;
		}
	}

}
