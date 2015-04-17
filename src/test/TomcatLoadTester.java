package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TomcatLoadTester {

	private static String URL = "http://localhost:8080/restapi/rest/trade/get?tradeid=1";
	// "http://localhost:8080/restapi/rest/trade/get?tradeid=1";
	public static final AtomicInteger counter = new AtomicInteger(0);
	public static final int maxThreadCount = 600;

	public static void main(String[] args) throws InterruptedException {
		new TomcatLoadTester();

	}

	public TomcatLoadTester() throws InterruptedException {
		ExecutorService exec1 = Executors.newCachedThreadPool();

		for (int i = 0; i < maxThreadCount; i++) {
			exec1.submit(new UrlReaderTask(URL));
		}
		exec1.shutdown();
		Thread.currentThread().sleep(5000);
		System.out.println("....NEXT....");
		// call complex servlet
		counter.set(0);

		/*
		 * ExecutorService exec2 = Executors.newCachedThreadPool(); for (int i =
		 * 0; i < maxThreadCount; i++) { exec2.submit(new
		 * UrlReaderTask("http://localhost:8080/test/complex")); }
		 * exec2.awaitTermination(1, TimeUnit.DAYS);
		 */
	}

	public class UrlReaderTask implements Runnable {
		private final String endpoint;

		public UrlReaderTask(String s) {
			endpoint = s;
		}

		@Override
		public void run() {
			try {
				actuallyrun();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

		public void actuallyrun() throws Exception {
			int count = counter.addAndGet(1);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(endpoint).openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(MessageFormat.format(
				// "thread[{0}] : {1} : {2}", count, inputLine, endpoint));
			}
			in.close();
		}
	}
}