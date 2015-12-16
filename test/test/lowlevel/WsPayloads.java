package test.lowlevel;

import javax.websocket.EncodeException;

import storage.CurrencyMarket;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Experiments with Encoder which encodes the object data into messages which
 * can be transported over the websocket connection.
 */
public class WsPayloads {

	/**
	 * A com.fasterxml.jackson.databind.ObjectMapper that serializes any Java
	 * value as a String.
	 */
	protected static ObjectMapper mapper = null;

	static {
		mapper = new ObjectMapper();
	}

	boolean on = true;
	int i = 0;

	@SuppressWarnings({ "unused" })
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

		return s;
	}
}