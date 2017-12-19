package Trees;

import java.util.Iterator;

import Lists.ArrayLists;
import Lists.Lists;
import Lists.Positions;
import Queues.LinkedQueue;
import Queues.Queues;

public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public boolean isInternal(Positions<E> p) throws IllegalArgumentException {
		return numChildren(p)>0;
	}

	@Override
	public boolean isExternal(Positions<E> p) throws IllegalArgumentException {
		return numChildren(p)==0;
	}

	@Override
	public boolean isRoot(Positions<E> p) throws IllegalArgumentException {
		return p==root();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int depth(Positions<E> p) {
		if(isRoot(p)) return 0;
		else return 1+depth(parent(p));
	}
	
	private int heightBad() {
		int h = 0;
		for(Positions<E> p : positions()) {
			if(isExternal(p)) {
				h = Math.max(h, depth(p));
			}
		}
		return h;
	}
	
	public int height(Positions<E> p) {
		int h=0;
		for(Positions<E> c : children(p)) {
			h = Math.max(h, 1+height(c));
		}
		return h;
	}
	
	private class ElementIterator implements Iterator<E> {
		
		Iterator<Positions<E>> posIterator = positions().iterator();
		
		@Override
		public boolean hasNext() {return posIterator.hasNext();}

		@Override
		public E next() {return posIterator.next().getElement();}
		
		public void remove() {posIterator.remove();}
		
	}
	
	private void preorderSubtree(Positions<E> p, Lists<Positions<E>> snapshot) {
		snapshot.add(p);
		for(Positions<E> c : children(p)) {
			preorderSubtree(c,snapshot);
		}
	}
	
	public Iterable<Positions<E>> preorder() {
		Lists<Positions<E>> snapshot = new ArrayLists<>();
		if(!isEmpty()) preorderSubtree(root(),snapshot);
		return snapshot;
	}
	
	private void postorderSubtree(Positions<E> p, Lists<Positions<E>> snapshot) {
		for(Positions<E> c : children(p)) {
			preorderSubtree(c,snapshot);
		}
		snapshot.add(p);
	}
	
	public Iterable<Positions<E>> postorder() {
		Lists<Positions<E>> snapshot = new ArrayLists<>();
		if(!isEmpty()) postorderSubtree(root(),snapshot);
		return snapshot;
	}
	
	public Iterable<Positions<E>> breathfirst() {
		Lists<Positions<E>> snapshot = new ArrayLists<>();
		if(!isEmpty()) {
			Queues<Positions<E>> fringe = new LinkedQueue<>();
			fringe.enqueue(root());
			while(!fringe.isEmpty()) {
				Positions<E> p = fringe.dequeue();
				snapshot.add(p);
				for(Positions<E> c : children(p))
					fringe.enqueue(c);
			}
		}
		return snapshot;
	}
}
