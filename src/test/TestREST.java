/**
 *
 */
package test;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.MyMath;
import front.restapi.JSONTradeMessage;

/**
 * test some operations towards the test API
 *
 */
public class TestREST {

	private final String URL_PUT = "http://localhost:8080/restapi/rest";

	public static Client client = null;

	JSONTradeMessage trade = new JSONTradeMessage("1415515", "EUR", "GBP",
			new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10, MyMath.MC),
			new BigDecimal(0.7471, MyMath.MC), "24-JAN-15 10:27:44", "FR");

	// @Test
	public void canGET() {
		final String url = URL_PUT + "/trade/get?tradeid=1";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request().accept(MediaType.APPLICATION_JSON)
				.get();
		common.TradeMessage html = response
				.readEntity(common.TradeMessage.class);
		System.out.println(html);
		response.close();

	}

	@BeforeClass
	public static void name() {
		client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void After() {
		client.close();
	}

	@Test
	public void canPUT() throws Exception {
		WebTarget target = client.target(URL_PUT).path("/trade/add");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(trade);

		for (int i = 0; i < 1000; i++) {
			Response rs = target.request().put(
					Entity.entity(json, MediaType.APPLICATION_JSON));

			String taskResponse = rs.readEntity(String.class);
			System.out.println(taskResponse);
			assertNotNull(taskResponse);
			// assertNotNull(taskResponse.getId());
			// assertEquals(taskResponse.getAssigned(), task.getAssigned());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}