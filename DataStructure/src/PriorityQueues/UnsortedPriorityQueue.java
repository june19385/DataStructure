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
