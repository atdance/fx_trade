package front.websocket;

import javax.net.websocket.DecodeException;
import javax.net.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Volume;

/**
 * Encoder which encodes the object data into messages which can be transported
 * over the websocket connection.
 */
public class MyEncoder implements Encoder.Text<Volume>, Decoder.Text<Volume> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyEncoder.class);

	/**
	 * A com.fasterxml.jackson.databind.ObjectMapper that serializes any Java
	 * value as a String.
	 */
	protected static ObjectMapper mapper = null;

	static {
		mapper = new ObjectMapper();
	}

	/*
	 * Not implemented .
	 * 
	 * @see javax.net.websocket.Decoder.Text#decode(java.lang.String)
	 */
	@Override
	public Volume decode(String s) throws DecodeException {
		// temporary workaround for a web socket implementation issue
		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());

		throw new DecodeException("", "Decode Not implemented");
	}

	@Override
	public String encode(Volume resp) throws EncodeException {
		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());

		String s = "";

		try {

			s = mapper.writeValueAsString(resp);
		} catch (JsonProcessingException ex) {
			LOGGER.error(ex.getMessage(), ex);
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