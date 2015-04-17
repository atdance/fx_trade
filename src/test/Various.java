/**
 *
 */
package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.MyTime;

/**
 * Micro-tests before implementing features in main code
 *
 */
public class Various {
	final public static ThreadLocal<Random> threadLocalRandom = new ThreadLocal<Random>() {
		@Override
		protected Random initialValue() {
			return new Random(Thread.currentThread().getId());
		}
	};
	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"dd-MMM-yy hh:mm:ss", Locale.GERMAN);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	// @Test
	public void test() {
		Map<String, String> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			// System.out.format("%s,%s \n", l.getDisplayCountry(), iso);
			countries.put(l.getDisplayCountry(), iso);
		}

		// System.out.println(countries.get("Switzerland"));

		try {
			Date date = formatter.parse("24-JAN-15 10:27:44");
			System.out.println(date.toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test
	public void refresh() {
		String resp = "'";
		System.out.format("%s,\n", resp);

		resp = "[{\"name\":\"New York\",\"data\":[{\"x\":0,\"y\":11},{\"x\":1,\"y\":11},{\"x\":2,\"y\":9},{\"x\":3,\"y\":7}]},{\"name\":\"London\",\"data\":[{\"x\":0,\"y\":6},{\"x\":1,\"y\":2},{\"x\":2,\"y\":4},{\"x\":3,\"y\":7}]},{\"name\":\"Tokyo\",\"data\":[{\"x\":0,\"y\":31},{\"x\":1,\"y\":26},{\"x\":2,\"y\":34},{\"x\":3,\"y\":31}]}]";
		System.out.format("%s,\n", resp);
	}

	@Test
	public void ss() {
		Random r = new Random();

		MyTime timer = new MyTime();
		int i = 2000000;
		for (int j = 0; j < i; j++) {
			// Math.random();
			// ThreadLocalRandom.current().nextInt(100);
			// threadLocalRandom.get().nextInt(100);
			r.nextInt(100);
		}

		System.out.println(timer);

	}

}