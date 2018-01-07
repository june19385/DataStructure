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

import java.util.HashSet;
import java.util.Set;

import Lists.PositionalDoublyLinkedList;
import Lists.PositionalList;
import Lists.Positions;
import Maps.Maps;
import Maps.ProbeHashMaps;
import PriorityQueues.AdaptablePriorityQueue;
import PriorityQueues.Entry;
import PriorityQueues.HeapAdaptablePriorityQueue;
import PriorityQueues.HeapPriorityQueue;
import PriorityQueues.PriorityQueues;
import Stacks.LinkedStack;
import Stacks.Stacks;

public class GraphAlgorithm {
	
	public <V,E> void DFS(Graph<V,E> g, Vertex<V> u, Set<Vertex<V>> known, Maps<Vertex<V>,Edge<E>> forest) {
		known.add(u);
		for(Edge<E> e : g.outgoingEdges(u)) {
			Vertex<V> v = g.opposite(u, e);
			if(!known.contains(v)) {
				forest.put(v, e);
				DFS(g,v,known,forest);
			}
		}
	}
	
	public <V,E> PositionalList<Edge<E>> constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v, Maps<Vertex<V>,Edge<E>> forest) {
		PositionalList<Edge<E>> path = new PositionalDoublyLinkedList<>();
		if(forest.get(v)!=null) {
			Vertex<V> walk = v;
			while(walk!=u) {
				Edge<E> edge = forest.get(walk);
				path.addFirst(edge);
				walk = g.opposite(walk, edge);
			}
		}
		return path;
	}
	
	public <V,E> Maps<Vertex<V>,Edge<E>> DFSComplete(Graph<V,E> g) {
		Set<Vertex<V>> known = new HashSet<>();
		Maps<Vertex<V>,Edge<E>> forest = new ProbeHashMaps<>();
		for(Vertex<V> u : g.vertices()) {
			if(!known.contains(u))
				DFS(g,u,known,forest);
		}
		return forest;
	}
	
	public <V,E> void BFS(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, Maps<Vertex<V>,Edge<E>> forest) {
		PositionalList<Vertex<V>> level = new PositionalDoublyLinkedList<>();
		known.add(s);
		level.addLast(s);
		while(!level.isEmpty()) {
			PositionalList<Vertex<V>> nextLevel = new PositionalDoublyLinkedList<>();
			for(Vertex<V> u : level) {
				for(Edge<E> e : g.outgoingEdges(u)) {
					Vertex<V> v = g.opposite(u, e);
					if(!known.contains(v)) {
						known.add(v);
						forest.put(v, e);
						nextLevel.addLast(v);
					}
				}
			}
			level = nextLevel;
		}
	}
	
	//Floyd-Warshall algorithm
	public <V,E> void transitiveClosure(Graph<V,E> g) {
		for(Vertex<V> k : g.vertices()) {
			for(Vertex<V> i : g.vertices()) {
				if(i!=k&&g.getEdge(i, k)!=null) {
					for(Vertex<V> j : g.vertices()) {
						if(i!=j && j!=k && g.getEdge(k, j)!=null) {
							if(g.getEdge(i, j)==null)
								g.insertEdge(i, j, null);
						}
					}
				}
			}
		}
	}
	
	public <V,E> PositionalList<Vertex<V>> topologicalSort(Graph<V,E> g) {
		PositionalList<Vertex<V>> topo = new PositionalDoublyLinkedList<>();
		Stacks<Vertex<V>> ready = new LinkedStack<>();
		Maps<Vertex<V>,Integer> inCount = new ProbeHashMaps<>();
		for(Vertex<V> u : g.vertices()) {
			inCount.put(u, g.inDegree(u));
			if(inCount.get(u)==0)
				ready.push(u);
		}
		while(!ready.isEmpty()) {
			Vertex<V> u = ready.pop();
			topo.addLast(u);
			for(Edge<E> e : g.outgoingEdges(u)) {
				Vertex<V> v = g.opposite(u, e);
				inCount.put(v, inCount.get(v)-1);
				if(inCount.get(v)==0)
					ready.push(v);
			}
		}
		return topo;
	}
	
	//Dijkstra's algorithm
	public <V> Maps<Vertex<V>, Integer> shortestPathLengths(Graph<V,Integer> g, Vertex<V> src) {
		Maps<Vertex<V>, Integer> d = new ProbeHashMaps<>();
		Maps<Vertex<V>, Integer> cloud = new ProbeHashMaps<>();
		AdaptablePriorityQueue<Integer,Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
		Maps<Vertex<V>,Entry<Integer,Vertex<V>>> pqTokens = new ProbeHashMaps<>();
		for(Vertex<V> v : g.vertices()) {
			if(v==src)
				d.put(v, 0);
			else
				d.put(v, Integer.MAX_VALUE);
			pqTokens.put(v, pq.insert(d.get(v),v));
		}
		while(!pq.isEmpty()) {
			Entry<Integer,Vertex<V>> entry = pq.removeMin();
			int key = entry.getKey();
			Vertex<V> u = entry.getValue();
			cloud.put(u, key);
			pqTokens.remove(u);
			for(Edge<Integer> e : g.outgoingEdges(u)) {
				Vertex<V> v = g.opposite(u, e);
				if(cloud.get(v)==null) {
					int wgt = e.getElement();
					if(d.get(u)+wgt<d.get(v)) {
						d.put(v, d.get(u)+wgt);
						pq.replaceKey(pqTokens.get(v), d.get(v));
					}
				}
			}
		}
		return cloud;
	}
	
	public <V> Maps<Vertex<V>, Edge<Integer>> spTree(Graph<V,Integer> g, Vertex<V> s, Maps<Vertex<V>,Integer> d) {
		Maps<Vertex<V>,Edge<Integer>> tree = new ProbeHashMaps<>();
		for(Vertex<V> v : d.keySet()) {
			if(v!=s) {
				for(Edge<Integer> e : g.incomingEdges(v)) {
					Vertex<V> u = g.opposite(v, e);
					int wgt = e.getElement();
					if(d.get(v)==d.get(u)+wgt)
						tree.put(v, e);
				}
			}
		}
		return tree;
	}
	
	public <V> PositionalList<Edge<Integer>> MST(Graph<V,Integer> g) {
		PositionalList<Edge<Integer>> tree = new PositionalDoublyLinkedList<>();
		PriorityQueues<Integer,Edge<Integer>> pq = new HeapPriorityQueue<>();
		Partition<Vertex<V>> forest = new Partition<>();
		Maps<Vertex<V>, Positions<Vertex<V>>> positions = new ProbeHashMaps<>();
		
		for(Vertex<V> v : g.vertices())
			positions.put(v, forest.makeCluster(v));
		
		for(Edge<Integer> e : g.edges())
			pq.insert(e.getElement(), e);
		
		int size = g.numVertices();
		
		while(tree.size()!=size-1 && !pq.isEmpty()) {
			Entry<Integer,Edge<Integer>> entry = (Entry<Integer, Edge<Integer>>) pq.removeMin();
			Edge<Integer> edge = entry.getValue();
			Vertex<V>[] endpoints = g.endVertices(edge);
			Positions<Vertex<V>> a = forest.find(positions.get(endpoints[0]));
			Positions<Vertex<V>> b = forest.find(positions.get(endpoints[1]));
			if(a!=b) {
				tree.addLast(edge);
				forest.union(a, b);
			}
		}
		return tree;
	}
}
