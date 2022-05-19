package com.bondsbiz.trade.business.api;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.encoders.MapWrapper;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.services.ForexMarketService;

/**
 * REST operations for a ForexExchange API .
 *
 */
@Path("trade")
public class ForexExchangeResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForexExchangeResource.class);

	private static final GenericEntity<List<Exchange>> emptyEntities = new GenericEntity<List<Exchange>>(
			Collections.emptyList()){
	};

	@Inject
	ForexMarketService forexMarketService;

	@GET
	@Consumes({ "text/html", "application/xhtml+xml", "application/xml" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {

		return Response.status(Status.OK).type(MediaType.TEXT_PLAIN).entity("OK").build();
	}

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON }) // xx added JSON
	public Response readAllZ() {

		GenericEntity<List<Exchange>> entities = null;

		try {
			entities = new GenericEntity<List<Exchange>>(forexMarketService.listAll()){
			};

		} catch (final Exception e) {
			LOGGER.warn("{} ", e.getMessage());

			entities = emptyEntities;
		}

		return Response.status(Status.OK).entity(entities).build();
	}

	/**
	 *
	 * @return a Response with the volume of the two exchanged currencies.
	 */
	@GET
	@Path("/volume")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response readLatestJSONTT() {

		return Response.status(Status.OK).entity(new MapWrapper(forexMarketService.volume())).build();
	}

	/**
	 * Create and saves in RAm storage a TradeMessage record
	 *
	 * @param JSONTradeMessage which is a json on wire representation of a
	 *                         TradeMessage
	 * @return @see Manager#insert(TradeMessage)
	 */
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON }) // xxx removed MediaType.TEXT_PLAIN,
	@Consumes({ MediaType.APPLICATION_JSON })
	// public Response postTrade(@Valid final TradeMessage pMsg) { removed @Valid
	public Response postTrade(final TradeMessage pMsg) {
		// WsAnnotation.sendAll();

		return forexMarketService.insert(pMsg);
	}

	/**
	 * Returns always the same TradeMessage object in the Response. Used for testing
	 * that the service is working.
	 *
	 * @param ForexExchangeResourceTest record
	 * @param auth
	 * @return
	 */
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON }) // xxx removed MediaType.APPLICATION_JSON,
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response selectById(@QueryParam("tradeid") final Integer pID) { // xxx
		return forexMarketService.selectById(pID);

	}
}