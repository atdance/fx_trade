package com.bondsbiz.trade;

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
		return Long.toString(nanoTosecs(totalTime)) + SPACE + Long.toString(totalTime / 1000000) + " sec " + Long.toString(totalTime / 1000000000);
	}

	private synchronized void doTotalTime() {
		final long now = System.nanoTime();
		totalTime = now - prevTime;
		prevTime = now;
	}
}