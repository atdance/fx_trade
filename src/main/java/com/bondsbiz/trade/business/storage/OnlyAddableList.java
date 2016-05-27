package com.bondsbiz.trade.business.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * not thread safe list where elements can only be added and not removed
 *
 * @param <K>
 *
 *
 */
class OnlyAddableList<T> implements Iterable<T> {
	private final List<T> list;

	public OnlyAddableList() {
		list = new ArrayList<>();
	}

	public boolean add(T t) {
		return list.add(t);
	}

	public T get(int i) {
		return list.get(i);
	}

	public List<T> getAll() {
		return list;
	}

	public int size() {
		return list.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}
}