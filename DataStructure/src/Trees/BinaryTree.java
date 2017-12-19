package Trees;

import Lists.Positions;

public interface BinaryTree<E> extends Tree<E> {
	Positions<E> left(Positions<E> p) throws IllegalArgumentException;
	Positions<E> right(Positions<E> p) throws IllegalArgumentException;
	Positions<E> sibling(Positions<E> p) throws IllegalArgumentException;
}
