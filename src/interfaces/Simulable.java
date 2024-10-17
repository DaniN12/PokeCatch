package interfaces;

import java.util.LinkedHashSet;

import classes.Combat;
import classes.MyException;
import classes.Pokemon;
import classes.Trainer;

    /**

    The Simulable interface defines the operations that can be performed on a Trainer in a Pokémon simulation.
    @author PerezImanol
    @version 1.0
    */

public interface Simulable {
    /**

    Adds a caught Pokémon to the trainer's collection.
    @param pokemon The Pokémon to be added.
    @param trainerID The ID of the trainer.
    @throws MyException if an error occurs while adding the Pokémon.
    */

    public void addCaughtPokemon(Pokemon pokemon, int trainerID) throws MyException;

    /**

    Updates the combat history after a battle between two trainers.
    @param combat The combat object representing the battle.
    @throws MyException if an error occurs while updating the combat history.
    */

    public void updateCombatHistory(Combat combat) throws MyException;

    /**

    Retrieves the whole team of Pokémon for a given trainer as a LinkedHashSet.
    @param trainer The trainer whose team is to be retrieved.
    @return The LinkedHashSet containing the trainer's team of Pokémon.
    @throws MyException if an error occurs while retrieving the team.
    */
    public LinkedHashSet<Pokemon> getTeamPokemons(Trainer trainer) throws MyException;

    /**

    Moves a Pokémon between the trainer's team and the computer.
    @param trainer The trainer who owns the Pokémon.
    @param pokemon The Pokémon to be moved.
    @throws MyException if an error occurs while changing the position of the Pokémon.
    */
    public void changePosition(Trainer trainer, Pokemon pokemons) throws MyException;

    /**

    Switches the position of two Pokémon, one in the trainer's team and one in the computer.
    @param trainer The trainer who owns one of the Pokémon.
    @param pokemon1 The first Pokémon to be switched.
    @param pokemon2 The second Pokémon to be switched.
    @throws MyException if an error occurs while switching the position of the Pokémon.
    */
    public void switchPosition(Trainer trainer, Pokemon pokemon1, Pokemon pokemon2) throws MyException;

    /**

    Determines the winner of a battle based on the Pokémon IDs of both trainers' teams.
    @param pokemons An array of Pokémon IDs representing the teams of both trainers.
    @return The ID of the winning trainer.
    @throws MyException if an error occurs while determining the winner.
    */
    public int getWinner(int pokemons[]) throws MyException;
}
