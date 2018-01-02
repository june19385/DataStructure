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

public class ChainHashMaps<K, V> extends AbstractHashMaps<K, V> {

	private UnsortedTableMaps<K,V>[] table;
	public ChainHashMaps() {super();}
	public ChainHashMaps(int cap) {super(cap);}
	public ChainHashMaps(int cap, int p) {super(cap,p);}
	
	@Override
	protected void createTable() {
		table = (UnsortedTableMaps<K,V>[]) new UnsortedTableMaps[capacity];
	}

	@Override
	protected V bucketGet(int h, K k) {
		UnsortedTableMaps<K,V> bucket = table[h];
		if(bucket==null) return null;
		return bucket.get(k);
	}

	@Override
	protected V bucketPut(int h, K k, V v) {
		UnsortedTableMaps<K,V> bucket = table[h];
		if(bucket==null) {
			bucket = table[h] = new UnsortedTableMaps<>();
		}
		int oldSize = bucket.size();
		V answer = bucket.put(k, v);
		n+=(bucket.size()-oldSize);
		return answer;
	}

	@Override
	protected V bucketRemove(int h, K k) {
		UnsortedTableMaps<K,V> bucket = table[h];
		if(bucket==null) return null;
		int oldSize = bucket.size();
		V answer = bucket.remove(k);
		n-=(oldSize-bucket.size());
		return answer;
	}
	
	public Iterable<Entry<K,V>> entrySet() {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>();
		for(int h=0;h<capacity;h++) {
			if(table[h]!=null) {
				for(Entry<K,V> entry:table[h].entrySet())
					buffer.add(entry);
			}
		}
		return buffer;
	}

}
