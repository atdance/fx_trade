package com.bondsbis.trade;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.services.ForexMarketService;
import com.bondsbiz.trade.business.storage.MyMath;

public class DataLoad {

	public DataLoad(ForexMarketService pForexMarketService) {
	}

	public static Exchange makeRandomExchange() {

		TradeMessage validTrade = makeRandomTrade();

		CurrencyPair currencyPair = new CurrencyPair(validTrade.getCurrencyFrom(), validTrade.getCurrencyTo());
		Exchange exchange = new Exchange(currencyPair, validTrade.getAmountSell(), validTrade.getAmountBuy());

		return exchange;
	}

	public static TradeMessage makeRandomTrade() {

		String userID = "" + ThreadLocalRandom.current().nextInt(10, 255);
		String amountSell = "" + ThreadLocalRandom.current().nextInt(1, 99_000);

		String amountBuy = "" + ThreadLocalRandom.current().nextInt(1, 99_000);

		return new TradeMessage(userID, "EUR", "GBP", new BigDecimal(amountSell, MyMath.MC),
				new BigDecimal(amountBuy, MyMath.MC), new BigDecimal("0.7471", MyMath.MC), "24-JAN-15 10:27:44", "FR");
	}

}