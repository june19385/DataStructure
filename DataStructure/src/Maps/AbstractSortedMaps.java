package Maps;

import java.util.Comparator;

import PriorityQueues.Entry;

public abstract class AbstractSortedMaps<K, V> extends AbstractMaps<K, V> implements SortedMaps<K, V> {
	private Comparator<K> comp;

	protected AbstractSortedMaps(Comparator<K> c) {
		comp = c;
	}

	protected AbstractSortedMaps() {
		this(new DefaultComparator<K>());
	}

	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		return comp.compare(a.getKey(), b.getKey());
	}
	
	protected int compare(K a, Entry<K,V> b) {
		return comp.compare(a, b.getKey());
	}

	protected int compare(Entry<K,V> a, K b) {
		return comp.compare(a.getKey(), b);
	}

	protected int compare(K a, K b) {
		return comp.compare(a, b);
	}

	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key,key)==0);
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Incompatible key");
		}
	}
}
