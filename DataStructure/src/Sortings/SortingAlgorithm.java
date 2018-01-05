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

package Sortings;

import java.util.Arrays;
import java.util.Comparator;

import Queues.LinkedQueue;
import Queues.Queues;

public class SortingAlgorithm {
	
	
	/*
	 * Array-Based MergeSort
	 */
	
	public <K> void merge(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
		int i=0, j=0;
		while(i+j<S.length) {
			if(j==S2.length||(i<S1.length&&comp.compare(S1[i], S2[j])<0))
				S[i+j] = S1[i++];
			else
				S[i+j] = S2[j++];
		}
	}
	
	public <K> void mergeSort(K[] S, Comparator<K> comp) {
		int n = S.length;
		if(n<2) return;
		int mid = n/2;
		K[] S1 = Arrays.copyOfRange(S, 0, mid);
		K[] S2 = Arrays.copyOfRange(S,mid,n);
		mergeSort(S1,comp);
		mergeSort(S2,comp);
		merge(S1,S2,S,comp);
	}
	
	/*
	 * LinkedList-Based MergeSort
	 */
	
	public <K> void merge(Queues<K> S1, Queues<K> S2, Queues<K> S, Comparator<K> comp) {
		while(!S1.isEmpty()&&!S2.isEmpty()) {
			if(comp.compare(S1.first(), S2.first())<0)
				S.enqueue(S1.dequeue());
			else
				S.enqueue(S2.dequeue());
		}
		while(!S1.isEmpty())
			S.enqueue(S1.dequeue());
		while(!S2.isEmpty())
			S.enqueue(S2.dequeue());
	}
	
	public <K> void mergeSort(Queues<K> S, Comparator<K> comp) {
		int n = S.size();
		if(n<2) return;
		Queues<K> S1 = new LinkedQueue<>();
		Queues<K> S2 = new LinkedQueue<>();
		while(S1.size()<n/2)
			S1.enqueue(S.dequeue());
		while(!S.isEmpty())
			S2.enqueue(S.dequeue());
		mergeSort(S1,comp);
		mergeSort(S2,comp);
		merge(S1,S2,S,comp);
	}
	
	/*
	 * Bottom-Up MergeSort
	 */
	public <K> void merge(K[] in, K[] out, Comparator<K> comp, int start, int inc) {
		int end1 = Math.min(start+inc, in.length);
		int end2 = Math.min(start+2*inc, in.length);
		int x = start;
		int y = start+inc;
		int z = start;
		while(x<end1&&y<end2) {
			if(comp.compare(in[x], in[y])<0)
				out[z++] = in[x++];
			else
				out[z++] = in[y++];
		}
		if(x<end1) System.arraycopy(in, x, out, z, end1-x);
		else if(x<end2) System.arraycopy(in, y, out, z, end2-y);
	}
	
	public <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {
		int n = orig.length;
		K[] src = orig;
		K[] dest = (K[]) new Object[n];
		K[] temp;
		for(int i=1;i<n;i*=2) {
			for(int j=0;j<n;j+=2*i)
				merge(src,dest,comp,j,i);
			temp = src;
			src = dest;
			dest = temp;
		}
		if(orig!=src)
			System.arraycopy(src, 0, orig, 0, n);
	}
	
	public <K> void quickSort(Queues<K> S, Comparator<K> comp) {
		int n = S.size();
		if(n<2) return;
		K pivot = S.first();
		Queues<K> L = new LinkedQueue<>();
		Queues<K> E = new LinkedQueue<>();
		Queues<K> G = new LinkedQueue<>();
		while(!S.isEmpty()) {
			K element = S.dequeue();
			int c = comp.compare(element, pivot);
			if(c<0) L.enqueue(element);
			else if(c==0) E.enqueue(element);
			else G.enqueue(element);
		}
		quickSort(L,comp);
		quickSort(G,comp);
		while(!L.isEmpty()) S.enqueue(L.dequeue());
		while(!E.isEmpty()) S.enqueue(E.dequeue());
		while(!G.isEmpty()) S.enqueue(G.dequeue());
	}
	
	public <K> void quickSortInPlace(K[] S, Comparator<K> comp, int a, int b) {
		if(a>=b) return;
		int left = a;
		int right = b-1;
		K pivot = S[b];
		K temp;
		while(left<=right) {
			while(left<=right&&comp.compare(S[left], pivot)<0) left++;
			while(left<=right&&comp.compare(S[right], pivot)>0) right--;
			if(left<=right) {
				temp = S[left];
				S[left] = S[right];
				S[right] = temp;
				left++;
				right--;
			}
		}
		temp = S[left];
		S[left] = S[b];
		S[b] = temp;
		quickSortInPlace(S,comp,a,left-1);
		quickSortInPlace(S,comp,left+1,b);
		
	}
	
	
}
