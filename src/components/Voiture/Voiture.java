package components.Voiture;

import java.awt.Color;
import java.awt.Dimension;
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
    private int nombreRotations = 0;
    private boolean chercher = false;
    private boolean uTurn = false;
    private boolean dansIntersection = false;
    private boolean horizontale = false;
    private boolean fini = false;

    private Cellule cellActuelle;
    private Structure structure;
    private Coordonnee positionActuelle;
    private int xActuelle;
    private int yActuelle;
    private int derniereDirection;

    public Voiture() {
        Ville.setNombreVoitures(Ville.getNombreVoitures() + 1);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(20, 30));
        Cellule.grid.setConstraints(this, Cellule.constraints);

        premierArrangement();

        try {
            circule();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setTauxRecherche(double nouvTaux) {
        tauxRecherche = nouvTaux;
    }

    private void startChercher() {

    }

    private void stopChercher() {

    }

    private void premierArrangement() {
        positionActuelle = Ville.entrees[random.nextInt(Ville.entrees.length)];
        cellActuelle = Ville.getCellule(positionActuelle);
        structure = cellActuelle.getStructure();

        if (structure.getOrientation() == "HORIZONTALE")
            horizontale = true;
    }

    private void circule() throws InterruptedException {
        tourne();
        cellActuelle = Ville.getCellule(positionActuelle);
        structure = cellActuelle.getStructure();
        xActuelle = positionActuelle.getX();
        yActuelle = positionActuelle.getY();
        Parking parking = cellActuelle.getParking();

        ajouter();

        Thread.sleep(structure.getVitesseMax());

        // if (Ville.getResetStatus() || fini) {
        // enlever();
        // Ville.setNombreVoitures(Ville.getNombreVoitures() - 1);
        // return;
        // }

        while (!Ville.getCirculationStatus()) {
            Thread.sleep(500);
        }

        if (!Ville.estSortie(positionActuelle)) {
            if (chercher && parking != null) {
                circulationParking(parking);
            } else {
                if (structure instanceof Rue)
                    circulationRue();
                else if (structure instanceof Intersection)
                    circulationIntersection();

                enlever();
                circule();
            }
        } else {
            positionActuelle = Ville.getEntree(positionActuelle);
            enlever();
            circule();
        }
    }

    private void circulationParking(Parking parking) throws InterruptedException {
        nombreRotations = 0;
        uTurn = false;
        ajouter();
        Thread.sleep(structure.getVitesseMax());
        horizontale = !horizontale;
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

        horizontale = !horizontale;
        circule();
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
            randomDirection = 1;
            changerDirection();
        } else if (nombreRotations == 1) {
            if (!horizontale)
                changerDirection();
            randomDirection = 1;
        }

        if (randomDirection == 0) {
            if (!dansIntersection)
                uTurn = true;
            if (horizontale)
                yActuelle -= derniereDirection;
            else {
                xActuelle += derniereDirection;
                nombreRotations++;
            }

            horizontale = !horizontale;
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        } else if (randomDirection == 1) {
            if (uTurn)
                horizontale = !horizontale;
            if (horizontale) {
                xActuelle += derniereDirection;
                nombreRotations++;
            } else
                yActuelle += derniereDirection;
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        } else if (randomDirection == 2) {
            if (horizontale)
                yActuelle += derniereDirection;
            else
                xActuelle -= derniereDirection;

            horizontale = !horizontale;

            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        }

        if (Ville.getCellule(positionActuelle) instanceof CelluleIntersection)
            dansIntersection = true;
        else
            dansIntersection = false;
    }

    private void tourne() {
        if (horizontale) {
            setPreferredSize(new Dimension(30, 20));
        } else
            setPreferredSize(new Dimension(20, 30));

        update();
    }

    private void changerDirection() {
        if (derniereDirection == -1)
            derniereDirection = 1;
        else
            derniereDirection = -1;
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
