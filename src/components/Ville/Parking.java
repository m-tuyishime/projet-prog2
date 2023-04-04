package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;

import components.Cellule.Cellule;
import components.Cellule.CelluleParking;
import components.Ville.Intersection.Intersection;

// Classe qui représente un parking
public class Parking extends Structure {
    // nombre maximum de temps que la voiture peut rester dans le parking
    public static final int maxTemps = 1000 * 30;
    // la largeur et la longueur du parking
    private int longueur = 4;
    private int largeur = 2;
    // la couleur du parking
    private Color couleur;
    // la capacité du parking
    private static int capacite = 4;
    // le nombre de places réservées par le serveur
    private int reservations = 0;
    // Si il reste des places libres dans le parking
    private boolean libre = true;
    // la direction de la voie adjacente au parking
    private int direction = 0;

    public Parking(Ville ville, Coordonnee startPosition, String orientation, Color couleur) {
        super(ville, startPosition);
        this.couleur = couleur;
        setOrientation(orientation);
        setLargeLongueur(largeur, longueur);
        setLayout(new GridLayout(getTailleY(), getTailleX()));
        construire();
        updateLibreStatus();
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getDirection() {
        return direction;
    }

    public boolean estLibre() {
        return libre;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int nouvReservations) {
        // On ne peut pas avoir un nombre de réservations négatif
        if (nouvReservations >= 0)
            reservations = nouvReservations;
    }

    // met à jour la variable libre en fonction de la capacité et du nombre de
    // réservations
    private void updateLibreStatus() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Si la capacité est atteinte, le parking devient rouge
                if (occupation >= capacite) {
                    libre = false;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.RED));
                    // Si la capacité + le nombre de réservations est atteinte, le parking
                    // devient jaune
                } else if (occupation + reservations >= capacite) {
                    libre = false;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.YELLOW));
                    // Sinon, le parking est vert
                } else {
                    libre = true;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.GREEN));
                }
            }
        };

        // On met à jour la variable libre toutes les 500ms
        Timer timer = new Timer();
        timer.schedule(task, 0, 500);
    }

    // Redéfini la méthode qui crée les cellules
    // qui composent le parking
    @Override
    protected void peupler() {
        Coordonnee position;

        // itere sur chaque case pour créer les cellules
        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                int xActuelle = getStartIndexCellX() + x;
                int yActuelle = getStartIndexCellY() + y;
                position = new Coordonnee(xActuelle, yActuelle);

                // Crée la cellule et l'ajoute à la ville
                CelluleParking cellule = new CelluleParking(this, position, couleur);
                add(cellule);
                Ville.setCellule(position, cellule);

                // Si la cellule est sur le bord du parking, on cherche la cellule adjacente
                Cellule[] cellulesAutour = new Cellule[4];
                if (xActuelle - 1 > 0)
                    cellulesAutour[0] = Ville.getCellule(new Coordonnee(xActuelle - 1, yActuelle));
                if (yActuelle + 1 < Ville.nbLignes)
                    cellulesAutour[1] = Ville.getCellule(new Coordonnee(xActuelle, yActuelle + 1));
                if (xActuelle + 1 < Ville.nbColonnes)
                    cellulesAutour[2] = Ville.getCellule(new Coordonnee(xActuelle + 1, yActuelle));
                if (yActuelle - 1 < 0)
                    cellulesAutour[3] = Ville.getCellule(new Coordonnee(xActuelle, yActuelle - 1));

                // Si la cellule adjacente est une rue ou une intersection, on la relie au
                // parking
                for (Cellule celluleAutour : cellulesAutour) {
                    if ((celluleAutour != null) && (celluleAutour.getStructure() instanceof Rue
                            || celluleAutour.getStructure() instanceof Intersection)) {
                        celluleAutour.setParking(this);
                        direction = celluleAutour.getDirection();
                    }
                }
            }
        }
    }
}
