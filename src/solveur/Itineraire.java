package solveur;

import java.util.*;

public class Itineraire {

	//Champs
	private double dureeTotale;		// durée de la transition
	private int denivTotal;
	private LinkedList<Transition> L = new LinkedList<>();
	
	//Constructeurs
	public Itineraire() {
		dureeTotale = 0;
		denivTotal = 0;
		L = null;
	}
	public Itineraire(double duree, int deniv, LinkedList<Transition> l) {
		dureeTotale = duree;
		denivTotal = deniv;
		L = l;
	}

	//Getters
	public double getDureeTotale() {
		return dureeTotale;
	}
	public int getDenivTotal() {
		return denivTotal;
	}
	public LinkedList<Transition> getL() {
		return L;
	}
	
	//Setters
	public void setDenivTotal(int denivTotal) {
		this.denivTotal = denivTotal;
	}
	public void setDureeTotale(double dureeTotale) {
		this.dureeTotale = dureeTotale;
	}
	public void setL(LinkedList<Transition> l) {
		L = l;
	}
	
	//Méthodes
	String conversionTemps(double duree) {	//retourne le temps en heure, minute et seconde
		String tps_converti = "";
		double h,min,s,time;

		h = Math.floor(duree / 3600);
		time = ((duree / 3600) - h)*60;
		
		min = Math.floor(time);
		time = (time - min)*60;

		s = time;
		s = Math.round(s);
		
		tps_converti = (int)h+"h "+(int)min+"mn "+(int)s+"s";	//on mets tout en String
		
		return (tps_converti);
	}
	
	public String affichageItin(Point dep, Point arr) {		//Affiche un itinéraire entre les points dep et arr
		String res;
		res = "Plus court chemin entre les points "+ dep.getNom() + " <"+dep.getX()+","+dep.getY()+"> and "
																  + arr.getNom()+" <"+arr.getX()+","+arr.getY()+">";
		for (Transition i: L) {
		res += "\n  "+i.toString(); //on rajoute chaque étape du trajet entre dep et arr
		}
		res += "\nDuree du trajet : "+dureeTotale+" secondes, soit "+ conversionTemps(dureeTotale);
		res += "\nCumul des deniveles : "+ denivTotal+"m\n";
		return res;
	}
	
	@Override
	public String toString() {
		
		return "Itineraire [dureeTotale=" + dureeTotale + ", denivTotal=" + denivTotal + ", L=" + L + "]";
	}
}
