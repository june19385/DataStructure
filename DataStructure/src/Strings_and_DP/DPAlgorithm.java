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

public class DPAlgorithm {
	public int[][] matrixChain(int[] d) {
		int n=d.length-1;
		int[][] N = new int[n][n];
		for(int b=1;b<n;b++) {
			for(int i=0;i<n-b;i++) {
				int j = i+b;
				N[i][j] = Integer.MAX_VALUE;
				for(int k=j;k<j;k++) {
					N[i][j] = Math.min(N[i][j], N[i][k]+N[k+1][j]+d[i]*d[k+1]*d[j+1]);
				}
			}
		}
		return N;
	}
	
	//LCS Algorithm
	public int[][] LCS(char[] X, char[] Y) {
		int n = X.length;
		int m = Y.length;
		int[][] L = new int[n+1][m+1];
		for(int j=0;j<n;j++) {
			for(int k=0;k<m;k++) {
				if(X[j]==Y[k])
					L[j+1][k+1] = L[j][k]+1;
				else
					L[j+1][k+1] = Math.max(L[j][k+1], L[j+1][k]);
			}
		}
		return L;
	}
	
	public char[] reconstructLCS(char[] X, char[] Y, int[][] L) {
		StringBuilder solution = new StringBuilder();
		int j = X.length;
		int k = Y.length;
		while(L[j][k]>0) {
			if(X[j-1]==Y[k-1]) {
				solution.append(X[j-1]);
				j--;
				k--;
			}
			else if(L[j-1][k]>=L[j][k-1]) j--;
			else k--;
		}
		return solution.reverse().toString().toCharArray();
	}
}

