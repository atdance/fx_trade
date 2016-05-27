package com.bondsbiz.trade.business.services;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.model.MarketVolume;
import com.bondsbiz.trade.business.storage.CurrencyMarket;

public class Manager implements OperationsInterface {

	@Inject
	CurrencyMarket storage;

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(Manager.class);
	}
	protected static final String NOK = "NOK", OK = "OK ", SUCCESS = " SUCCESS ", FAILED_STRING = " FAILED ";

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#insert(java.lang.Object)
	 */
	@Override
	public Response insert(TradeMessage record) {
		Response res = null;

		try {
			final Exchange exc = record.getExchange();
			storage.add(exc);
			// return 0L;
			final String message = "ADD " + SUCCESS;
			res = Response.status(Status.OK).entity(message).build();
		} catch (final Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			LOG.warn(ex.getMessage(), ex);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#selectById(long)
	 */
	@Override
	public Response selectById(Long pID) {

		Response vRes = null;

		if (null == pID) {
			LOG.error("pID null");
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
					.entity("missing required parameter 'tradeid'").build();
		}

		try {
			vRes = Response.status(Status.OK).entity(storage.createTrade()).build();

		} catch (final Exception ex) {
			LOG.error(ex.getMessage(), ex);
			vRes = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return vRes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#selectAll()
	 */
	@Override
	public Response selectAll() {
		List<Exchange> resp = null;
		try {
			resp = storage.getAll();
		} catch (final Exception ex) {
			resp = Collections.emptyList();
			LOG.error(ex.getMessage(), ex);
		}
		return Response.status(Status.OK).entity(resp).build();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#listLatest()
	 */
	@Override
	public Response volume() {
		MarketVolume resp = null;
		try {
			resp = storage.volume();
		} catch (final Exception ex) {
			LOG.error(ex.getMessage(), ex);
		}
		return Response.status(Status.OK).entity(resp).build();
	}

}
