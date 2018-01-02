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

import Lists.ArrayLists;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

	protected ArrayLists<Entry<K,V>> heap = new ArrayLists<>();
	
	public HeapPriorityQueue() {super();}
	public HeapPriorityQueue(Comparator<K> comp) {super(comp);}
	public HeapPriorityQueue(K[] keys, V[] values) {
		super();
		for(int j=0;j<Math.min(keys.length, values.length);j++) {
			heap.add(new PQEntry<>(keys[j],values[j]));
		}
		heapify();
	}
	protected int parent(int j) {return (j-1)/2;}
	protected int left(int j) {return 2*j+1;}
	protected int right(int j) {return 2*j+2;}
	protected boolean hasLeft(int j) {return left(j)<heap.size();}
	protected boolean hasRight(int j) {return right(j)<heap.size();}
	
	protected void heapify() {
		int startIndex = parent(size()-1);
		for(int j = startIndex;j>=0;j--) {
			downheap(j);
		}
	}
	
	protected void swap(int i, int j) {
		Entry<K,V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	
	protected void upheap(int j) {
		while(j>0) {
			int p = parent(j);
			if(compare(heap.get(j),heap.get(p))>=0) break;
			swap(j,p);
			j = p;
		}
	}
	
	protected void downheap(int j) {
		while(hasLeft(j)) {
			int leftIndex = left(j);
			int smallChildIndex = leftIndex;
			if(hasRight(j)) {
				int rightIndex = right(j);
				if(compare(heap.get(leftIndex),heap.get(rightIndex))>0)
					smallChildIndex = rightIndex;
			}
			if(compare(heap.get(smallChildIndex),heap.get(j))>=0) break;
			swap(j,smallChildIndex);
			j = smallChildIndex;
		}
	}
	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new PQEntry<>(key,value);
		heap.add(newest);
		upheap(heap.size()-1);
		return newest;
	}

	@Override
	public Entry<K, V> min() {
		if(heap.isEmpty()) return null;
		return heap.get(0);
	}

	@Override
	public Entry<K, V> removeMin() {
		if(heap.isEmpty()) return null;
		Entry<K,V> answer = heap.get(0);
		swap(0,heap.size()-1);
		heap.remove(heap.size()-1);
		downheap(0);
		return answer;
	}

}
