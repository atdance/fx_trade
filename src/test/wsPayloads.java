package test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storage.CurrencyMarket;
import websocket.MyEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Currencies;

/**
 * Experiments with Encoder which encodes the object data into messages which
 * can be transported over the websocket connection.
 */
public class wsPayloads {

	/**
	 * A com.fasterxml.jackson.databind.ObjectMapper that serializes any Java
	 * value as a String.
	 */
	protected static ObjectMapper mapper = null;

	private static Logger LOG = null;

	static {
		LOG = LoggerFactory.getLogger(MyEncoder.class);
		mapper = new ObjectMapper();
	}

	boolean on = true;
	int i = 0;

	@SuppressWarnings("unchecked")
	public String encode(Object resp) throws EncodeException {

		String s = "";

		if (false) {
			s = "{'eur':2000,'gbp':1000}";
			s = "eur:" + (i * i * 1000);

			s = "[" + "{" + "name: 'eur'," + "data: [ { x: 1000, y: 5000} ]"
					+ "}, {" + "name: 'gbp'," + "data: [ { x: 800, y: 1000} ]"
					+ "}" + "]";
		}

		CurrencyMarket mkt = CurrencyMarket.getInstance();

		// s = CurrencyMarket.getInstance().volume().toString();

		// org.json.simple.JSONObject obj = new JSONObject();
		// obj.put("eur", new Integer(2000));
		// obj.put("gbp", new Integer(1000));
		// s = obj.toJSONString();

		// Json.createReader(new StringReader(string)).readObject();
		// Message msg = new Message(jsonObject);
		// s msg.getJson().toString();

		// s = resp.toString();

		// Message message = new Message();

		// JsonObject jsonObject = Json.createObjectBuilder()
		// .add("eur", resp.getEur()).add("gbp", resp.getGbp()).build();

		// return message.getBuffer();
		// return ByteBuffer.wrap(SerializationUtils.serialize(message));

		// return Json.createObjectBuilder().add("eur", 2000).add("gbp", 1000)
		// .build().toString();

		// temporary workaround for a web socket implementation issue

		if (false) {
			// Message message = new Message();
			s = Json.createObjectBuilder().add("eur", 2000).add("gbp", 1000)
					.build().toString();
			// response = message.toString();
			// StringWriter writer = new StringWriter();
			// Json.createGenerator(writer).writeStartObject().write("eur",
			// 2000)
			// .write("gbp", 1000).writeEnd().flush();
			// s = writer.toString();
		}

		if (false) {
			LinkedHashMap<String, Object> ramo = new LinkedHashMap<String, Object>();
			ramo.put(Currencies.EUR.name().toLowerCase(), 2000);
			ramo.put(Currencies.GBP.name().toLowerCase(), 1000);

			List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
			lst.add(ramo);

			s = "{'eur':2000,'gbp':1000}";
		}

		if (false) {
			StringWriter result = new StringWriter();
			try (JsonGenerator gen = Json.createGenerator(result)) {
				gen.writeStartObject().write("eur", 2000).write("gbp", 1000)
						.writeEnd();
				// gen.writeStartObject().write("3000").writeEnd();
			}
			s = result.toString();
		}

		if (false) {
			JsonObject jsonObject = Json.createObjectBuilder().add("eur", 2000)
					.add("gbp", 1000).build();
			s = jsonObject.toString();
		}
		// try {
		// s = mapper.writeValueAsString(resp);
		// } catch (JsonProcessingException e) {
		// e.printStackTrace();
		// }
		return s;
	}
}