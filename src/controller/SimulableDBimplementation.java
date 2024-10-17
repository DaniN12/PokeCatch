package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import classes.Combat;
import classes.MyException;
import classes.Pokemon;
import classes.Trainer;
import interfaces.Simulable;

	/**
    This class implements the Simulable interface to provide methods for managing a Trainer's actions in a Pokémon simulation.
    The class establishes a connection with a database using the OpenCloseConnection class and provides methods to add caught Pokémon,
    update combat history, retrieve a trainer's team of Pokémon, and perform various actions related to Pokémon battles.
    @author PerezImanol
    @version 1.0
    */

public class SimulableDBimplementation implements Simulable {

	private Connection con;
	private PreparedStatement stmt;
	private OpenCloseConnection occ = new OpenCloseConnection();

	/**
	 * 
	 * Adds a caught Pokémon to the trainer's collection.
	 * 
	 * @param pokemon   The Pokémon to be added.
	 * 
	 * @param trainerID The ID of the trainer.
	 * 
	 * @throws MyException if an error occurs while adding the Pokémon.
	 */

	@Override
	public void addCaughtPokemon(Pokemon pokemon, int trainerID) throws MyException {
		final String queryCatch = "INSERT INTO Pokemon(pokemon_id, trainer_id, pokemon_lvl, nickname, location) VALUES( ?, ?, ?, ?, ?)";

		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(queryCatch);
			stmt.setInt(1, pokemon.getPokedexID());
			stmt.setInt(2, trainerID);
			stmt.setInt(3, pokemon.getLevel());
			stmt.setString(4, pokemon.getNickname());
			stmt.setBoolean(5, pokemon.isTeam());
			stmt.executeUpdate();

		} catch (SQLException e) {
			String error = "Error adding caught pokemon";
			MyException er = new MyException(error);
			throw er;
		}

		occ.closeConnection(stmt, con);
	}

	/**
	 * 
	 * Updates the combat history after a battle between two trainers.
	 * 
	 * @param combat The combat object representing the battle.
	 * 
	 * @throws MyException if an error occurs while updating the combat history.
	 */

	@Override
	public void updateCombatHistory(Combat combat) throws MyException {
		if (combat != null) {
			final String insertBattleData = "insert into Combat(trainer_id1, trainer_id2, winner) values(?, ?, ?)";
			con = occ.openConnection();

			try {
				stmt = con.prepareStatement(insertBattleData);
				stmt.setInt(1, combat.getTrainer1());
				stmt.setInt(2, combat.getTrainer2());
				stmt.setInt(3, combat.getWinnerTrainerID());
				stmt.executeUpdate();

			} catch (SQLException e) {
				String error = "Error updating combat history";
				MyException er = new MyException(error);
				throw er;
			}

			occ.closeConnection(stmt, con);
		}
	}

	/**
	 * Retrieves the team Pokemons belonging to a specific Trainer.
	 *
	 * @param trainer The Trainer object for whom to retrieve the team Pokemons.
	 * @return A LinkedHashSet of Pokemon objects representing the team Pokemons.
	 * @throws MyException If there is an error retrieving the team Pokemons.
	 */

	@Override
	public LinkedHashSet<Pokemon> getTeamPokemons(Trainer trainer) throws MyException {
		ResultSet rs = null;
		LinkedHashSet<Pokemon> pokemons = new LinkedHashSet<>();
		final String queryPokemons = "select * from Pokemon where location = true and trainer_id = ?";

		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(queryPokemons);
			stmt.setInt(1, trainer.getTrainerID());
			rs = stmt.executeQuery();

			while (rs.next()) {
				Pokemon p = new Pokemon();
				p.setPokedexID(rs.getInt("pokedex_id"));
				p.setRegion(rs.getString("region"));
				p.setName(rs.getString("pokemon_name"));
				p.setNickname(rs.getString("nickname"));
				p.setType1(rs.getString("type1"));
				p.setType2(rs.getString("type2"));
				p.setLevel(rs.getInt("pokemon_lvl"));
				pokemons.add(p);
			}
		} catch (SQLException e) {
			String error = "Error getting team pokemons";
			MyException er = new MyException(error);
			throw er;
		}
		occ.closeConnection(stmt, con);

		return pokemons;
	}

	/**
	 * Changes the position of a Pokemon in a Trainer's team.
	 *
	 * @param trainer The Trainer object for whom to change the position.
	 * @param pokemon The Pokemon object to change the position of.
	 * @throws MyException If there is an error changing the position.
	 */

	@Override
	public void changePosition(Trainer trainer, Pokemon pokemon) throws MyException {
		final String queryUpdateTeam = "UPDATE Pokemon SET location = ? WHERE trainer_id = ? AND pokemon_id = ?";

		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(queryUpdateTeam);
			stmt.setBoolean(1, !pokemon.isTeam());
			stmt.setInt(2, trainer.getTrainerID());
			stmt.setInt(3, pokemon.getPokedexID());
			stmt.executeUpdate();

		} catch (SQLException e) {
			String error = "Error getting professor";
			MyException er = new MyException(error);
			throw er;
		}

		occ.closeConnection(stmt, con);
	}

	/**
	 * Switches the positions of two Pokemons in a Trainer's team.
	 *
	 * @param trainer  The Trainer object for whom to switch the positions.
	 * @param pokemon1 The first Pokemon to switch positions with.
	 * @param pokemon2 The second Pokemon to switch positions with.
	 * @throws MyException If there is an error switching the positions.
	 */

	@Override
	public void switchPosition(Trainer trainer, Pokemon pokemon1, Pokemon pokemon2) throws MyException {
		final String switchQuery = "UPDATE Pokemon SET location = ? WHERE trainer_id = ? AND pokemon_id = ?";
		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(switchQuery);
			stmt.setBoolean(1, !pokemon1.isTeam());
			stmt.setInt(2, trainer.getTrainerID());
			stmt.setInt(3, pokemon1.getPokedexID());
			stmt.executeUpdate();

			stmt = con.prepareStatement(switchQuery);
			stmt.setBoolean(1, !pokemon2.isTeam());
			stmt.setInt(2, trainer.getTrainerID());
			stmt.setInt(3, pokemon2.getPokedexID());
			int flag = stmt.executeUpdate();
			if (flag > 0) {
				MyException message = new MyException("Pokemons switched successfully");
				throw message;
			}
		} catch (SQLException e) {
			String error = "Error swithing positions";
			MyException er = new MyException(error);
			throw er;
		}

		occ.closeConnection(stmt, con);
	}

	/**
	 * Retrieves the winner of a battle based on the given array of Pokemon IDs.
	 *
	 * @param pokemons An array of Pokemon IDs participating in the battle.
	 * @return The ID of the winning Pokemon.
	 * @throws MyException If there is an error retrieving the winner.
	 */

	@Override
	public int getWinner(int pokemons[]) throws MyException {

		int winner = 0;
		ResultSet rs = null;
		final String callWinnerFunction = "select getWinner(?,?,?,?,?,?,?,?,?,?,?,?) as winner";
		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(callWinnerFunction);
			for (int i = 0; i < pokemons.length; i++) {
				stmt.setInt(i + 1, pokemons[i]);
			}

			rs = stmt.executeQuery();

			if (rs.next()) {
				winner = rs.getInt("winner");
			}
		} catch (SQLException e) {
			String error = "Error getting a winner";
			MyException er = new MyException(error);
			throw er;
		}
		return winner;

	}
}