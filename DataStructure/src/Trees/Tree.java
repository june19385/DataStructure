package Trees;

import java.util.Iterator;
import java.lang.Iterable;
import Lists.Positions;

public interface Tree<E> extends Iterable<E> {
	Positions<E> root();
	Positions<E> parent(Positions<E> p) throws IllegalArgumentException;
	Iterable<Positions<E>> children(Positions<E> p) throws IllegalArgumentException;
	int numChildren(Positions<E> p) throws IllegalArgumentException;
	boolean isInternal(Positions<E> p) throws IllegalArgumentException;
	boolean isExternal(Positions<E> p) throws IllegalArgumentException;
	boolean isRoot(Positions<E> p) throws IllegalArgumentException;
	int size();
	boolean isEmpty();
	Iterator<E> iterator();
	Iterable<Positions<E>> positions();
}
