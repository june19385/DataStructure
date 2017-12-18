package Lists;

import java.util.NoSuchElementException;

public interface Iterators<E> {
	boolean hasNext();
	E next() throws NoSuchElementException;
	void remove() throws IllegalStateException;
}
