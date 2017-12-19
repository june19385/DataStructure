package Trees;

import Lists.Positions;
import Lists.ArrayLists;
import Lists.Lists;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

	@Override
	public Iterable<Positions<E>> children(Positions<E> p) throws IllegalArgumentException {
		Lists<Positions<E>> snapshot = new ArrayLists<>(2);
		if(left(p)!=null) snapshot.add(left(p));
		if(right(p)!=null) snapshot.add(right(p));
		return snapshot;
	}

	@Override
	public int numChildren(Positions<E> p) throws IllegalArgumentException {
		int count = 0;
		if(left(p)!=null) count++;
		if(right(p)!=null) count++;
		return count;
	}

	@Override
	public Positions<E> sibling(Positions<E> p) throws IllegalArgumentException {
		Positions<E> parent = parent(p);
		if(parent==null) return null;
		if(p==left(parent)) return right(parent);
		else return left(parent);
	}
	
	private void inorderSubtree(Positions<E> p, Lists<Positions<E>> snapshot) {
		if(left(p)!=null)
			inorderSubtree(left(p),snapshot);
		snapshot.add(p);
		if(right(p)!=null)
			inorderSubtree(right(p),snapshot);
	}
	
	public Iterable<Positions<E>> inorder() {
		Lists<Positions<E>> snapshot = new ArrayLists<>();
		if(!isEmpty()) inorderSubtree(root(),snapshot);
		return snapshot;
	}
	
	public Iterable<Positions<E>> positions() {
		return inorder();
	}

}
