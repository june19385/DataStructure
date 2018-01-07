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

import Lists.PositionalDoublyLinkedList;
import Lists.PositionalList;
import Lists.Positions;
import Maps.Maps;
import Maps.ProbeHashMaps;

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {

	private class InnerVertex<V> implements Vertex<V> {
		private V element;
		private Positions<Vertex<V>> pos;
		private Maps<Vertex<V>,Edge<E>> outgoing, incoming;
		
		public InnerVertex(V elem, boolean graphIsDirected) {
			element = elem;
			outgoing = new ProbeHashMaps<>();
			if(graphIsDirected) incoming = new ProbeHashMaps<>();
			else incoming = outgoing;
		}
		
		@Override
		public V getElement() {return element;}
		public void setPosition(Positions<Vertex<V>> p) {pos = p;}
		public Positions<Vertex<V>> getPosition() {return pos;}
		public Maps<Vertex<V>, Edge<E>> getOutgoing() {return outgoing;}
		public Maps<Vertex<V>, Edge<E>> getIncoming() {return incoming;}
		public boolean validate(Graph<V,E> graph) {return((this==graph)&&(pos!=null));}
	}
	
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Positions<Edge<E>> pos;
		private Vertex<V>[] endpoints;
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[] {u,v};
		}
		@Override
		public E getElement() {return element;}
		public Vertex<V>[] getEndpoints() {return endpoints;}
		public void setPosition(Positions<Edge<E>> p) {pos = p;}
		public Positions<Edge<E>> getPosition() {return pos;}
		public boolean validate(Graph<V,E> graph) {return((this==graph)&&(pos!=null));}
	}
	
	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices = new PositionalDoublyLinkedList<>();
	private PositionalList<Edge<E>> edges = new PositionalDoublyLinkedList<>();
	
	public AdjacencyMapGraph(boolean directed) {isDirected = directed;}
	
	@Override
	public int numVertices() {
		return vertices.size();
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		return vertices;
	}

	@Override
	public int numEdges() {
		return edges.size();
	}

	@Override
	public Iterable<Edge<E>> edges() {
		return edges;
	}

	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);
	}

	@Override
	public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if(endpoints[0]==v) return endpoints[1];
		else if(endpoints[1]==v) return endpoints[0];
		else throw new IllegalArgumentException("v is not incident to this edge");
	}

	@Override
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().size();
	}

	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values();
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values();
	}

	@Override
	public Vertex<V> insertVertex(V element) {
		InnerVertex<V> v = new InnerVertex<>(element,isDirected);
		v.setPosition(vertices.addLast(v));
		return v;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
		if(getEdge(u,v)==null) {
			InnerEdge<E> e = new InnerEdge<>(u,v,element);
			e.setPosition(edges.addLast(e));
			InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		}
		else throw new IllegalArgumentException("Edge from u to v exists");
	}

	@Override
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		for(Edge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for(Edge<E> e : vert.getIncoming().values())
			removeEdge(e);
		vertices.remove(vert.getPosition());
	}

	@Override
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> ed = validate(e);
		InnerVertex<V>[] verts = (InnerVertex<V>[]) ed.getEndpoints();
		verts[0].getOutgoing().remove(verts[1]);
		verts[1].getIncoming().remove(verts[0]);
		edges.remove(ed.getPosition());
	}
	
	private InnerVertex<V> validate(Vertex<V> v) {
		if(!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
		InnerVertex<V> vert = (InnerVertex<V>) v;
		if(!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
		return vert;
	}
	
	private InnerEdge<E> validate(Edge<E> e) {
		if(!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
		InnerEdge<E> ed = (InnerEdge<E>) e;
		if(ed.validate(this)) throw new IllegalArgumentException("Invalid edge");
		return ed;
	}

}
