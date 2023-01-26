package applications;
import parseur.*;
import solveur.*;

import java.io.FileInputStream;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class Console {
	
	static Scanner in = new Scanner(System.in);
	static private Station station = new Station();
	static private boolean Ismode;
	static private int pointDep, pointArr;
	
	public static void main(String[] argv) throws Exception{
			
		parse(argv);
			
		int choix=0;
		do {
			menu(); // menu pour savoir notre choix
			try {
				System.out.print("votre choix? ");
				choix = in.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Erreur de saisie\n");
				in.next();
			}
			switch (choix) {
			case 1 : 
				choix_point();
				break;
			case 2 : 
				choix_mode();
				break;
			case 0: 
			}
		} while (choix!=0);	  
		try{ //réalisation de l'algorithme de Dijkstra sur les points de départ et d'arrivée rentrés. 
			Itineraire itin1;
			Point Dep, Arr;
			Dep = station.getMapPoints().get(pointDep);
			Arr = station.getMapPoints().get(pointArr);
			
			//Dijkstra
			itin1 = station.dijkstra(Dep, Arr, Ismode);
		    itin1.affichageItin(Dep, Arr);
		}
		catch(PointInexistant e) { // Exception si on rentre un mauvais point
			System.out.print("Le point de depart ou d arrivee n existe(nt) pas dans la map\n");

		}
		finally {
			System.out.println("Execution itinéraire terminé");
		}
	}

	static void menu() {
		  System.out.println("\n1: choix des points arrivée/départ\n2: choix du mode de calcul\n0: calculer la plus courte distance");
		 }
	
	static void choix_mode() {
		try {
			System.out.println("\nMode :\n Absolu? - false\n Réel? - true\n");
			Ismode = in.nextBoolean();
		}
		catch (InputMismatchException ex) {
			System.out.println("Saisie du mode invalide\n");
			in.next();
		}
	}
	
	static void choix_point() { // choix des points de départ et d'arrivée
		try {
			System.out.println("\nPoint de départ ?");
	        pointDep = in.nextInt();
	        System.out.println("Point d'arrivée ?");
	        pointArr = in.nextInt();
		}
		catch (InputMismatchException ex) { // Exception si on rentre un mauvais point
			System.out.println("Saisie des points invalides\n");
			in.next();
		}
	}
	
	static public void parse(String[] argv) throws Exception{
		if (argv.length != 1) // on regarde si il y a bien un argument rentré ( l'argument station.xml )
			System.err.println("usage : java Console station/station.xml");
		else {
			System.out.println("analyse de " + argv[0] + "...");
			
			// Le parseur SAX
			@SuppressWarnings("deprecation")
			XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			
			StationHandler handler = new StationHandler();
			
			// Creation d'un flot XML sur le fichier d'entree
			InputSource input = new InputSource(new FileInputStream(argv[0]));

			// Connexion du ContentHandler
			reader.setContentHandler(handler);
			// Lancement du traitement...
			
			reader.parse(input);
			
			station = handler.getStation();
			System.out.println("Station Console: \n"+station.toString());
		}
	}
}
