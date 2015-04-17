package services;

import java.util.List;
import java.util.Map;

import common.Exchange;
import common.TradeMessage;

/**
 * Includes the most common operations that classes in this package perform. It
 * was needed to simplify the behavior of classes in the package . Is used in
 * Services.java .
 *
 *
 * @see Services
 *
 */
public interface OperationsInterface {

	public abstract Long insert(TradeMessage t);

	public abstract Long insertLatest(TradeMessage t);

	public abstract TradeMessage selectById(Long id);

	public abstract List<Exchange> selectAll();

	public abstract Map<String, Object> volume();
}