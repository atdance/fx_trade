package test.lowlevel;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.websocket.EncodeException;
import javax.net.websocket.Session;
import javax.ws.rs.core.MultivaluedHashMap;

import org.glassfish.jersey.media.sse.EventChannel;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import services.Services;

import common.TradeMessage;
import common.Volume;

/**
 * First tests with websocket. Simple in-memory data storage for the
 * application.
 */
public class DataProvider<PAYLOAD extends TradeMessage> extends
		Services<PAYLOAD> {

	/** Broadcaster for server-sent events. */
	private static SseBroadcaster sseBroadcaster = new SseBroadcaster();

	/** Map that stores web socket sessions corresponding to a given X ID. */
	private static final MultivaluedHashMap<Integer, Session> webSockets = new MultivaluedHashMap<>();

	synchronized boolean addL(int xId) {
		Volume X = getVolume();
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
	synchronized void addWebSocket(int xId, Session session) {
		webSockets.add(xId, session);
		Volume X = getVolume();
		if (X != null) {
			try {
				session.getRemote().sendObject(X);
			} catch (IOException | EncodeException ex) {
				Logger.getLogger(DataProvider.class.getName()).log(
						Level.SEVERE, null, ex);
			}
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
	static synchronized void removeWebSocket(int xId, Session session) {
		List<Session> sessions = webSockets.get(xId);
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
	private static void wsBroadcast(int xId, Volume DR) {
		List<Session> sessions = webSockets.get(xId);
		if (sessions != null) {
			for (Session session : sessions) {
				try {
					session.getRemote().sendObject(DR);
				} catch (IOException | EncodeException ex) {
					Logger.getLogger(DataProvider.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
	}

	/**
	 * @return
	 */
	private Volume getVolume() {
		return vMapper.volume();
	}

}
