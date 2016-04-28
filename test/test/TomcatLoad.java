/**
 *
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author name
 *
 */
public class TomcatLoad {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TomcatLoad.class);
	public static final int maxThreadCount = 600;
	ExecutorService pool = null;

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
			LOGGER.warn(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		Assert.assertNull("Exception happened ", exception);
		Assert.assertNull("Exception happened ", commonException);
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
				LOGGER.warn(e.getMessage(), e);
				commonException = e;
			}
		}

		public void actuallyrun() throws IOException {
			if (ThreadLocalRandom.current().nextInt(1000) > THRESHOLD) {
				Thread.yield();
			}
			try (BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(endpoint).openStream()));) {
				@SuppressWarnings("unused")
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					// NO OP;
				}
			}
		}
	}

}