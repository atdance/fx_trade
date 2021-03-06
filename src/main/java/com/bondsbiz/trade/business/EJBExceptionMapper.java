package com.bondsbiz.trade.business;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException ex) {
		final Throwable cause = ex.getCause();
		if (cause instanceof OptimisticLockException) {
			final OptimisticLockException actual = (OptimisticLockException) cause;
			return Response.status(Response.Status.CONFLICT)
					.header("cause", "conflict cause by entity: " + actual.getEntity())
					.header("additional-info", actual.getMessage()).build();

		}

		return Response.serverError().header("cause", ex.toString()).build();
	}

}
