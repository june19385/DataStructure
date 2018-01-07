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

import Lists.Positions;
import PriorityQueues.Entry;

public class AVLTreeMaps<K, V> extends TreeMaps<K, V> {
	
	public AVLTreeMaps() {super();}
	
	public AVLTreeMaps(Comparator<K> comp) {super(comp);}
	
	protected int height(Positions<Entry<K,V>> p) {
		return tree.getAux(p);
	}
	
	protected void recomputeHeight(Positions<Entry<K,V>> p) {
		tree.setAux(p, 1+Math.max(height(left(p)), height(right(p))));
	}
	
	protected boolean isBalanced(Positions<Entry<K,V>> p) {
		return Math.abs(height(left(p))-height(right(p)))<=1;
	}
	
	protected Positions<Entry<K,V>> tallerChild(Positions<Entry<K,V>> p) {
		if(height(left(p))>height(right(p))) return left(p);
		if(height(left(p))<height(right(p))) return right(p);
		if(isRoot(p)) return left(p);
		if(p==left(parent(p))) return left(p);
		else return right(p);
	}
	
	protected void rebalance(Positions<Entry<K,V>> p) {
		int oldHeight,newHeight;
		do {
			oldHeight = height(p);
			if(!isBalanced(p)) {
				p = restructure(tallerChild(tallerChild(p)));
				recomputeHeight(left(p));
				recomputeHeight(right(p));
			}
			recomputeHeight(p);
			newHeight = height(p);
			p = parent(p);
		} while(oldHeight!=newHeight&&p!=null);
	}
	
	protected void rebalanceInsert(Positions<Entry<K,V>> p) {
		rebalance(p);
	}
	
	protected void rebalanceDelete(Positions<Entry<K,V>> p) {
		if(!isRoot(p))
			rebalance(p);
	}
}
