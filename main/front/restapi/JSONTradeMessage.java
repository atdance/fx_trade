/**
 *
 */
package front.restapi;

import java.math.BigDecimal;

import common.MyMath;

/**
 *
 * Implements the JSON wire message in the form of: {"userId": "134256",
 * "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy":
 * 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15
 * 10:27:44", "originatingCountry" : "FR"}
 *
 */
public class JSONTradeMessage {
	public String userId = null;
	public String currencyFrom = null;
	public String currencyTo = null;
	public BigDecimal amountSell = new BigDecimal(0, MyMath.MC);
	public BigDecimal amountBuy = new BigDecimal(0, MyMath.MC);
	public BigDecimal rate = new BigDecimal(0, MyMath.MC);
	public String timePlaced = null;
	public String originatingCountry = null;

	public JSONTradeMessage(String userId, String currencyFrom,
			String currencyTo, BigDecimal amountSell, BigDecimal amountBuy,
			BigDecimal rate, String timePlaced, String originCountry) {
		this.userId = userId;
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.amountSell = amountSell;
		this.amountBuy = amountBuy;
		this.rate = rate;
		this.timePlaced = timePlaced;
		this.originatingCountry = originCountry;
	}

	public JSONTradeMessage() {
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
				+ ((amountBuy == null) ? 0 : amountBuy.hashCode());
		result = prime * result
				+ ((amountSell == null) ? 0 : amountSell.hashCode());
		result = prime * result
				+ ((currencyFrom == null) ? 0 : currencyFrom.hashCode());
		result = prime * result
				+ ((currencyTo == null) ? 0 : currencyTo.hashCode());
		result = prime
				* result
				+ ((originatingCountry == null) ? 0 : originatingCountry
						.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result
				+ ((timePlaced == null) ? 0 : timePlaced.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		JSONTradeMessage other = (JSONTradeMessage) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "TradeMessage [userId=" + userId + ", currencyFrom="
				+ currencyFrom + ", currencyTo=" + currencyTo + ", amountSell="
				+ amountSell + ", amountBuy=" + amountBuy + ", rate=" + rate
				+ ", timePlaced=" + timePlaced + ", originatingCountry="
				+ originatingCountry + "]";
	}

}