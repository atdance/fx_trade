package com.bondsbis.trade;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InvalidClassException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbis.trade.model.UnconstrainedTradeMessage;
import com.bondsbiz.trade.business.encoders.MapWrapper;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.storage.MyMath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForexExchangeResourceTestIT extends ClientDriver {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForexExchangeResourceTestIT.class);

	@Test
	public final void shouldReturnSizeWhenOfExhangesDatabase() throws InvalidClassException {
		final String url = URL_BASE + "trade/list";

		final Response response = super.getResponse(url, MediaType.APPLICATION_JSON);

		List<Exchange> got = response.readEntity(new GenericType<List<Exchange>>(){
		});

		if (got.size() > 0) {

			assertTrue(got.get(0).getClass() == Exchange.class);
		}
	}

	@Test
	public void getVolumeOfExchanges() throws Exception {
		final String url = URL_BASE + "trade/volume";

		final Response response = super.getResponse(url, MediaType.APPLICATION_JSON);

		MapWrapper got = response.readEntity(MapWrapper.class);

		assertTrue(got != null);

		assertTrue(got.getMap().size() >= 0);

		// assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@SuppressWarnings("unused")
	private void getVolumeOfExchangesWithoutWrapperTODO() throws Exception {
		final String url = URL_BASE + "trade/volume";

		final Response response = super.getResponse(url, MediaType.APPLICATION_XML);

		Map<String, BigDecimal> got = response.readEntity(new GenericType<Map<String, BigDecimal>>(){
		});

		assertTrue(got.size() >= 0);

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	/**
	 * Ensure we can access resource.
	 */
	@Test
	public final void testPing() throws InvalidClassException {
		final String url = URL_BASE + "trade/";

		final Response response = super.getResponse(url, MediaType.TEXT_PLAIN);

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
	}

	/**
	 * tests a not existing Url. A kind of mutation test
	 *
	 * @throws InvalidClassException
	 */
	@Test
	public final void should_receive_StatusNotFound_when_resource_not_existing() throws InvalidClassException {
		final String badUrl = URL_BASE + "trade/volume/ping";

		final Response response = super.getResponse(badUrl, MediaType.TEXT_PLAIN);

		assertTrue(response.getStatus() == Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void should_pass_when_post_valid_trademessage() throws Exception {
		final String url = URL_BASE + "trade/add";

		TradeMessage validTrade = DataLoad.makeRandomTrade();

		boolean checkMediaType = false;

		final Response response = super.post(url, validTrade, MediaType.APPLICATION_JSON, checkMediaType);

		assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

		BigDecimal got = response.readEntity(BigDecimal.class);

		assertTrue(got.intValue() >= 0);
	}

	@Test
	public void should_receive_StatusNotAcceptable_when_post_invalid_trade() throws Exception {
		final String url = URL_BASE + "trade/add";

		String notValidUserId = null;

		UnconstrainedTradeMessage invalidTrade = new UnconstrainedTradeMessage(notValidUserId, null, null, null,
				new BigDecimal("747.10", MyMath.MC), new BigDecimal("0.7471", MyMath.MC), "17-APR-15 10:27:44", "FR");

		boolean checkMediaType = false;

		final Response response = super.post(url, invalidTrade, MediaType.APPLICATION_JSON, checkMediaType);

		assertTrue(response.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode());
	}

	@SuppressWarnings("unused")
	private void postData(String path, String param) throws IOException {
		StringBuffer response = null;

		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(1000);// 1 second
		connection.setReadTimeout(1000);

		connection.setRequestProperty("Accept", "application/json");

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {

			// Gson gson = new Gson();

			// BufferedReader br2 = new BufferedReader(new
			// InputStreamReader(connection.getInputStream()));

			// @SuppressWarnings("unchecked")
			// List<Exchange> data = gson.fromJson(br2, List.class);
//			br2.close();

			Jsonb jsonb = JsonbBuilder.create();

			@SuppressWarnings("unchecked")
			List<Exchange> data = jsonb.fromJson(connection.getInputStream(), List.class);

			LOGGER.debug("{}", data);

			connection.disconnect();
		} else {
			LOGGER.debug("{}", responseCode);
		}
	}
}