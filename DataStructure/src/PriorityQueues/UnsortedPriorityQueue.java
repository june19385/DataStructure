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

import Lists.PositionalDoublyLinkedList;
import Lists.PositionalList;
import Lists.Positions;

public class UnsortedPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

	private PositionalList<Entry<K,V>> list = new PositionalDoublyLinkedList<>();
	
	public UnsortedPriorityQueue() {super();}
	public UnsortedPriorityQueue(Comparator<K> comp) {super(comp);}
	
	private Positions<Entry<K,V>> findMin(){
		Positions<Entry<K,V>> small = list.first();
		for(Positions<Entry<K,V>> walk : list.positions()) {
			if(compare(walk.getElement(),small.getElement())<0)
				small = walk;
		}
		return small;
	}
	
	@Override
	public int size() {return list.size();}

	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newest = new PQEntry<>(key,value);
		list.addLast(newest);
		return newest;
	}

	@Override
	public Entry<K, V> min() {
		if(list.isEmpty()) return null;
		return findMin().getElement();
	}

	@Override
	public Entry<K, V> removeMin() {
		if(list.isEmpty()) return null;
		return list.remove(findMin());
	}

}
