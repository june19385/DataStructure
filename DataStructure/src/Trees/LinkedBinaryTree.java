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

import Lists.Positions;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

	private static class Node<E> implements Positions<E>{
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		@Override
		public E getElement() throws IllegalStateException {return element;}
		public Node<E> getParent() {return parent;}
		public Node<E> getLeft() {return left;}
		public Node<E> getRight() {return right;}
		
		public void setElement(E e) {element = e;}
		public void setParent(Node<E> parentNode) {parent = parentNode;}
		public void setLeft(Node<E> leftChild) {left = leftChild;}
		public void setRight(Node<E> rightChild) {right = rightChild;}
	}
	
	private Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e,parent,left,right);
	}
	
	protected Node<E> root;
	private int size;
	
	public LinkedBinaryTree() {
		root = null;
		size = 0;
	}
	
	private Node<E> validate(Positions<E> p) throws IllegalArgumentException {
		if(!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
		Node<E> node = (Node<E>) p;
		if(node.getParent()==node) throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}
	
	@Override
	public Positions<E> root() {
		return root;
	}
	
	@Override
	public Positions<E> left(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	@Override
	public Positions<E> right(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}

	@Override
	public Positions<E> parent(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	@Override
	public int size() {
		return size;
	}

	public Positions<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root = createNode(e,null,null,null);
		size = 1;
		return root;
	}
	
	public Positions<E> addLeft(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getLeft()==null) throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e,parent,null,null);
		parent.setLeft(child);
		size++;
		return child;
	}
	
	public Positions<E> addRight(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if(parent.getRight()==null) throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e,parent,null,null);
		parent.setRight(child);
		size++;
		return child;
	}
	
	public E set(Positions<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}
	
	public void attach(Positions<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
		size+=t1.size()+t2.size();
		if(!t1.isEmpty()) {
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		if(!t2.isEmpty()) {
			t2.root.setParent(node);
			node.setRight(t2.root);
			t2.root = null;
			t2.size = 0;
		}
	}
	
	public E remove(Positions<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(numChildren(p)==2) throw new IllegalArgumentException("p has two children");
		Node<E> child = (node.getLeft()!=null ? node.getLeft() : node.getRight());
		if(child!=null) child.setParent(node.getParent());
		if(node==root) root = child;
		else {
			Node<E> parent = node.getParent();
			if(node==parent.getLeft()) parent.setLeft(child);
			else parent.setRight(child);
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);
		return temp;
	}
	
	@Override
	public Iterable<Positions<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
