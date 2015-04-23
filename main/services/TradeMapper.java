/**
 *
 */
package services;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storage.CurrencyMarket;

import common.Exchange;
import common.TradeMessage;
import common.Volume;

/**
 * @param <T>
 * @param <K>
 *
 */
public class TradeMapper implements OperationsInterface {

	private static Logger LOG = null;
	static {
		LOG = LoggerFactory.getLogger(TradeMapper.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#insert(java.lang.Object)
	 */
	@Override
	public Long insert(TradeMessage record) {
		Exchange exc = record.getExchange();
		CurrencyMarket.getInstance().add(exc);
		return 0L;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#insertLatest(java.lang.Object)
	 */
	@Override
	public Long insertLatest(TradeMessage record) {
		Exchange exc = record.getExchange();
		CurrencyMarket.getInstance().addLatest(exc);
		return 0L;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#selectById(long)
	 */
	@Override
	public TradeMessage selectById(Long id) {
		return CurrencyMarket.getInstance().createTrade();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see services.OperationsInterface#selectAll()
	 */
	@Override
	public List<Exchange> selectAll() {
		List<common.Exchange> resp = null;
		try {
			resp = CurrencyMarket.getInstance().getAll();
		} catch (Exception ex) {
			resp = Collections.emptyList();
		}
		return resp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see services.OperationsInterface#listLatest()
	 */
	@Override
	public Volume volume() {
		Volume resp = null;
		try {
			resp = CurrencyMarket.getInstance().volume();
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
		return resp;
	}
}