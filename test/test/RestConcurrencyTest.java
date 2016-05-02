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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RestConcurrencyTest.class);

	static final AtomicInteger successfulOps = new AtomicInteger(0);
	static final int MAX_THREADS_SIZE = 50;
	static final int MAX_TASKS_SIZE = 40;
	static final String URL_PUT = Common.URL_BASE + "/trade/add";
	static final String URL_GET_VOLUME = Common.URL_BASE + "/trade/volume";

	static JSONTradeMessage trade = new JSONTradeMessage("333", "EUR", "GBP",
			new BigDecimal("1000", MyMath.MC), new BigDecimal("747.10",
					MyMath.MC), new BigDecimal("0.7471", MyMath.MC),
			"21-APR-15 10:27:44", "FR");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		try {
			RateLimiter.disable();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
	}

	@Test
	public void test() {
		ExecutorService pool = Executors.newCachedThreadPool();

		try {
			timePost(pool, MAX_THREADS_SIZE);
			pool.awaitTermination(3, TimeUnit.SECONDS);
			pool.shutdown();
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
	}

	@After
	public void tearDown() {
		try {
			Assert.assertEquals(successfulOps.intValue(), (MAX_THREADS_SIZE));

			successfulOps.set(0);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
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

		List<MyTask> tasks = new ArrayList<>();

		for (int i = 0; i < concurrency; i++) {
			if (i < MAX_TASKS_SIZE) {
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
	protected Client client = null;

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(MyTask.class);
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
			LOGGER.info(e.getMessage(), e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			aDone.countDown(); // Tell timer we're done
		}

	}

	public abstract void actuallyrun() throws JsonProcessingException;

	/**
	 * @param client
	 */
	protected void close(Client client) {
		if (null != client) {
			try {
				client.close();
			} catch (Exception e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
	}

	/**
	 * do not use assertEquals(200, response.getStatus() because we save the
	 * results instead.
	 *
	 * @param response
	 */
	protected void handleResponse(Response response) {
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			RestConcurrencyTest.successfulOps.addAndGet(1);
		} else {
			response.readEntity(String.class);
		}
	}
}

class GetTask extends MyTask {

	public GetTask(final CountDownLatch ready, final CountDownLatch start,
			CountDownLatch done) {
		super(ready, start, done);
	}

	@Override
	public void actuallyrun() throws JsonProcessingException {
		if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
			Thread.yield();
		}
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

			handleResponse(response);
		} catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
		} finally {
			close(client);
		}
	}
}

class PostTask extends MyTask {
	static final int THRESHOLD = 500;

	public PostTask(final CountDownLatch ready, final CountDownLatch start,
			CountDownLatch done) {
		super(ready, start, done);
	}

	@Override
	public void actuallyrun() throws JsonProcessingException {
		if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
			Thread.yield();
		}

		try {
			client = ClientBuilder.newClient();

			WebTarget target = client.target(RestConcurrencyTest.URL_PUT);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(RestConcurrencyTest.trade);

			Invocation.Builder invocationBuilder = target
					.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.post(Entity.entity(json,
					MediaType.APPLICATION_JSON));

			handleResponse(response);
		} finally {
			close(client);
		}
	}
}