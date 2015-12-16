/**
 *
 */
package storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Currencies;
import common.Exchange;
import common.MyMath;
import common.TradeMessage;
import common.Volume;

/**
 * Contains information about the market constituted by two currencies. Thread
 * consistency implemented with a ReentrantReadWriteLock .
 *
 */
public class CurrencyMarket {
	private static CurrencyMarket instance = new CurrencyMarket();

	public static CurrencyMarket getInstance() {
		return instance;
	}

	// }

	// final class Market {
	// public static final Market instance = new Market();

	/**
	 * used for caching the latest incoming data
	 */
	private Volume cachedVolume = new Volume();

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	private final OnlyAddableList<Exchange> exchanges = new OnlyAddableList<Exchange>();

	/**
	 * Store the total of handled volume of one currency .
	 *
	 */
	private BigDecimal currency1VolumeTotal = new BigDecimal(0, MyMath.MC);
	/**
	 * Store the total of handled volume of one currency .
	 *
	 */

	private BigDecimal currency2VolumeTotal = new BigDecimal(0, MyMath.MC);

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(CurrencyMarket.class);
	}

	private CurrencyMarket() {
		cachedVolume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.ZERO);
		cachedVolume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.ZERO);
	}

	// private volatile static CurrencyMarket localInstance = null;

	// private CurrencyMarket() {
	// cachedVolume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.ZERO);
	// cachedVolume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.ZERO);
	// }

	// public static synchronized CurrencyMarket getInstance() {
	// CurrencyMarket tempInstance = localInstance;

	// if (tempInstance == null) {
	// synchronized (CurrencyMarket.class) {
	// tempInstance = localInstance;
	// if (tempInstance == null) {
	// tempInstance = new CurrencyMarket();
	// localInstance = tempInstance;
	// }
	// }
	// }
	// return tempInstance;
	// }

	public void add(Exchange obj) {
		writeLock.lock();
		LOG.debug(" cur1 add " + obj.amountBuy);
		try {
			exchanges.add(obj);

			currency1VolumeTotal = currency1VolumeTotal.add(obj.amountSell);
			currency2VolumeTotal = currency2VolumeTotal.add(obj.amountBuy);

			cachedVolume = null;

			cachedVolume = new Volume();
			cachedVolume.put(Currencies.EUR.name().toLowerCase(),
					currency1VolumeTotal);
			cachedVolume.put(Currencies.GBP.name().toLowerCase(),
					currency2VolumeTotal);
		} finally {
			writeLock.unlock();
		}
	}

	public List<Exchange> getAll() {
		readLock.lock();

		List<Exchange> res = null;
		try {
			LOG.info(" market getAll #" + exchanges.size());
			res = exchanges.getAll();
		} finally {
			readLock.unlock();
		}
		return res;
	}

	/**
	 *
	 * @return the total of handled volume of two currencies .
	 */
	public Volume volume() {
		readLock.lock();

		Volume res = null;

		try {

			if (cachedVolume != null) {
				LOG.info(" from cache,cur1 " + currency1VolumeTotal.intValue());
				res = cachedVolume;
			} else {
				LOG.info(" not from cache,cur1 "
						+ currency1VolumeTotal.intValue());

				res = new Volume();
				res.put(Currencies.EUR.name().toLowerCase(),
						currency1VolumeTotal);
				res.put(Currencies.GBP.name().toLowerCase(),
						currency2VolumeTotal);
			}
		} finally {
			readLock.unlock();
		}
		return res;
	}

	private final TradeMessage defaultMsg = new TradeMessage("134256", "EUR",
			"GBP", new BigDecimal(1000, MyMath.MC), new BigDecimal(747.10,
					MyMath.MC), new BigDecimal(0.7471, MyMath.MC),
			"17-APR-15 10:27:44", "FR");

	/**
	 * used for the GET test request
	 */
	public TradeMessage createTrade() {
		return defaultMsg;
	}

}