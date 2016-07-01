package com.bondsbiz.trade.business.api;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

import com.bondsbiz.trade.MyTime;
import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.services.Manager;
import com.bondsbiz.trade.business.websocket.DataProvider;

/**
 * REST operations for a TradeAPI .
 *
 */
@Stateless
@Path("trade")
public class TradeAPI {

	@Inject
	Manager manager;

	ConcurrentLinkedQueue<Response> cacheGraph = new ConcurrentLinkedQueue<>();
	ConcurrentLinkedQueue<Response> cacheAll = new ConcurrentLinkedQueue<>();

	static Logger LOG = null;

	static {
		LOG = LoggerFactory.getLogger(TradeAPI.class);
	}

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_XML })
	public Response readAll(@Context HttpServletRequest request) {
		LOG.info("GET test");
		Response res = null;
		final MyTime timer = new MyTime();
		try {

			if (!cacheAll.isEmpty()) {
				res = cacheAll.peek();
			}
			if (res == null) {
				res = manager.selectAll();
				cacheAll.add(res);
			}

		} catch (final Exception ex) {
			LOG.warn(ex.getMessage(), ex);
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
	@Produces({ MediaType.APPLICATION_XML })
	public Response readLatest(@Context HttpServletRequest request) {
		LOG.info("GET volume");
		Response res = null;
		try {
			res = manager.volume();
			cacheGraph.add(res);
		} catch (final Exception ex) {
			LOG.warn(ex.getMessage(), ex);
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
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML })
	public Response writeTrade(@Context HttpServletRequest request, @Valid final TradeMessage pMsg) {
		Response res = null;

		//CurrencyPair currencyPair = new CurrencyPair(pMsg.getCurrencyFrom(), pMsg.getCurrencyTo());
		//Exchange exchange = new Exchange(currencyPair, pMsg.getAmountSell(), pMsg.getAmountBuy());
		//res = manager.insert(exchange);
		//cacheGraph.clear();
		//cacheAll.clear();
		
		// WsAnnotation.sendAll();
		
		res =  Response.status(Status.OK).type(MediaType.APPLICATION_XML).build();
		
		return res;
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
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response readTrade(@Context HttpServletRequest request, @QueryParam("tradeid") final Long pID) {
		LOG.info("/gettest");
		Response res = null;
		try {
			res = manager.selectById(pID);
		} catch (final Exception ex) {
			LOG.warn(ex.getMessage(), ex);
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return res;
	}

	/**
	 * Streams server-sent events.
	 *
	 * @return Long-running response in form of an event channel.
	 */
	@GET
	@Path("events")
	@Produces(EventChannel.SERVER_SENT_EVENTS)
	public EventChannel getEvents() {
		final EventChannel ec = new EventChannel();
		DataProvider.addEventChannel(ec);
		return ec;
	}
}