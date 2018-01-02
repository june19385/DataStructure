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
