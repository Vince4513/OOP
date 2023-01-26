package solveur;

public class Navette extends Transition{
	//Champs
	private String type;
	private double tpsTrajet;
	
	//Constructeurs
	public Navette() {
		super();
		type = "";
		tpsTrajet = 0;
	}
	public Navette(int num, String nom, Point dep, Point arr, String t, double tpsTraj) {
		super(num, nom, dep, arr);
		type = t;
		tpsTrajet = tpsTraj;
	}
	
	//Setters
	public void setType(String type) {
		this.type = type;
	}
	public void setTpsTrajet(double tpsTrajet) {
		this.tpsTrajet = tpsTrajet;
	}
	//Getters
	public String getType() {
		return type;
	}
	public double getTpsTrajet() {
		return tpsTrajet;
	}

	//Méthodes
	public double dureeTransition(boolean mode) { 	//temps de transition d'une navette
		if(mode) {
			setTpsTransition(tpsTrajet+java.lang.Math.random()*MAXWAITING); //en temps réel variable aléatoire qu'on rajoute au temps fixe
		}
		else {
			setTpsTransition(tpsTrajet);
		}
		return(getTpsTransition());
	}

	public String toString() {
		return super.toString() + " kind: " + type + " from: " + getDepart().getNom() + " to: " + getArrivee().getNom() + " duration: "+ getTpsTransition();
	}
}
