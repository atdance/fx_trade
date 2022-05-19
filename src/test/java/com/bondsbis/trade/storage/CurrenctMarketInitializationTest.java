package com.bondsbis.trade.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.bondsbiz.trade.business.storage.CurrencyMarket;

public class CurrenctMarketInitializationTest {

	CurrencyMarket market;

	@Before
	public void setUp() throws Exception {
		market = new CurrencyMarket();
	}

	/**
	 * object under test is initiated with a not empty MarketVolume
	 */
	@Test
	public final void shouldReturnMarketVolumeIsTwo() {
		assertTrue(market.volume().size() == 2);
	}

	@Test
	public final void should_return_empty_list() {
		assertTrue(market.getAll().size() == 0);
	}

	@Test
	public final void should_not_add_when_input_is_null() {
		assertFalse(market.add(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void should_not_get_when_input_is_negative() throws Exception {

		int ID = -1;

		market.getById(ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void should_not_get_when_input_is_zero() throws Exception {

		int ID = 0;

		market.getById(ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void should_not_get_when_input_is_one() throws Exception {

		int ID = 1;

		market.getById(ID);
	}
}