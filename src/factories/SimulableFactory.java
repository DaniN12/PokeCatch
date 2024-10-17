package factories;

import controller.SimulableDBimplementation;
import interfaces.Simulable;

/**
 * 
 * The SimulableFactory class is a factory class that provides a single instance
 * of a Simulable object. This class returns a SimulableDBimplementation object
 * as the instance of the Simulable interface.
 */

public class SimulableFactory {
	/**
	 * The obj variable is a static field of the SimulableFactory class. This field
	 * holds an instance of the SimulableDBimplementation class, which implements
	 * the Simulable interface.
	 */
	private static Simulable obj = new SimulableDBimplementation();

	/**
	 * The getSimulable method is a static method of the SimulableFactory class.
	 * This method returns the obj variable, which holds an instance of the
	 * SimulableDBimplementation class.
	 * 
	 * @return the Simulable object instance provided by the factory.
	 */
	public static Simulable getSimulable() {
		return obj;
	}

}