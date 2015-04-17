/**
 *
 */
package storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Currencies;
import common.Exchange;
import common.MyMath;
import common.TradeMessage;

/**
 * Contains information about the market constituted by two currencies.
 *
 */
public class CurrencyMarket {

	OnlyAddableList<Exchange> exchanges = new OnlyAddableList<Exchange>();

	private volatile static CurrencyMarket localInstance = null;

	private static List<BigDecimal> cur1Buffer = new ArrayList<BigDecimal>(1000);
	private static List<BigDecimal> cur2Buffer = new ArrayList<BigDecimal>(1000);

	/**
	 * Store the total of handled volume of one currency .
	 *
	 */
	BigDecimal currency1VolumeTotal = new BigDecimal(0, MyMath.MC);
	/**
	 * Store the total of handled volume of one currency .
	 *
	 */

	BigDecimal currency2VolumeTotal = new BigDecimal(0, MyMath.MC);

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(CurrencyMarket.class);
	}

	private CurrencyMarket() {
	}

	public static synchronized CurrencyMarket getInstance() {
		CurrencyMarket tempInstance = localInstance;

		if (tempInstance == null) {
			synchronized (CurrencyMarket.class) {
				tempInstance = localInstance;
				if (tempInstance == null) {
					tempInstance = new CurrencyMarket();
					localInstance = tempInstance;
				}
			}
		}

		return tempInstance;
	}

	public void add(Exchange obj) {
		exchanges.add(obj);
		// LOG.debug(" market add " + obj.amountBuy);
		currency1VolumeTotal = currency1VolumeTotal.add(obj.amountSell);
		currency2VolumeTotal = currency2VolumeTotal.add(obj.amountBuy);
	}

	public List<Exchange> getAll() {
		LOG.info(" market getAll #" + exchanges.size());
		return exchanges.getAll();
	}

	public void addLatest(Exchange obj) {
		LOG.debug(" cur1 addlatest " + obj.amountBuy);
		cur1Buffer.add(obj.amountBuy);
		cur2Buffer.add(obj.amountSell);
	}

	/**
	 * List the total of handled volume of two currencies .
	 *
	 * @return
	 */
	public Map<String, Object> volume() {
		LOG.info(" cur1 " + currency1VolumeTotal.intValue());

		Map<String, Object> ramo = new LinkedHashMap<String, Object>();
		ramo.put(Currencies.EUR.name().toLowerCase(),
				currency1VolumeTotal.intValue());
		ramo.put(Currencies.GBP.name().toLowerCase(),
				currency2VolumeTotal.intValue());

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		lst.add(ramo);
		return ramo;
	}

	/**
	 * used for the GET test request
	 */
	public TradeMessage createTrade() {
		return new TradeMessage("134256", "EUR", "GBP", new BigDecimal(1000,
				MyMath.MC), new BigDecimal(747.10, MyMath.MC), new BigDecimal(
				0.7471, MyMath.MC), "17-APR-15 10:27:44", "FR");
	}

}