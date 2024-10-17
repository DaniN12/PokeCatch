package classes;

import java.util.ArrayList;

/**
 * 
 * A class that represents a Professor, which is a subclass of Trainer and has a
 * region and an initial selection of Pokemon. It provides setters and getters
 * for region and initialSelection fields. The class also includes a method to
 * get an initial Pokemon by name and a method to complete a Professor's
 * information.
 * 
 * @author Alexander Epelde
 */

public class Professor extends Trainer {

	private String region;
	private ArrayList<Pokemon> initialSelection;

	/**
	 * Returns the region of the Professor.
	 * 
	 * @return The region of the Professor.
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region of the Professor.
	 * 
	 * @param region The region to set.
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Returns the initial selection of Pokemon of the Professor.
	 * 
	 * @return The initial selection of Pokemon of the Professor.
	 */
	public ArrayList<Pokemon> getInitialSelection() {
		return initialSelection;
	}

	/**
	 * Sets the initial selection of Pokemon of the Professor.
	 * 
	 * @param initialSelection The initial selection of Pokemon to set.
	 */
	public void setInitialSelection(ArrayList<Pokemon> initialSelection) {
		this.initialSelection = initialSelection;
	}

	/**
	 * Returns the Pokemon from the initial selection of the Professor with the
	 * given name.
	 * 
	 * @param name The name of the Pokemon to get.
	 * @return The Pokemon with the given name or null if it is not in the initial
	 *         selection.
	 */
	public Pokemon getInitial(String name) {
		return initialSelection.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
	}

	/**
	 * Completes the information of the Professor with the information of the given
	 * Trainer and the given region.
	 * 
	 * @param t The Trainer to get the information from.
	 * @param r The region to set.
	 */
	public void completeProf(Trainer t, String r) {
		this.setTrainerID(t.getTrainerID());
		this.setName(t.getName());
		this.setBirthdate(t.getBirthdate());
		this.setGender(t.getGender());
		this.setOriginCity(t.getOriginCity());
		this.setBadges(t.getBadges());
		this.setPokeballs(t.getPokeballs());
		this.setRegion(r);
	}

	/**
	 * Returns a String representation of the Professor.
	 * 
	 * @return A String representation of the Professor.
	 */
	@Override
	public String toString() {
		return "Professor [region=" + region + ", initialSelection=" + initialSelection + "]";
	}

}