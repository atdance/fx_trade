package com.bondsbiz.trade.business.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.model.MarketVolume;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.storage.CurrencyMarket;
import com.bondsbiz.trade.MyMath;

public class TestAPI {

	private Client client = null;

	private final Logger LOGGER = LoggerFactory.getLogger(TestAPI.class);

	private final static String URL_BASE = "http://localhost:8080/trade/api";

	private final TradeMessage trade = new TradeMessage("1415515", "EUR", "GBP", new BigDecimal("1000", MyMath.MC),
			new BigDecimal("747.10", MyMath.MC), new BigDecimal("0.7471", MyMath.MC), "24-JAN-15 10:27:44", "FR");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientBuilder.newClient();
	}

	@After
	public void tearDown() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	@Test
	public void canGET_TEST() throws Exception {
		final String url = URL_BASE + "/trade/gettest?tradeid=1";

		LOGGER.info(url);

		final WebTarget target = client.target(url);
		final Response response = target.request().accept(MediaType.APPLICATION_XML).get();

		LOGGER.info("media type " + response.getMediaType().toString());
		final TradeMessage msg = response.readEntity(TradeMessage.class);

		LOGGER.info(msg.toString());
		LOGGER.info("");
		LOGGER.info(CurrencyMarket.defaultMsg.toString());

		assertTrue(msg.equals(CurrencyMarket.defaultMsg));
	}

	@Test
	public void postInvalidTrade() throws Exception {
		final String url = URL_BASE + "/trade/add";

		LOGGER.info(url);

		final WebTarget target = client.target(url);

		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_XML);

		String shortUserId = null;

		TradeMessage trade = new TradeMessage(shortUserId, null, null, null, new BigDecimal("747.10", MyMath.MC),
				new BigDecimal("0.7471", MyMath.MC), "17-APR-15 10:27:44", "FR");

		final Response response = invocationBuilder.post(Entity.entity(trade, MediaType.APPLICATION_XML));

		assertFalse(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@Test
	public void postValidTrade() throws Exception {
		final String url = URL_BASE + "/trade/add";

		LOGGER.info(url);

		final WebTarget target = client.target(url);

		final Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_XML);

		final Response response = invocationBuilder.post(Entity.entity(trade, MediaType.APPLICATION_XML));

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@Test
	public void getVolumeOfExchanges() throws Exception {
		final String url = URL_BASE + "/trade/volume";

		LOGGER.info(url);

		final WebTarget target = client.target(url);
		final Response response =

				target.request().accept(MediaType.APPLICATION_XML).get();

		LOGGER.info("_" + response.getMediaType().toString());

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@Test
	public void canGET_Currency_VOLUME() throws Exception {

		this.postValidTrade();

		final String url = URL_BASE + "/trade/volume";

		LOGGER.info(url);

		final int OPS = 30;
		int successfulOps = 0;

		for (int i = 0; i < OPS; i++) {
			final WebTarget target = client.target(url);
			final Response response =

					target.request().accept(MediaType.APPLICATION_XML).get();

			final MarketVolume volume = response.readEntity(MarketVolume.class);

			Assert.assertNotNull(volume);

			/*
			 * do not use assertEquals(200, response.getStatus() because we save
			 * the results instead.
			 */
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				successfulOps++;
			} else {
				@SuppressWarnings("unused")
				final String taskResponse = response.readEntity(String.class);
			}
			response.close();
		}
		Assert.assertEquals(OPS, successfulOps);
	}

}