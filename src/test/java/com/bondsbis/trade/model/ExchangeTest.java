package com.bondsbis.trade.model;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.bondsbis.trade.DataLoad;
import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.services.ModelValidator;
import com.bondsbiz.trade.business.storage.MyMath;

/**
 * Testing strategy.
 *
 * <pre>
 * s
 *  Partition on CurrencyMarket getByID() :
 *   ID = null
 *   ID = 1
 *   ID = first index
 *   ID = SIZE
 *   ID = SIZE + 1
 *
 * </pre>
 *
 */
public class ExchangeTest {

	@Test
	public void ID_generator_steps_correctly() {
		TradeMessage validTrade = DataLoad.makeRandomTrade();

		CurrencyPair currencyPair = new CurrencyPair(validTrade.getCurrencyFrom(), validTrade.getCurrencyTo());

		Exchange exchange1 = new Exchange(currencyPair, validTrade.getAmountSell(), validTrade.getAmountBuy());

		Exchange exchange2 = new Exchange(currencyPair, new BigDecimal(Integer.MAX_VALUE), validTrade.getAmountBuy());

		assertTrue(exchange2.getID() == (exchange1.getID() + 1));
	}

	@Test
	public void should_have_constraint_violations_when_creating_with_null_currency_pair() {
		CurrencyPair validcurrencyPair = new CurrencyPair("GBP", "EUR");

		CurrencyPair invalidCurrencyPair = null;

		Exchange invalidExchange = new Exchange(invalidCurrencyPair, new BigDecimal("1500", MyMath.MC),
				new BigDecimal("2000", MyMath.MC));

		assertTrue(ModelValidator.hasConstraintViolations(validcurrencyPair, invalidExchange));
	}

	@Test
	public void should_have_constraint_violations_when_creating_with_null_sell_amount() {
		CurrencyPair validcurrencyPair = new CurrencyPair("GBP", "EUR");

		BigDecimal invalidSellAmount = null;

		Exchange exchange = new Exchange(validcurrencyPair, invalidSellAmount, new BigDecimal("2000", MyMath.MC));

		assertTrue(ModelValidator.hasConstraintViolations(validcurrencyPair, exchange));
	}

	@Test
	public void should_have_constraint_violations_when_creating_with_invalid_buy_amount() {
		CurrencyPair validcurrencyPair = new CurrencyPair("GBP", "EUR");

		BigDecimal invalidBuyAmount = null;

		Exchange exchange = new Exchange(validcurrencyPair, new BigDecimal("2000", MyMath.MC), invalidBuyAmount);

		assertTrue(ModelValidator.hasConstraintViolations(validcurrencyPair, exchange));
	}

	@Test
	public void should_have_constraint_violations_when_creating_with_buy_amount_below_allowed_range() {
		CurrencyPair validcurrencyPair = new CurrencyPair("GBP", "EUR");

		BigDecimal invalidBuyAmount = new BigDecimal("-1");

		Exchange exchange = new Exchange(validcurrencyPair, new BigDecimal("2000", MyMath.MC), invalidBuyAmount);

		assertTrue(ModelValidator.hasConstraintViolations(validcurrencyPair, exchange));
	}

	@Test
	public void should_have_constraint_violations_when_creating_with_buy_amount_exceeds_than_allowed_range() {
		CurrencyPair validcurrencyPair = new CurrencyPair("GBP", "EUR");

		BigDecimal invalidBuyAmount = new BigDecimal(Exchange.MAX_ALLOWED + ".1");

		Exchange exchange = new Exchange(validcurrencyPair, new BigDecimal("2000", MyMath.MC), invalidBuyAmount);

		assertTrue(ModelValidator.hasConstraintViolations(validcurrencyPair, exchange));
	}

}