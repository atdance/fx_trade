/**
 *
 */
package restapi;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
		// this.pair = new CurrencyPair(currencyFrom, currencyTo);
		// this.exc = new Exchange(pair, amountSell, amountBuy);
	}

	// public CurrencyPair getCurrencyPair() {
	// return pair;
	// }

	// public Exchange getExchange() {
	// return exc;
	// }

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
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