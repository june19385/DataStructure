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

package PriorityQueues;

import java.util.Comparator;

public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {
	
	protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V> {
		private int index;
		public AdaptablePQEntry(K key, V value, int j) {
			super(key, value);
			index = j;
		}
		public int getIndex() {return index;}
		public void setIndex(int j) {index = j;}
	}
	
	public HeapAdaptablePriorityQueue() {super();}
	public HeapAdaptablePriorityQueue(Comparator<K> comp) {super(comp);}
	
	protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException {
		if(!(entry instanceof AdaptablePQEntry)) throw new IllegalArgumentException("Invalid entry");
		AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry;
		int j = locator.getIndex();
		if(j>=heap.size()||heap.get(j)!=locator) throw new IllegalArgumentException("Invalid entry");
		return locator;
	}
	
	protected void swap(int i, int j) {
		super.swap(i, j);
		((AdaptablePQEntry<K,V>)heap.get(i)).setIndex(i);
		((AdaptablePQEntry<K,V>)heap.get(j)).setIndex(j);
	}
	
	protected void bubble(int j) {
		if(j>0&&compare(heap.get(j),heap.get(parent(j)))<0)
			upheap(j);
		else
			downheap(j);
	}
	
	public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new AdaptablePQEntry<>(key,value,heap.size());
		heap.add(newest);
		upheap(heap.size()-1);
		return newest;
	}
	
	@Override
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		AdaptablePQEntry<K,V> locator = validate(entry);
		int j = locator.getIndex();
		if(j==heap.size()-1)
			heap.remove(heap.size()-1);
		else {
			swap(j,heap.size()-1);
			heap.remove(heap.size()-1);
			bubble(j);
		}
	}

	@Override
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		AdaptablePQEntry<K,V> locator = validate(entry);
		checkKey(key);
		locator.setKey(key);
		bubble(locator.getIndex());
	}

	@Override
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		AdaptablePQEntry<K,V> locator = validate(entry);
		locator.setValue(value);
	}

}
