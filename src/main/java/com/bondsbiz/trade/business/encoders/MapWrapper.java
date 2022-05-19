package com.bondsbiz.trade.business.encoders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MapWrapper {

	private Map<String, BigDecimal> map = new HashMap<>();

	/**
	 * Empty constructor for Jaxb
	 */
	public MapWrapper() {

	}

	public MapWrapper(Map<String, BigDecimal> pMap) {
		map = pMap;
	}

	public Map<String, BigDecimal> getMap() {
		return map;
	}

	public void setMap(Map<String, BigDecimal> pMap) {
		map = pMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MapWrapper other = (MapWrapper) obj;
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		return true;
	}

}
