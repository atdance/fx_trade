package com.bondsbiz.trade.business.model;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketVolume extends HashMap<String, BigDecimal> {

	private static final long serialVersionUID = -4011973740508066273L;

	/**
	 * In trading, volume refers to the number of transactions of a trader, a
	 * broker or a market within a certain time period. When trading on the
	 * forex market, there is no central exchange. Although you can measure the
	 * volume in transactions for an individual broker.
	 */
	public MarketVolume() {
		super();
	}
}