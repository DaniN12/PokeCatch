package factories;

import controller.LogeableDBimplementation;
import interfaces.Logeable;

/**
 * 
 * The LogeableFactory class provides a factory method for getting a Logeable
 * object which can be used for logging purposes. This factory method returns a
 * LogeableDBimplementation object which implements the Logeable interface.
 */

public class LogeableFactory {
// The obj static variable holds a reference to the LogeableDBimplementation instance.
	private static Logeable obj = new LogeableDBimplementation();

	/**
	 * This static method returns a Logeable object which can be used for logging
	 * purposes.
	 * 
	 * @return a Logeable object
	 */
	public static Logeable getLogeable() {
		return obj;
	}

}
