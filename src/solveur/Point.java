package solveur;

import java.util.*;

public class Point {
	private int numero;
	private String nom;
	private int altitude;
	private int x;
	private int y;
	private ArrayList<Transition> listeTransitions = new ArrayList<>(); //liste des transitions du point
	
	//Constructeurs
	public Point() {
		numero = 0;
		nom = "";
		altitude = 0;
		x = 0;
		y = 0;
	}
	public Point(int n, String Nom, int Alt) {
		numero = n;
		nom = Nom;
		altitude = Alt;
		x = 0;
		y = 0;
	}

	//Getters
	public int getNumero() {
		return numero;
	}
	public String getNom() {
		return nom;
	}
	public int getAltitude() {
		return altitude;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ArrayList<Transition> getListeTransitions() {
		return listeTransitions;
	}
	
	//Setters
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setListeTransitions(ArrayList<Transition> listeTransitions) {
		this.listeTransitions = listeTransitions;
	}

	//Méthodes
	public void addTransition(Transition T) { // ajoute une transition à la liste de base 
		this.listeTransitions.add(T); 
	}
	
	public void affichagePoint(){	//affiche un point
		System.out.println("\nNumero : "+ numero);
		System.out.println("Nom : "+ nom);
		System.out.println("Altitude : "+ altitude);

		System.out.println("\nTransitions sortantes du point : ");

		//Affiche les transitions sortantes du Point
		for (Transition i : listeTransitions) { //affiche toute les transitions du point
			System.out.println(i.toString());
		}
	}
}
