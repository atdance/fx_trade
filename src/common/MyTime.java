package common;

import java.util.concurrent.TimeUnit;

/**
 * Utility used for benchmarks.
 *
 */
public class MyTime {
	private long prevTime;
	private long totalTime;
	private final static String SPACE = " ";

	/**
 *
 */
	public MyTime() {
		prevTime = System.nanoTime();
	}

	public long nanoTosecs(long pNanos) {
		return TimeUnit.SECONDS.toSeconds(pNanos);
	}

	@Override
	public String toString() {
		doTotalTime();
		return ("" + nanoTosecs(totalTime) + SPACE + (totalTime / 1000000)
				+ " sec " + (totalTime / 1000000000));
	}

	private synchronized void doTotalTime() {
		long now = System.nanoTime();
		totalTime = now - prevTime;
		prevTime = now;
	}
}
