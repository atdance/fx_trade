/**
 *
 */
package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import common.MyMath;

import front.restapi.JSONTradeMessage;

/**
 * test some operations towards the test API
 *
 */
public class TestREST {

	private final String URL_BASE = "http://localhost:8080/restapi/rest";

	public static Client client = null;

	JSONTradeMessage trade = new JSONTradeMessage("1415515", "EUR", "GBP",
			new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10, MyMath.MC),
			new BigDecimal(0.7471, MyMath.MC), "24-JAN-15 10:27:44", "FR");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientBuilder.newClient();
	}

	@Test
	public void canGET() {
		final String url = URL_BASE + "/trade/gettest?tradeid=1";
		client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.get();
		common.TradeMessage localtrade = response
				.readEntity(common.TradeMessage.class);
		assertNotNull(localtrade);
		assertNotNull(localtrade.amountBuy);
		assertTrue(localtrade.amountBuy.compareTo(BigDecimal.ZERO) > 0);

		response.close();
		client.close();

	}

	@Test
	public void canPOST() throws Exception {
		WebTarget target = client.target(URL_BASE).path("/trade/add");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(trade);
		int OPS = 1000;
		int successfulOps = 0;

		for (int i = 0; i < OPS; i++) {
			Invocation.Builder invocationBuilder = target
					.request(MediaType.APPLICATION_JSON);

			Response response = invocationBuilder.post(Entity.entity(json,
					MediaType.APPLICATION_JSON));

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				successfulOps++;
			} else {
				String taskResponse = response.readEntity(String.class);
				System.out.println(taskResponse);
			}
			response.close();
		}

		Assert.assertEquals(OPS, successfulOps);
	}

	@Test
	public void canGET_Currency_VOLUME() throws Exception {
		final String url = URL_BASE + "/trade/volume";
		client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		int OPS = 1000;
		int successfulOps = 0;

		for (int i = 0; i < OPS; i++) {
			Response response = target.request()
					.accept(MediaType.APPLICATION_JSON).get();

			Map<String, Object> obj = new HashMap<String, Object>();

			Map<String, Object> volume = response.readEntity(obj.getClass());

			System.out.println("::" + volume.get("eur").toString());

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				successfulOps++;
			} else {
				String taskResponse = response.readEntity(String.class);
				System.out.println(taskResponse);
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