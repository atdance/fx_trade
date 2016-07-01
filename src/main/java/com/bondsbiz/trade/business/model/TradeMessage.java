package com.bondsbiz.trade.business.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Version;
import javax.validation.Valid;
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
	// @NotNull
	// private CurrencyPair currencyPair = null;
	// @NotNull
	// private Exchange exchange = null;
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

	public TradeMessage(@Valid String pUserId, String pCurrencyFrom, String pCurrencyTo, @Valid BigDecimal pAmountSell,
			@Valid BigDecimal pAmountBuy, @Valid BigDecimal pRate, String pTimePlaced, String pOriginCountry) {

		this.userId = pUserId;
		this.rate = pRate;
		this.timePlaced = pTimePlaced;
		this.originatingCountry = pOriginCountry;

		this.currencyFrom = pCurrencyFrom;
		this.currencyTo = pCurrencyTo;
		this.amountBuy = pAmountBuy;
		this.amountSell = pAmountSell;

		// this.currencyPair = new CurrencyPair(pCurrencyFrom, pCurrencyTo);
		// this.exchange = new Exchange(currencyPair, pAmountSell, pAmountBuy);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String pID) {
		userId = pID;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}
	public BigDecimal getAmountSell(){
		return amountSell;
	}
	
	public void setAmountSell(BigDecimal amountSell){
		this.amountSell = amountSell;
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

}