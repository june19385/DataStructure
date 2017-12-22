package Lists;

import java.util.Iterator;

public interface PositionalList<E> extends Iterable<E>{
	int size();
	boolean isEmpty();
	Positions<E> first();
	Positions<E> last();
	Positions<E> before(Positions<E> p) throws IllegalArgumentException;
	Positions<E> after(Positions<E> p) throws IllegalArgumentException;
	Positions<E> addFirst(E e);
	Positions<E> addLast(E e);
	Positions<E> addBefore(Positions<E> p, E e) throws IllegalArgumentException;
	Positions<E> addAfter(Positions<E> p, E e) throws IllegalArgumentException;
	E set(Positions<E> p, E e) throws IllegalArgumentException;
	E remove(Positions<E> p) throws IllegalArgumentException;
	public Iterator<E> iterator();
	public Iterable<Positions<E>> positions();
}
