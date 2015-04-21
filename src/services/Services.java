/**
 *
 */
package services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.TradeMessage;
import front.restapi.ApiGenericResponse;

/**
 * Not public. External clients may use the subclasses through
 * TradeServices.getInstance() .
 *
 *
 * @see Services
 *
 *
 */
public class Services<PAYLOAD extends TradeMessage> {
	/**
	 * used for responses to clients of rest API.
	 */
	protected static final String NOK = "NOK", OK = "OK ",
			SUCCESS = " SUCCESS ", FAILED_STRING = " FAILED ";

	/**
	 * A com.fasterxml.jackson.databind.ObjectMapper that serializes any Java
	 * value as a String.
	 */
	protected static ObjectMapper mapper = null;

	protected OperationsInterface vMapper = new TradeMapper();

	protected Services() {
	}

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(Services.class);
		mapper = new ObjectMapper();
	}

	/**
	 * Implements the REST GET requests. It send forward the request to storage
	 * layer.
	 *
	 * @param pID
	 *            parameter for some operations
	 * @return Response response with an OK or NOK status.
	 *
	 */
	public Response serviceGet(final Long pID) {
		Response vRes = null;
		LOG.info("");
		if (null == pID) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type(MediaType.TEXT_PLAIN)
					.entity("missing required parameter 'tradeid'").build();
		}

		try {
			vRes = Response.status(Status.OK)
					.entity(mapper.writeValueAsString(vMapper.selectById(pID)))
					.build();

		} catch (final Exception ex) {
			LOG.warn("", ex.getMessage());
			vRes = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
		}
		return vRes;
	}

	/**
	 * Executes operations towards storage.
	 *
	 * @param trade
	 *            incoming message to be handled
	 * @param pOpType
	 *            of operation to perform
	 * @param pID
	 *            parameter for some operations
	 * @return Response response with an OK or NOK status.
	 */
	public Response service(TradeMessage trade, final Operation pOpType,
			final Long pID) {

		Response res = null;

		try {
			switch (pOpType) {
			case ADD:
				vMapper.insert(trade);
				vMapper.insertLatest(trade);
				break;
			default:
				throw new IllegalArgumentException(
						"Type of operation not available");
			}

			ApiGenericResponse vApiResp = new ApiGenericResponse(); //

			vApiResp.setStatus(OK);
			vApiResp.setMessage(pOpType.name() + SUCCESS);

			final String resString = mapper.writeValueAsString(vApiResp);
			res = Response.status(Status.OK).entity(resString).build();
		} catch (final Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
			LOG.warn("", ex.getMessage());
		}
		return res;
	}
}