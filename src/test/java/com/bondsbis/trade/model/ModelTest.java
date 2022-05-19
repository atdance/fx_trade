package com.bondsbis.trade.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bondsbis.trade.ClientDriver;
import com.bondsbis.trade.DataLoad;
import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.storage.Currencies;
import com.bondsbiz.trade.business.storage.MyMath;

/**
 * tests for classes in package .model
 *
 */
public class ModelTest extends ClientDriver {

	TradeMessage validTrade = DataLoad.makeRandomTrade();

	CurrencyPair currencyPair = new CurrencyPair(validTrade.getCurrencyFrom(), validTrade.getCurrencyTo());

	Exchange exchange = new Exchange(currencyPair, validTrade.getAmountSell(), validTrade.getAmountBuy());

	List<Exchange> exchanges = new ArrayList<>();

	@Test
	public void testMapOfStringInteger() {
		Map<String, BigDecimal> map = new HashMap<>();

		GenericEntity<Map<String, BigDecimal>> mapOfString = new GenericEntity<Map<String, BigDecimal>>(map){
		};

		Class<?> rawType = mapOfString.getRawType();

		assertEquals(rawType, HashMap.class);

		Type type = mapOfString.getType();

		assertTrue(type instanceof ParameterizedType);

		ParameterizedType pType = (ParameterizedType) type;

		Type typeArgs[] = pType.getActualTypeArguments();

		assertEquals(2, typeArgs.length);

		assertTrue(typeArgs[0] instanceof Class<?>);

		Class<?> typeArgType = (Class<?>) typeArgs[0];

		assertEquals(typeArgType, String.class);

		assertTrue(typeArgs[1] instanceof Class<?>);

		typeArgType = (Class<?>) typeArgs[1];

		assertEquals(typeArgType, BigDecimal.class);
	}

	@Test
	public void mapUnwrapped() {

		Map<String, BigDecimal> volume = new HashMap<>();

		volume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.TEN);
		volume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.TEN);

		GenericEntity<Map<String, BigDecimal>> entities = new GenericEntity<Map<String, BigDecimal>>(volume){
		};

		Response r = Response.ok().entity(entities).build();

		assertSame(volume, r.getEntity());

		Class<?> rawType = entities.getRawType();

		assertEquals(rawType, HashMap.class);

		Type type = entities.getType();

		assertTrue(type instanceof ParameterizedType);

		ParameterizedType pType = (ParameterizedType) type;

		Type typeArgs[] = pType.getActualTypeArguments();

		assertEquals(2, typeArgs.length);

		assertTrue(typeArgs[0] instanceof Class<?>);

		Class<?> typeArgType = (Class<?>) typeArgs[0];

		assertEquals(typeArgType, String.class);

		assertTrue(typeArgs[1] instanceof Class<?>);

		typeArgType = (Class<?>) typeArgs[1];

		assertEquals(typeArgType, BigDecimal.class);
	}

	@Test
	public void mapGetEntity() {
		Map<String, BigDecimal> volume = new HashMap<>();

		volume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.TEN);
		volume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.TEN);

		Response r = Response.ok().entity(volume).build();

		assertSame(volume, r.getEntity());
	}

	@Test
	public void testGetEntityUnwrapped() {

		exchanges.add(exchange);

		GenericEntity<List<Exchange>> entities = new GenericEntity<List<Exchange>>(exchanges){
		};

		Response r = Response.ok().entity(entities).build();

		assertSame(exchanges, r.getEntity());
	}

	@Test
	public void testGetEntity() {

		exchanges.add(exchange);

		Response r = Response.ok().entity(exchanges).build();

		assertSame(exchanges, r.getEntity());
	}

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void should_catch_constraint_violations_with_not_valid_messages() {

		String notValidUserId = null;

		TradeMessage invalidTrade = new TradeMessage(notValidUserId, null, null, null,
				new BigDecimal("747.10", MyMath.MC), new BigDecimal("0.7471", MyMath.MC), "17-APR-15 10:27:44", "FR");

		CurrencyPair currencyPair = new CurrencyPair(invalidTrade.getCurrencyFrom(), invalidTrade.getCurrencyTo());

		Exchange exchange = new Exchange(currencyPair, invalidTrade.getAmountSell(), invalidTrade.getAmountBuy());

		Set<ConstraintViolation<CurrencyPair>> constraintViolations = validator.validate(currencyPair);

		assertEquals(2, constraintViolations.size());

		Set<ConstraintViolation<Exchange>> constraintViolationsX = validator.validate(exchange);

		assertEquals(1, constraintViolationsX.size());

		assertEquals("must not be null", constraintViolationsX.iterator().next().getMessage());
	}

}