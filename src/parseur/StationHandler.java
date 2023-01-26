package parseur;
import solveur.*;

import java.io.*;
import java.util.*;
import org.xml.sax.*;

public class StationHandler implements ContentHandler {
  String typeCourant; // Type de l'element en cours de reconnaissance
  String typePrincCourant = ""; // Type de classe en cours
  
  Station station = new Station(); 
  Map<Integer,Point> mp = new HashMap<Integer,Point>(); //map de point 
  Point Pcourant;
  Transition Courant;
  
  public Station getStation() throws IOException, SAXException{
	  return station;
  }
  
  public void startDocument() throws SAXException {
    System.out.println("start document...");  
  }

  public void endDocument() throws SAXException {
    System.out.println("\nDocument termine.");
  }

// Balise ouvrante d'element : name dans localName
  public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) throws SAXException {
	  if (localName.equals("point")) 		{ Pcourant = new Point(); } //on regarde si la balise est un point
	  else if (localName.equals("navette"))	{ Courant = new Navette(); } //on regarde si la balise est un navette
	  else if (localName.equals("piste")) 	{ Courant = new Descente(); } //on regarde si la balise est un descente
	  else if (localName.equals("remontee")){ Courant = new Remontee(); } //on regarde si la balise est un remontée
	  typeCourant=localName;
	  
	  if (localName.equals("point") || localName.equals("navette") || localName.equals("piste") || localName.equals("remontee")) {
		  typePrincCourant=localName; //le type principal est la classe de la nouvelle classe que l'on veut implémenter
	  }
  }

  // Balise fermante : name dans localName
  public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
	  if (localName.equals("point")) {
		  mp.put(Pcourant.getNumero(),Pcourant);
	  }
	  else if (localName.equals("navette") || localName.equals("piste") || localName.equals("remontee")) {
		  Courant.getDepart().addTransition(Courant);
		  Courant = null;
	  }
	  else if (localName.equals("docbase")) {
		  station.setMapPoints(mp);
	  }
	  typeCourant=null;
		
	  if (localName.equals("point") || localName.equals("navette") || localName.equals("piste") || localName.equals("remontee")) {
		  typePrincCourant=null;
	  }
  }

  // Contenu de l'element courant...
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (typeCourant != null) {
    String contenu = new String (ch,start,length); //on récupère le contenu que l'on veut implémenter
      	if (typePrincCourant.equals("point"))  { //on regarde si la balise est un point et on implémente la variable dans contenu
			if (typeCourant.equals("numero")) {Pcourant.setNumero(Integer.parseInt(contenu));}
			else if (typeCourant.equals("nom")) {Pcourant.setNom(contenu);}
			else if (typeCourant.equals("altitude")) {Pcourant.setAltitude(Integer.parseInt(contenu));}
			else if (typeCourant.equals("x")) {Pcourant.setX(Integer.parseInt(contenu));}
			else if (typeCourant.equals("y")) {Pcourant.setY(Integer.parseInt(contenu));}
      	}
      	else if (typePrincCourant.equals("navette"))  { //on regarde si la balise est un navette et on implémente la variable dans contenu
			if (typeCourant.equals("numero")) {Courant.setNumero(Integer.parseInt(contenu));}
			else if (typeCourant.equals("nom")) {Courant.setNom(contenu);}
			else if (typeCourant.equals("type")) {((Navette)Courant).setType(contenu);}
			else if (typeCourant.equals("depart")) {Courant.setDepart(mp.get(Integer.parseInt(contenu)));}
			else if (typeCourant.equals("arrivee")) {Courant.setArrivee(mp.get(Integer.parseInt(contenu)));}
			else if (typeCourant.equals("tpsTrajet")) {((Navette)Courant).setTpsTrajet(Double.parseDouble(contenu));}
      	}
      	else if (typePrincCourant.equals("piste"))  { //on regarde si la balise est une piste et on implémente la variable dans contenu
			if (typeCourant.equals("numero")) {Courant.setNumero(Integer.parseInt(contenu));}
			else if (typeCourant.equals("nom")) {Courant.setNom(contenu);}
			else if (typeCourant.equals("type")) {((Descente)Courant).setType(contenu);}
			else if (typeCourant.equals("depart")) {Courant.setDepart(mp.get(Integer.parseInt(contenu)));}
			else if (typeCourant.equals("arrivee")) {Courant.setArrivee(mp.get(Integer.parseInt(contenu)));}	
			else if (typeCourant.equals("tpsDenivele")) {((Descente)Courant).setTpsDeniv(Double.parseDouble(contenu));}
      	}
      	else if (typePrincCourant.equals("remontee"))  {//on regarde si la balise est une remontée et on implémente la variable dans contenu
			if (typeCourant.equals("numero")) {Courant.setNumero(Integer.parseInt(contenu));}
			else if (typeCourant.equals("nom")) {Courant.setNom(contenu);}
			else if (typeCourant.equals("type")) {((Remontee)Courant).setType(contenu);}
			else if (typeCourant.equals("depart")) {Courant.setDepart(mp.get(Integer.parseInt(contenu)));}
			else if (typeCourant.equals("arrivee")) {Courant.setArrivee(mp.get(Integer.parseInt(contenu)));}
			else if (typeCourant.equals("tpsFixe")) {((Remontee)Courant).setTpsFixe(Double.parseDouble(contenu));}
			else if (typeCourant.equals("tpsDenivele")) {((Remontee)Courant).setTpsDeniv(Double.parseDouble(contenu));}
      	}
      	else if (typeCourant.equals("docbase")){
      		System.out.println("Balise "+ typeCourant); 
      	}
      	else { System.out.println("Balise "+ typeCourant +" inconnue"); }
    }
 }

  // Sur le reste : NOP
  public void startPrefixMapping(String prefix, String uri) {}
  public void endPrefixMapping(String prefix) {}
  public void ignorableWhitespace(char [] ch, int start, int length) throws SAXException {}
  public void processingInstruction(String target, String data) throws SAXException {}
  public void skippedEntity(String name) throws SAXException {}
  public void setDocumentLocator(Locator locator) {}
}









