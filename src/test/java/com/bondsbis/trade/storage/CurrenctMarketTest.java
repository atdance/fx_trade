package com.bondsbis.trade.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.bondsbis.trade.DataLoad;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.storage.CurrencyMarket;

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
@RunWith(JUnit4.class)
public class CurrenctMarketTest {

	private static final int SIZE = 9_999;

	private static CurrencyMarket market;

	private static final int EXPECTED_SIZE = SIZE;

	private static final int IDs[] = new int[SIZE];

	static {
		market = new CurrencyMarket();

		int i = 0;

		for (i = 0; i < EXPECTED_SIZE; i++) {
			Exchange exch = DataLoad.makeRandomExchange();

			IDs[i] = exch.getID();

			market.add(exch);
		}
	}

	/**
	 * object under test is initiated with a not empty MarketVolume
	 */
	@Test
	public final void shouldReturnMarketVolumeIsTwo() {
		assertTrue(market.volume().size() == 2);
	}

	@Test
	public final void should_return_size_one() {
		assertTrue(market.getAll().size() == EXPECTED_SIZE);
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
	public final void should_not_get_when_input_ID_is_below_firstID() throws Exception {

		int ID = IDs[0] - 1;

		market.getById(ID);
	}

	@Test
	public final void should_get_result_whit_first_ID() throws Exception {

		int ID = IDs[0];

		Exchange got = market.getById(ID);

		assertTrue(got.getID() == ID);
	}

	@Test
	public final void should_get_result_when_input_ID_is_maximum_boundary_value() throws Exception {

		int ID = IDs[EXPECTED_SIZE - 1];

		Exchange got = market.getById(ID);

		assertTrue(got.getID() == ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void should_not_get_result_when_input_ID_is_larger_then_maximum_boundary_value() throws Exception {

		int ID = IDs[EXPECTED_SIZE - 1] + 1;

		Exchange got = market.getById(ID);

		assertTrue(got.getID() == ID);
	}
}