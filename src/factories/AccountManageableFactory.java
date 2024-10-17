package factories;

import controller.AccountManageableDBimplementation;
import interfaces.AccountManageable;

/**
 * 
 * The AccountManageableFactory class provides a static method to obtain an
 * instance of the AccountManageable interface implementation, which is
 * currently set to AccountManageableDBimplementation. This class follows the
 * Singleton design pattern to ensure that only one instance of the
 * AccountManageable interface implementation is created. The method
 * getAccountManageable() returns the instance of the AccountManageable
 * interface implementation. This implementation can be changed to a different
 * implementation by changing the value of the static variable "obj".
 */
public class AccountManageableFactory {
	private static AccountManageable obj = new AccountManageableDBimplementation();

	/**
	 * Returns an instance of the AccountManageable interface implementation.
	 *
	 * @return the instance of the AccountManageable interface implementation
	 */
	public static AccountManageable getAccountManageable() {
		return obj;
	}

}