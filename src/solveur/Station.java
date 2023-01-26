package solveur;
import java.util.*;

public class Station {
	Map<Integer, Point> MapPoints = new HashMap<Integer, Point>();
	Itineraire itin;

	//Constructeurs
	public Station(){
		MapPoints = null;
		itin = null;
	}
	public Station(Map<Integer,Point> mapPoints, Itineraire i){
		MapPoints = mapPoints;
		itin = i;
	}

	//Getters
	public Map<Integer, Point> getMapPoints() {
		return MapPoints;
	}
	public Itineraire getItin() {
		return itin;
	}

	//Setters
	public void setMapPoints(Map<Integer, Point> mapPoints) {
		MapPoints = mapPoints;
	}
	public void setItin(Itineraire itin) {
		this.itin = itin;
	}

	//Méthodes
	public void affichageStation(){ //affiche la station
		try {
			System.out.println("\n Points : \n");

			//Affiche les cles et les valeurs de la Map
			for (Integer i : MapPoints.keySet()) {
				System.out.println("Cle: "+ i + " Valeur: "+ MapPoints.get(i).getNom());
			}
		}catch(Exception e) {
			System.out.println("Map vide ou autres");
		}
	}

	@Override
	public String toString() {
		return "Station [MapPoints=" + MapPoints + ", itin=" + itin + "]";
	}
	
	public Itineraire dijkstra(Point dep, Point arr, boolean mode) throws PointInexistant { // algorithme de dijkstra
		double max;
		Point x;
		Itineraire itin = new Itineraire(); //création de l'itinéraire que l'on vas renvoyer
		
		try {
			//Initialisation des variables -------------------------------------------------------------
			ArrayList<Boolean> mark = new ArrayList<>();	//On marque le point courant
			ArrayList<Transition> pere = new ArrayList<>();	//on met la clé du Point
			ArrayList<Double>  pi   = new ArrayList<>();	// on met la durée de la transaction

			//Initialisation du max au poids de toutes les transitions cumulées
			max = 0;
			for (Point y: MapPoints.values()) {
				for (Transition j: y.getListeTransitions()) {
					max += j.dureeTransition(mode);
				}
			}
			//Initialisation des listes
			for (int i = 1; i <= MapPoints.size(); i++) {
			      mark.add(false);
			      pere.add(null);
			      pi.add(max);
			}
			//Marquage du Point de depart
			mark.set(dep.getNumero()-1, true);
			pi.set(dep.getNumero()-1, 0.0);

			//Dijkstra ----------------------------------------------------------------------------------
			x = dep;	//Affectation du point de depart au Point courant x
			
			//Test que le point en cours à toujours des transitions suivantes et que l'on n'a pas atteint le point arrivee
			while (mark.get(arr.getNumero()-1) != true){
				
				//Determination de la transition la plus courte du point courant
				for (Transition i: x.getListeTransitions()) { // On rajoute les informations pour les listes pere et pi avec le nouveau point x
					if (pi.get(i.getArrivee().getNumero()-1) > i.dureeTransition(mode) + pi.get(x.getNumero()-1)) {
						pere.set(i.getArrivee().getNumero()-1,i); // on met dans pere la transition qui permets d'y arriver
						pi.set(i.getArrivee().getNumero()-1,i.dureeTransition(mode) + pi.get(x.getNumero()-1)); // on met dans pi le nouveau minimum pour arriver au point n°i
					}
				}
				
				//Détermination du minimum non marqué pour marquer le prochain point
				int Pmin = 0;
				double min = max;
				for (int i = 0; i < pi.size(); i++) {
					if(pi.get(i) < min && mark.get(i) != true) {
						min = pi.get(i);
						Pmin = i+1;
					}
				}

				mark.set(Pmin-1, true); // on marque le minimum trouvé
				x = MapPoints.get(Pmin); // on change de Point et on met Pmin
			}
			//Calcul de l'itininéraire -----------------------------------------------------------------
			LinkedList<Transition> ordreTransit = new LinkedList<>();
			double dureeTot = 0;
			int denivTot = 0;
			
			Transition k = pere.get(arr.getNumero()-1); // on prend la dernière transition qui fait arriver sur le point d'arrivée final
			ordreTransit.addFirst(k);					//Ajout en tête pour les avoir dans l'ordre
			dureeTot += k.dureeTransition(mode);
			denivTot += Math.abs(k.getArrivee().getAltitude() - k.getDepart().getAltitude());
			
			//Test si le point de départ de la transition courante est différent du point de départ
			while (k.getDepart().getNumero() != dep.getNumero()) { 
				k = pere.get(k.getDepart().getNumero()-1);
				ordreTransit.addFirst(k); //ajout en tête
				dureeTot += k.dureeTransition(mode); //ajoute la durée de la transition k à la transition totale
				denivTot += Math.abs(k.getArrivee().getAltitude() - k.getDepart().getAltitude());
			}
			
			//Remplissage de itin
			itin.setDureeTotale(dureeTot);
			itin.setDenivTotal(denivTot);
			itin.setL(ordreTransit);
			
			return itin;
		}
		catch(NullPointerException ex) {
			throw new PointInexistant();
		}
	}
}
