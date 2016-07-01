package com.bondsbiz.trade.business.storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ejb.Stateless;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.MyMath;
import com.bondsbiz.trade.business.model.Currencies;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.MarketVolume;
import com.bondsbiz.trade.business.model.TradeMessage;

/**
 * Contains information about the market constituted by two currencies. Thread
 * consistency implemented with a ReentrantReadWriteLock .
 *
 */
@Stateless
public class CurrencyMarket {

	/**
	 * used for caching the latest incoming data
	 */
	private MarketVolume cachedVolume = new MarketVolume();

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	private final OnlyAddableList<Exchange> exchanges = new OnlyAddableList<>();

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

	public CurrencyMarket() {
		cachedVolume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.ZERO);
		cachedVolume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.ZERO);
	}

	public boolean add1(@Valid Exchange obj) {
		return true;
	}
	// boolean add(@Valid Exchange obj) {
	// writeLock.lock();
	// boolean res = false;
	/*
	 * try { currency1VolumeTotal =
	 * currency1VolumeTotal.add(obj.getAmountSell()); currency2VolumeTotal =
	 * currency2VolumeTotal.add(obj.getAmountBuy());
	 * 
	 * exchanges.add(obj);
	 * 
	 * cachedVolume = null;
	 * 
	 * cachedVolume = new MarketVolume();
	 * cachedVolume.put(Currencies.EUR.name().toLowerCase(),
	 * currency1VolumeTotal);
	 * cachedVolume.put(Currencies.GBP.name().toLowerCase(),
	 * currency2VolumeTotal); res = true; } catch (NullPointerException e) {
	 * 
	 * } finally { writeLock.unlock(); }
	 */
	// return res;
	// }

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

	public MarketVolume volume() {
		readLock.lock();

		MarketVolume res = null;

		try {

			if (cachedVolume != null) {
				LOG.info(" from cache,cur1 " + currency1VolumeTotal.intValue());
				res = cachedVolume;
			} else {
				LOG.info(" not from cache,cur1 " + currency1VolumeTotal.intValue());

				res = new MarketVolume();
				res.put(Currencies.EUR.name().toLowerCase(), currency1VolumeTotal);
				res.put(Currencies.GBP.name().toLowerCase(), currency2VolumeTotal);
			}
		} finally {
			readLock.unlock();
		}
		return res;
	}

	public static final TradeMessage defaultMsg = new TradeMessage("134256", "EUR", "GBP",
			new BigDecimal("1000", MyMath.MC), new BigDecimal("747.10", MyMath.MC), new BigDecimal("0.7471", MyMath.MC),
			"17-APR-15 10:27:44", "FR");

	public TradeMessage createTrade() {
		return  defaultMsg;
	}

}