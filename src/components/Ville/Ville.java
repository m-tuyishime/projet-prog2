package components.Ville;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;

import components.Cellule.Cellule;
import components.Rue.Rue;

public class Ville extends JPanel {
    private int nbLignes = 14;
    private int nbColonnes = 20;
    private Cellule[][] grille = new Cellule[nbLignes][nbColonnes];

    // public Ville() {
    // setLayout(new GridLayout(nbLignes, nbColonnes));
    // ajouterCellules();
    // dessinerVille();
    // }

    public Ville() {
        setLayout(null);
        dessinerVille();
    }

    public Dimension getGridDimension() {
        return new Dimension(nbColonnes, nbLignes);
    }

    private Cellule getCellule(Coordonnee position) {
        return grille[position.getY()][position.getX()];
    }

    private void setCellule(Coordonnee position, Cellule nouvCellule) {
        grille[position.getX()][position.getY()] = nouvCellule;
        add(nouvCellule, position.getX(), position.getY());
        nouvCellule.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    private void ajouterCellules() {
        for (int l = 0; l < nbLignes; l++) {
            for (int c = 0; c < nbColonnes; c++) {
                Coordonnee position = new Coordonnee(l, c);
                Cellule cellule = new Cellule();
                setCellule(position, cellule);
            }
        }
    }

    private void ajouteRue(Coordonnee startPosition, int longueur, String orientation, Coordonnee[][] connexions) {
        int startX = startPosition.getX();
        int startY = startPosition.getY();
        Coordonnee position;

        for (int l = 0; l < longueur; l++) {
            for (int c = 0; c < 2; c++) {
                if (orientation == "VERTICAL")
                    position = new Coordonnee(startX + c, startY + l);
                else if (orientation == "HORIZONTAL")
                    position = new Coordonnee(startX + l, startY + c);
                else
                    throw new IllegalArgumentException(
                            "L'orientation de la rue doit être soit \"VERTICAL\" ou \"HORIZONTAL\"");

                Cellule cellule = getCellule(position);
                if (l % 2 == 0 && c != 0 && orientation == "VERTICAL")
                    cellule.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
                if (l % 2 == 0 && c != 0 && orientation == "HORIZONTAL")
                    cellule.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.WHITE));
                cellule.setBackground(Color.GRAY);
            }
        }
    }

    private void ajouteIntersection(Coordonnee startPosition, Coordonnee[][] connexions) {
        int startX = startPosition.getX();
        int startY = startPosition.getY();
        int largeur = 2;
        int longueur = largeur;
        Coordonnee position;

        for (int l = 0; l < longueur; l++) {
            for (int c = 0; c < largeur; c++) {
                position = new Coordonnee(startX + c, startY + l);
                Cellule cellule = getCellule(position);

                int top = 0, left = 0, bottom = 0, right = 0;
                int largeurBord = 5;
                if (c == 0)
                    left = largeurBord;
                if (c == largeur - 1)
                    right = largeurBord;
                if (l == longueur - 1)
                    top = largeurBord;
                if (l == 0)
                    bottom = largeurBord;
                cellule.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));
                cellule.setBackground(Color.GRAY);
            }
        }
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

        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICAL", null);
        y += longueurRue;
        // ajouteIntersection(new Coordonnee(x, y), null);
        new Rue(this, new Coordonnee(x + longueurIntersection, y), nbColonnes - (4 + x + longueurIntersection),
                "HORIZONTAL", null);
        y += longueurIntersection;
        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICAL", null);

        x = nbColonnes - 4;
        y = 0;

        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICAL", null);
        y += longueurRue;
        // ajouteIntersection(new Coordonnee(x, y), null);
        y += longueurIntersection;
        new Rue(this, new Coordonnee(x, y), longueurRue, "VERTICAL", null);

        // ajouteParking(new Coordonnee(4, 1), "VERTICAL", Color.YELLOW, null);
        // ajouteParking(new Coordonnee(nbColonnes - (4 + 2), nbLignes - (4 + 1)),
        // "VERTICAL", Color.PINK, null);
        // ajouteParking(new Coordonnee(6, longueurRue + 2), "HORIZONTAL", Color.BLUE,
        // null);
        // ajouteParking(new Coordonnee(11, longueurRue - 2), "HORIZONTAL",
        // Color.ORANGE, null);
    }
}
