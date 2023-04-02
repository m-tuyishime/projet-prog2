package components.Ville;

import javax.swing.JPanel;

import java.awt.Color;

import components.Cellule.Cellule;
import components.Ville.Intersection.Intersection;

public class Ville extends JPanel {
    // Si les voitures bougent
    private static boolean circulation = true;
    private static boolean reset = false;
    private static int nombreVoitures = 0;

    public static final int nbLignes = 14;
    public static final int nbColonnes = 20;
    private static Cellule[][] cellules = new Cellule[nbLignes][nbColonnes];

    public static final Coordonnee[] entrees = {
            new Coordonnee(2, 0),
            new Coordonnee(0, 7),
            new Coordonnee(3, 13),
            new Coordonnee(16, 0),
            new Coordonnee(19, 6),
            new Coordonnee(17, 13)
    };
    public static final Coordonnee[] sorties = {
            new Coordonnee(3, 0),
            new Coordonnee(0, 6),
            new Coordonnee(2, 13),
            new Coordonnee(17, 0),
            new Coordonnee(19, 7),
            new Coordonnee(16, 13)
    };

    public Ville() {
        setLayout(null);
        dessinerVille();
    }

    public static Cellule getCellule(Coordonnee position) {
        try {
            return cellules[position.getY()][position.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static int getNombreVoitures() {
        return nombreVoitures;
    }

    public static boolean getResetStatus() {
        return reset;
    }

    public static boolean getCirculationStatus() {
        return circulation;
    }

    public static Coordonnee getEntree(Coordonnee sortie) {
        if (!estSortie(sortie))
            throw new IllegalArgumentException("La coordonnée donnée n'est pas une sortie");
        Cellule cellule = getCellule(sortie);
        Structure structure = cellule.getStructure();
        if (structure.getOrientation() == "HORIZONTALE") {
            int x = nbColonnes - 1;
            if (sortie.getX() == x)
                x = 0;
            return new Coordonnee(x, sortie.getY());
        } else {
            int y = nbLignes - 1;
            if (sortie.getY() == y)
                y = 0;
            return new Coordonnee(sortie.getX(), y);
        }
    }

    public static void setNombreVoitures(int nombre) {
        nombreVoitures = nombre;
    }

    public static void setResetStatus(boolean status) {
        reset = status;
    }

    public static void setCirculationStatus(boolean status) {
        circulation = status;
    }

    public static void setCellule(Coordonnee position, Cellule cellule) {
        try {
            cellules[position.getY()][position.getX()] = cellule;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("La position de cellule donnée ne fait pas partie de la grille");
        }
    }

    public static boolean estSortie(Coordonnee position) {
        boolean trouvee = false;

        for (int i = 0; i < sorties.length; i++) {
            if (sorties[i].equals(position)) {
                trouvee = true;
                break;
            }
        }

        return trouvee;
    }

    private void dessinerVille() {
        new Rue(this, new Coordonnee(2, 0), 6, "VERTICALE");
        new Rue(this, new Coordonnee(0, 6), 2, "HORIZONTALE");
        new Rue(this, new Coordonnee(4, 6), 12, "HORIZONTALE");
        new Rue(this, new Coordonnee(2, 8), 6, "VERTICALE");

        new Rue(this, new Coordonnee(16, 0), 6, "VERTICALE");
        new Rue(this, new Coordonnee(18, 6), 2, "HORIZONTALE");
        new Rue(this, new Coordonnee(16, 8), 6, "VERTICALE");

        new Intersection(this, new Coordonnee(2, 6));
        new Intersection(this, new Coordonnee(16, 6));

        new Parking(this, new Coordonnee(4, 1), "VERTICALE", new Color(123, 50, 250));
        new Parking(this, new Coordonnee(14, 10),
                "VERTICALE", Color.PINK);
        // new Parking(this, new Coordonnee(8, 8), "HORIZONTALE", Color.BLUE);
        new Parking(this, new Coordonnee(11, 4), "HORIZONTALE", Color.ORANGE);
    }

}
