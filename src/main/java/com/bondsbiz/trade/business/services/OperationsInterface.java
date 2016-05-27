package com.bondsbiz.trade.business.services;

import javax.ws.rs.core.Response;

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

	public abstract Response insert(TradeMessage t);

	public abstract Response selectById(Long id);

	public abstract Response selectAll();

	/**
	 *
	 * @return List of the total of handled volume of two currencies.
	 */
	public abstract Response volume();
}