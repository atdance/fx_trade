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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storage.CurrencyMarket;

import common.MyTime;
import common.Volume;

import front.restapi.TradeAPI;

/**
 */
@ServerEndpoint(value = "/websocket/WsAnnotation", encoders = { front.websocket.MyEncoder.class })
public class WsAnnotation {

	Volume currencyVolume = null;

	CurrencyMarket mkt = storage.CurrencyMarket.getInstance();

	private static Set<Session> sessions = new HashSet<>();

	static Logger LOG = null;

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
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EncodeException e) {
			e.printStackTrace();
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
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EncodeException e) {
			e.printStackTrace();
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
}
