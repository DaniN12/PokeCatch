package interfaces;


import classes.MyException;
import classes.Trainer;

public interface Logeable {

	/**
	 * This method is in charge of identifying if the username and the password introduced 
	 * belong to one trainer or if it belongs to one professor
	 * @param username the String used to identify a certain trainer or professor
	 * @param password the password that it uses to enter that account
	 * @return Trainer or Professor whether the person is a trainer or a professor 
	 * @throws MyException
	 */
	public Trainer getPerson(String username, String password) throws MyException;

}
