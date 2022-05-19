package com.bondsbiz.trade.business.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.jboss.weld.util.Services;

import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;

/**
 * Includes the most common operations that classes in this package perform. It
 * was needed to simplify the behavior of classes in the package . Is used in
 * Services.java .
 *
 *
 * @see Services
 *
 */
public interface OperationsInterface {

	public Response selectById(Integer id);

	public List<Exchange> listAll();

	/**
	 *
	 * @return List of the total of handled volume of two currencies.
	 */
	public Map<String, BigDecimal> volume();

	Response insert(@Valid TradeMessage pMsg);
}