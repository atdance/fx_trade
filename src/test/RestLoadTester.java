/**
 *
 */
package test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.MyMath;
import front.restapi.JSONTradeMessage;

public class RestLoadTester {

	private static String URL = "http://localhost:8080/restapi/rest/trade/add";
	// "http://localhost:8080/restapi/rest/trade/get?tradeid=1";
	public static final AtomicInteger counter = new AtomicInteger(0);
	public static final int maxThreadCount = 100;

	private final String URL_PUT = "http://localhost:8080/restapi/rest";

	JSONTradeMessage trade = new JSONTradeMessage("134256", "EUR", "GBP",
			new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10, MyMath.MC),
			new BigDecimal(0.7471, MyMath.MC), "24-JAN-15 10:27:44", "FR");

	public static void main(String[] args) throws InterruptedException {
		new RestLoadTester();

	}

	public RestLoadTester() throws InterruptedException {
		ExecutorService exec1 = Executors.newCachedThreadPool();

		for (int i = 0; i < maxThreadCount; i++) {
			exec1.submit(new MyTask(URL));
		}
		exec1.shutdown();
		Thread.sleep(5000);
		System.out.println(" " + counter.intValue());
		// call complex servlet
		counter.set(0);

		/*
		 * ExecutorService exec2 = Executors.newCachedThreadPool(); for (int i =
		 * 0; i < maxThreadCount; i++) { exec2.submit(new
		 * UrlReaderTask("http://localhost:8080/test/complex")); }
		 * exec2.awaitTermination(1, TimeUnit.DAYS);
		 */
	}

	public class MyTask implements Runnable {
		private final String endpoint;

		public MyTask(String s) {
			endpoint = s;
		}

		@Override
		public void run() {
			try {
				actuallyrun();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		public void actuallyrun() throws Exception {
			Client client = null;
			try {
				client = ClientBuilder.newClient();

				for (int i = 0; i < 10; i++) {
					counter.addAndGet(1);
					WebTarget target = client.target(URL_PUT)
							.path("/trade/add");
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(trade);

					Response rs = target.request().put(
							Entity.entity(json, MediaType.APPLICATION_JSON));

					String taskResponse = rs.readEntity(String.class);
					// System.out.println(taskResponse);

				}
			} finally {
				if (null != client) {
					client.close();
				}
			}
		}
	}
}