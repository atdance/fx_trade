package com.bondsbiz.trade.business.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An exchange between two currencies and the amount requested
 *
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Exchange {
	@NotNull
	private CurrencyPair aPair;
	@NotNull
	private BigDecimal amountSell;
	@NotNull
	private BigDecimal amountBuy;

	/**
	 *
	 */
	public Exchange(CurrencyPair pair, BigDecimal pSell, BigDecimal pBuy) {
		aPair = pair;
		amountSell = pSell;
		amountBuy = pBuy;
	}

	public Exchange() {

	}

	public CurrencyPair getaPair() {
		return aPair;
	}

	public void setaPair(CurrencyPair aPair) {
		this.aPair = aPair;
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

	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aPair == null) ? 0 : aPair.hashCode());
		result = prime * result + ((amountBuy == null) ? 0 : amountBuy.hashCode());
		result = prime * result + ((amountSell == null) ? 0 : amountSell.hashCode());
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
		final Exchange other = (Exchange) obj;
		if (aPair == null) {
			if (other.aPair != null) {
				return false;
			}
		} else if (!aPair.equals(other.aPair)) {
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
		return true;
	}

	@Override
	public String toString() {
		return "Exchange [aPair=" + aPair + ", amountSell=" + amountSell + ", amountBuy=" + amountBuy + "]";
	}

}