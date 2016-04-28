/**
 *
 */
package test.lowlevel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Micro-tests before implementing features in main code
 *
 */
public class Various {
	Logger LOGGER = LoggerFactory.getLogger(Various.class);
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
	// @Test
	public void test() {
		Map<String, String> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(), iso);
		}

		LOGGER.info(countries.get("Switzerland"));

		try {
			Date date = formatter.parse("24-JAN-15 10:27:44");
			LOGGER.info(date.toString());

		} catch (ParseException e) {
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

}