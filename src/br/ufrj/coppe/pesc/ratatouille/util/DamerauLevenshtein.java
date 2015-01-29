package br.ufrj.coppe.pesc.ratatouille.util;

public class DamerauLevenshtein {

	
	public static int calculateOptimalStringAlignmentDistance(char[] str1, char[] str2){
		int[][] d = new int[str1.length][str2.length];
		int i, j, cost;
		
		for (i=0; i<str1.length;i++){
			d[i][0] = i;
		}
		for (j=0; j<str2.length;j++){
			d[0][j] = j;
		}
		
		for (i = 1; i < str1.length; i++){
			for (j = 1; j < str2.length; j++){
				if (str1[i]==str2[j]) cost = 0;
				else cost = 1;
				
				d[i][j] = minimum(d[i-1][j]+1, //deletion
								  d[i][j-1]+1, //insertion
								  d[i-1][j-1] + cost //substitution
								 );
				
				if (i > 1 && j > 1 && str1[i] == str2[j-1] && str1[i-1] == str2[j]){
					d[i][j] = Math.min(d[i][j], d[i-2][j-2] + cost);
				}
			}
		}
		
		return d[str1.length-1][str2.length-1];
	}
	
	
	private static int minimum (int i, int j, int k){
		int min = Math.min(i, j);
		return Math.min(min, k);
	}

}
