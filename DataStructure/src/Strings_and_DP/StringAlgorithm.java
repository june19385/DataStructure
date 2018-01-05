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

package Strings_and_DP;

import Maps.ChainHashMaps;
import Maps.Maps;

public class StringAlgorithm {
	
	//Brute Force Algorithm
	public int findBrute(char[] text, char[] pattern) {
		int n = text.length;
		int m = pattern.length;
		for(int i=0;i<=n-m;i++) {
			int k = 0;
			while(k<m && text[i+k]==pattern[k]) {
				k++;
			}
			if(k==m)
				return i;
		}
		return -1;
	}
	
	//Boyer-Moore Algorithm
	public int findBoyerMoore(char[] text, char[] pattern) {
		int n = text.length;
		int m = pattern.length;
		Maps<Character,Integer> last = new ChainHashMaps<>();
		for(int i=0;i<n;i++)
			last.put(text[i], -1);
		for(int k=0;k<m;k++) {
			last.put(pattern[k], k);
		}
		int i = m-1;
		int k = m-1;
		while(i<n) {
			if(text[i]==pattern[k]) {
				if(k==0) return i;
				i--;
				k--;
			}
			else {
				i+=m-Math.min(k, 1+last.get(text[i]));
				k=m-1;
			}
		}
		return -1;
	}
	
	//Knuth-Morris_Pratt algorithm
	public int findKMP(char[] text, char[] pattern) {
		int n = text.length;
		int m = pattern.length;
		if(m==0) return 0;
		int[] fail = computeFailKMP(pattern);
		int j = 0;
		int k = 0;
		while(j<n) {
			if(text[j]==pattern[k]) {
				if(k==m-1) return j-m+1;
				j++;
				k++;
			}
			else if(k>0) k=fail[k-1];
			else j++;
		}
		return -1;
	}
	
	private int[] computeFailKMP(char[] pattern) {
		int m = pattern.length;
		int[] fail = new int[m];
		int j = 1;
		int k = 0;
		while(j<m) {
			if(pattern[j]==pattern[k]) {
				fail[j]=k+1;
				j++;
				k++;
			}
			else if(k>0) k=fail[k-1];
			else j++;
		}
		return fail;
	}
}
