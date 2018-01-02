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
