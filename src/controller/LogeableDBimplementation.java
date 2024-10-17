package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import classes.Combat;
import classes.Pokemon;
import classes.Professor;
import classes.Trainer;
import classes.MyException;
import interfaces.Logeable;

public class LogeableDBimplementation implements Logeable {

	private Connection con;
	private PreparedStatement stmt;
	private OpenCloseConnection occ = new OpenCloseConnection();
	/*
	 * This statement must be used twice so I declare it here in order to write it
	 * just once
	 */
	final String queryGetProfessor = "select trainer_id, trainer_name, birthdate, gender, city, badges, pokeball, region from Trainer join Professor on trainer_id=professor_id where trainer_id=?;";

	/*
	 * This method gets an username, password and manages the information. If it
	 * recieves the username and password of someone identified as Trainer the
	 * method will give the information related with that trainer and if its a
	 * professor he will also give the information related with that professor
	 */
	public Trainer getPerson(String username, String password) throws MyException {
		/*
		 * Here we declare a null trainer because we have to return it on the method an
		 * it is the one that will get all the values so in order to be able to make it
		 * a professor or just a trainer we give it null value
		 */
		Trainer t = null;
		int id = isUser(username, password);
		// If it is a User it starts identifying whether it is a professor or not
		if (id != 0) {
			boolean professor = isProfessor(id);
			if (professor) {
				t = getProfessor(id);
			} else {
				t = getTrainer(id);
			}
		}
		// Here if the username or password are incorrect or are not related the value
		// will return ass null
		return t;
	}

	/**
	 * This is the method in charge of getting the id of the person that is logging
	 * in
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws MyException
	 */
	private int isUser(String username, String password) throws MyException {
		final String queryUsers = "select user_id from Login where username=? and passwd=?";
		int id = 0;

		con = occ.openConnection();
		try {
			stmt = con.prepareStatement(queryUsers);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			String error = "Error logging in";
			MyException er = new MyException(error);
			throw er;

		}
		occ.closeConnection(stmt, con);
		return id;
	}

	/**
	 * This method is used to identify if the user is a professor or not
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	private boolean isProfessor(int id) throws MyException {
		boolean pro = false;
		ResultSet rsgp2;

		con = occ.openConnection();
		try {
			stmt = con.prepareStatement(queryGetProfessor);
			stmt.setInt(1, id);
			rsgp2 = stmt.executeQuery();

			while (rsgp2.next()) {
				/*
				 * We use the region attribute to identify if the person is a professor because
				 * just if you are a professor you will have that attribute
				 */
				if (!rsgp2.getString("region").equals(null)) {
					pro = true;
				}
			}
		} catch (SQLException e) {
			String error = "Error checking for professor's existence";
			MyException er = new MyException(error);
			throw er;
		}
		occ.closeConnection(stmt, con);

		return pro;
	}

	/**
	 * This method gives all the information of the Trainer with the id that it gets
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	private Trainer getTrainer(int id) throws MyException {
		final String queryInfo = "Select trainer_id, trainer_name, birthdate, gender, city, badges, pokeball from Trainer where trainer_id = ?";
		/*
		 * A trainer has different collections in it like the pokemon that it has and
		 * its combat history show we declare auxiliar sets for it
		 */
		LinkedHashSet<Pokemon> aux = new LinkedHashSet<>();
		LinkedHashSet<Combat> auxC = new LinkedHashSet<>();
		Trainer t = new Trainer();

		con = occ.openConnection();
		try {
			stmt = con.prepareStatement(queryInfo);
			stmt.setInt(1, id);
			ResultSet rti = stmt.executeQuery();

			// Here we give values to the trainer
			while (rti.next()) {
				t.setTrainerID(rti.getInt("trainer_id"));
				t.setName(rti.getString("trainer_name"));
				t.setBirthdate(rti.getDate("birthdate"));
				t.setGender(rti.getString("gender"));
				t.setOriginCity(rti.getString("city"));
				t.setBadges(rti.getInt("badges"));
				t.setPokeballs(rti.getInt("pokeball"));
				final String queryTeam = "Select pokedex_id, region, pokemon_name, nickname, type1, type2, pokemon_lvl, location from Pokemon_static join Pokemon on pokemon_id=pokedex_id where trainer_id=? and location=true";
				stmt = con.prepareStatement(queryTeam);
				stmt.setInt(1, id);

				try {
					ResultSet rte = stmt.executeQuery();
					// Here we give values to the set storaging the trainers pokemon
					while (rte.next()) {
						Pokemon p = new Pokemon();
						p.setPokedexID(rte.getInt("pokedex_id"));
						p.setRegion(rte.getString("region"));
						p.setName(rte.getString("pokemon_name"));
						p.setNickname(rte.getString("nickname"));
						p.setType1(rte.getString("type1"));
						p.setType2(rte.getString("type2"));
						p.setLevel(rte.getInt("pokemon_lvl"));
						p.setTeam(rte.getBoolean("location"));
						aux.add(p);
					}
				} catch (SQLException e) {
					String error = "Error getting the trainer's team";
					MyException er = new MyException(error);
					throw er;
				}
				// Here we give values to the set storaging the trainers combat history
				final String queryCombat = "Select trainer_id1, trainer_id2, winner from Combat where trainer_id1=? or trainer_id2=?";
				stmt = con.prepareStatement(queryCombat);
				stmt.setInt(1, id);
				stmt.setInt(2, id);

				try {
					ResultSet rtc = stmt.executeQuery();

					while (rtc.next()) {
						Combat c = new Combat();
						c.setTrainer1(rtc.getInt("trainer_id1"));
						c.setTrainer2(rtc.getInt("trainer_id1"));
						c.setWinnerTrainerID(rtc.getInt("winner"));
						auxC.add(c);
					}
				} catch (SQLException e) {
					String error = "Error getting the trainer's combat history";
					MyException er = new MyException(error);
					throw er;
				}
				t.setTeam(aux);
				t.setCombatHistory(auxC);
			}
		} catch (SQLException e) {
			String error = "Error getting trainer";
			MyException er = new MyException(error);
			throw er;
		}

		occ.closeConnection(stmt, con);

		return t;
	}

	/**
	 * This method does the same thing as getTrainer but for the professor
	 * 
	 * @param id
	 * @return
	 * @throws MyException
	 */
	private Professor getProfessor(int id) throws MyException {
		/*
		 * In this case the professors do not have any combats or team. However they do
		 * have 3 pokemon as initial selection that we will have to add
		 */
		ArrayList<Pokemon> aux = new ArrayList<>();
		Professor p = new Professor();
		final String queryPokePro = "Select pokedex_id, region, pokemon_name, nickname, type1, type2, pokemon_lvl from Pokemon_static join Pokemon on pokemon_id=pokedex_id where trainer_id=?";

		con = occ.openConnection();

		try {
			stmt = con.prepareStatement(queryGetProfessor);
			stmt.setInt(1, id);
			ResultSet rsgp = stmt.executeQuery();

			// Here we give values to the professor
			while (rsgp.next()) {

				p.setTrainerID(rsgp.getInt("trainer_id"));
				p.setName(rsgp.getString("trainer_name"));
				p.setBirthdate(rsgp.getDate("birthdate"));
				p.setGender(rsgp.getString("gender"));
				p.setOriginCity(rsgp.getString("city"));
				p.setBadges(rsgp.getInt("badges"));
				p.setPokeballs(rsgp.getInt("pokeball"));
				p.setRegion(rsgp.getString("region"));

				stmt = con.prepareStatement(queryPokePro);
				stmt.setInt(1, id);
				try {
					ResultSet rspp = stmt.executeQuery();

					// Here we give values to the set storaging the professor pokemons initials
					while (rspp.next()) {
						Pokemon po = new Pokemon();
						po.setPokedexID(rspp.getInt("pokedex_id"));
						po.setRegion(rspp.getString("region"));
						po.setName(rspp.getString("pokemon_name"));
						po.setNickname(rspp.getString("nickname"));
						po.setType1(rspp.getString("type1"));
						po.setType2(rspp.getString("type2"));
						po.setLevel(rspp.getInt("pokemon_lvl"));
						aux.add(po);
					}
				} catch (SQLException e) {
					String error = "Error getting professor's initial selection";
					MyException er = new MyException(error);
					throw er;
				}
			}
		} catch (SQLException e) {
			String error = "Error getting professor";
			MyException er = new MyException(error);
			throw er;
		}

		p.setInitialSelection(aux);
		occ.closeConnection(stmt, con);

		return p;
	}
}
