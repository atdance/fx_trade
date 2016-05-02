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

import common.Volume;

@ServerEndpoint(value = "/websocket/WsAnnotation", encoders = { front.websocket.MyEncoder.class })
public class WsAnnotation {

	Volume currencyVolume = null;

	CurrencyMarket mkt = storage.CurrencyMarket.getInstance();

	private static Set<Session> sessions = new HashSet<>();

	static org.slf4j.Logger aLOG = null;

	static {
		aLOG = LoggerFactory.getLogger(WsAnnotation.class);
	}

	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);

		currencyVolume = mkt.volume();
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendObject(currencyVolume);
			}
		} catch (IOException ex) {
			aLOG.error(ex.getMessage(), ex);
			try {
				session.close();
			} catch (IOException ex1) {
				aLOG.error(ex1.getMessage(), ex1);
			}
		} catch (EncodeException ex) {
			aLOG.error(ex.getMessage(), ex);
		}
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}

	@OnMessage
	public void echoTextMessage(Session session) {
		currencyVolume = mkt.volume();
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendObject(currencyVolume);
			}
		} catch (IOException ex) {
			aLOG.error(ex.getMessage(), ex);
			try {
				session.close();
			} catch (IOException e1) {
				aLOG.error(e1.getMessage(), e1);
			}
		} catch (EncodeException e) {
			aLOG.error(e.getMessage(), e);
		}
	}

	public static void sendAll() {
		synchronized (sessions) {
			Volume volume = storage.CurrencyMarket.getInstance().volume();
			for (Session session : sessions) {
				if (session.isOpen()) {
					session.getAsyncRemote().sendObject(volume);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public static void sendAll2() {
		@SuppressWarnings("unused")
		Volume currencyVolume = storage.CurrencyMarket.getInstance().volume();

		int max = 1;
		int counter = 0;
		for (Session session : sessions) {
			if ((counter < max) && session.isOpen()) {
				// ((TyrusSession)
				// session).broadcast(currencyVolume.toString());
				counter++;
			}
		}
	}
}