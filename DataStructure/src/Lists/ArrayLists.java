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

package Lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLists<E> implements Lists<E> {	//implements "Lists.java" in my project
	
	private static final int CAPACITY = 16;
	private E[] data;
	private int size;
	
	public ArrayLists() {
		this(CAPACITY);
	}
	
	public ArrayLists(int capacity) {
		data = (E[]) new Object[capacity];
		size = 0;
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
	public E get(int i) throws IndexOutOfBoundsException {
		checkIndex(i,size);
		return data[i];
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i,size);
		E temp = data[i];
		data[i] = e;
		return temp;
	}
	
	private void resize(int capacity) {
		E[] temp = (E[]) new Object[capacity];
		for(int k=0; k<size; k++) {
			temp[k] = data[k];
		}
		data = temp;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i,size+1);
		if(size==data.length) resize(2*data.length);
		for (int k = size-1;k>=i;k--) {
			data[k+1]=data[k];
		}
		data[i]=e;
		size++;
	}
	
	@Override
	public void add(E e) throws IndexOutOfBoundsException {
		this.add(size,e);
	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i,size);
		E temp = data[i];
		for(int k=i;k<size-1;k++) {
			data[k] = data[k+1];
		}
		data[size-1] = null;
		size--;
		return temp;
	}
	
	private void checkIndex(int i, int n) throws IndexOutOfBoundsException {
		if(i<0||i>=n)
			throw new IndexOutOfBoundsException("Illegal index: "+i);
	}
	
	private class ArrayIterator implements Iterator<E>{
		private int j;
		private boolean removable;
		
		public ArrayIterator() {
			j = 0;
			removable = false;
		}
		
		@Override
		public boolean hasNext() {
			return j < size;
		}
		@Override
		public E next() throws NoSuchElementException {
			if(j==size) throw new NoSuchElementException("No next element");
			removable = true;
			return data[j++];
		}
		@Override
		public void remove() throws IllegalStateException {
			if(!removable) throw new IllegalStateException("nothing to remove");
			ArrayLists.this.remove(j-1);
			j--;
			removable = false;
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}


}
