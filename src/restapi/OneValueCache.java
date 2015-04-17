/**
 *
 */
package restapi;

import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.Response;

//    private volatile OneValueCache cache =       new OneValueCache(null, null);
//    public void service(ServletRequest req, ServletResponse resp) {
//        BigInteger i = extractFromRequest(req);
//        BigInteger[] factors = cache.getFactors(i);
//        if (factors == null) {
//            factors = factor(i);
//            cache = new OneValueCache(i, factors);
//        }
//       encodeIntoResponse(resp, factors);

@Immutable
public class OneValueCache {
	private final boolean astale;
	private final Response aRes;

	public OneValueCache(Response res, boolean stale) {
		aRes = res;
		astale = stale;
	}

	public Response getCached() {
		if (astale) {
			return null;
		} else {
			return aRes;
		}
	}

	/**
	 * @return the astale
	 */
	public boolean isStale() {
		return astale;
	}

	/**
	 * @return the aRes
	 */
	public Response getaRes() {
		return aRes;
	}
}
