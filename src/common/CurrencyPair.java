/**
 *
 */
package common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A pair of currencies
 *
 */
public class CurrencyPair {

	public String currencyFrom;
	public String currencyTo;

	public CurrencyPair() {
	}

	/**
	 * thread-safe immutable class representing a Currency pair.
	 */
	public CurrencyPair(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * @return the currencyFrom
	 */
	public String from() {
		return currencyFrom;
	}

	/**
	 * @return the currencyTo
	 */
	public String to() {
		return currencyTo;
	}

	@Override
	public String toString() {
		return "CurrencyPair [currencyFrom=" + currencyFrom + ", currencyTo="
				+ currencyTo + "]";
	}

}