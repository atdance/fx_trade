package com.bondsbiz.trade.business.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A currencyPair of currencies
 *
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class CurrencyPair {
	@NotNull
	private String currencyFrom;
	@NotNull
	private String currencyTo;

	public CurrencyPair() {
	}

	/**
	 * thread-safe immutable class representing a Currency currencyPair.
	 */
	public CurrencyPair(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyFrom == null) ? 0 : currencyFrom.hashCode());
		result = prime * result + ((currencyTo == null) ? 0 : currencyTo.hashCode());
		return result;
	}

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
		final CurrencyPair other = (CurrencyPair) obj;
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

	@Override
	public String toString() {
		return "CurrencyPair [currencyFrom=" + currencyFrom + ", currencyTo=" + currencyTo + "]";
	}

}