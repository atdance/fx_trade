/**
 *
 */
package test.lowlevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * for tests
 */
public class SynteticDataProducer {
	private static List<Map<String, Integer>> JSONObject = null;

	@SuppressWarnings("unused")
	private static List<Map<String, Object>> getJsonData() {
		JSONObject = array();
		JSONObject.add(leaf(0, rangeRand(0, 8)));
		JSONObject.add(leaf(1, rangeRand(0, 8)));
		JSONObject.add(leaf(2, rangeRand(0, 8)));
		JSONObject.add(leaf(3, rangeRand(0, 8)));

		Map<String, Object> ramo = new LinkedHashMap<String, Object>();
		ramo.put("name", "New York");
		ramo.put("data", JSONObject);
		int temp = rangeRand(8, 16);

		JSONObject = array();
		JSONObject.add(leaf(0, temp));
		JSONObject.add(leaf(1, temp));
		JSONObject.add(leaf(2, temp));
		JSONObject.add(leaf(3, temp));

		// Map<String, List<Map<String, Integer>>> ramo1 = new HashMap<String,
		// List<Map<String, Integer>>>();
		Map<String, Object> ramo1 = new LinkedHashMap<String, Object>();
		ramo1.put("name", "London");
		ramo1.put("data", JSONObject);

		JSONObject = array();
		JSONObject.add(leaf(0, rangeRand(16, 32)));
		JSONObject.add(leaf(1, rangeRand(16, 32)));
		JSONObject.add(leaf(2, rangeRand(16, 32)));
		JSONObject.add(leaf(3, rangeRand(16, 32)));

		Map<String, Object> ramo2 = new LinkedHashMap<String, Object>();
		ramo2.put("name", "Tokyo");
		ramo2.put("data", JSONObject);

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		lst.add(ramo);
		lst.add(ramo1);
		lst.add(ramo2);
		return lst;
	}

	private static int rangeRand(int min, int max) {
		return ThreadLocalRandom.current().nextInt((max - min)) + min;
	}

	private static List<Map<String, Integer>> array() {
		return new ArrayList<Map<String, Integer>>();
	}

	private static Map<String, Integer> leaf(Integer x, Integer y) {
		Map<String, Integer> leaf = new HashMap<String, Integer>();
		leaf.put("x", x);
		leaf.put("y", y);
		return leaf;
	}
}