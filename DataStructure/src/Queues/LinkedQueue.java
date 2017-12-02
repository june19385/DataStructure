package Queues;

import LinkedLists.SinglyLinkedList;	//import LinkedLists.SinglyLinkedList in this project

public class LinkedQueue<E> implements Queues<E> {

	private SinglyLinkedList<E> list;
	
	public LinkedQueue() {
		list = new SinglyLinkedList<>();
	}
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void enqueue(E e) {
		list.addLast(e);
	}

	@Override
	public E first() {
		return list.first();
	}

	@Override
	public E dequeue() {
		return list.removeFirst();
	}

}
