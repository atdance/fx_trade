package com.bondsbiz.trade.business.services;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import com.bondsbiz.trade.business.model.Exchange;
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

	public Response selectById(Long id);

	public Response selectAll();

	/**
	 *
	 * @return List of the total of handled volume of two currencies.
	 */
	public Response volume();

	public Response insert(@Valid Exchange record);
}