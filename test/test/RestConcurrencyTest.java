/**
 *
 */
package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import common.Volume;

import front.filter.RateLimiter;
import front.restapi.JSONTradeMessage;

/**
 * @author name
 *
 */
public class RestConcurrencyTest {
	static final AtomicInteger successfulOps = new AtomicInteger(0);
	static final int maxThreadCount = 50;

	final static String URL_PUT = Common.URL_BASE + "/trade/add";
	final static String URL_GET_VOLUME = Common.URL_BASE + "/trade/volume";

	static JSONTradeMessage trade = new JSONTradeMessage("333", "EUR", "GBP",
			new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10, MyMath.MC),
			new BigDecimal(0.7471, MyMath.MC), "21-APR-15 10:27:44", "FR");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		try {
			RateLimiter.disable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		ExecutorService pool = Executors.newCachedThreadPool();

		try {
			timePost(pool, maxThreadCount);
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
			Assert.assertEquals(successfulOps.intValue(), (maxThreadCount));

			successfulOps.set(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RateLimiter.enable();
			// client.close();
		}
	}

	public static long timePost(Executor executor, int concurrency)
			throws InterruptedException {
		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrency);

		List<MyTask> tasks = new ArrayList<MyTask>();

		final int MAX = 40;

		for (int i = 0; i < concurrency; i++) {
			if (i < MAX) {
				tasks.add(new PostTask(ready, start, done));
			} else {
				tasks.add(new GetTask(ready, start, done));
			}
		}
		for (MyTask myTask : tasks) {
			executor.execute(myTask);
		}

		ready.await(); // Wait for all workers to be ready
		long startNanos = System.nanoTime();
		start.countDown(); // And they're off!
		done.await(); // Wait for all workers to finish
		return System.nanoTime() - startNanos;
	}
}

abstract class MyTask implements Runnable {
	int THRESHOLD = 500;
	private final CountDownLatch aReady;
	private final CountDownLatch aStart;
	private final CountDownLatch aDone;

	public MyTask(final CountDownLatch ready, final CountDownLatch start,
			CountDownLatch done) {
		aReady = ready;
		aStart = start;
		aDone = done;
	}

	@Override
	public void run() {
		aReady.countDown();
		try {
			aStart.await();

			actuallyrun();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			aDone.countDown(); // Tell timer we're done
		}

	}

	public abstract void actuallyrun() throws JsonProcessingException,
			IOException;
}

class GetTask extends MyTask {

	public GetTask(/* WebTarget pTarget, */final CountDownLatch ready,
			final CountDownLatch start, CountDownLatch done) {
		super(ready, start, done);
	}

	@Override
	public void actuallyrun() throws JsonProcessingException, IOException {
		if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
			Thread.yield();
		}
		Client client = null;
		try {
			client = ClientBuilder.newClient();

			WebTarget target = client
					.target(RestConcurrencyTest.URL_GET_VOLUME);

			Response response = target.request().get();
			String msg = response.readEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			Volume volume = mapper.readValue(msg, Volume.class);
			assertNotNull(volume);
			assertTrue(volume.get("eur").compareTo(BigDecimal.ZERO) > 0);

			// String json =
			// mapper.writeValueAsString(RestConcurrencyTest.trade);

			// Invocation.Builder invocationBuilder = target
			// .request(MediaType.APPLICATION_JSON);
			// Response response = invocationBuilder.post(Entity.entity(json,
			// MediaType.APPLICATION_JSON));

			/*
			 * do not use assertEquals(200, response.getStatus() because we save
			 * the results instead.
			 */
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				RestConcurrencyTest.successfulOps.addAndGet(1);
			} else {
				String taskResponse = response.readEntity(String.class);
			}
		} finally {
			// response.close();
			client.close();
		}
	}
}

class PostTask extends MyTask {
	int THRESHOLD = 500;

	public PostTask(/* WebTarget pTarget, */final CountDownLatch ready,
			final CountDownLatch start, CountDownLatch done) {
		super(ready, start, done);
	}

	@Override
	public void actuallyrun() throws JsonProcessingException {
		if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
			Thread.yield();
		}
		Client client = null;
		try {
			client = ClientBuilder.newClient();

			WebTarget target = client.target(RestConcurrencyTest.URL_PUT);

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
			}
		} finally {
			client.close();
		}

	}
}