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
	
	public Iterator<E> iterator() {return new ElementIterator();}
	
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
