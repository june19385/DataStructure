package Stacks;

import LinkedLists.SinglyLinkedList;	//import LinkedLists.SinglyLinkedList in this project

public class LinkedStack<E> implements Stacks<E> {	//implements "Stacks.java" in my project
	
	private SinglyLinkedList<E> list;
	
	public LinkedStack() {
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
	public void push(E e) {
		list.addFirst(e);
	}

	@Override
	public E top() {
		return list.first();
	}

	@Override
	public E pop() {
		return list.removeFirst();
	}

}
