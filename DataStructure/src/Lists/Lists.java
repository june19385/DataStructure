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

import java.lang.Iterable;
import java.util.Iterator;

public interface Lists<E> extends Iterable<E>{
	int size();
	boolean isEmpty();
	E get(int i) throws IndexOutOfBoundsException;
	E set(int i, E e) throws IndexOutOfBoundsException;
	void add(int i, E e) throws IndexOutOfBoundsException;
	void add(E e) throws IndexOutOfBoundsException;
	E remove(int i) throws IndexOutOfBoundsException;
	public Iterator<E> iterator();
}
