package factories;

import controller.DataBattleShowableDBimplementation;
import interfaces.DataBattleShowable;

/**
 * 
 * The DataBattleFactory class is a factory class that creates and returns an
 * instance of DataBattleShowable interface. The class initializes a static
 * object of DataBattleShowableDBimplementation which implements the
 * DataBattleShowable interface. The class provides a public static method
 * getDataBattleShowable() which returns the static object.
 */
public class DataBattleFactory {
	private static DataBattleShowable obj = new DataBattleShowableDBimplementation();

	/**
	 * Returns the static object of DataBattleShowable interface.
	 * 
	 * @return DataBattleShowable object
	 */
	public static DataBattleShowable getDataBattleShowable() {
		return obj;
	}

}
