package test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.JSONP;

import services.TradeServices;
import common.TradeMessage;
import front.restapi.APICommon;

/**
 * REST operations for a TradeAPI .
 *
 */
@Path("tradetest")
public class RestSErvlet extends APICommon {

	@GET
	@Path("/getnonjax")
	@JSONP
	@Produces({ "application/javascript", MediaType.APPLICATION_JSON })
	public Response readBeanJSONP(@Context HttpServletRequest request,
			@QueryParam("tradeid") final Long pID) {
		// LOG.info("GET nonJAX");
		Response res = null;
		try {
			res = TradeServices.getInstance(TradeMessage.class).serviceGet(pID);
		} catch (Exception ex) {
			res = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return res;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		System.out.println("xxx GET text_plain");
		return "TradeAPI Text, reply: Got it!";
	}

	@GET
	@Path("/refresh2")
	@Produces(MediaType.TEXT_PLAIN)
	public String refresh() {
		System.out.println("xxx GET refresh json");

		String resp = "[{'name':'New York','data':[{'x':0,'y':11},{'x':1,'y':11},{'x':2,'y':9},{'x':3,'y':7}]},{'name':'London','data':[{'x':0,'y':6},{'x':1,'y':2},{'x':2,'y':4},{'x':3,'y':7}]},{'name':'Tokyo','data':[{'x':0,'y':31},{'x':1,'y':26},{'x':2,'y':34},{'x':3,'y':31}]}]";

		try {
			return resp;
		} catch (Exception ex) {
			System.out.println("TradeApi exc...");
			ex.printStackTrace();
			System.out.println(ex);
		}
		return resp;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String gotIt() {
		System.out.println("xxx GET text_html");
		return "<h1>TradeAPI Html reply: Got it!</h1>";
	}
}