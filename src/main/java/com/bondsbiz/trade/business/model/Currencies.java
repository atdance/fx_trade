package com.bondsbiz.trade.business.model;

import java.util.HashSet;
import java.util.Set;

import org.joda.money.CurrencyUnit;

/**
 * Allowed currencies. Their lower case should correspond to the names of the
 * values of the array data in the javascript graph renderer.
 */
public enum Currencies {
	EUR("EUR"), GBP("GBP");

	private final static Set<String> values = new HashSet<>(Currencies.values().length);

	static {
		for (final Currencies f : Currencies.values()) {
			values.add(f.name());
		}
	}

	CurrencyUnit cu;

	Currencies(String txt) {
		cu = CurrencyUnit.of(txt);
	}

	public static boolean contains(String value) {
		return values.contains(value);
	}
}
