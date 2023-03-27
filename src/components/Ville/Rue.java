package components.Ville;

import java.awt.Color;

public class Rue extends Structure {
    public Rue(Ville ville, Coordonnee startPosition, int longueur, String orientation, Coordonnee[][] connexions) {
        super(ville, startPosition, orientation, connexions);
        this.largeur = 2;
        this.longueur = longueur;
        setBackground(Color.GRAY);
        ville.add(this);
    }

    public double multipleDimension(int nbCellules, String dimension) {
        double multiple;
        if (dimension == "LARGEUR")
            multiple = nbCellules / ville.getGridDimension().getWidth();
        else if (dimension == "LONGUEUR")
            multiple = nbCellules / ville.getGridDimension().getHeight();
        else
            throw new IllegalArgumentException(
                    "Le parametre \"dimension\" doit être donné une valeur \"LARGEUR\" ou \"LONGUEUR\"");
        return multiple;
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
