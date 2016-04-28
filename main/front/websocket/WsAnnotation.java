package front.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.LoggerFactory;

import storage.CurrencyMarket;

import common.MyTime;
import common.Volume;

import front.restapi.TradeAPI;

//import org.glassfish.tyrus.core.TyrusSession;

/**
 */
@ServerEndpoint(value = "/websocket/WsAnnotation", encoders = { front.websocket.MyEncoder.class })
public class WsAnnotation {

	Volume currencyVolume = null;

	CurrencyMarket mkt = storage.CurrencyMarket.getInstance();

	private static Set<Session> sessions = new HashSet<>();

	static org.slf4j.Logger LOG = null;

	static {
		LOG = LoggerFactory.getLogger(TradeAPI.class);
	}

	@OnOpen
	public void onOpen(Session session) {
		MyTime timer = new MyTime();
		sessions.add(session);

		currencyVolume = mkt.volume();
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendObject(currencyVolume);
			}
		} catch (IOException ex) {
			LOG.error(ex.getMessage(), ex);
			try {
				session.close();
			} catch (IOException ex1) {
				LOG.error(ex1.getMessage(), ex1);
			}
		} catch (EncodeException ex) {
			LOG.error(ex.getMessage(), ex);
		}
		LOG.info("elapsed " + timer.toString());
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}

	@OnMessage
	public void echoTextMessage(Session session, String msg, boolean last) {
		currencyVolume = mkt.volume();
		MyTime timer = new MyTime();
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendObject(currencyVolume);
			}
		} catch (IOException ex) {
			LOG.error(ex.getMessage(), ex);
			try {
				session.close();
			} catch (IOException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		} catch (EncodeException e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.info("elapsed " + timer.toString());
	}

	public static void sendAll() {
		MyTime timer = new MyTime();
		synchronized (sessions) {
			Volume volume = storage.CurrencyMarket.getInstance().volume();
			for (Session session : sessions) {
				if (session.isOpen()) {
					session.getAsyncRemote().sendObject(volume);
				}
			}
		}
		LOG.info("elapsed " + timer.toString());
	}

	// @OnMessage
	@SuppressWarnings("unused")
	public static void sendAll2() {
		Volume currencyVolume = storage.CurrencyMarket.getInstance().volume();

		MyTime timer = new MyTime();
		int max = 1;
		int counter = 0;
		for (Session session : sessions) {
			if ((counter < max) && session.isOpen()) {
				// ((TyrusSession)
				// session).broadcast(currencyVolume.toString());
				counter++;
			}
		}
		LOG.info("elapsed " + timer.toString());
	}
}