/**
 *
 */
package common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper classes that validates fields include in the constructor of
 * TradeMessage
 */
public class TradeMessageValidator {

	private final String dateFormat = "dd-MMM-yy hh:mm:ss";
	private final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat,
			Locale.GERMAN);

	private final java.util.List<String> COUNTRIES = Arrays.asList(Locale
			.getISOCountries());

	private final BigDecimal HUNDRED = new BigDecimal(100, MyMath.MC);
	private final BigDecimal MAX = new BigDecimal(99000000, MyMath.MC);

	/**
	 *
	 */
	private final String MSG = "The value has to be bigger than " + HUNDRED
			+ " and lower than " + MAX + " ";
	/**
	 *
	 */
	private final String MSG_FORMAT = "Not valid Date string format, use ";

	private final Logger LOG = LoggerFactory
			.getLogger(TradeMessageValidator.class);

	private static final TradeMessageValidator INSTANCE = new TradeMessageValidator();

	private TradeMessageValidator() {
	}

	/**
	 * Runtime initialization, by default ThreadSafe,Eager initialization.
	 */
	public static TradeMessageValidator getInstance() {
		return INSTANCE;
	}

	void checkArgString(String pVal) throws IllegalArgumentException {
		if (null == pVal || pVal.isEmpty() || pVal.length() < 1) {
			throw new IllegalArgumentException(
					"not valid String input, empty or null");
		}
	}

	void checkValidInputChars(String s) {
		if (!s.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(
					"not valid String input, allowed: [A-Za-z0-9]+");
		}
	}

	/**
	 * The value has to be bigger than 100 and lower than 99,000,000.
	 *
	 * @param pVal
	 */
	void checkAmount(BigDecimal pVal) {
		if (pVal == null) {
			throw new IllegalArgumentException("not valid amount");
		}
		// if (!(pVal.compareTo(HUNDRED) > 0)) {
		// throw new IllegalArgumentException(MSG );
		// }
		if (!(pVal.compareTo(BigDecimal.ZERO) > 0)) {
			throw new IllegalArgumentException("not valid amount. must be > 0");
		}
		if ((pVal.compareTo(MAX) > 0)) {
			throw new IllegalArgumentException(MSG);
		}
	}

	void checkCountry(String pVal) {
		if (!COUNTRIES.contains(pVal)) {
			throw new IllegalArgumentException("not valid ISO country code "
					+ pVal);
		}
	}

	void checkRate(BigDecimal pVal) {
		if (pVal == null) {
			throw new IllegalArgumentException("not valid rate, NULL");
		}
		if (!(pVal.compareTo(BigDecimal.ZERO) > 0)) {
			throw new IllegalArgumentException(MSG);
		}
	}

	void checkCurrency(String pVal) {
		if (pVal == null) {
			throw new IllegalArgumentException("not valid Currency:  NULL");
		}
		if (!Currencies.contains(pVal)) {
			throw new IllegalArgumentException("Not valid Currency ");

		}
	}

	void checkDateString(String pVal) {
		try {
			Date date = formatter.parse(pVal);
		} catch (ParseException e) {
			LOG.error(MSG_FORMAT + dateFormat + " for input string " + pVal);
			throw new IllegalArgumentException(MSG_FORMAT + dateFormat
					+ " for input string " + pVal);
		} catch (NumberFormatException e) {
			LOG.error(MSG_FORMAT + dateFormat + " for input string " + pVal);
			throw new IllegalArgumentException(MSG_FORMAT + dateFormat
					+ " for input string " + pVal);
		}

	}

}