package interfaces;

import java.util.LinkedHashSet;

import classes.Login;
import classes.MyException;
import classes.Professor;
import classes.Trainer;

/**
 * 
 * This interface defines the methods that must be implemented by a class
 * responsible for managing user accounts, including trainers and professors.
 * 
 * @author Alexander Epelde
 */

public interface AccountManageable {

	/**
	 * Adds a new trainer to the system.
	 * 
	 * @param trainer The trainer to be added.
	 * @throws MyException If there is an error adding the trainer.
	 */
	public void addTrainer(Trainer trainer) throws MyException;

	/**
	 * Modifies an existing trainer's details.
	 * 
	 * @param trainer The trainer to be modified.
	 * @throws MyException If there is an error modifying the trainer.
	 */
	public void modifyTrainer(Trainer trainer) throws MyException;

	/**
	 * Deletes a trainer from the system.
	 * 
	 * @param trainerID The ID of the trainer to be deleted.
	 * @throws MyException If there is an error deleting the trainer.
	 */
	public void deleteTrainer(int trainerID) throws MyException;

	/**
	 * Upgrades a trainer to a professor.
	 * 
	 * @param professor The trainer to be upgraded to a professor.
	 * @throws MyException If there is an error upgrading the trainer.
	 */
	public void upgradeToProfessor(Professor professor) throws MyException;

	/**
	 * Gives pokeballs to a trainer.
	 * 
	 * @param trainer The trainer to whom pokeballs will be given.
	 * @throws MyException If there is an error giving pokeballs to the trainer.
	 */
	public void givePokeballs(Trainer trainer) throws MyException;

	/**
	 * Returns a linked hash set containing all the trainers in the system.
	 * 
	 * @return A linked hash set containing all the trainers in the system.
	 * @throws MyException If there is an error retrieving the trainers.
	 */
	public LinkedHashSet<Trainer> getTrainers() throws MyException;

	/**
	 * Sets the login information for the account management system.
	 * 
	 * @param log The login information to be set.
	 * @throws MyException If there is an error setting the login information.
	 */
	public void setLogin(Login log) throws MyException;

}