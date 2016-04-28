/**
 *
 */
package common;


/**
 * A currencyPair of currencies
 *
 */
public class CurrencyPair {

	public String currencyFrom;
	public String currencyTo;

	public CurrencyPair() {
	}

	/**
	 * thread-safe immutable class representing a Currency currencyPair.
	 */
	public CurrencyPair(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currencyFrom == null) ? 0 : currencyFrom.hashCode());
		result = prime * result
				+ ((currencyTo == null) ? 0 : currencyTo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CurrencyPair other = (CurrencyPair) obj;
		if (currencyFrom == null) {
			if (other.currencyFrom != null) {
				return false;
			}
		} else if (!currencyFrom.equals(other.currencyFrom)) {
			return false;
		}
		if (currencyTo == null) {
			if (other.currencyTo != null) {
				return false;
			}
		} else if (!currencyTo.equals(other.currencyTo)) {
			return false;
		}
		return true;
	}

}