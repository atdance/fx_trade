package com.bondsbiz.trade.business.websocket;

import java.util.List;

import javax.inject.Inject;
import javax.websocket.Session;
import javax.ws.rs.core.MultivaluedHashMap;

import org.glassfish.jersey.media.sse.EventChannel;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.storage.CurrencyMarket;
import com.bondsbiz.trade.business.model.MarketVolume;

/**
 * First tests with websocket. Simple in-memory data storage for the
 * application.
 */
public class DataProvider<PAYLOAD extends TradeMessage> {

	@Inject
	static CurrencyMarket mkt;

	static Logger logger = LoggerFactory.getLogger(DataProvider.class);

	/** Broadcaster for server-sent events. */
	private static SseBroadcaster sseBroadcaster = new SseBroadcaster();

	/** Map that stores web socket sessions corresponding to a given X ID. */
	@SuppressWarnings("rawtypes")
	private static final MultivaluedHashMap<Integer, Session> webSockets = new MultivaluedHashMap<>();

	synchronized boolean addL(int xId) {
		final MarketVolume X = getMarketVolume();
		if (X != null) {
			// if (X.list == null) {
			// X.list = new ArrayList<>();
			// }
			// X.list.add(obj);
			wsBroadcast(xId, X);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Registers a new channel for sending events. An event channel corresponds
	 * to a client (browser) event source connection.
	 *
	 * @param ec
	 *            Event channel to be registered for sending events.
	 */
	public static void addEventChannel(EventChannel ec) {
		sseBroadcaster.add(ec);
	}

	/**
	 * Registers a new web socket session and associates it with a X ID. This
	 * method should be called when a client opens a web socket connection to a
	 * particular X URI.
	 *
	 * @param xId
	 *            X ID to associate the web socket session with.
	 * @param session
	 *            New web socket session to be registered.
	 */
	@SuppressWarnings("unchecked")
	synchronized void addWebSocket(int xId, Session session) {// Session<?> MICH
																// session) {
		webSockets.add(xId, session);
		final MarketVolume X = getMarketVolume();
		if (X != null) {
			// try {
			session.getAsyncRemote().sendObject(X);
			// MICH session.getRemote().sendObject(X);
			// } catch (IOException | EncodeException ex) {
			// logger.error(ex.getMessage(), ex);
			// }
		}
	}

	/**
	 * Removes the existing web socket session associated with a X ID. This
	 * method should be called when a client closes the web socket connection to
	 * a particular X URI.
	 *
	 * @param xId
	 *            ID of the X the web socket session is associated with.
	 * @param session
	 *            Web socket session to be removed.
	 */
	static synchronized void removeWebSocket(int xId, Session session) {// MICH
																		// Session<?>
																		// session)
																		// {
		@SuppressWarnings("rawtypes")
		final List<Session> sessions = webSockets.get(xId);
		if (sessions != null) {
			sessions.remove(session);
		}
	}

	/**
	 * Broadcasts the newly added obj to all web sockets associated with the
	 * affected X.
	 *
	 * @param xId
	 *            ID of the affected X.
	 * @param obj
	 *            obj that was added to the X or {@link objCoding#obj_CLEAR_ALL}
	 *            if the X was cleared (i.e. all list were deleted).
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void wsBroadcast(int xId, MarketVolume DR) {
		final List<Session> sessions = webSockets.get(xId);
		if (sessions != null) {
			for (final Session session : sessions) {
				// try {
				session.getAsyncRemote().sendObject(DR);
				// MICH session.getRemote().sendObject(DR);
				// } catch (IOException | EncodeException ex) {
				// logger.error(ex.getStackTrace().toString(), ex);
				// }
			}
		}
	}

	/**
	 * @return
	 */
	private MarketVolume getMarketVolume() {
		return mkt.volume();
	}

}
