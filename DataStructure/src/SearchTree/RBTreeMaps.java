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
import Maps.Entry;

public class RBTreeMaps<K, V> extends TreeMaps<K, V> {
	
	public RBTreeMaps() {super();}
	
	public RBTreeMaps(Comparator<K> comp) {super(comp);}
	
	private boolean isBlack(Positions<Entry<K,V>> p) {return tree.getAux(p)==0;}
	private boolean isRed(Positions<Entry<K,V>> p) {return tree.getAux(p)==1;}
	private void makeBlack(Positions<Entry<K,V>> p) {tree.setAux(p, 0);}
	private void makeRed(Positions<Entry<K,V>> p) {tree.setAux(p, 1);}
	private void setColor(Positions<Entry<K,V>> p, boolean toRed) {
		tree.setAux(p, toRed ? 1 : 0);
	}
	
	private void resolveRed(Positions<Entry<K,V>> p) {
		Positions<Entry<K,V>> parent, uncle, middle, grand;
		parent = parent(p);
		if(isRed(parent)) {
			uncle = sibling(parent);
			if(isBlack(uncle)) {
				middle = restructure(p);
				makeBlack(middle);
				makeRed(left(middle));
				makeRed(right(middle));
			}
			else {
				makeBlack(parent);
				makeBlack(uncle);
				grand = parent(parent);
				if(!isRoot(grand)) {
					makeRed(grand);
					resolveRed(grand);
				}
			}
		}
	}
	
	private void remedyDoubleBlack(Positions<Entry<K,V>> p) {
		Positions<Entry<K,V>> z = parent(p);
		Positions<Entry<K,V>> y = sibling(p);
		if(isBlack(y)) {
			if(isRed(left(y))||isRed(right(y))) {
				Positions<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
				Positions<Entry<K,V>> middle = restructure(x);
				setColor(middle,isRed(z));
				makeBlack(left(middle));
				makeBlack(right(middle));
			}
			else {
				makeRed(y);
				if(isRed(z)) makeBlack(z);
				else if(!isRoot(z)) remedyDoubleBlack(z);
			}
		}
		else {
			rotate(y);
			makeBlack(y);
			makeRed(z);
			remedyDoubleBlack(p);
		}
	}
	
	protected void rebalanceInsert(Positions<Entry<K,V>> p) {
		if(!isRoot(p)) {
			makeRed(p);
			resolveRed(p);
		}
	}
	
	protected void rebalanceDelete(Positions<Entry<K,V>> p) {
		if(isRed(p))
			makeBlack(p);
		else if(!isRoot(p)) {
			Positions<Entry<K,V>> sib = sibling(p);
			if(isInternal(sib)&&(isBlack(sib)||isInternal(left(sib))))
				remedyDoubleBlack(p);
		}
	}
}
