package components.Voiture;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import components.Cellule.Cellule;
import components.Cellule.CelluleIntersection;
import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Rue;
import components.Ville.Structure;
import components.Ville.Ville;
import components.Ville.Intersection.Intersection;
import serveur.Serveur;

public class Voiture extends JPanel {
    private static Random random = new Random();
    // Taux de recherche d'un stationnement
    private static double tauxRecherche = 1;
    private int nombreRotations = 0;
    private boolean chercher = false;
    private boolean uTurn = false;
    private boolean dansIntersection = false;
    private boolean horizontale = false;
    private Serveur serveur = new Serveur();

    private Cellule cellActuelle;
    private Structure structure;
    private Coordonnee positionActuelle;
    private int xActuelle;
    private int yActuelle;
    private int derniereDirection;
    private ArrayList<Structure> cheminParking;

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

    private void chercherParking() {
        if (!chercher) {
            if (tauxRecherche <= 0.0) {
                return;
            } else if (tauxRecherche >= 1.0) {

            } else {
                int randomInt = random.nextInt(100);
                if (randomInt < tauxRecherche * 100) {

                } else {
                    return;
                }
            }

            chercher = true;
            cheminParking = serveur.getCheminParking(positionActuelle, derniereDirection);

            Color couleurBordure = serveur.getCouleurParking();
            int largeurBorder = 3;
            setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                    couleurBordure));
        }
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

        Cellule cellSuivante = Ville.getCellule(positionActuelle);
        CelluleIntersection cellIntersection = null;
        if (cellSuivante instanceof CelluleIntersection)
            cellIntersection = (CelluleIntersection) cellSuivante;

        while (cellSuivante.estOccupe()
                || (cellIntersection != null && !cellIntersection.estGo() && !dansIntersection)) {
            Thread.sleep(500);
            update();
        }

        chercherParking();

        enlever();

        cellActuelle = cellSuivante;
        structure = cellActuelle.getStructure();
        xActuelle = positionActuelle.getX();
        yActuelle = positionActuelle.getY();
        Parking parking = cellActuelle.getParking();

        ajouter();

        Thread.sleep(structure.getVitesseMax());

        if (Ville.getResetStatus()) {
            enlever();
            Ville.setNombreVoitures(Ville.getNombreVoitures() - 1);
            return;
        }

        while (!Ville.getCirculationStatus()) {
            Thread.sleep(500);
        }

        if (!Ville.estSortie(positionActuelle)) {
            if (chercher && parking != null) {
                circulationParking(parking);
            }

            if (structure instanceof Rue)
                circulationRue();
            else if (structure instanceof Intersection)
                circulationIntersection();

            circule();

        } else {
            positionActuelle = Ville.getEntree(positionActuelle);
            circule();
        }
    }

    private void circulationParking(Parking parking) throws InterruptedException {
        enlever();
        nombreRotations = 0;
        uTurn = false;

        horizontale = !horizontale;
        tourne();

        ajouter();
        Thread.sleep(structure.getVitesseMax());
        enlever();
        parking.addOccupation();
        chercher = false;
        Thread.sleep(random.nextInt(Parking.maxTemps));

        while (cellActuelle.estOccupe()) {
            Thread.sleep(500);
        }

        ajouter();
        parking.removeOccupation();
        Thread.sleep(structure.getVitesseMax());

        horizontale = !horizontale;

        tourne();
        enlever();
        ajouter();
    }

    private void circulationRue() {
        nombreRotations = 0;
        if (!chercher || !structure.equals(cheminParking.get(cheminParking.size() - 1)))
            uTurn = false;
        else
            uTurn = true;

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

        if (uTurn && dansIntersection) {
            // uTurn assigne par circulationIntersection
            randomDirection = 1;
            changerDirection();
        } else if (uTurn) {
            // uTurn assigne par circulationRue
            randomDirection = 0;
        } else if (nombreRotations == 1) {
            if (!horizontale)
                changerDirection();
            randomDirection = 1;
        }

        if (chercher) {
            int cheminIndex = cheminParking.indexOf(structure);
            Structure structureSuivante = cheminParking.get(cheminIndex + 1);
            int direction = getDirectionSuivante(structureSuivante);

            if (direction != -1)
                randomDirection = direction;
            else if (!dansIntersection && !uTurn)
                randomDirection = 1;
            else
                randomDirection = 0;
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

    private int getDirectionSuivante(Structure destination) {
        int compte = 3;
        Coordonnee positionActuelle = new Coordonnee(this.positionActuelle.getX(), this.positionActuelle.getY());
        int directionSuivante = -1;

        for (int direction = 0; direction < compte; direction++) {
            int yActuelle = this.yActuelle;
            int xActuelle = this.xActuelle;

            if (direction == 0) {
                if (horizontale)
                    yActuelle -= derniereDirection;
                else
                    xActuelle += derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            } else if (direction == 1) {
                if (horizontale)
                    xActuelle += derniereDirection;
                else
                    yActuelle += derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            } else if (direction == 2) {
                if (horizontale)
                    yActuelle += derniereDirection;
                else
                    xActuelle -= derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            }
            Cellule celluleSuivante = Ville.getCellule(positionActuelle);
            if (celluleSuivante != null && celluleSuivante.getStructure().equals(destination)) {
                directionSuivante = direction;
                break;
            }
        }
        return directionSuivante;
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

    private void ajouter() throws InterruptedException {
        cellActuelle.setOccupe(true);
        cellActuelle.add(this);
        update();
    }

    private void enlever() {
        cellActuelle.remove(this);
        cellActuelle.setOccupe(false);
        update();
    }

}
