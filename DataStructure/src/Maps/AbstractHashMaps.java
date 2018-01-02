package Maps;

import java.util.Random;

import Lists.ArrayLists;

public abstract class AbstractHashMaps<K, V> extends AbstractMaps<K, V> {

	protected int n = 0;
	protected int capacity;
	private int prime;
	private long scale, shift;
	
	protected abstract void createTable();
	protected abstract V bucketGet(int h, K k);
	protected abstract V bucketPut(int h, K k, V v);
	protected abstract V bucketRemove(int h, K k);
	
	public AbstractHashMaps(int cap,int p) {
		prime = p;
		capacity = cap;
		Random rand = new Random();
		scale = rand.nextInt(prime-1)+1;
		shift = rand.nextInt(prime);
		createTable();
	}
	
	public AbstractHashMaps(int cap) {
		this(cap,109345121);
	}
	
	public AbstractHashMaps() {
		this(17);
	}
	
	private int hashValue(K key) {
		return (int)((Math.abs(key.hashCode()*scale+shift)%prime)%capacity);
	}
	
	private void resize(int newCap) {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>(n);
		for(Entry<K,V> e : entrySet())
			buffer.add(e);
		capacity = newCap;
		createTable();
		n = 0;
		for(Entry<K,V> e : buffer) {
			put(e.getKey(),e.getValue());
		}
	}
	
	@Override
	public int size() {
		return n;
	}

	@Override
	public V get(K key) {
		return bucketGet(hashValue(key),key);
	}

	@Override
	public V put(K key, V value) {
		V answer = bucketPut(hashValue(key),key,value);
		if(n>capacity-1) resize(2*capacity+1);
		return answer;
	}

	@Override
	public V remove(K key) {
		return bucketRemove(hashValue(key),key);
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
