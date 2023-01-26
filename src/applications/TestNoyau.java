package applications;
import java.util.*;
import solveur.*;

public class TestNoyau {
    static Scanner in = new Scanner(System.in);
	static Station station = new Station();
    
    static public void main(String[] args){

        //Création de la Map
        Map<Integer,Point> mp = new HashMap<Integer,Point>();
        mp.put(0, new Point(0, "0", 1800));
        mp.put(1, new Point(1, "1", 2000));
        mp.put(2, new Point(2, "2", 1700));
        mp.put(3, new Point(3, "3", 1300));
        mp.put(4, new Point(4, "4", 1900));
        mp.put(5, new Point(5, "5", 1500));
        station.setMapPoints(mp);
        
        creerTransitionGrapheCours();

        try {
            //Affichage station
    		for (Point i : station.getMapPoints().values()) {
        		i.affichagePoint();
        	}
    	}catch(Exception e) {
    		System.out.println("Probleme affichage liste");
    	}
              
        try { 
            int pointDep, pointArr;
            Itineraire itin1;
            boolean Ismode;
            
            //Choix du mode
            System.out.println("\nChoisir Mode\nMode absolu : false?\nMode reel : true?");
            Ismode = in.nextBoolean();
            //System.out.println("Mode : "+Ismode);

            //Choix des points
            System.out.println("\nPoint de départ ?");
            pointDep = in.nextInt();
            System.out.println("Point d'arrivée ?");
            pointArr = in.nextInt();
            
            //Dijkstra
            Point Dep, Arr;
			Dep = station.getMapPoints().get(pointDep);
			Arr = station.getMapPoints().get(pointArr);
			itin1 = station.dijkstra(Dep, Arr, Ismode);
            itin1.affichageItin(Dep, Arr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    static void creerTransitionGrapheCours(){  //création d'un graphe pour tester notre algorithme de Dijkstra	
    	try {
            ArrayList<Transition> listT = new ArrayList<>();

            //Transition de 1
            Navette T1 = new Navette(0,"P1_P2",station.getMapPoints().get(0),station.getMapPoints().get(1),"Navette",2);
            Descente D1 = new Descente(1,"P1_P3",station.getMapPoints().get(0),station.getMapPoints().get(2),"Descente",8);
            listT.add(T1);
            listT.add(D1);
            station.getMapPoints().get(0).setListeTransitions(listT);
            listT = new ArrayList<>();
            
            //Transition de 2
            Navette T2 = new Navette(0,"P2_P4",station.getMapPoints().get(1),station.getMapPoints().get(3),"Navette",1);
    		Descente D2 = new Descente(1,"P2_P3",station.getMapPoints().get(1),station.getMapPoints().get(2),"Descente",5);
            listT.add(T2);
            listT.add(D2);
            station.getMapPoints().get(1).setListeTransitions(listT);
            listT = new ArrayList<>();

            //Transition de 3
            Remontee R3 = new Remontee(2,"P3_P5",station.getMapPoints().get(2),station.getMapPoints().get(4),"Remontee",1,0);
            listT.add(R3);
            station.getMapPoints().get(2).setListeTransitions(listT);
            listT = new ArrayList<>();

            //Transition de 4
            Navette T4 = new Navette(0,"P4_P3",station.getMapPoints().get(3),station.getMapPoints().get(2),"Navette",2);
    		Remontee R4 = new Remontee(1,"P4_P5",station.getMapPoints().get(3),station.getMapPoints().get(4),"Remontee",4,0);
            Remontee R4bis = new Remontee(2,"P4_P6",station.getMapPoints().get(3),station.getMapPoints().get(5),"Remontee",1,0);
            listT.add(T4);
            listT.add(R4);
            listT.add(R4bis);
            station.getMapPoints().get(3).setListeTransitions(listT);
            listT = new ArrayList<>();

            //Transition de 5
            Navette T5 = new Navette(0,"P5_P2",station.getMapPoints().get(4),station.getMapPoints().get(1),"Navette",3);
    		Descente D5 = new Descente(1,"P5_P6",station.getMapPoints().get(4),station.getMapPoints().get(5),"Descente",2);
            //listT.clear();
            listT.add(T5);
            listT.add(D5);
            station.getMapPoints().get(4).setListeTransitions(listT);
            listT = new ArrayList<>();

    	}catch(Exception e) {
    		System.out.println("Probleme pour remplir liste");
    	}
    }
}
