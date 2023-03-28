package components.Ville;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import components.Cellule.Cellule;
import components.Ville.Intersection.Intersection;

public class Ville extends JPanel {
    private int nbLignes = 14;
    private int nbColonnes = 20;

    public Ville() {
        setLayout(null);
        dessinerVille();
    }

    public Dimension getGridDimension() {
        return new Dimension(nbColonnes, nbLignes);
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

        // ajouteParking(new Coordonnee(4, 1), "VERTICALE", Color.YELLOW, null);
        // ajouteParking(new Coordonnee(nbColonnes - (4 + 2), nbLignes - (4 + 1)),
        // "VERTICALE", Color.PINK, null);
        // ajouteParking(new Coordonnee(6, longueurRue + 2), "HORIZONTAL", Color.BLUE,
        // null);
        // ajouteParking(new Coordonnee(11, longueurRue - 2), "HORIZONTAL",
        // Color.ORANGE, null);
    }
}
