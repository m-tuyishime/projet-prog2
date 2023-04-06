package components.Ville.Intersection;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import components.Cellule.CelluleIntersection;
import components.Ville.Coordonnee;
import components.Ville.Structure;
import components.Ville.Ville;

// Classe qui représente une intersection
public class Intersection extends Structure {
    // la vitesse maximale des voitures dans l'intersection
    public static final int vitesseMax = 2000;
    // la largeur et la longueur de l'intersection
    public static final int largeur = 2;
    // le panneau qui contient les cellules de l'intersection
    private JPanel arrierePlan;
    // tableau de cellules de l'intersection
    private ArrayList<CelluleIntersection> cellules = new ArrayList<CelluleIntersection>();

    public Intersection(Ville ville, Coordonnee startPosition) {
        super(ville, startPosition);
        setVitesseMax(vitesseMax);
        arrierePlan = new JPanel();

        setOrientation("VERTICALE");
        setLargeLongueur(largeur, largeur);
        setLayout(null);

        // ajoute une bordure blanche autour de l'intersection
        int largeurBorder = 4;
        setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                Color.WHITE));

        construire();
    }

    public ArrayList<CelluleIntersection> getCellules() {
        return cellules;
    }

    // Redéfini la méthode qui crée les cellules
    // qui composent la structure
    @Override
    protected void peupler() {
        // crée l'arrière plan de l'intersection
        peuplerArrierePlan();
        // ajoute l'arrière plan à l'intersection
        add(arrierePlan);
        // crée des feux de circulation
        FeuxCirculation feuxCirculation = new FeuxCirculation(this);
        // ajoute les feux de circulation à l'intersection
        add(feuxCirculation);

        // redessine les feux de circulation toutes les 10ms
        // pour qu'ils soient toujours au premier plan
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                feuxCirculation.repaint();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 100, 10);
        // met les feux de circulation au premier plan
        setComponentZOrder(feuxCirculation, 0);
    }

    // crée les cellules qui composent l'intersection et les met dans
    // le panneau arrierePlan
    private void peuplerArrierePlan() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // calcule la largeur et la longueur du panneau
                int largeurContenu = getWidth() - getInsets().left - getInsets().right;
                int longueurContenu = getHeight() - getInsets().top - getInsets().bottom;
                arrierePlan.setBounds(getInsets().left, getInsets().top, largeurContenu, longueurContenu);
            }
        };

        // redimensionne le panneau arrierePlan à chaque fois que la taille de
        // l'intersection change
        Timer timer = new Timer();
        timer.schedule(task, 0, 10);

        // donne à arrierePlan un layout de grille avec le même
        // proportion que la grille de cellules
        arrierePlan.setLayout(new GridLayout(getTailleY(), getTailleX()));

        Coordonnee position;
        // itère sur l'intersection et crée les cellules
        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() +
                        y);
                CelluleIntersection cellule = new CelluleIntersection(this, position, 0);
                arrierePlan.add(cellule);
                cellules.add(cellule);
                Ville.setCellule(position, cellule);
            }
        }
    }
}
