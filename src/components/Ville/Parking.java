package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;

import components.Cellule.Cellule;
import components.Cellule.CelluleParking;
import components.Ville.Intersection.Intersection;

public class Parking extends Structure {
    public static final int maxTemps = 1000 * 30;
    private int longueur = 4;
    private int largeur = 2;
    private Color couleur;
    private static int capacite = 4;
    private int reservations = 0;
    private boolean libre = true;
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
        if (nouvReservations >= 0)
            reservations = nouvReservations;
    }

    private void updateLibreStatus() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (occupation >= capacite) {
                    libre = false;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.RED));
                } else if (occupation + reservations >= capacite) {
                    libre = false;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.YELLOW));
                } else {
                    libre = true;
                    int largeurBorder = 4;
                    setBorder(
                            BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                                    Color.GREEN));
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 500);
    }

    @Override
    protected void peupler() {
        Coordonnee position;

        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                int xActuelle = getStartIndexCellX() + x;
                int yActuelle = getStartIndexCellY() + y;
                position = new Coordonnee(xActuelle, yActuelle);

                CelluleParking cellule = new CelluleParking(this, position, couleur);
                add(cellule);
                Ville.setCellule(position, cellule);

                Cellule[] cellulesAutour = new Cellule[4];
                if (xActuelle - 1 > 0)
                    cellulesAutour[0] = Ville.getCellule(new Coordonnee(xActuelle - 1, yActuelle));
                if (yActuelle + 1 < Ville.nbLignes)
                    cellulesAutour[1] = Ville.getCellule(new Coordonnee(xActuelle, yActuelle + 1));
                if (xActuelle + 1 < Ville.nbColonnes)
                    cellulesAutour[2] = Ville.getCellule(new Coordonnee(xActuelle + 1, yActuelle));
                if (yActuelle - 1 < 0)
                    cellulesAutour[3] = Ville.getCellule(new Coordonnee(xActuelle, yActuelle - 1));

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
