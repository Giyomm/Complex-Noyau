package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Graphe {
	
	private HashMap<Integer, String> sommets;
	private HashMap<Integer, ArrayList<Integer>> sommetsPointes;
	private int nombreDeSommets, nombreDArretes;
	private int[] arretes;
	
	public Graphe(int[] arretes, int nombreDeSommets, int nombreDArretes) {
		this.arretes = arretes;
		this.nombreDeSommets = nombreDeSommets;
		this.nombreDArretes = nombreDArretes;
		this.sommetsPointes = new HashMap<Integer, ArrayList<Integer>>();
		
		sommets = new HashMap<>(nombreDeSommets);
		for (int i=1; i <= nombreDeSommets; i++)
			sommets.put(i, "no-noyau");
		
		for (int i=0; i < nombreDArretes*2; i+=2) {
			ArrayList<Integer> result = sommetsPointes.get(arretes[i+1]);
			if ( result != null) {
				result.add(arretes[i]);
				sommetsPointes.put(arretes[i+1], result);
			}
			else {
				result = new ArrayList<Integer>();
				result.add(arretes[i]);
				sommetsPointes.put(arretes[i+1], result);
			}
		}
	}
	
	public void genererDicmac(String fichier) throws IOException {
		PrintWriter cible = new PrintWriter(fichier+".dicmac", "UTF-8");

		cible.println("p cnf " + nombreDeSommets + " " + (nombreDArretes + nombreDeSommets));
		
		int premier, second;
		for (int i=0; i < nombreDArretes*2; i+=2) {
			premier = arretes[i]; second = arretes[i+1];
			cible.println("-" + (premier) 	+ " -" + (second) 	+ " 0");
		}
		
		for (int i=1; i<=nombreDeSommets; i++) {
			ArrayList<Integer> result = sommetsPointes.get(i);
			if (result != null ) {
				cible.print(i + " ");
				
				for(int j=0; j < result.size(); j++)
					cible.print(result.get(j) + " ");				
				cible.println("0");
			}
			else
				cible.println(i + " 0");
		}
		
		cible.close();
	}
	
	public void genererSAT(String fichier) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("minisat "+fichier+".dicmac "+fichier+".sat");
		pr.waitFor();
	}
	
	public boolean aNoyau(String fichier) throws IOException, InterruptedException {
		this.genererDicmac(fichier);
		this.genererSAT(fichier);

		BufferedReader br = new BufferedReader(new FileReader(fichier+".sat"));
		boolean noyau = br.readLine().equals("SAT");
		br.close();

		if (noyau)
			this.trouverNoyau(fichier);
		System.out.println(sommets);

		return noyau;
	}
	
	private void trouverNoyau(String fichier) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fichier+".sat"));
		br.readLine();
		String[] line = br.readLine().split(" ");
		
		for (int i = 0; i < line.length-1; i++)
			if ( ! line[i].contains("-"))
				sommets.put(i+1,"noyau");

		br.close();
	}

	public HashMap<Integer, String> getSommets() {
		return sommets;
	}

	public void setSommets(HashMap<Integer, String> sommets) {
		this.sommets = sommets;
	}

	public int getNombreDeSommets() {
		return nombreDeSommets;
	}

	public void setNombreDeSommets(int nombreDeSommets) {
		this.nombreDeSommets = nombreDeSommets;
	}

	public int getNombreDArretes() {
		return nombreDArretes;
	}

	public void setNombreDArretes(int nombreDArretes) {
		this.nombreDArretes = nombreDArretes;
	}

	public int[] getArretes() {
		return arretes;
	}

	public void setArretes(int[] arretes) {
		this.arretes = arretes;
	}
}
