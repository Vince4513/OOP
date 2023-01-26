package solveur;

public abstract class Transition {
	//Champs
	private int numero;
	private String nom;
	private Point depart;
	private Point arrivee;
	private double tpsTransition;
	
	public static final int MAXWAITING = 30*60; //30min
	
	//Constructeur
	public Transition(){
		numero = 0;
		nom = "";
		depart = null;
		arrivee = null;
		tpsTransition = 0;
	}
	public Transition(int num, String n, Point dep, Point arr){
		numero = num;
		nom = n;
		depart = dep;
		arrivee = arr;
		tpsTransition = 0;
	}
	
	//Getters
	public int getNumero() {
		return numero;
	}
	public String getNom() {
		return nom;
	}
	public Point getDepart() {
		return depart;
	}
	public Point getArrivee() {
		return arrivee;
	}
	public double getTpsTransition() {
		return (int)tpsTransition;
	}

	//Setters
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setDepart(Point depart) {
		this.depart = depart;
	}
	public void setArrivee(Point arrivee) {
		this.arrivee = arrivee;
	}
	public void setTpsTransition(double tpsTransition) {
		this.tpsTransition = tpsTransition;
	}

	//Methodes
	abstract public double dureeTransition(boolean mode);
	
	public String toString() {
		return "Transition number: " + numero + " name: " + nom;
	}
}