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

package Lists;

import java.util.Iterator;

public interface PositionalList<E> extends Iterable<E>{
	int size();
	boolean isEmpty();
	Positions<E> first();
	Positions<E> last();
	Positions<E> before(Positions<E> p) throws IllegalArgumentException;
	Positions<E> after(Positions<E> p) throws IllegalArgumentException;
	Positions<E> addFirst(E e);
	Positions<E> addLast(E e);
	Positions<E> addBefore(Positions<E> p, E e) throws IllegalArgumentException;
	Positions<E> addAfter(Positions<E> p, E e) throws IllegalArgumentException;
	E set(Positions<E> p, E e) throws IllegalArgumentException;
	E remove(Positions<E> p) throws IllegalArgumentException;
	public Iterator<E> iterator();
	public Iterable<Positions<E>> positions();
}
