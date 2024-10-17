package interfaces;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import classes.Combat;
import classes.MyException;
import classes.Pokemon;
import classes.PokemonExtra;

/**
 * 
 * The DataBattleShowable interface provides methods to retrieve and display
 * data related to Pokemon battles. It contains three methods that retrieve data
 * from different sources: getPokemons(), getTrainerCombatHistory(), and
 * getPcPokemons().
 * 
 * @author Alexander Epelde
 */

public interface DataBattleShowable {

	/**
	 * Returns a LinkedHashSet of PokemonExtra objects representing the all the
	 * pokemon stored in the database
	 *
	 * @return LinkedHashSet of PokemonExtra objects
	 * @throws MyException if an error occurs while retrieving the data
	 */
	public LinkedHashSet<PokemonExtra> getPokemons() throws MyException;

	/**
	 * Returns a LinkedHashSet of Combat objects representing the battles that a
	 * particular trainer has participated in.
	 *
	 * @param trainerID the ID of the trainer whose combat history should be
	 *                  retrieved
	 * @return LinkedHashSet of Combat objects
	 * @throws MyException if an error occurs while retrieving the data
	 */
	public LinkedHashSet<Combat> getTrainerCombatHistory(Integer trainerID) throws MyException;

	/**
	 * Returns a LinkedHashMap of Pokemon objects representing the Pokemon in a
	 * particular trainer's PC.
	 *
	 * @param trainer_id the ID of the trainer whose PC Pokemon should be retrieved
	 * @return LinkedHashMap of String keys and Pokemon objects
	 * @throws MyException if an error occurs while retrieving the data
	 */
	public LinkedHashMap<String, Pokemon> getPcPokemons(int trainer_id) throws MyException;

}
