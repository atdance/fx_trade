package com.bondsbiz.trade.business.storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.model.Exchange;

/**
 * Contains information about the market constituted by two currencies. Thread
 * consistency implemented with a ReentrantReadWriteLock .
 *
 */
@ApplicationScoped
public class CurrencyMarket {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyMarket.class);

	private static final boolean cacheEnabled = false;

	Map<String, BigDecimal> marketVolume = new HashMap<>();

	private final Map<Integer, Exchange> database = new HashMap<>();

	/**
	 * Store the total of handled volume of one currency .
	 */
	private BigDecimal currency1VolumeTotal = new BigDecimal(0, MyMath.MC);

	/**
	 * Store the total of handled volume of one currency .
	 */
	private BigDecimal currency2VolumeTotal = new BigDecimal(0, MyMath.MC);

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	public CurrencyMarket() {
		marketVolume.put(Currencies.EUR.name().toLowerCase(), BigDecimal.ZERO);
		marketVolume.put(Currencies.GBP.name().toLowerCase(), BigDecimal.ZERO);
	}

	public boolean add(@Valid Exchange obj) {
		writeLock.lock();
		boolean res = false;

		try {

			database.put(obj.getID(), obj);

			try {

				currency1VolumeTotal = currency1VolumeTotal.add(obj.getAmountSell());

				currency2VolumeTotal = currency2VolumeTotal.add(obj.getAmountBuy());

				BigDecimal currencyA = currency1VolumeTotal.add(obj.getAmountSell());

				BigDecimal currencyB = currency2VolumeTotal.add(obj.getAmountBuy());

				BigDecimal prevCurA = marketVolume.get(Currencies.EUR.name().toLowerCase());

				marketVolume.put(Currencies.EUR.name().toLowerCase(), prevCurA.add(currencyA));

				BigDecimal prevCurB = marketVolume.get(Currencies.EUR.name().toLowerCase());

				marketVolume.put(Currencies.GBP.name().toLowerCase(), prevCurB.add(currencyB));
			} catch (Exception forgiven) {
				LOGGER.debug("{}", "CurrencyMarket add() " + forgiven.getMessage());
			}

			res = true;
		} catch (Exception e) {
			res = false;
			LOGGER.error("CurrencyMarket add {}", e.getMessage());
		} finally {
			writeLock.unlock();
		}

		return res;
	}

	public List<Exchange> getAll() {
		readLock.lock();

		try {
			LOGGER.debug(" market getAll #" + database.size());

			Set<Entry<Integer, Exchange>> entries = database.entrySet();

			LOGGER.debug("------ CurrencyMarket getAll ");

			for (Entry<Integer, Exchange> entry : entries) {
				LOGGER.debug(" " + entry.getKey());
			}
			LOGGER.debug("------ getAll end ");

			List<Exchange> exchangesTemp = new ArrayList<>();

			for (Entry<Integer, Exchange> entry : entries) {

				exchangesTemp.add(entry.getValue());
			}
			return exchangesTemp;

		} catch (final Exception e) {

			throw e;
		} finally {
			readLock.unlock();
		}
	}

	public Map<String, BigDecimal> volume() {
		readLock.lock();

		Map<String, BigDecimal> res = new HashMap<>();

		try {

			if (cacheEnabled) {
				LOGGER.info(" from cache, cur1 " + marketVolume.get(Currencies.EUR.name().toLowerCase()));

				res = marketVolume;

			} else {

				res.put(Currencies.EUR.name().toLowerCase(), currency1VolumeTotal);
				res.put(Currencies.GBP.name().toLowerCase(), currency2VolumeTotal);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			readLock.unlock();
		}
		return res;
	}

	public Exchange getById(Integer pID) throws Exception {
		readLock.lock();

		try {
			Exchange exch = database.get(pID);

			if (null == exch) {
				throw new IllegalArgumentException();
			}

			return exch;

		} catch (Exception e) {
			throw e;
		} finally {

			readLock.unlock();
		}
	}

}