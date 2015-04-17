/**
 *
 */
package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author name
 *
 */
public class InputValidation {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldNotCreateUsernameFromInjectionAttackString() {
		String s = "' OR 1=1 --";
		String g = "sadsadfsdfd.s";
		String k = "aaaa.a.ff";
		System.out.println(isValid(k));
	}

	public boolean isValid(String s) {
		return s.matches("[A-Za-z0-9]+");
	}

}

class Username {
	public final String username;

	public Username(String username) {
		this.username = username;
	}

}
