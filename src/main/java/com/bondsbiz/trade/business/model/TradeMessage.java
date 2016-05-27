package com.bondsbiz.trade.business.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Implements a TradeMessage message as for this JSON form of: {"userId":
 * "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000,
 * "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "
 * originatingCountry" : "FR"}
 *
 *
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TradeMessage {
	@NotNull
	@Size(min = 2, max = 256)
	private String userId = null;
	@NotNull
	private String currencyFrom = null;
	@NotNull
	private String currencyTo = null;
	@NotNull
	private BigDecimal amountSell = null;
	@NotNull
	private BigDecimal amountBuy = null;
	@NotNull
	private CurrencyPair currencyPair = null;
	@NotNull
	private Exchange exchange = null;
	@NotNull
	private BigDecimal rate = null;
	@NotNull
	private String timePlaced = null;
	@NotNull
	private String originatingCountry = null;

	@Version
	private long version;

	public TradeMessage() {
	}

	public TradeMessage(String pUserId, String pCurrencyFrom, String pCurrencyTo,
			BigDecimal pAmountSell, BigDecimal pAmountBuy, BigDecimal pRate, String pTimePlaced, String pOriginCountry)
			throws IllegalArgumentException {

		this.userId = pUserId;
		this.currencyFrom = pCurrencyFrom;
		this.currencyTo = pCurrencyTo;
		this.amountBuy = pAmountBuy;
		this.amountSell = pAmountSell;
		this.rate = pRate;
		this.timePlaced = pTimePlaced;
		this.originatingCountry = pOriginCountry;
		this.currencyPair = new CurrencyPair(pCurrencyFrom, pCurrencyTo);
		this.exchange = new Exchange(currencyPair, pAmountSell, pAmountBuy);
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}

	public void setCurrencyPair(CurrencyPair currencyPair) {
		this.currencyPair = currencyPair;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String pID) {
		userId = pID;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}

	public String getCurrencyTo() {
		return currencyTo;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public String getTimePlaced() {
		return timePlaced;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	/**
	 * @return the amountBuy
	 */
	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	@Override
	public String toString() {
		return "TradeMessage [userId=" + userId + ", currencyFrom=" + currencyFrom + ", currencyTo=" + currencyTo
				+ ", amountSell=" + amountSell + ", rate=" + rate + ", timePlaced=" + timePlaced
				+ ", originatingCountry=" + originatingCountry + ", amountBuy=" + amountBuy + ", currencyPair="
				+ currencyPair + ", exchange=" + exchange + ", version=" + version + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountBuy == null) ? 0 : amountBuy.hashCode());
		result = prime * result + ((amountSell == null) ? 0 : amountSell.hashCode());
		result = prime * result + ((currencyFrom == null) ? 0 : currencyFrom.hashCode());
		result = prime * result + ((currencyPair == null) ? 0 : currencyPair.hashCode());
		result = prime * result + ((currencyTo == null) ? 0 : currencyTo.hashCode());
		result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
		result = prime * result + ((originatingCountry == null) ? 0 : originatingCountry.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((timePlaced == null) ? 0 : timePlaced.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
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
		final TradeMessage other = (TradeMessage) obj;
		if (amountBuy == null) {
			if (other.amountBuy != null) {
				return false;
			}
		} else if (!amountBuy.equals(other.amountBuy)) {
			return false;
		}
		// if (amountSell == null) {
		// if (other.amountSell != null)
		// return false;
		// } else if (!amountSell.equals(other.amountSell))
		// return false;
		if (currencyFrom == null) {
			if (other.currencyFrom != null) {
				return false;
			}
		} else if (!currencyFrom.equals(other.currencyFrom)) {
			return false;
		}
		if (currencyPair == null) {
			if (other.currencyPair != null) {
				return false;
			}
		} else if (!currencyPair.equals(other.currencyPair)) {
			return false;
		}
		if (currencyTo == null) {
			if (other.currencyTo != null) {
				return false;
			}
		} else if (!currencyTo.equals(other.currencyTo)) {
			return false;
		}
		if (exchange == null) {
			if (other.exchange != null) {
				return false;
			}
		} else if (!exchange.equals(other.exchange)) {
			return false;
		}
		if (originatingCountry == null) {
			if (other.originatingCountry != null) {
				return false;
			}
		} else if (!originatingCountry.equals(other.originatingCountry)) {
			return false;
		}
		if (rate == null) {
			if (other.rate != null) {
				return false;
			}
		} else if (!rate.equals(other.rate)) {
			return false;
		}
		if (timePlaced == null) {
			if (other.timePlaced != null) {
				return false;
			}
		} else if (!timePlaced.equals(other.timePlaced)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		if (version != other.version) {
			return false;
		}
		return true;
	}

}