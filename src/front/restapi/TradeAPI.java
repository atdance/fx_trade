package front.restapi;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.sse.EventChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import services.Operation;
import services.TradeServices;

import common.MyTime;
import common.TradeMessage;

/**
 * REST operations for a TradeAPI .
 *
 */

@Path("trade")
public class TradeAPI extends APICommon {

	ConcurrentLinkedQueue<Response> cacheGraph = new ConcurrentLinkedQueue<Response>();
	ConcurrentLinkedQueue<Response> cacheAll = new ConcurrentLinkedQueue<Response>();

	static Logger LOG = null;

	static {
		LOG = LoggerFactory.getLogger(TradeAPI.class);
	}

	/**
	 * Returns always the same TradeMessage object in the Response. Used for
	 * testing that the service is working.
	 *
	 * @param TradeAPI
	 *            record
	 * @param auth
	 * @return
	 */
	@GET
	@Path("/gettest")
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	public Response readTrade(@Context HttpServletRequest request,
			@QueryParam("tradeid") final Long pID) {
		LOG.info("GET");
		Response res = null;
		try {
			res = TradeServices.getInstance(TradeMessage.class).serviceGet(pID);
		} catch (Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return res;
	}

	@GET
	@Path("/list")
	@Produces({ "application/javascript", MediaType.APPLICATION_JSON })
	public Response readAll(@Context HttpServletRequest request) {
		LOG.info("GET test");
		Response res = null;
		MyTime timer = new MyTime();
		try {

			if (!cacheAll.isEmpty()) {
				res = cacheAll.peek();
			}
			if (res == null) {
				res = TradeServices.getInstance(TradeMessage.class).listAll();
				cacheAll.add(res);
			}

		} catch (Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		LOG.info("elapsed " + timer.toString());
		return res;
	}

	/**
	 *
	 * @return a Response with the volume of the two exchanged currencies.
	 */
	@GET
	@Path("/volume")
	@Produces({ "application/javascript", MediaType.APPLICATION_JSON })
	public Response readLatest(@Context HttpServletRequest request) {
		LOG.info("GET volume");
		Response res = null;
		try {
			res = TradeServices.getInstance(TradeMessage.class).volume();
			cacheGraph.add(res);
		} catch (Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return res;
	}

	/**
	 * Create and saves in RAm storage a TradeMessage record
	 *
	 *
	 * @param JSONTradeMessage
	 *            which is a json on wire representation of a TradeMessage
	 * @return Ok or NOk response
	 */
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	public Response writeTrade(@Context HttpServletRequest request,
			final JSONTradeMessage pJSONmsg) {
		LOG.info("PUT ");
		Response res = null;
		try {
			TradeMessage msg = new TradeMessage(pJSONmsg);
			res = TradeServices.getInstance(TradeMessage.class).service(msg,
					Operation.ADD, null);

			cacheGraph.clear();
			cacheAll.clear();
			front.websocket.WsAnnotation.sendAll();

		} catch (IllegalArgumentException ex) {
			String msg = ex.getMessage();
			LOG.warn(msg);

			res = Response.status(Response.Status.BAD_REQUEST)
					.type(MediaType.TEXT_PLAIN).entity(msg).build();

		} catch (Exception ex) {
			LOG.warn(ex.getMessage());
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return res;
	}

	/**
	 * Streams server-sent events. Not used
	 *
	 * @return Long-running response in form of an event channel.
	 */
	@GET
	@Path("events")
	@Produces(EventChannel.SERVER_SENT_EVENTS)
	public EventChannel getEvents() {
		EventChannel ec = new EventChannel();
		test.lowlevel.DataProvider.addEventChannel(ec);
		return ec;
	}
}