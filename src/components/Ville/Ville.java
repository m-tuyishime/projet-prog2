package components.Ville;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import components.Cellule.Cellule;
import components.Ville.Intersection.Intersection;

public class Ville extends JPanel {
    private static int nbLignes = 14;
    private static int nbColonnes = 20;
    private Cellule[][] cellules = new Cellule[nbLignes][nbColonnes];

    public Ville() {
        setLayout(null);
        dessinerVille();
    }

    public static Dimension getGridDimension() {
        return new Dimension(nbColonnes, nbLignes);
    }

    public Cellule getCellule(Coordonnee position) {
        return cellules[position.getY()][position.getX()];
    }

    public void setCellule(Coordonnee position, Cellule cellule) {
        cellules[position.getY()][position.getX()] = cellule;
    }

    private void dessinerVille() {
        int x = 2, y = 0;
        int longueurRue = 6;
        int longueurIntersection = 2;

        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICALE", null);
        y += longueurRue;
        new Intersection(this, new Coordonnee(x, y), null);
        new Rue(this, new Coordonnee(x + longueurIntersection, y), nbColonnes - (4 + x + longueurIntersection),
                "HORIZONTALE", null);
        y += longueurIntersection;
        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICALE", null);

        x = nbColonnes - 4;
        y = 0;

        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICALE", null);
        y += longueurRue;
        new Intersection(this, new Coordonnee(x, y), null);
        y += longueurIntersection;
        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICALE", null);

        new Parking(this, new Coordonnee(4, 1), "VERTICALE", Color.YELLOW, null);
        new Parking(this, new Coordonnee(nbColonnes - (4 + 2), nbLignes - (4 + 1)),
                "VERTICALE", Color.PINK, null);
        new Parking(this, new Coordonnee(6, longueurRue + 2), "HORIZONTALE", Color.BLUE,
                null);
        new Parking(this, new Coordonnee(11, longueurRue - 2), "HORIZONTALE",
                Color.ORANGE, null);
    }
}
