/**
 *
 */
package test;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.MyMath;

import front.filter.RateLimiter;
import front.restapi.JSONTradeMessage;

/**
 * @author name
 *
 */
public class RestConcurrencyTest {
	static final AtomicInteger successfulOps = new AtomicInteger(0);
	static final int maxThreadCount = 100;

	static final int loopsCount = 10;

	private final static String URL_BASE = "http://localhost:8080/restapi/rest";
	private static String URL_PUT = URL_BASE + "/trade/add";

	static JSONTradeMessage trade = new JSONTradeMessage("333", "EUR", "GBP",
			new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10, MyMath.MC),
			new BigDecimal(0.7471, MyMath.MC), "21-APR-15 10:27:44", "FR");

	private Client client = null;
	private WebTarget target = null;
	private ExecutorService pool = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		try {
			RateLimiter.disable();
			client = ClientBuilder.newClient();
			target = client.target(URL_PUT);
			pool = Executors.newCachedThreadPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		pool = Executors.newCachedThreadPool();

		RateLimiter.disable();

		try {
			time(pool, 100, new POSTTask(target));
			pool.awaitTermination(3, TimeUnit.SECONDS);
			pool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		try {
			System.out.println(" successfulOps " + successfulOps.intValue()
					+ " vs " + (maxThreadCount * loopsCount));

			Assert.assertEquals(successfulOps.intValue(),
					(maxThreadCount * loopsCount));

			successfulOps.set(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RateLimiter.enable();
			client.close();
		}
	}

	public static long time(Executor executor, int concurrency,
			final Runnable action) throws InterruptedException {
		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrency);
		for (int i = 0; i < concurrency; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					ready.countDown(); // Tell timer we're ready
					try {
						start.await(); // Wait till peers are ready
						action.run();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						done.countDown(); // Tell timer we're done
					}
				}
			});
		}
		ready.await(); // Wait for all workers to be ready
		long startNanos = System.nanoTime();
		start.countDown(); // And they're off!
		done.await(); // Wait for all workers to finish
		return System.nanoTime() - startNanos;
	}
}

class POSTTask implements Runnable {
	private WebTarget target = null;
	int THRESHOLD = 500;
	int LOOP = 10;

	public POSTTask(WebTarget pTarget) {
		target = pTarget;
	}

	@Override
	public void run() {
		try {
			actuallyrun();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void actuallyrun() throws JsonProcessingException {
		for (int i = 0; i < LOOP; i++) {

			if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
				Thread.yield();
			}

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(RestConcurrencyTest.trade);

			Invocation.Builder invocationBuilder = target
					.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json,
					MediaType.APPLICATION_JSON));

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				RestConcurrencyTest.successfulOps.addAndGet(1);
			} else {
				String taskResponse = response.readEntity(String.class);
				System.out.println(taskResponse);
			}
		}
	}
}
