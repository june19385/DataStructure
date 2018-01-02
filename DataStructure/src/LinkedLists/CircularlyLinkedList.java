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

package LinkedLists;

public class CircularlyLinkedList<E> {

	private static class Node<E>{
		private E element;
		private Node<E> next;
		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}
		public E getElement() {
			return element;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	}
	
	private Node<E> tail;
	private int size=0;
	
	public CircularlyLinkedList() {
		tail=null;
		size=0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E first() {
		if(isEmpty()) return null;
		return tail.getNext().getElement();
	}
	
	public E Last() {
		if(isEmpty()) return null;
		return tail.getElement();
	}
	
	public void rotate() {
		if(tail!=null) tail= tail.getNext();
	}
	
	public void addFirst(E e) {
		if(size==0) {
			tail = new Node<E>(e,null);
			tail.setNext(tail);
		}
		else {
			Node<E> newest = new Node<E>(e,tail.getNext());
			tail.setNext(newest);
		}
		size++;
	}
	
	public void addLast(E e) {
		addFirst(e);
		tail = tail.getNext();
	}
	
	public E removeFirst() {
		if(isEmpty()) return null;
		Node<E> head = tail.getNext();
		if(head==tail) tail=null;
		else tail.setNext(head.getNext());
		size--;
		return head.getElement();
	}
	
}
