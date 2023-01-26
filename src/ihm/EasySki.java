package ihm;
import solveur.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import parseur.*;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;

import javax.swing.*;

/**
 * Prototype d'interface graphique de l'application EasySKi
 * - charge une image du plan de la station .jpg en parametre du main)
 * - lui superpose une grille (X,Y) en vue du reperage de lieux de la station par leurs coordonnees pour en faire un
 * plan "cliquable"
 * - ou permet de saisir un lieu directement par son nom.
 * Ce prototype ne fait qu'afficher les coordonnees cliquees ou le nom d'un lieu saisi dans une
 * zone de texte.
 * 
 * Usage : java ihm.EasySkiProto <plan jpg>
 * 
 * @author Bernard.Carre -at- polytech-lille.fr
 * 
 */

@SuppressWarnings("serial")
public class EasySki extends JFrame {

	/**
	 * Precision de la grille de reperage.
	 * 
	 */
	protected static final int DELTA = 20;
	/**
	 * Taille du plan.
	 */
	protected int hauteurPlan, largeurPlan;

	/**
	 * ImageCanvas scrollable
	 * NE PAS MODIFIER
	 * Plan "cliquable" = image + grille.	 * 
	 */
	protected ImageCanvas canvas = new ImageCanvas();
	/**
	 * Vue "scrollable" du plan.
	 */
	protected ScrollPane planView = new ScrollPane();
	
	/**
	 * InteractionPanel
	 * Panel d'interaction avec l'utilisateur
	 */	
	protected InteractionPanel interact = new InteractionPanel();
	
	static protected Station station = new Station();
	static protected boolean Ismode;
	/**
	 * Charge une image de plan et construit l'interface graphique.
	 * 
	 * @param fichierImage
	 *            Nom du fichier image de plan
	 * @throws java.io.IOException
	 *             Erreur d'acces au fichier
	 */
	public EasySki(String fichierImage) throws java.io.IOException {

		// Chargement de l'image
		Image im = new ImageIcon(fichierImage).getImage();
		hauteurPlan = im.getHeight(this);
		largeurPlan = im.getWidth(this);

		// Preparation de la vue scrollable de l'image
		canvas.setImage(im); // image a afficher dans le Canvas
		canvas.addMouseListener(interact.getSelectionPanel()); // notification de clic sur la grille
		planView.setSize(hauteurPlan + DELTA, largeurPlan + DELTA);
		planView.add(canvas); // apposition de la vue scrollable sur l'ImageCanvas

		// Construction de l'ensemble
		setLayout(new BorderLayout());
		add(planView, BorderLayout.CENTER);
		add(interact, BorderLayout.SOUTH);

		// Evenement de fermeture de la fenetre : quitter l'application
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	/**
	 * 
	 * Classe utilitaire interne (sous-classe de java.awt.Canvas = plan (jpg) + grille de coordonnees cliquable
	 * NE PAS MODIFIER
	 */
	class ImageCanvas extends Canvas {
		Image image;

		void setImage(Image img) {
			image = img;
			setSize(largeurPlan, hauteurPlan);
			repaint();
		}

		/**
		 * Affiche l'image + la grille.
		 * 
		 */
		public void paint(Graphics g) {
			if (image != null)
				g.drawImage(image, DELTA, DELTA, this);

			// Grille de repérage apposée
			int lignes = hauteurPlan / DELTA;
			int colonnes = largeurPlan / DELTA;
			g.setColor(Color.gray);
			for (int i = 1; i <= lignes; i++) {
				g.drawString("" + i, 0, (i + 1) * DELTA);
				g.drawLine(DELTA, i * DELTA, DELTA + largeurPlan, i * DELTA);
			}
			g.drawLine(DELTA, (lignes + 1) * DELTA, DELTA + largeurPlan, (lignes + 1) * DELTA);
			for (int i = 1; i <= colonnes; i++) {
				g.drawString("" + i, i * DELTA, DELTA / 2);
				g.drawLine(i * DELTA, DELTA, i * DELTA, DELTA + hauteurPlan);
			}
			g.drawLine((colonnes + 1) * DELTA, DELTA, (colonnes + 1) * DELTA, DELTA + hauteurPlan);
		}
	}

	/**
	 * InteractionPanel
	 * Panel d'interaction avec l'utilisateur composee :
	 * - d'un panel de selection utilisateur "SelectionSubPanel"
	 * - une zone de texte "resultTextArea" pour afficher les resultats 
	 * 
	 */
	class InteractionPanel extends JPanel {
 		SelectionSubPanel selectionPanel = new SelectionSubPanel();
		JTextArea resultTextArea = new JTextArea(5, 30);

		InteractionPanel() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(selectionPanel);
			resultTextArea.setEditable(false); // non editable (produit par les resultats de l'application)
			add(resultTextArea);
		}

		/**
		 * SelectionSubPanel
		 * Sous-panel de selection utilisateur :
		 * - a l'ecoute (implements MouseListener) de clics utilisateur sur le plan:
		 * enregistre les coordonnees (x,y) et les affiche dans les label "X=...", "Y=..."
		 * - permet de saisir un nom de lieu dans le JTextField "lieu"
		 * - bouton : "GO!" : reporte les saisies dans la zone "resultTextArea"		 * 
		 */
		class SelectionSubPanel extends JPanel implements MouseListener {
			/**
			 * Valeurs de x, y cliquees
			 */
			int x, y;
			int pCourant;
			int pDep, pArr;
			/**
			 * Affichage de X,Y cliquees
			 */
			JLabel xLabel = new JLabel("X");
			JLabel yLabel = new JLabel("Y");
			/**
			 * Champ de saisie d'un nom de lieu
			 */
			JTextField xText = new JTextField(20);
			JTextField yText = new JTextField(20);
			JTextField lieuText = new JTextField(20);
			JTextField depText = new JTextField(20);
			JTextField arrText = new JTextField(20);
			/**
			 * Reporte les saisies dans la zone "resultTextArea"
			 */
			JButton setDep = new JButton("SET DEPART");
			JButton setArr = new JButton("SET ARRIVEE");
			JButton go = new JButton("GO!"); // reporte les saisies dans la zone "resultTextArea"
			
			JRadioButton rBtn = new JRadioButton("TEMPS REEL");

			// Construction et ecouteur du bouton "GO!"
			SelectionSubPanel() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // organisation verticale
				setBorder(BorderFactory.createEmptyBorder(5,2,5,2));
				
				//Affichage de selectionSubPanel
				add(xLabel);
				add(xText);
				add(yLabel);
				add(yText);
				add(new JLabel("NOM LIEU"));
				add(lieuText);
				add(setDep);
				add(depText);
				add(setArr);
				add(arrText);
				add(rBtn);
				add(go, BorderLayout.CENTER);
				
				setDep.addActionListener(evt ->{ //quand on appuie sur set depart pour mettre le point actuel au départ
					if (lieuText != null) {
						depText.setText(lieuText.getText());
						pDep = pCourant;
					}
				});
				setArr.addActionListener(evt ->{ //quand on appuie sur set arrivée pour mettre le point actuel à l'arrivée
					if (lieuText != null) {
						arrText.setText(lieuText.getText());
						pArr = pCourant;
					}
				});
				rBtn.addActionListener(evt ->{ // sélectionne ou non le fait d'être en temps réel ou en temps absolu
					if(rBtn.isSelected())
						Ismode = true;
					else
						Ismode = false;
				});
				go.addActionListener(evt -> { //quand on appuie sur GO! on réalise l'algorithme de Dijkstra
					Itineraire itin1;
					Point Dep, Arr;
					Dep = station.getMapPoints().get(pDep);
					Arr = station.getMapPoints().get(pArr);
					
					//Dijkstra
					try {
						itin1 = station.dijkstra(Dep, Arr, Ismode);
						resultTextArea.setText(itin1.affichageItin(Dep, Arr));
					} catch (PointInexistant e) { // Exception si on rentre un mauvais point
						System.out.println("Point inexistant dans la station");
					}
				});
			}

			/**
			 * 
			 * Reactions au clic utilisateur sur la grille
			 * - methode "void mouseReleased(MouseEvent e)" : selection de coordonnees
			 * - autres méthodes sans effet
			 * 
			 */
			public void mouseReleased(MouseEvent e) {
				x = e.getX() / DELTA;
				y = e.getY() / DELTA;
				xText.setText("" + x);
				yText.setText("" + y);
			
				lieuText.setText("");
				for (Point p: station.getMapPoints().values()){ //on parcourt les points dans la Map
					if (p.getX() == x && p.getY() == y) { // on regarde si les coordonnées du point parcourus sont les mêmes que les coordonnées dans X et Y
						lieuText.setText(p.getNom()); //on affiche dans lieu le point qui correspond aux coordonnées
						pCourant = p.getNumero(); // on stocke son numéro pour pouvoir l'utiliser après
					}
				}
				
			}

			/**
			 * NOP
			 */
			public void mousePressed(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseClicked(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseEntered(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseExited(MouseEvent e) {
			}
		}

		SelectionSubPanel getSelectionPanel() {
			return selectionPanel;
		}

	}

	/**
	 * 
	 * Usage : java ihm.EasySkiProto <plan jpg>
	 * 
	 * @param argv[0]
	 *            plan jpg
	 * @throws java.io.IOException
	 *             Erreur d'acces au fichier
	 * @throws SAXException 
	 */
	public static void main(String argv[]) throws java.io.IOException, SAXException {
		if (argv.length != 2) {
			System.err.println("usage : java ihm.EasySkiProto station.jpg");
			System.err.println("usage : java Console station/station.xml");
		}
		else {
			System.out.println("analyse de " + argv[1] + "...");
			
			// Le parseur SAX
			@SuppressWarnings("deprecation")
			XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			
			StationHandler handler = new StationHandler();
			
			// Creation d'un flot XML sur le fichier d'entree
			InputSource input = new InputSource(new FileInputStream(argv[1]));

			// Connexion du ContentHandler
			reader.setContentHandler(handler);
			// Lancement du traitement...
			
			reader.parse(input);
			
			station = handler.getStation();
			
			EasySki window = new EasySki(argv[0]);
			window.setTitle("EasySki");
			window.setSize(600, 640);
			window.setVisible(true);
		}			
	}
}