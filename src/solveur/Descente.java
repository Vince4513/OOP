package solveur;

public class Descente extends Transition{
	
	private String type;	//type de descente
	private double tpsDeniv;	//temps dénivelé
	
	//Constructeurs
	public Descente() {
		super();
		type = "";
		tpsDeniv = 0;
	}
	
	public Descente(int numero, String nom, Point depart, Point arrivee, String t, double tps) {
		super(numero,nom,depart,arrivee);
		type = t;
		tpsDeniv = tps;
	}
	
	//Setters
	public void setType(String type) {
		this.type = type;
	}
	public void setTpsDeniv(double tpsDeniv) {
		this.tpsDeniv = tpsDeniv;
	}
	//Getters
	public String getType() {
		return type;
	}
	public double getTpsDeniv() {
		return tpsDeniv;
	}

	//Méthode
	public double dureeTransition(boolean mode) { // retourne le temps de la transition d'une descente
		if (mode) {
			setTpsTransition((double)(getDepart().getAltitude() - getArrivee().getAltitude())/100 * tpsDeniv);
		}
		else {
			setTpsTransition((double)(getDepart().getAltitude() - getArrivee().getAltitude())/100 * tpsDeniv); 		//même temps temps absolu que temps réel
		}
		return (getTpsTransition());
	}

	public String toString() { 
		return super.toString() + " kind: " + type + " from: " + getDepart().getNom() + " to: " + getArrivee().getNom() + " duration: "+ getTpsTransition();
	}
}
