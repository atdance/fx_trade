/**
 *
 */
package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author name
 *
 */
public class TomcatLoad {
	public static final int maxThreadCount = 600;
	ExecutorService pool = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = Executors.newCachedThreadPool();
	}

	Exception commonException = null;

	@Test
	public void test() {
		Exception exception = null;

		for (int i = 0; i < maxThreadCount; i++) {
			pool.submit(new UrlReaderTask(Common.URL));
		}
		try {
			pool.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			exception = e;
			e.printStackTrace();
		}
		Assert.assertNull("Exception happened ", exception);
		Assert.assertNull("Exception happened ", commonException);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	class UrlReaderTask implements Runnable {
		private final String endpoint;
		private final int THRESHOLD = 500;

		public UrlReaderTask(String s) {
			endpoint = s;
		}

		@Override
		public void run() {
			try {
				actuallyrun();
			} catch (Exception e) {
				commonException = e;
			}
		}

		public void actuallyrun() throws Exception {
			if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
				Thread.yield();
			}
			try (BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(endpoint).openStream()));) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					// NO OP;
				}
			}
		}
	}

}
