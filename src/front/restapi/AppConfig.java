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

	public AppConfig() {
		super(front.restapi.TradeAPI.class, common.CurrencyPair.class,
				common.Exchange.class, common.Countries.class,
				storage.CurrencyMarket.class, common.Currencies.class,
				common.TradeMessage.class,
				// register Jackson ObjectMapper resolver
				MyObjectMapperProvider.class, JacksonFeature.class);

		packages("restapi;common");
	}
}