package test.lowlevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * for tests
 */
public class Pair {
	@SuppressWarnings("unused")
	private static List<Map<String, Integer>> JSONObject = null;

	public static Map<String, Integer> getJsonData() {
		Map<String, Integer> leaf = new HashMap<String, Integer>();
		leaf.put("eur", 2000);
		leaf.put("gbp", 1000);
		return leaf;
	}

	@SuppressWarnings("unused")
	private static int rangeRand(int min, int max) {
		return ThreadLocalRandom.current().nextInt((max - min)) + min;
	}

	@SuppressWarnings("unused")
	private static List<Map<String, Integer>> array() {
		return new ArrayList<Map<String, Integer>>();
	}

	@SuppressWarnings("unused")
	private static Map<String, Integer> leaf(Integer x, Integer y) {
		Map<String, Integer> leaf = new HashMap<String, Integer>();
		leaf.put("eur", x);
		leaf.put("gbp", y);
		return leaf;
	}
}