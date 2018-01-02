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
import java.lang.Iterable;
import Lists.Positions;

public interface Tree<E> extends Iterable<E> {
	Positions<E> root();
	Positions<E> parent(Positions<E> p) throws IllegalArgumentException;
	Iterable<Positions<E>> children(Positions<E> p) throws IllegalArgumentException;
	int numChildren(Positions<E> p) throws IllegalArgumentException;
	boolean isInternal(Positions<E> p) throws IllegalArgumentException;
	boolean isExternal(Positions<E> p) throws IllegalArgumentException;
	boolean isRoot(Positions<E> p) throws IllegalArgumentException;
	int size();
	boolean isEmpty();
	Iterator<E> iterator();
	Iterable<Positions<E>> positions();
}
