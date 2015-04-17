/**
 *
 */
package storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * not thread safe list where elements can only be added and not removed
 *
 *
 */
public class OnlyAddableList<T> implements Iterable {
	private final List<T> list;

	public OnlyAddableList() {
		list = new ArrayList<T>();
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