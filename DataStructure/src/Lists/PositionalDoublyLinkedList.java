package Lists;

import java.util.Iterator;
import java.lang.Iterable;
import java.util.NoSuchElementException;

public class PositionalDoublyLinkedList<E> implements PositionalList<E> {
	
	private static class Node<E> implements Positions<E>{
		private E element;
		private Node<E> prev;
		private Node<E> next;
		public Node(E e, Node<E> p,Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E e) {
			element = e;
		}
		public Node<E> getPrev() {
			return prev;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setPrev(Node<E> p) {
			prev = p;
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	}

	private Node<E> header;
	private Node<E> trailer;
	private int size;
	
	public PositionalDoublyLinkedList() {
		header = new Node<E>(null,null,null);
		trailer = new Node<E>(null,header,null);
		header.setNext(trailer);
		size=0;
	}
	
	private Node<E> validate(Positions<E> p) throws IllegalArgumentException{
		if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
		Node<E> node = (Node<E>)p;
		if(node.getNext()==null) throw new IllegalArgumentException("p is no longer in the list");
		return node;
	}
	
	private Positions<E> position(Node<E> node) {
		if(node==header||node==trailer) return null;
		return node;
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
	public Positions<E> first() {
		return position(header.getNext());
	}

	@Override
	public Positions<E> last() {
		return position(trailer.getPrev());
	}

	@Override
	public Positions<E> before(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev());
	}

	@Override
	public Positions<E> after(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	private Positions<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<E>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
		return newest;
	}
	
	@Override
	public Positions<E> addFirst(E e) {
		return addBetween(e,header,header.getNext());
	}

	@Override
	public Positions<E> addLast(E e) {
		return addBetween(e,trailer.getPrev(),trailer);
	}

	@Override
	public Positions<E> addBefore(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e,node.getPrev(),node);
	}

	@Override
	public Positions<E> addAfter(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e,node,node.getNext());
	}

	@Override
	public E set(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E answer = node.getElement();
		node.setElement(e);
		return answer;
	}

	@Override
	public E remove(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		E answer = node.getElement();
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		return answer;
	}
	
	private class PositionIterator implements Iterator<Positions<E>>{
		
		private Positions<E> cursor = first();
		private Positions<E> recent = null;

		@Override
		public boolean hasNext() {
			return (cursor!=null);
		}

		@Override
		public Positions<E> next() throws NoSuchElementException {
			if(cursor==null) throw new NoSuchElementException("nothing left");
			recent = cursor;
			cursor = after(cursor);
			return recent;
		}

		@Override
		public void remove() throws IllegalStateException {
			if(recent==null) throw new IllegalStateException("nothing to remove");
			PositionalDoublyLinkedList.this.remove(recent);
			recent = null;
		}
		
	}
	
	private class PositionIterable implements Iterable<Positions<E>>{

		@Override
		public Iterator<Positions<E>> iterator() {
			return new PositionIterator();
		}
		
	}
	
	public Iterable<Positions<E>> positions() {
		return new PositionIterable();
	}
	
	private class ElementIterator implements Iterator<E> {

		Iterator<Positions<E>> posIterator = new PositionIterator();
		
		@Override
		public boolean hasNext() {return posIterator.hasNext();}

		@Override
		public E next() throws NoSuchElementException {return posIterator.next().getElement();}

		@Override
		public void remove() throws IllegalStateException {posIterator.remove();}
		
	}


	@Override
	public Iterator<E> iterator() {return new ElementIterator();}
	
}


