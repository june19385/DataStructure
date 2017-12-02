package Stacks;

public class ArrayStack<E> implements Stacks<E> {	//implements "Stacks.java" in my project
	
	private final static int CAPACITY = Integer.MAX_VALUE;
	private E[] data;
	private int pointer;
	
	public ArrayStack() {
		this(CAPACITY);
	}
	public ArrayStack(int capacity) {
		data = (E[]) new Object[capacity];
		pointer = -1;
	}
	
	@Override
	public int size() {
		return (pointer+1);
	}

	@Override
	public boolean isEmpty() {
		return (pointer==-1);
	}

	@Override
	public void push(E e) throws IllegalStateException{
		if(size()==data.length) throw new IllegalStateException("Stack is full");
		data[++pointer]=e;
	}

	@Override
	public E top() {
		if(isEmpty()) return null;
		return data[pointer];
	}

	@Override
	public E pop() {
		if(isEmpty()) return null;
		E answer = data[pointer];
		data[pointer] = null;
		pointer--;
		return answer;
	}

}
