package components.Ville;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Structure extends JPanel {
    protected Ville ville;
    protected int tailleX;
    protected int tailleY;
    protected int startIndexCellX;
    protected int startIndexCellY;

    public Structure(Ville ville, Coordonnee startPosition, Coordonnee[][] connexions) {
        this.ville = ville;
        startIndexCellX = startPosition.getX();
        startIndexCellY = startPosition.getY();

        dessinerStructure();

    }

    public int getTailleY() {
        return tailleY;
    }

    public int getStartIndexCellX() {
        return startIndexCellX;
    }

    public int getStartIndexCellY() {
        return startIndexCellY;
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
                int width = (int) (ville.getWidth() * multipleDimension(tailleX, "LARGEUR"));
                int height = (int) (ville.getHeight() * multipleDimension(tailleY, "LONGUEUR"));
                int startLayoutX = (int) (ville.getWidth() * multipleDimension(startIndexCellX, "LARGEUR"));
                int startLayoutY = (int) (ville.getHeight() * multipleDimension(startIndexCellY, "LONGUEUR"));
                setBounds(startLayoutX, startLayoutY, width, height);
            }
        });

    }
}
