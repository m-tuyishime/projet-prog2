package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import components.Cellule.Cellule;

public class Rue extends Structure {
    private int largeur;
    private String orientation;

    public Rue(Ville ville, Coordonnee startPosition, int longueur, String orientation, Coordonnee[][] connexions) {
        super(ville, startPosition, connexions);
        largeur = 2;
        this.orientation = orientation;

        if (orientation == "VERTICALE") {
            tailleX = largeur;
            tailleY = longueur;
        } else if (orientation == "HORIZONTALE") {
            tailleX = longueur;
            tailleY = largeur;
        } else
            throw new IllegalArgumentException(
                    "L'orientation de la rue doit être soit \"VERTICALE\" ou \"HORIZONTALE\"");

        setLayout(new GridLayout(tailleY, tailleX));
        peupler();
        ville.add(this);
    }

    private void peupler() {
        Coordonnee position;
        for (int y = 0; y < tailleY; y++) {
            for (int x = 0; x < tailleX; x++) {
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() + y);
                Cellule cellule = new Cellule(position);

                int tailleBordureCell = 2;
                int cellBordureDroite = 0, cellBordureGauche = 0, cellBordureHaut = 0, cellBordureBas = 0;
                if (orientation == "VERTICALE" && y % 2 == 0) {
                    if (x == 0)
                        cellBordureDroite = tailleBordureCell;
                    else if (x == 1)
                        cellBordureGauche = tailleBordureCell;
                } else if (orientation == "HORIZONTALE" && x % 2 == 0) {
                    if (y == 0)
                        cellBordureBas = tailleBordureCell;
                    else if (y == 1)
                        cellBordureHaut = tailleBordureCell;
                }

                cellule.setBorder(BorderFactory.createMatteBorder(cellBordureHaut, cellBordureGauche, cellBordureBas,
                        cellBordureDroite,
                        Color.WHITE));
                add(cellule);
            }
        }
    }

    // private void ajouteRue() {
    // int startX = startPosition.getX();
    // int startY = startPosition.getY();
    // Coordonnee position;

    // for (int l = 0; l < longueur; l++) {
    // for (int c = 0; c < 2; c++) {
    // if (orientation == "VERTICAL")
    // position = new Coordonnee(startX + c, startY + l);
    // else if (orientation == "HORIZONTAL")
    // position = new Coordonnee(startX + l, startY + c);
    // else
    // throw new IllegalArgumentException(
    // "L'orientation de la rue doit être soit \"VERTICAL\" ou \"HORIZONTAL\"");

    // Cellule cellule = getCellule(position);
    // if (l % 2 == 0 && c != 0 && orientation == "VERTICAL")
    // cellule.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
    // if (l % 2 == 0 && c != 0 && orientation == "HORIZONTAL")
    // cellule.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.WHITE));
    // cellule.setBackground(Color.GRAY);
    // }
    // }
    // }
}
