package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;

import components.Cellule.Cellule;

public class Parking extends Structure {
    private int longueur = 4;
    private int largeur = 2;
    private Color couleur;

    public Parking(Ville ville, Coordonnee startPosition, String orientation, Color couleur,
            Coordonnee[][] connexions) {
        super(ville, startPosition, null);
        this.couleur = couleur;

        setLayout(new GridLayout(getTailleY(), getTailleX()));
    }

    @Override
    protected void peupler() {
        Coordonnee position;

        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() + y);

                Cellule cellule = new Cellule(position);
                cellule.setBackground(couleur);
                add(cellule);
            }
        }
    }
}
