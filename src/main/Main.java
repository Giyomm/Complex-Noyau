package main;

public class Main {

	public static void main(String[] args) {
		int[] a1 = {1,4, 2,1, 2,4, 3,4,	3,6, 7,4, 7,6, 7,8,	8,8};
		int[] a11 = {1,4, 2,1, 2,4, 3,4, 3,6, 7,4, 7,6, 7,8, 8,8, 2,5, 7,7, 3,2, 5,3};
		int[] a2 = {1,2, 2,3, 3,1};
		int[] a3 = {};
		
		Graphe g1 = new Graphe(a1, 8, 9);
		Graphe g11 = new Graphe(a11, 8, 13);
		Graphe g2 = new Graphe(a2, 3, 3);
		Graphe g3 = new Graphe(a3, 4, 0);
		
		try {
			g1.aNoyau("g1");
			g11.aNoyau("g11");
			g2.aNoyau("g2");
			g3.aNoyau("g3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
