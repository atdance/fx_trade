package com.bondsbiz.trade.business.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An exchange between two currencies and the amount requested
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Exchange {

	private static AtomicInteger IDGenerator = new AtomicInteger();

	public final static String MAX_ALLOWED = "100000000";

	int ID;

	@NotNull
	private CurrencyPair currencyPair;

	@NotNull
	@DecimalMin("1.00")
	@DecimalMax("100000000")
	private BigDecimal amountSell;

	@NotNull
	@DecimalMin("1.00")
	@DecimalMax(MAX_ALLOWED)
	private BigDecimal amountBuy;

	/**
	 *
	 */
	public Exchange(@Valid CurrencyPair pair, @Valid BigDecimal pSell, @Valid BigDecimal pBuy) {
		currencyPair = pair;
		amountSell = pSell;
		amountBuy = pBuy;

		ID = IDGenerator.getAndIncrement();

		if (ID == Integer.MAX_VALUE) {
			throw new IllegalArgumentException("ID " + ID + " is Integer.MAX_VALUE");
		}
	}

	/**
	 * public for json-b
	 */
	public Exchange() {

	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(CurrencyPair pCurrencyPair) {
		currencyPair = pCurrencyPair;
	}

	public BigDecimal getAmountSell() {
		return amountSell;
	}

	public void setAmountSell(BigDecimal pAmountSell) {
		amountSell = pAmountSell;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public void setAmountBuy(BigDecimal pAmountBuy) {
		amountBuy = pAmountBuy;
	}

	public int getID() {
		return ID;
	}

	public void setID(int pID) {
		ID = pID;
	}

	@Override
	public String toString() {
		return "Exchange [ID=" + ID + ", pair=" + currencyPair + ", sell=" + amountSell + ", buy=" + amountBuy + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((amountBuy == null) ? 0 : amountBuy.hashCode());
		result = prime * result + ((amountSell == null) ? 0 : amountSell.hashCode());
		result = prime * result + ((currencyPair == null) ? 0 : currencyPair.hashCode());
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
		Exchange other = (Exchange) obj;
		if (ID != other.ID) {
			return false;
		}
		if (amountBuy == null) {
			if (other.amountBuy != null) {
				return false;
			}
		} else if (!amountBuy.equals(other.amountBuy)) {
			return false;
		}
		if (amountSell == null) {
			if (other.amountSell != null) {
				return false;
			}
		} else if (!amountSell.equals(other.amountSell)) {
			return false;
		}
		if (currencyPair == null) {
			if (other.currencyPair != null) {
				return false;
			}
		} else if (!currencyPair.equals(other.currencyPair)) {
			return false;
		}
		return true;
	}

}