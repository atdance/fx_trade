package common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final String dateFormat = "dd-MMM-yy hh:mm:ss";
	private final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat,
			Locale.GERMAN);

	private final java.util.List<String> COUNTRIES = Arrays.asList(Locale
			.getISOCountries());

	private final BigDecimal HUNDRED = new BigDecimal(100, MyMath.MC);
	private final BigDecimal MAX = new BigDecimal(99000000, MyMath.MC);

	private final BigDecimal MAX_RATE = new BigDecimal(40000, MyMath.MC);

	private final Logger LOG = LoggerFactory.getLogger(TradeMessage.class);

	public String userId = null;
	public String currencyFrom = null;
	public String currencyTo = null;
	public BigDecimal amountSell = new BigDecimal(0, MyMath.MC);
	public BigDecimal amountBuy = new BigDecimal(0, MyMath.MC);
	public BigDecimal rate = new BigDecimal(0, MyMath.MC);
	public String timePlaced = null;
	public String originatingCountry = null;

	public CurrencyPair pair = null;
	public Exchange exc;

	public TradeMessage() {
		this.pair = new CurrencyPair(currencyFrom, currencyTo);
		this.exc = new Exchange(pair, amountSell, amountBuy);
	}

	public TradeMessage(String pUserId, String pCurrencyFrom,
			String pCurrencyTo, BigDecimal pAmountSell, BigDecimal pAmountBuy,
			BigDecimal pRate, String pTimePlaced, String pOriginCountry)
			throws IllegalArgumentException {

		TradeMessageValidator.getInstance().checkArgString(pUserId);
		TradeMessageValidator.getInstance().checkValidInputChars(pUserId);
		TradeMessageValidator.getInstance().checkArgString(pCurrencyFrom);
		TradeMessageValidator.getInstance().checkArgString(pCurrencyTo);
		TradeMessageValidator.getInstance().checkArgString(pOriginCountry);
		this.userId = pUserId;

		TradeMessageValidator.getInstance().checkCurrency(pCurrencyFrom);
		TradeMessageValidator.getInstance().checkCurrency(pCurrencyTo);

		this.currencyFrom = pCurrencyFrom;
		this.currencyTo = pCurrencyTo;

		TradeMessageValidator.getInstance().checkAmount(pAmountSell);
		TradeMessageValidator.getInstance().checkAmount(pAmountBuy);
		this.amountSell = pAmountSell;
		this.amountBuy = pAmountBuy;

		TradeMessageValidator.getInstance().checkRate(pRate);
		this.rate = pRate;

		TradeMessageValidator.getInstance().checkDateString(pTimePlaced);
		this.timePlaced = pTimePlaced;

		TradeMessageValidator.getInstance().checkArgString(pOriginCountry);
		TradeMessageValidator.getInstance().checkCountry(pOriginCountry);
		this.originatingCountry = pOriginCountry;

		this.pair = new CurrencyPair(pCurrencyFrom, pCurrencyTo);
		this.exc = new Exchange(pair, pAmountSell, pAmountBuy);
	}

	public TradeMessage(JSONTradeMessage msg) {
		this(msg.userId, msg.currencyFrom, msg.currencyTo, msg.amountSell,
				msg.amountBuy, msg.rate, msg.timePlaced, msg.originatingCountry);
	}

	public CurrencyPair getCurrencyPair() {
		return pair;
	}

	public Exchange getExchange() {
		return exc;
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
				+ amountSell + ", amountBuy=" + amountBuy + ", rate=" + rate
				+ ", timePlaced=" + timePlaced + ", originatingCountry="
				+ originatingCountry + "]";
	}

}