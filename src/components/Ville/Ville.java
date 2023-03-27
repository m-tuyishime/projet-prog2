package components.Ville;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;

import components.Cellule.Cellule;
import components.Ville.Intersection.Intersection;

public class Ville extends JPanel {
    private int nbLignes = 14;
    private int nbColonnes = 20;
    private Cellule[][] grille = new Cellule[nbLignes][nbColonnes];

    public Ville() {
        setLayout(null);
        dessinerVille();
    }

    public Dimension getGridDimension() {
        return new Dimension(nbColonnes, nbLignes);
    }

    public Cellule getCellule(Coordonnee position) {
        return grille[position.getY()][position.getX()];
    }

    public void setCellule(Coordonnee position, Cellule nouvCellule) {
        grille[position.getX()][position.getY()] = nouvCellule;
        add(nouvCellule, position.getX(), position.getY());
        nouvCellule.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    private void ajouteParking(Coordonnee startPosition, String orientation, Color couleur, Coordonnee[][] connexions) {
        int startX = startPosition.getX();
        int startY = startPosition.getY();
        Coordonnee position;

        for (int l = 0; l < 4; l++) {
            for (int c = 0; c < 2; c++) {
                if (orientation == "VERTICAL")
                    position = new Coordonnee(startX + c, startY + l);
                else if (orientation == "HORIZONTAL")
                    position = new Coordonnee(startX + l, startY + c);
                else
                    throw new IllegalArgumentException(
                            "L'orientation de la rue doit être soit \"VERTICAL\" ou \"HORIZONTAL\"");

                Cellule cellule = getCellule(position);
                cellule.setBackground(couleur);
            }
        }
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
