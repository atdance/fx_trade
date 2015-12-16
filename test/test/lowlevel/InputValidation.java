/**
 *
 */
package test.lowlevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author name
 *
 */
public class InputValidation {
	Logger logger = LoggerFactory.getLogger(InputValidation.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unused")
	@Test
	public void shouldNotCreateUsernameFromInjectionAttackString() {
		String s = "' OR 1=1 --";
		String g = "sadsadfsdfd.s";
		String k = "aaaa.a.ff";
		logger.info(isValid(k));
	}

	public String isValid(String s) {
		boolean res = s.matches("[A-Za-z0-9]+");
		return "" + res;
	}

}

class Username {
	public final String username;

	public Username(String username) {
		this.username = username;
	}

}
