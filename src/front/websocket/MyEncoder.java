package front.websocket;

import java.util.HashMap;
import java.util.Map;

import javax.net.websocket.DecodeException;
import javax.net.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encoder which encodes the object data into messages which can be transported
 * over the websocket connection.
 */
public class MyEncoder implements Encoder.Text<Map<String, Object>>,
		Decoder.Text<Map<String, Object>> {

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

	@Override
	public Map<String, Object> decode(String s) throws DecodeException {
		// temporary workaround for a web socket implementation issue
		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());

		System.out.println("decode..........");
		Map<String, Object> shape = new HashMap<String, Object>();
		return shape;
	}

	@Override
	public String encode(Map<String, Object> resp) throws EncodeException {
		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());

		String s = "";

		try {

			s = mapper.writeValueAsString(resp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return s;
	}

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	/*
	 * We can always return true, as this decoder can work with any messages
	 * used by this application
	 * 
	 * @see javax.net.websocket.Decoder.Text#willDecode(java.lang.String)
	 */
	@Override
	public boolean willDecode(String arg0) {

		return true;
	}
}