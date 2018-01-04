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

package SearchTree;

import java.util.Comparator;

import Lists.ArrayLists;
import Lists.Positions;
import Maps.AbstractSortedMaps;
import Maps.Entry;
import Trees.LinkedBinaryTree;

public class TreeMaps<K, V> extends AbstractSortedMaps<K, V> {

	protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>> {
		protected static class BSTNode<E> extends Node<E> {
			int aux = 0;
			
			public BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
				super(e, parent, leftChild, rightChild);
			}
			
			public int getAux() {return aux;}
			public void setAux(int value) {aux = value;}
		}
		
		public int getAux(Positions<Entry<K,V>> p) {
			return ((BSTNode<Entry<K,V>>)p).getAux();
		}
		
		public void setAux(Positions<Entry<K,V>> p, int value) {
			((BSTNode<Entry<K,V>>)p).setAux(value);
		}
		
		protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left, Node<Entry<K,V>> right) {
			return new BSTNode<>(e,parent,left,right);
		}
		
		private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild) {
			child.setParent(parent);
			if(makeLeftChild) parent.setLeft(child);
			else parent.setRight(child);
		}
		
		public void rotate(Positions<Entry<K,V>> p) {
			Node<Entry<K,V>> x = validate(p);
			Node<Entry<K,V>> y = x.getParent();
			Node<Entry<K,V>> z = y.getParent();
			if(z==null) {
				root = x;
				x.setParent(null);
			}
			else relink(z,x,y==z.getLeft());
			if(x==y.getLeft()) {
				relink(y,x.getRight(),true);
				relink(x,y,false);
			}
			else {
				relink(y,x.getLeft(),false);
				relink(x,y,true);
			}
		}
		
		public Positions<Entry<K,V>> restructure(Positions<Entry<K,V>> x) {
			Positions<Entry<K,V>> y = parent(x);
			Positions<Entry<K,V>> z = parent(y);
			if((x==right(y))==(y==right(z))) {
				rotate(y);
				return y;
			}
			else {
				rotate(x);
				rotate(x);
				return x;
			}
		}
	}
	
	protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();
	
	public TreeMaps() {
		super();
		tree.addRoot(null);
	}
	
	public TreeMaps(Comparator<K> comp) {
		super(comp);
		tree.addRoot(null);
	}
	
	@Override
	public int size() {
		return (tree.size()-1)/2;
	}
	
	private void expandExternal(Positions<Entry<K,V>> p, Entry<K,V> entry) {
		tree.set(p, entry);
		tree.addLeft(p, null);
		tree.addRight(p, null);
	}
	
	protected Positions<Entry<K,V>> root() {return tree.root();}
	
	protected Positions<Entry<K, V>> parent(Positions<Entry<K, V>> p) { 
		return tree.parent(p); 
	} 
 
	protected Positions<Entry<K, V>> left(Positions<Entry<K, V>> p) { 
		return tree.left(p); 
	} 
	 
	protected Positions<Entry<K, V>> right(Positions<Entry<K, V>> p) { 
			return tree.right(p); 
	} 
 
	protected Positions<Entry<K, V>> sibling(Positions<Entry<K, V>> p) { 
			return tree.sibling(p); 
	} 
 
	protected boolean isRoot(Positions<Entry<K, V>> p) { 
			return tree.isRoot(p); 
	} 
 
	protected boolean isExternal(Positions<Entry<K, V>> p) { 
			return tree.isExternal(p); 
	} 

	protected boolean isInternal(Positions<Entry<K, V>> p) { 
			return tree.isInternal(p); 
	} 

	protected void set(Positions<Entry<K, V>> p, Entry<K, V> e) { 
			tree.set(p, e); 
	} 

	protected Entry<K, V> remove(Positions<Entry<K, V>> p) { 
			return tree.remove(p); 
	} 

	protected void rotate(Positions<Entry<K, V>> p) { 
			tree.rotate(p); 
	} 
		 
	protected Positions<Entry<K, V>> restructure(Positions<Entry<K, V>> x) { 
			return tree.restructure(x); 
	} 


	private Positions<Entry<K,V>> treeSearch(Positions<Entry<K,V>> p, K key) {
		if(isExternal(p))
			return p;
		int comp = compare(key,p.getElement());
		if(comp==0)
			return p;
		else if(comp<0)
			return treeSearch(left(p),key);
		else
			return treeSearch(right(p),key);
	}
	
	// empty method
	protected void rebalanceInsert(Positions<Entry<K,V>> p) { }

	protected void rebalanceDelete(Positions<Entry<K,V>> p) { }

	protected void rebalanceAccess(Positions<Entry<K,V>> p) { }
	
	@Override
	public V get(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		rebalanceAccess(p);
		if(isExternal(p)) return null;
		return p.getElement().getValue();
	}

	@Override
	public V put(K key, V value) throws IllegalArgumentException {
		checkKey(key);
		Entry<K,V> newEntry = new MapEntry<>(key,value);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isExternal(p)) {
			expandExternal(p,newEntry);
			rebalanceInsert(p);
			return null;
		}
		else {
			V old = p.getElement().getValue();
			set(p,newEntry);
			rebalanceAccess(p);
			return old;
		}
	}
	
	protected Positions<Entry<K,V>> treeMax(Positions<Entry<K,V>> p) {
		Positions<Entry<K,V>> walk = p;
		while(isInternal(walk))
			walk = right(walk);
		return parent(walk);
	}
	
	protected Positions<Entry<K,V>> treeMin(Positions<Entry<K,V>> p) {
		Positions<Entry<K,V>> walk = p;
		while(isInternal(walk))
			walk = left(walk);
		return parent(walk);
	}

	@Override
	public V remove(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isExternal(p)) {
			rebalanceAccess(p);
			return null;
		}
		else {
			V old = p.getElement().getValue();
			if(isInternal(left(p))&&isInternal(right(p))) {
				Positions<Entry<K,V>> replacement = treeMax(left(p));
				set(p,replacement.getElement());
				p = replacement;
			}
			Positions<Entry<K,V>> leaf = (isExternal(left(p))?left(p):right(p));
			Positions<Entry<K,V>> sib = sibling(leaf);
			remove(leaf);
			remove(p);
			rebalanceDelete(sib);
			return old;
		}
	}
	

	@Override
	public Entry<K, V> firstEntry() {
		if(isEmpty()) return null;
		return treeMin(root()).getElement();
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isInternal(p)) return p.getElement();
		while(!isRoot(p)) {
			if(p==left(parent(p))) return parent(p).getElement();
			else p = parent(p);
		}
		return null;
	}

	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isInternal(p)&&isInternal(right(p))) return treeMin(right(p)).getElement();
		while(!isRoot(p)) {
			if(p==left(parent(p))) return parent(p).getElement();
			else p = parent(p);
		}
		return null;
	}

	@Override
	public Entry<K, V> lastEntry() {
		if(isEmpty()) return null;
		return treeMax(root()).getElement();
	}

	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isInternal(p)) return p.getElement();
		while(!isRoot(p)) {
			if(p==right(parent(p))) return parent(p).getElement();
			else p = parent(p);
		}
		return null;
	}

	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Positions<Entry<K,V>> p = treeSearch(root(),key);
		if(isInternal(p)&&isInternal(left(p))) return treeMax(left(p)).getElement();
		while(!isRoot(p)) {
			if(p==right(parent(p))) return parent(p).getElement();
			else p = parent(p);
		}
		return null;
	}
	
	private void subMapRecurse(K fromKey, K toKey, Positions<Entry<K,V>> p, ArrayLists<Entry<K,V>> buffer) {
		if(isInternal(p)) {
			if(compare(p.getElement(),fromKey)<0)
				subMapRecurse(fromKey,toKey,right(p),buffer);
			else {
				subMapRecurse(fromKey,toKey,left(p),buffer);
				if(compare(p.getElement(),toKey)<0) {
					buffer.add(p.getElement());
					subMapRecurse(fromKey,toKey,right(p),buffer);
				}
			}
		}
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>(size());
		if(compare(fromKey,toKey)<0)
			subMapRecurse(fromKey,toKey,root(),buffer);
		return buffer;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayLists<Entry<K,V>> buffer = new ArrayLists<>(size());
		for(Positions<Entry<K,V>> p : tree.inorder())
			if(isInternal(p)) buffer.add(p.getElement());
		return buffer;
	}


}
