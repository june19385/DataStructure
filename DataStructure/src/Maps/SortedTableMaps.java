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

import java.util.Comparator;

import Lists.ArrayLists;
import PriorityQueues.Entry;

public class SortedTableMaps<K, V> extends AbstractSortedMaps<K, V> {
	
	private ArrayLists<MapEntry<K,V>> table = new ArrayLists<>();
	public SortedTableMaps() {super();}
	public SortedTableMaps(Comparator<K> comp) {super(comp);}
	
	private int findIndex(K key, int low, int high) {
		if(high<low) return high+1;
		int mid = (low+high)/2;
		int comp = compare(key,table.get(mid));
		if(comp==0) return mid;
		else if(comp<0) return findIndex(key,low,mid-1);
		else return findIndex(key,mid+1,high);
	}
	
	private int findIndex(K key) {return findIndex(key,0,table.size()-1);}

	@Override
	public int size() {
		return table.size();
	}
	
	@Override
	public V get(K key) {
		int j = findIndex(key);
		if(j==size()||compare(key,table.get(j))!=0) return null;
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		int j = findIndex(key);
		if(j<size()&&compare(key,table.get(j))==0) return table.get(j).setValue(value);
		table.add(j,new MapEntry<K,V>(key,value));
		return null;
	}

	@Override
	public V remove(K key) {
		int j = findIndex(key);
		if(j==size()||compare(key,table.get(j))!=0) return null;
		return table.remove(j).getValue();
	}
	
	private Entry<K,V> safeEntry(int j) {
		if(j<0||j>=table.size()) return null;
		return table.get(j);
	}
	
	@Override
	public Entry<K, V> firstEntry() {
		return safeEntry(0);
	}

	@Override
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size()-1);
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		return safeEntry(findIndex(key));
	}

	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		int j = findIndex(key);
		if(j==size()||!key.equals(table.get(j).getKey())) j--;
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		return safeEntry(findIndex(key)-1);
	}

	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		int j = findIndex(key);
		if(j<size()&&key.equals(table.get(j).getKey())) j++;
		return safeEntry(j);
	}
	
	private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>();
		int j = startIndex;
		while(j<table.size()&&(stop==null||compare(stop,table.get(j))>0)) buffer.add(table.get(j++));
		return buffer;
	}
	
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return snapshot(0,null);
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		return snapshot(findIndex(fromKey),toKey);
	}
}
