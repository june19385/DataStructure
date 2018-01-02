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
