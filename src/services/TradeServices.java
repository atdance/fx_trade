package services;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.TradeMessage;

/**
 *
 * This implements CRUD operations towards the storage.
 *
 * @param <ID>
 *            used for retrieval on an ID like a Long or long
 * @param <PAYLOAD>
 *            used for returning objects like a TradeMessage
 *
 */
public class TradeServices<PAYLOAD extends TradeMessage> extends
		Services<PAYLOAD> {

	private static volatile TradeServices<?> localInstance = null;

	static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(TradeServices.class);
	}

	private TradeServices() {
	}

	/**
	 * Factory method to get instance
	 *
	 * @return
	 */
	public static synchronized TradeServices<? extends TradeMessage> getInstance(
			Class<? extends TradeMessage> t) {
		TradeServices<? extends TradeMessage> tempInstance = localInstance;

		if (tempInstance == null) {
			synchronized (TradeServices.class) {
				tempInstance = localInstance;
				if (tempInstance == null) {
					tempInstance = new TradeServices();
					localInstance = tempInstance;
				}
			}
		}
		return tempInstance;
	}

	/**
	 * List all connectionnodes
	 *
	 * @return
	 * @throws JsonProcessingException
	 */
	public Response listAll() throws JsonProcessingException {

		List<common.Exchange> resp = vMapper.selectAll();

		return Response.status(Status.OK)
				.entity(mapper.writeValueAsString(resp)).build();
	}

	/**
	 * List the total of handled volume of two currencies .
	 *
	 * @return
	 * @throws JsonProcessingException
	 */
	public Response volume() throws JsonProcessingException {

		// List<common.Exchange>
		Map<String, Object> resp = vMapper.volume();

		return Response.status(Status.OK)
				.entity(mapper.writeValueAsString(resp)).build();

	}

}