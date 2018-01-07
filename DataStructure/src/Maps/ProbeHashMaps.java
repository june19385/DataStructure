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

import Lists.ArrayLists;
import PriorityQueues.Entry;

public class ProbeHashMaps<K, V> extends AbstractHashMaps<K, V> {
	
	private MapEntry<K,V>[] table;
	private MapEntry<K,V> DEFUNCT = new MapEntry<>(null,null);
	
	public ProbeHashMaps() {super();}
	public ProbeHashMaps(int cap) {super(cap);}
	public ProbeHashMaps(int cap, int p) {super(cap,p);}
	
	@Override
	protected void createTable() {
		table = (MapEntry<K,V>[]) new MapEntry[capacity];
	}
	
	private boolean isAvailable(int j) {
		return (table[j]==null || table[j]==DEFUNCT);
	}
	
	private int findSlot(int h, K k) {
		int avail = -1;
		int j = h;
		do {
			if(isAvailable(j)) {
				if(avail==-1) avail=j;
				if(table[j]==null) break;
			}
			else if(table[j].getKey().equals(k)) {
				return j;
			}
			j = (j+1)%capacity;
		}while(j!=h);
		return -(avail+1);
	}

	@Override
	protected V bucketGet(int h, K k) {
		int j = findSlot(h,k);
		if(j<0) return null;
		return table[j].getValue();
	}

	@Override
	protected V bucketPut(int h, K k, V v) {
		int j = findSlot(h,k);
		if(j>=0) return table[j].setValue(v);
		table[-(j+1)] = new MapEntry<>(k,v);
		n++;
		return null;
	}

	@Override
	protected V bucketRemove(int h, K k) {
		int j = findSlot(h,k);
		if(j<0) return null;
		V answer = table[j].getValue();
		table[j] = DEFUNCT;
		n--;
		return answer;
	}
	
	public Iterable<Entry<K,V>> entrySet() {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>();
		for(int h=0;h<capacity;h++) {
			if(!isAvailable(h)) buffer.add(table[h]);
		}
		return buffer;
	}

}
