package components.Voiture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import components.Cellule.Cellule;
import components.Cellule.CelluleIntersection;
import components.Cellule.CelluleRue;
import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Rue;
import components.Ville.Structure;
import components.Ville.Ville;
import components.Ville.Intersection.Intersection;

public class Voiture extends JPanel {
    private static Random random = new Random();
    // Taux de recherche d'un stationnement
    private static double tauxRecherche = 0.5;
    // Si les voitures bougent
    private static boolean circulation = true;
    private static boolean reset = false;
    private static int nombreVoitures = 0;
    private int nombreRotations = 0;
    private boolean chercher = false;
    private boolean uTurn = false;
    private boolean dansIntersection = false;

    private boolean tourner;
    private Cellule cellActuelle;
    private Structure structure;
    private Coordonnee positionActuelle;
    private int xActuelle;
    private int yActuelle;
    private int derniereDirection;

    public Voiture() {
        nombreVoitures++;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(20, 30));
        Cellule.grid.setConstraints(this, Cellule.constraints); // Ajout des contraintes de la deuxième ligne à la
                                                                // ille

        positionActuelle = Ville.entrees[0];
        // positionActuelle = Ville.entrees[random.nextInt(Ville.entrees.length)];
        cellActuelle = Ville.getCellule(positionActuelle);
        if (cellActuelle.getStructure().getOrientation() == "HORIZONTALE")
            tourner = true;
        // ajouter();
        try {
            circule();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getNombreVoitures() {
        return nombreVoitures;
    }

    public static void setResetStatus(boolean status) {
        reset = status;
    }

    public static void setCirculation(boolean status) {
        circulation = status;
    }

    public static void setTauxRecherche(double nouvTaux) {
        tauxRecherche = nouvTaux;
    }

    private void startChercher() {

    }

    private void stopChercher() {

    }

    private void circule() throws InterruptedException {
        if (Ville.estSortie(positionActuelle))
            return;

        tourne();
        cellActuelle = Ville.getCellule(positionActuelle);
        if (reset) {
            enlever();
            return;
        }

        while (!circulation) {
            Thread.sleep(500);
        }

        structure = cellActuelle.getStructure();

        xActuelle = positionActuelle.getX();
        yActuelle = positionActuelle.getY();

        Parking parking = cellActuelle.getParking();
        if (chercher && parking != null) {
            nombreRotations = 0;
            uTurn = false;
            ajouter();
            Thread.sleep(structure.getVitesseMax());
            tourner = true;
            tourne();
            paintComponent(getGraphics());
            Thread.sleep(structure.getVitesseMax());
            enlever();
            parking.addOccupation();
            stopChercher();
            Thread.sleep(random.nextInt(Parking.maxTemps));
            parking.removeOccupation();
            ajouter();
            Thread.sleep(structure.getVitesseMax());

            tourner = true;
            circule();
        } else {
            // checks
            ajouter();
            if (structure instanceof Rue)
                circulationRue();
            else if (structure instanceof Intersection)
                circulationIntersection();
            Thread.sleep(structure.getVitesseMax());
            enlever();
            circule();
        }
    }

    private void circulationRue() {
        nombreRotations = 0;
        uTurn = false;
        if (structure.getOrientation() == "VERTICALE")
            yActuelle += cellActuelle.getDirection();
        else
            xActuelle += cellActuelle.getDirection();

        positionActuelle = new Coordonnee(xActuelle, yActuelle);
        derniereDirection = cellActuelle.getDirection();
    }

    private void circulationIntersection() {
        // Decide quel direction aller 0 = gauche, 1 = continue, 2 = droite
        int randomDirection;
        if (dansIntersection)
            randomDirection = random.nextInt(2);
        else
            randomDirection = random.nextInt(3);

        if (uTurn) {
            positionActuelle = new Coordonnee(xActuelle, yActuelle - derniereDirection);
            tourner = true;
        } else if (nombreRotations == Intersection.maxRotations) {
            Cellule[] cellulesAutour = {
                    Ville.getCellule(new Coordonnee(xActuelle + derniereDirection, yActuelle)),
                    Ville.getCellule(new Coordonnee(xActuelle, yActuelle + derniereDirection))
            };
            for (Cellule cellule : cellulesAutour) {
                if (cellule != null && cellule instanceof CelluleRue) {
                    Coordonnee nouvPosition = cellule.getPosition();
                    if (nouvPosition.getX() != positionActuelle.getX())
                        tourner = true;
                    positionActuelle = nouvPosition;
                    break;
                }
            }
        } else if (randomDirection == 0) {
            if (!dansIntersection)
                uTurn = true;
            xActuelle += derniereDirection;
            tourner = true;
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        } else if (randomDirection == 1) {
            yActuelle += derniereDirection;
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        } else if (randomDirection == 2) {
            xActuelle -= derniereDirection;
            tourner = true;
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        }

        if (Ville.getCellule(positionActuelle) instanceof CelluleIntersection)
            dansIntersection = true;
        else
            dansIntersection = false;
    }

    private void tourne() {
        if (tourner) {
            setPreferredSize(new Dimension(30, 20));
            nombreRotations++;
            tourner = false;
        } else
            setPreferredSize(new Dimension(20, 30));

        update();
    }

    private void update() {
        cellActuelle.validate();
        cellActuelle.repaint();
    }

    private void ajouter() {
        cellActuelle.add(this);
        update();
    }

    private void enlever() {
        cellActuelle.remove(this);
        update();
    }
}
