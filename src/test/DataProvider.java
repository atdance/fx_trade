package test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.websocket.EncodeException;
import javax.net.websocket.Session;
import javax.ws.rs.core.MultivaluedHashMap;

import org.glassfish.jersey.media.sse.EventChannel;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import services.Services;

import common.TradeMessage;

/**
 * Simple in-memory data storage for the application.
 */
public class DataProvider<PAYLOAD extends TradeMessage> extends
		Services<PAYLOAD> {

	/** Broadcaster for server-sent events. */
	private static SseBroadcaster sseBroadcaster = new SseBroadcaster();

	/** Map that stores web socket sessions corresponding to a given drawing ID. */
	private static final MultivaluedHashMap<Integer, Session> webSockets = new MultivaluedHashMap<>();

	synchronized boolean addShape(int drawingId) {
		Map<String, Object> drawing = getVolume();
		if (drawing != null) {
			// if (drawing.shapes == null) {
			// drawing.shapes = new ArrayList<>();
			// }
			// drawing.shapes.add(shape);
			wsBroadcast(drawingId, drawing);
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
	 * Registers a new web socket session and associates it with a drawing ID.
	 * This method should be called when a client opens a web socket connection
	 * to a particular drawing URI.
	 *
	 * @param drawingId
	 *            Drawing ID to associate the web socket session with.
	 * @param session
	 *            New web socket session to be registered.
	 */
	synchronized void addWebSocket(int drawingId, Session session) {
		webSockets.add(drawingId, session);
		Map<String, Object> drawing = getVolume();
		if (drawing != null) {
			try {
				session.getRemote().sendObject(drawing);
			} catch (IOException | EncodeException ex) {
				Logger.getLogger(DataProvider.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Removes the existing web socket session associated with a drawing ID.
	 * This method should be called when a client closes the web socket
	 * connection to a particular drawing URI.
	 *
	 * @param drawingId
	 *            ID of the drawing the web socket session is associated with.
	 * @param session
	 *            Web socket session to be removed.
	 */
	static synchronized void removeWebSocket(int drawingId, Session session) {
		List<Session> sessions = webSockets.get(drawingId);
		if (sessions != null) {
			sessions.remove(session);
		}
	}

	/**
	 * Broadcasts the newly added shape to all web sockets associated with the
	 * affected drawing.
	 *
	 * @param drawingId
	 *            ID of the affected drawing.
	 * @param shape
	 *            Shape that was added to the drawing or
	 *            {@link ShapeCoding#SHAPE_CLEAR_ALL} if the drawing was cleared
	 *            (i.e. all shapes were deleted).
	 */
	private static void wsBroadcast(int drawingId, Map<String, Object> DR) {
		List<Session> sessions = webSockets.get(drawingId);
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
	private Map<String, Object> getVolume() {
		return vMapper.volume();
	}

}
