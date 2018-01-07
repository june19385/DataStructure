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

public interface Graph<V, E> {
	int numVertices();
	Iterable<Vertex<V>> vertices();
	int numEdges();
	Iterable<Edge<E>> edges();
	Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;
	Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException;
	Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;
	int outDegree(Vertex<V> v) throws IllegalArgumentException;
	int inDegree(Vertex<V> v) throws IllegalArgumentException;
	Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;
	Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;
	Vertex<V> insertVertex(V element);
	Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;
	void removeVertex(Vertex<V> v) throws IllegalArgumentException;
	void removeEdge(Edge<E> e) throws IllegalArgumentException;
}
