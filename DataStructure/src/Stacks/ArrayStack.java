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
