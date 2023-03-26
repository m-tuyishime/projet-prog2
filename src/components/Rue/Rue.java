package components.Rue;

import java.awt.Color;

import javax.swing.JPanel;

import components.Ville.Coordonnee;
import components.Ville.Ville;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Rue extends JPanel {
    private Ville ville;
    private int largeur;
    private int longueur;
    private int startX;
    private int startY;
    private String orientation;

    public Rue(Ville ville, Coordonnee startPosition, int longueur, String orientation, Coordonnee[][] connexions) {
        this.ville = ville;
        this.largeur = 2;
        this.longueur = longueur;
        startX = startPosition.getX();
        startY = startPosition.getY();
        this.orientation = orientation;

        dessinerRue();

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

    public void dessinerRue() {
        // Calcule les bounds quand le JFrame est redimensionné
        ville.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                double widthMultiple;
                double heightMultiple;
                if (orientation == "VERTICAL") {
                    widthMultiple = multipleDimension(largeur, "LARGEUR");
                    heightMultiple = multipleDimension(longueur, "LONGUEUR");
                } else if (orientation == "HORIZONTAL") {
                    widthMultiple = multipleDimension(longueur, "LARGEUR");
                    heightMultiple = multipleDimension(largeur, "LONGUEUR");
                } else
                    throw new IllegalArgumentException(
                            "L'orientation de la rue doit être soit \"VERTICAL\" ou \"HORIZONTAL\"");

                int width = (int) (ville.getWidth() * widthMultiple);
                int height = (int) (ville.getHeight() * heightMultiple);
                int startLayoutX = (int) (ville.getWidth() * multipleDimension(startX, "LARGEUR"));
                int startLayoutY = (int) (ville.getHeight() * multipleDimension(startY, "LONGUEUR"));
                setBounds(startLayoutX, startLayoutY, width, height);
            }
        });

        setBackground(Color.GRAY);
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
