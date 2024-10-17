package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import classes.Combat;
import classes.MyException;
import classes.Pokemon;
import classes.PokemonExtra;
import interfaces.DataBattleShowable;

/**
 * 
 * This class implements the DataBattleShowable interface to provide methods to
 * retrieve and display data related to battles from a database. The class
 * establishes a connection with the database using the OpenCloseConnection
 * class, and provides the methods to get a set of PokemonExtra objects, get a
 * set of Combat objects representing a trainer's combat history, and get a
 * linked hash map of Pokemon objects representing a trainer's PC Pokemons.
 * 
 * @author Alexander Epelde
 * @version 1.0
 */

public class DataBattleShowableDBimplementation implements DataBattleShowable {

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
	 * 
	 * Returns a LinkedHashSet with all the pokemons stored in the database.
	 * 
	 * @return a LinkedHashSet with all the pokemons stored in the database.
	 * 
	 * @throws MyException if an error occurs while accessing the database.
	 */
	@Override
	public LinkedHashSet<PokemonExtra> getPokemons() throws MyException {
		LinkedHashSet<PokemonExtra> pokemons = new LinkedHashSet<>();

		// SQL query to get data from the table "Pokemon_static"
		query = "select * from Pokemon_static";

		// Opening a connection to the database
		con = occ.openConnection();

		try {
			// Creating a prepared statement for the query
			stmt = con.prepareStatement(query);
			// Executing the query and storing the results in the ResultSet object rs
			ResultSet rs = stmt.executeQuery();

			// Iterating through each row of the ResultSet
			while (rs.next()) {
				// Creating a new PokemonExtra object
				PokemonExtra p = new PokemonExtra();
				// Setting the attributes of the PokemonExtra object using data from the
				// ResultSet
				p.setPokedexID(rs.getInt("pokedex_id"));
				p.setName(rs.getString("pokemon_name"));
				p.setType1(rs.getString("type1"));
				p.setType2(rs.getString("type2"));
				p.setRegion(rs.getString("region"));
				p.setCaptureRatio(rs.getFloat("capture_ratio"));
				// Adding the PokemonExtra object to the LinkedHashSet of pokemons
				pokemons.add(p);
			}
		} catch (SQLException e) {
			// If an SQLException is thrown, create a new MyException with an error message
			String error = "Error getting the pokedex";
			MyException er = new MyException(error);
			// Throw the MyException
			throw er;
		}
		// Closing the connection to the database
		occ.closeConnection(stmt, con);
		// Return the LinkedHashSet of pokemons
		return pokemons;

	}

	/**
	 * 
	 * Returns a LinkedHashSet with all the combat history of a given trainer.
	 * 
	 * @param trainerID the id of the trainer whose combat history is requested.
	 * 
	 * @return a LinkedHashSet with all the combat history of the given trainer.
	 * 
	 * @throws MyException if an error occurs while accessing the database.
	 */

	@Override
	public LinkedHashSet<Combat> getTrainerCombatHistory(Integer trainerID) throws MyException {
		// Create a new LinkedHashSet to store the Combat objects.
		LinkedHashSet<Combat> combat = new LinkedHashSet<>();

		// Create the query to retrieve the combat history for the given trainer ID.
		query = "select * from Combat where trainer_id1 = ? or trainerid2 = ?";

		ResultSet rs;

		// Open a connection to the database.
		con = occ.openConnection();
		try {
			// Prepare the SQL statement.
			stmt = con.prepareStatement(query);
			stmt.setInt(1, trainerID);
			stmt.setInt(2, trainerID);
			// Execute the query.
			rs = stmt.executeQuery();

			// Loop through the results of the query and create a new Combat object for each
			// row.
			while (rs.next()) {
				Combat c = new Combat();
				c.setTrainer1(rs.getInt("trainer_id1"));
				c.setTrainer2(rs.getInt("trainer_id3"));
				c.setWinnerTrainerID(rs.getInt("winner"));
				// Add the Combat object to the LinkedHashSet.
				combat.add(c);
			}

		} catch (SQLException e) {
			// If there is an error, create a new MyException and throw it.
			String error = "Error getting combat history";
			MyException er = new MyException(error);
			throw er;
		}
		// Close the database connection.
		occ.closeConnection(stmt, con);

		// Return the LinkedHashSet of Combat objects.
		return combat;
	}

	/**
	 * 
	 * Returns a LinkedHashMap with all the pokemons stored in a given trainer's PC.
	 * 
	 * @param trainer_id the id of the trainer whose pokemons are requested.
	 * 
	 * @return a LinkedHashMap with all the pokemons stored in the given trainer's
	 *         PC.
	 * 
	 * @throws MyException if an error occurs while accessing the database.
	 */

	@Override
	public LinkedHashMap<String, Pokemon> getPcPokemons(int trainer_id) throws MyException {
		LinkedHashMap<String, Pokemon> aux = new LinkedHashMap<>();

		// Query to retrieve the necessary information from the database
		query = "Select pokedex_id, region, pokemon_name, nickname, type1, type2, pokemon_lvl, location from Pokemon_static join Pokemon on pokemon_id=pokedex_id where trainer_id=? and location=false";
		con = occ.openConnection();

		try {
			// Create the statement to execute the query and set the trainer ID parameter
			stmt = con.prepareStatement(query);
			stmt.setInt(1, trainer_id);

			// Execute the query and iterate over the result set to create Pokémon objects
			// and add them to the LinkedHashMap
			ResultSet rte = stmt.executeQuery();
			while (rte.next()) {
				Pokemon p = new Pokemon();
				p.setPokedexID(rte.getInt("pokedex_id"));
				p.setRegion(rte.getString("region"));
				p.setName(rte.getString("pokemon_name"));
				p.setNickname(rte.getString("nickname"));
				p.setType1(rte.getString("type1"));
				p.setType2(rte.getString("type2"));
				p.setLevel(rte.getInt("pokemon_lvl"));
				aux.put(p.getName(), p);
			}

		} catch (SQLException e) {
			// If there is an exception, throw a custom exception indicating that there was
			// an error retrieving the Pokémon from the database
			String error = "Error getting the trainer's pc pokemons";
			MyException er = new MyException(error);
			throw er;
		}

		// Return the LinkedHashMap containing the trainer's Pokémon
		return aux;
	}

}
