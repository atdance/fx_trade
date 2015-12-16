package common;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import front.restapi.JSONTradeMessage;

/**
 *
 * Implements a TradeMessage message as for this JSON form of: {"userId":
 * "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000,
 * "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15
 * 10:27:44", "originatingCountry" : "FR"}
 *
 *
 */
public class TradeMessage {
	private final String userId;
	private final String currencyFrom;
	private final String currencyTo;
	private final BigDecimal amountSell;// = new BigDecimal(0, MyMath.MC);
	private final BigDecimal amountBuy;// = new BigDecimal(0, MyMath.MC);
	private final BigDecimal rate;// = new BigDecimal(0, MyMath.MC);
	private final String timePlaced;
	private final String originatingCountry;

	private final CurrencyPair currencyPair;
	private final Exchange exchange;

	public TradeMessage() {
		userId = null;
		currencyFrom = null;
		currencyTo = null;
		amountSell = null;// = new BigDecimal(0, MyMath.MC);
		amountBuy = null;// = new BigDecimal(0, MyMath.MC);
		rate = null;// = new BigDecimal(0, MyMath.MC);
		timePlaced = null;
		originatingCountry = null;
		exchange = null;
		currencyPair = null;
	}

	// public TradeMessage() {
	// this.currencyPair = new CurrencyPair(currencyFrom, currencyTo);
	// this.exchange = new Exchange(currencyPair, amountSell, amountBuy);
	// }

	public TradeMessage(String pUserId, String pCurrencyFrom,
			String pCurrencyTo, BigDecimal pAmountSell, BigDecimal pAmountBuy,
			BigDecimal pRate, String pTimePlaced, String pOriginCountry)
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
		TradeMessageValidator.getInstance().validate(this);
	}

	public TradeMessage(JSONTradeMessage msg) {
		this(msg.userId, msg.currencyFrom, msg.currencyTo, msg.amountSell,
				msg.amountBuy, msg.rate, msg.timePlaced, msg.originatingCountry);
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public Exchange getExchange() {
		return exchange;
	}

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
				+ amountSell + ", amountBuy=" + getAmountBuy() + ", rate="
				+ rate + ", timePlaced=" + timePlaced + ", originatingCountry="
				+ originatingCountry + "]";
	}

	/**
	 * @return the amountBuy
	 */
	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

}