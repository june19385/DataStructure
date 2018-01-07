/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Maps;

import java.util.Iterator;

import PriorityQueues.Entry;

public abstract class AbstractMaps<K, V> implements Maps<K, V> {

	protected static class MapEntry<K,V> implements Entry<K,V> {
		private K k;
		private V v;
		public MapEntry(K key, V value) {
			k = key;
			v = value;
		}
		
		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}
		
		protected void setKey(K key) {k = key;}
		protected V setValue(V value) {
			V old = v;
			v = value;
			return old;
		}		
	}
	
	private class KeyIterator implements Iterator<K> {
		private Iterator<Entry<K,V>> entries = entrySet().iterator();
		public boolean hasNext() {return entries.hasNext();}
		public K next() {return entries.next().getKey();}
		public void remove() {throw new UnsupportedOperationException();}
	}
	
	private class KeyIterable implements Iterable<K> {
		public Iterator<K> iterator() {return new KeyIterator();}
	}
	
	@Override
	public Iterable<K> keySet() {
		return new KeyIterable();
	}
	
	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K,V>> entries = entrySet().iterator();
		public boolean hasNext() {return entries.hasNext();}
		public V next() {return entries.next().getValue();}
		public void remove() {throw new UnsupportedOperationException();}
	}
	
	private class ValueIterable implements Iterable<V> {
		public Iterator<V> iterator() {return new ValueIterator();}
	}

	@Override
	public Iterable<V> values() {
		return new ValueIterable();
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}
}
