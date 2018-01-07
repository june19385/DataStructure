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

package Graphs;

import Lists.Positions;

public class Partition<E> {

	private class Locator<E> implements Positions<E> {
		public E element;
		public int size;
		public Locator<E> parent;
		public Locator(E elem) {
			element = elem;
			size = 1;
			parent = this;
		}
		@Override
		public E getElement() throws IllegalStateException {
			return element;
		}
		
		private boolean validate(Partition<E> universe) {
			return (Partition.this == universe);
		}
	}
	
	public Positions<E> makeCluster(E e) {
		return new Locator<E>(e);
	}
	
	public Positions<E> find(Positions<E> p) {
		Locator<E> loc = validate(p);
		if(loc.parent!=loc)
			loc.parent = (Locator<E>) find(loc.parent);
		return loc.parent;
	}
	
	public void union(Positions<E> p, Positions<E> q) {
		Locator<E> a = (Locator<E>) find(p);
		Locator<E> b = (Locator<E>) find(q);
		if(a!=b) {
			if(a.size>b.size) {
				b.parent = a;
				a.size+=b.size;
			}
			else {
				a.parent = b;
				b.size+=a.size;
			}
		}
	}
	
	private Locator<E> validate(Positions<E> pos) {
		if(!(pos instanceof Locator)) throw new IllegalArgumentException("Invalid position");
		Locator<E> loc = (Locator<E>) pos;
		if(!loc.validate(this)) throw new IllegalArgumentException("Position does not belong to this structure");
		return loc;
	}
}
