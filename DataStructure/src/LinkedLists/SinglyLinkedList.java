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

public class SinglyLinkedList<E> implements Cloneable{
	
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
	
	private Node<E> head;
	private Node<E> tail;
	private int size;
	
	public SinglyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E first() {
		if(isEmpty()) return null;
		return head.getElement();
	}
	
	public E last() {
		if (isEmpty()) return null;
		return tail.getElement();
	}
	
	public void addFirst(E e) {
		head = new Node<E>(e,head);
		if(size==0) tail = head;
		size++;
	}
	
	public void addLast(E e) {
		Node<E> newest = new Node<E>(e,null);
		if(isEmpty()) head=newest;
		else tail.setNext(newest);
		tail = newest;
		size++;
	}
	
	public E removeFirst() {
		if(isEmpty()) return null;
		E answer = head.getElement();
		head = head.getNext();
		size--;
		if(size==0) tail=null;
		return answer;
	}
	
	public boolean equals(Object o) {
		if(o==null) return false;
		if(getClass()!=o.getClass()) return false;
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) o;
		if(size!=other.size) return false;
		Node walkA = head;
		Node walkB = other.head;
		while(walkA!=null) {
			if(!walkA.getElement().equals(walkB.getElement())) return false;
			walkA=walkA.getNext();
			walkB=walkB.getNext();
		}
		return true;
	}
	
	public SinglyLinkedList<E> clone() throws CloneNotSupportedException{
		SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone();
		if(size>0) {
			other.head = new Node<E>(head.getElement(),null);
			Node<E> walk = head.getNext();
			Node<E> otherTail = other.head;
			while(walk!=null) {
				Node<E> newest = new Node<E>(walk.getElement(),null);
				otherTail.setNext(newest);
				otherTail=newest;
				walk=walk.getNext();
			}
		}
		return other;
	}
}
