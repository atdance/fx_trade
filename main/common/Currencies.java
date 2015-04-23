/**
 *
 */
package common;

import java.util.HashSet;
import java.util.Set;

/**
 * Allowed currencies. Their lower case should correspond to the names of the
 * values of the array data in the javascript graph renderer.
 */
public enum Currencies {
	EUR, GBP;

	private final static Set<String> values = new HashSet<String>(
			Currencies.values().length);

	static {
		for (Currencies f : Currencies.values()) {
			values.add(f.name());
		}
	}

	public static boolean contains(String value) {
		return values.contains(value);
	}
}
