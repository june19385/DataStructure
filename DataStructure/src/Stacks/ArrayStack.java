package Stacks;

public class ArrayStack<E> implements Stacks<E> {
	
	private final static int CAPACITY = Integer.MAX_VALUE;
	private E[] data;
	private int t = -1;
	
	public ArrayStack() {
		this(CAPACITY);
	}
	public ArrayStack(int capacity) {
		data = (E[]) new Object[capacity];
	}
	
	@Override
	public int size() {
		return (t+1);
	}

	@Override
	public boolean isEmpty() {
		return (t==-1);
	}

	@Override
	public void push(E e) throws IllegalStateException{
		if(size()==data.length) throw new IllegalStateException("Stack is full");
		data[++t]=e;
	}

	@Override
	public E top() {
		if(isEmpty()) return null;
		return data[t];
	}

	@Override
	public E pop() {
		if(isEmpty()) return null;
		E answer = data[t];
		data[t] = null;
		t--;
		return answer;
	}

}
