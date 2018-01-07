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

public class SplayTreeMaps<K, V> extends TreeMaps<K, V> {

	public SplayTreeMaps() {super();}
	
	public SplayTreeMaps(Comparator<K> comp) {super(comp);}
	
	private void splay(Positions<Entry<K,V>> p) {
		while(!isRoot(p)) {
			Positions<Entry<K,V>> parent = parent(p);
			Positions<Entry<K,V>> grand = parent(parent);
			if(grand==null) rotate(p);
			else if((parent==left(grand)) == (p==left(parent))) {
				rotate(parent);
				rotate(p);
			}
			else {
				rotate(p);
				rotate(p);
			}
		}
	}
	
	protected void rebalanceAccess(Positions<Entry<K,V>> p) {
		if(isExternal(p)) p = parent(p);
		if(p!=null) splay(p);
	}
	
	protected void rebalanceInsert(Positions<Entry<K,V>> p) {
		splay(p);
	}
	
	protected void rebalanceDelete(Positions<Entry<K,V>> p) {
		if(!isRoot(p)) splay(parent(p));
	}
}
