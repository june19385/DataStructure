package Maps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Lists.ArrayLists;

public class UnsortedTableMaps<K, V> extends AbstractMaps<K, V> {

	private ArrayLists<MapEntry<K,V>> table;
	
	public UnsortedTableMaps() {
		table = new ArrayLists<>();
	}
	
	private int findIndex(K key) {
		int n = table.size();
		for(int j=0;j<n;j++) {
			if(table.get(j).getKey().equals(key)) {
				return j;
			}
		}
		return -1;
	}
	
	@Override
	public int size() {
		return table.size();
	}

	@Override
	public V get(K key) {
		int j = findIndex(key);
		if(j == -1) return null;
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		int j = findIndex(key);
		if(j==-1) {
			table.add(new MapEntry<>(key,value));
			return null;
		}
		else {
			return table.get(j).setValue(value);
		}
	}

	@Override
	public V remove(K key) {
		int j = findIndex(key);
		int n = size();
		if(j==-1) return null;
		V answer = table.get(j).getValue();
		if(j!=n-1)
			table.set(j, table.get(n-1));
		table.remove(n-1);
		return answer;
	}
	
	private class EntryIterator implements Iterator<Entry<K,V>> {
		private int j = 0;

		@Override
		public boolean hasNext() {
			return j<table.size();
		}

		@Override
		public Entry<K, V> next() {
			if(j==table.size()) throw new NoSuchElementException();
			return table.get(j++);
		}
		
		public void remove() {throw new UnsupportedOperationException();}
	}
	
	private class EntryIterable implements Iterable<Entry<K,V>> {
		public Iterator<Entry<K,V>> iterator() {return new EntryIterator();}
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}

}
