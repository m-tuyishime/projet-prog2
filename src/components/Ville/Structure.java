package components.Ville;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Structure extends JPanel {
    protected Ville ville;
    protected int largeur;
    protected int longueur;
    protected int startX;
    protected int startY;
    protected String orientation;

    public Structure(Ville ville, Coordonnee startPosition, String orientation, Coordonnee[][] connexions) {
        this.ville = ville;
        startX = startPosition.getX();
        startY = startPosition.getY();
        if (orientation == null)
            this.orientation = "VERTICAL";
        else
            this.orientation = orientation;

        dessinerStructure();

    }

    private double multipleDimension(int nbCellules, String dimension) {
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

    private void dessinerStructure() {
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
                            "L'orientation doit être soit \"VERTICAL\" ou \"HORIZONTAL\"");

                int width = (int) (ville.getWidth() * widthMultiple);
                int height = (int) (ville.getHeight() * heightMultiple);
                int startLayoutX = (int) (ville.getWidth() * multipleDimension(startX, "LARGEUR"));
                int startLayoutY = (int) (ville.getHeight() * multipleDimension(startY, "LONGUEUR"));
                setBounds(startLayoutX, startLayoutY, width, height);
            }
        });

    }
}
