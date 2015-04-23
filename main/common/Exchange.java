package common;

import java.math.BigDecimal;

/**
 * An exchange between two currencies and the amount requested
 *
 */
public class Exchange {
	public CurrencyPair aPair;
	public BigDecimal amountSell;
	public BigDecimal amountBuy;

	public Exchange() {
	}

	/**
	 *
	 */
	public Exchange(CurrencyPair pair, BigDecimal pSell, BigDecimal pBuy) {
		aPair = pair;
		amountSell = pSell;
		amountBuy = pBuy;
	}

	@Override
	public String toString() {
		return "Exchange [aPair=" + aPair + ", amountSell=" + amountSell
				+ ", amountBuy=" + amountBuy + "]";
	}

	/**
	 * @return the aPair
	 */
	public CurrencyPair CurrencyPair() {
		return aPair;
	}
}