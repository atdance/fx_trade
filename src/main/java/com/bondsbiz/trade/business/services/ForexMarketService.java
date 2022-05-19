package com.bondsbiz.trade.business.services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.storage.CurrencyMarket;

public class ForexMarketService implements OperationsInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForexMarketService.class);

	@Inject
	CurrencyMarket storage;

	private static final Response RESPONSE_OK_XML = Response.status(Status.OK).type(MediaType.APPLICATION_XML).build();

	private static final Response RESPONSE_OK_JSON = Response.status(Status.OK).type(MediaType.APPLICATION_JSON).build();

	private static final Response RESPONSE_NOT_ACCEPTABLE = Response.status(Status.NOT_ACCEPTABLE).build();

	private static final Response RESPONSE_NOT_FOUND = Response.status(Status.NOT_FOUND).build();

	private static Logger LOG = LoggerFactory.getLogger(ForexMarketService.class);

	protected static final String NOK = "NOK", OK = "OK ", SUCCESS = " SUCCESS ", FAILED_STRING = " FAILED ";

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#selectById(long)
	 */
	@Override
	public Response selectById(Integer pID) {

		Response vRes = null;

		if (null == pID || pID < 0L) {
			LOGGER.error("pID null or negative ");
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
					.entity("missing required parameter 'tradeid'").build();
		}

		try {
			Exchange exch = storage.getById(pID);
			vRes = Response.status(Status.OK).entity(exch).build();

		} catch (final Exception e) {
			LOGGER.error("Manager selectById {} {} ", pID, e.getMessage());

			vRes = RESPONSE_NOT_FOUND;
		}

		return vRes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#insert(java.lang.Object)
	 */
	@Override
	public Response insert(TradeMessage pMsg) {
		if (ModelValidator.hasConstraintViolations(pMsg)) {

			return RESPONSE_NOT_ACCEPTABLE;
		}
		CurrencyPair currencyPair = new CurrencyPair(pMsg.getCurrencyFrom(), pMsg.getCurrencyTo());

		Exchange exchange = new Exchange(currencyPair, pMsg.getAmountSell(), pMsg.getAmountBuy());

		if (ModelValidator.hasConstraintViolations(currencyPair, exchange)) {

			return RESPONSE_NOT_ACCEPTABLE;
		}

		if (storage.add(exchange)) {

			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new BigDecimal(exchange.getID()))
					.build();

		} else {

			return RESPONSE_NOT_FOUND;
		}
	}

	@Override
	public List<Exchange> listAll() {
		List<Exchange> resp = null;
		try {
			resp = storage.getAll();
		} catch (final Exception e) {
			resp = Collections.emptyList();
			LOGGER.error("Manager listAll {} {} ", e.getMessage(), e);
		}
		return resp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#listLatest()
	 */
	@Override
	public Map<String, BigDecimal> volume() {
		Map<String, BigDecimal> resp = null;
		try {
			resp = storage.volume();
		} catch (final Exception e) {
			LOGGER.error("Manager volume {} {} ", e.getMessage(), e);

			resp = Collections.emptyMap();
		}
		return resp;
	}

}