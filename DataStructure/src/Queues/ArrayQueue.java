package Queues;

public class ArrayQueue<E> implements Queues<E> {	//implements "Queues.java" in my project
	
	private final static int CAPACITY = Integer.MAX_VALUE;
	private E[] data;
	private int size;
	private int front_index;
	
	public ArrayQueue() {
		this(CAPACITY);
	}
	public ArrayQueue(int capacity) {
		data = (E[]) new Object[capacity];
		size = 0;
		front_index = 0;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (size==0);
	}

	@Override
	public void enqueue(E e) throws IllegalStateException{
		if(size==data.length) throw new IllegalStateException("Queue is full");
		int available = (front_index+size)%data.length;
		data[available] = e;
		size++;
	}

	@Override
	public E first() {
		if(isEmpty()) return null;
		return data[front_index];
	}

	@Override
	public E dequeue() {
		if(isEmpty()) return null;
		E answer = data[front_index];
		data[front_index] = null;
		front_index=(front_index+1)%data.length;
		size--;
		return answer;
	}

}
