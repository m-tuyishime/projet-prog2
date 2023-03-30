package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;

import components.Cellule.CelluleParking;

public class Parking extends Structure {
    private int longueur = 4;
    private int largeur = 2;
    private Color couleur;

    public Parking(Ville ville, Coordonnee startPosition, String orientation, Color couleur) {
        super(ville, startPosition);
        this.couleur = couleur;
        setOrientation(orientation);
        setLargeLongueur(largeur, longueur);
        setLayout(new GridLayout(getTailleY(), getTailleX()));
        construire();
    }

    @Override
    protected void peupler() {
        Coordonnee position;

        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() + y);

                CelluleParking cellule = new CelluleParking(this, position, couleur);
                add(cellule);
                Ville.setCellule(position, cellule);
            }
        }
    }
}
