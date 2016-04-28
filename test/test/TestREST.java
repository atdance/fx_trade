/**
 *
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import common.MyMath;
import common.TradeMessage;
import common.Volume;

import front.restapi.JSONTradeMessage;

/**
 * test some operations towards the test API
 *
 */
public class TestREST {

	private ObjectMapper mapper = null;
	private String tradeAsJson = null;
	private Client client = null;

	private final Logger LOGGER = LoggerFactory.getLogger(TestREST.class);

	JSONTradeMessage trade = new JSONTradeMessage("1415515", "EUR", "GBP",
			new BigDecimal("1000", MyMath.MC), new BigDecimal("747.10",
					MyMath.MC), new BigDecimal("0.7471", MyMath.MC),
			"24-JAN-15 10:27:44", "FR");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientBuilder.newClient();
		mapper = new ObjectMapper();
		tradeAsJson = mapper.writeValueAsString(trade);
	}

	// @Test
	public void canPOST() throws Exception {
		WebTarget target = client.target(Common.URL_BASE).path("/trade/add");

		int OPS = 1;
		int successfulOps = 0;

		for (int i = 0; i < OPS; i++) {
			Response response = doPost(target);

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				successfulOps++;
			} else {
				@SuppressWarnings("unused")
				String taskResponse = response.readEntity(String.class);
			}
			response.close();
		}

		Assert.assertEquals(OPS, successfulOps);
	}

	@Test
	public void canGET() {
		final String url = Common.URL_BASE + "/trade/gettest?tradeid=1";

		WebTarget target = client.target(url);
		Response response =

		target.request().accept(MediaType.TEXT_PLAIN).get();

		String msg = response.readEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		TradeMessage localtrade = null;

		Exception exc = null;

		try {
			localtrade = mapper.readValue(msg, TradeMessage.class);
		} catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
			exc = e;
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
			exc = e;
		}
		if (exc != null) {
			LOGGER.info(exc.getCause() + "" + exc.getMessage());
		}
		assertTrue(exc == null);
		assertEquals(200, response.getStatus());
		if (null != localtrade) {
			assertTrue(localtrade != null);
			assertNotNull(localtrade);
			assertNotNull(localtrade.getAmountBuy());
			assertTrue(localtrade.getAmountBuy().compareTo(BigDecimal.ZERO) > 0);
		}
		response.close();
		client.close();

	}

	private Response doPost(WebTarget target) {
		Invocation.Builder invocationBuilder = target
				.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.post(Entity.entity(tradeAsJson,
				MediaType.APPLICATION_JSON));
		return response;
	}

	/**
	 * precondition is that some data is in storage, so we make a POSt first
	 *
	 * @throws Exception
	 */
	// @Test
	public void canGET_Currency_VOLUME() throws Exception {

		WebTarget post = client.target(Common.URL_BASE).path("/trade/add");
		doPost(post);

		final String url = Common.URL_BASE + "/trade/volume";
		client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		final int OPS = 30;
		int successfulOps = 0;

		for (int i = 0; i < OPS; i++) {

			Response response = target.request().get();

			String msg = response.readEntity(String.class);

			ObjectMapper mapper = new ObjectMapper();
			Volume volume = mapper.readValue(msg, Volume.class);

			assertNotNull(volume);
			assertTrue(volume.get("eur").compareTo(BigDecimal.ZERO) > 0);

			/*
			 * do not use assertEquals(200, response.getStatus() because we save
			 * the results instead.
			 */
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				successfulOps++;
			} else {
				@SuppressWarnings("unused")
				String taskResponse = response.readEntity(String.class);
			}
			response.close();
		}
		Assert.assertEquals(OPS, successfulOps);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}
}