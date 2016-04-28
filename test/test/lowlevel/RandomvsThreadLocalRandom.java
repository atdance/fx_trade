/**
 *
 */
package test.lowlevel;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.MyTime;

/**
 * Micro-tests before implementing features in main code
 *
 */
public class RandomvsThreadLocalRandom {
	Logger LOGGER = LoggerFactory.getLogger(RandomvsThreadLocalRandom.class);

	@Test
	public void RandomVsThreadLocalRandom() {
		Random r = new Random();

		MyTime timer = new MyTime();
		int i = 2000000;
		for (int j = 0; j < i; j++) {
			r.nextInt(100);
		}

		LOGGER.info(timer.toString());

		MyTime timer2 = new MyTime();
		ThreadLocalRandom local = ThreadLocalRandom.current();

		for (int j = 0; j < i; j++) {
			local.nextInt(100);
		}

		LOGGER.info(timer2.toString());
	}

}