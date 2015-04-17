package filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation It limits the rate of incoming requests at the
 * Application level .
 *
 * REQ_LIMT is the the number of request allowed in a time period
 * TIME_LIMIT_MILLIS new requests can be accepted after this limit
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class RateLimiter implements Filter {

	private final long TIME_LIMIT_MILLIS = 500L;
	long now = 0L, before = 0L, elapsedMillis = 0L;

	/**
	 * Default constructor.
	 */
	public RateLimiter() {
	}

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(RateLimiter.class);
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		now = System.nanoTime();

		elapsedMillis = TimeUnit.NANOSECONDS.toMillis(now - before);

		if (elapsedMillis < this.TIME_LIMIT_MILLIS) {
			LOG.info("NOK  " + elapsedMillis + " vs " + TIME_LIMIT_MILLIS);
			res.sendError(429);
			res.addIntHeader("Retry/After", (int) TIME_LIMIT_MILLIS);
			before = now;
			return;
		}
		before = now;

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}