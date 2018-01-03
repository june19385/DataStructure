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

//all component of this class use java.util package

package Maps;

import Lists.ArrayLists;
import Lists.Lists;

public class HashMultimaps<K, V> {
	Maps<K,Lists<V>> map;
	int total;
	public HashMultimaps() {
		map = new ChainHashMaps<>();
		total = 0;
	}
	
	public int size() {return total;}
	
	public boolean isEmpty() {return(total==0);}
	
	Iterable<V> get(K key) {
		Lists<V> secondary = map.get(key);
		if(secondary!=null) return secondary;
		return new ArrayLists<>();
	}
	
	void put(K key, V value) {
		Lists<V> secondary = map.get(key);
		if(secondary==null) {
			secondary = new ArrayLists<>();
			map.put(key, secondary);
		}
		secondary.add(value);
		total++;
	}
	
	boolean remove(K key, V value) {
		boolean wasRemoved = false;
		Lists<V> secondary = map.get(key);
		if(secondary!=null) {
			wasRemoved = (boolean)secondary.remove((int) value);
			if(wasRemoved) {
				total--;
				if(secondary.isEmpty()) map.remove(key);
			}
		}
		return wasRemoved;
	}
	
	Iterable<V> removeAll(K key) {
		Lists<V> secondary = map.get(key);
		if(secondary!=null) {
			total -= secondary.size();
			map.remove(key);
		}
		else {
			secondary = new ArrayLists<>();
		}
		return secondary;
	}
	
	Iterable<Entry<K,V>> entries() {
		Lists<Entry<K,V>> result = new ArrayLists<>();
		for(Entry<K,Lists<V>> secondary : map.entrySet()) {
			K key = secondary.getKey();
			for(V value : secondary.getValue())
				result.add(new AbstractMaps.MapEntry<K, V>(key, value));
		}
		return result;
	}
}
