package com.bondsbis.trade;

import static org.junit.Assert.assertTrue;

import java.io.InvalidClassException;
import java.math.BigDecimal;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;

/**
 * Matches an inserted and retrived item by their id.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class WorkFlowTestIT extends ClientDriver {

	@Test
	public void workflow_1() throws InvalidClassException {// should_pass_when_post_valid_trademessage()
		final String url = URL_BASE + "trade/add";

		TradeMessage validTrade = DataLoad.makeRandomTrade();

		boolean checkMediaType = false;

		final Response response = super.post(url, validTrade, MediaType.APPLICATION_JSON, checkMediaType);

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

		BigDecimal got = response.readEntity(BigDecimal.class);

		int expectedExchangeID = got.intValue();

		assertTrue(got.intValue() >= 0);

		final String url_get = URL_BASE + "trade/get?tradeid=" + expectedExchangeID;

		final Response responseGet = super.getResponse(url_get, MediaType.APPLICATION_JSON);

		Exchange gotExchange = responseGet.readEntity(Exchange.class);

		assertTrue(gotExchange != null);

		assertTrue(gotExchange.getID() == expectedExchangeID);
	}

}