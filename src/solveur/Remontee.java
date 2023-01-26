package solveur;

public class Remontee extends Transition{
	
	private String type;
	private double tpsFixe;
	private double tpsDeniv;

	//Setters
	public void setType(String type) {
		this.type = type;
	}
	public void setTpsFixe(double tpsFixe) {
		this.tpsFixe = tpsFixe;
	}
	public void setTpsDeniv(double tpsDeniv) {
		this.tpsDeniv = tpsDeniv;
	}
	//Constructeurs
	public Remontee() {
		super();
		type = "";
		tpsFixe = 0;
		tpsDeniv = 0;
	}
	public Remontee(int numero, String nom, Point depart, Point arrivee, String t, double tpsF, double tpsD) {
		super(numero,nom,depart,arrivee);
		type = t;
		tpsFixe = tpsF;
		tpsDeniv = tpsD;
	}

	//Getters
	public String getType() {
		return type;
	}
	public double getTpsFixe() {
		return tpsFixe;
	}
	public double getTpsDeniv() {
		return tpsDeniv;
	}

	//Méthode
	public double dureeTransition(boolean mode) { //temps transition d'une remontée
		if (mode) {	
			setTpsTransition((double)(getArrivee().getAltitude() - getDepart().getAltitude())/100 * tpsDeniv + tpsFixe + java.lang.Math.random() * MAXWAITING);//temps d'attente aléatoire en mode réel 
		}
		else {
			setTpsTransition((double)(getArrivee().getAltitude() - getDepart().getAltitude())/100 * tpsDeniv + tpsFixe);
		}
		return (getTpsTransition());
	}

	public String toString() {
		return super.toString() + " kind: " + type + " from: " + getDepart().getNom() + " to: " + getArrivee().getNom() + " duration: "+ getTpsTransition();
	}
}
