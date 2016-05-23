/**
 *
 */
package front.restapi;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * uses Jackson with no JAXB as for these examples
 *
 * https://github.com/jersey/jersey/tree/2.17/examples/json-jackson and this
 * specification
 * https://jersey.java.net/nonav/documentation/latest/media.html#json.jackson
 *
 *
 */
public class AppConfig extends ResourceConfig {

	/**
	 * register Jackson ObjectMapper resolver
	 */
	public AppConfig() {
		super(front.restapi.TradeAPI.class, common.TradeMessage.class,
				common.Volume.class, MyObjectMapperProvider.class,
				JacksonFeature.class);

		packages("front.restapi;common");
	}
}