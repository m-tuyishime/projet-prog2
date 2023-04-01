package components.Ville;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Structure extends JPanel {
    private Ville ville;
    private int tailleX, tailleY;
    private int startIndexCellX, startIndexCellY;
    private String orientation;
    private int largeur, longueur;
    private int vitesseMax = 1000;

    protected Structure(Ville ville, Coordonnee startPosition) {
        this.ville = ville;
        startIndexCellX = startPosition.getX();
        startIndexCellY = startPosition.getY();
    }

    protected Ville getVille() {
        return ville;
    }

    protected int getTailleY() {
        return tailleY;
    }

    protected int getTailleX() {
        return tailleX;
    }

    protected int getStartIndexCellX() {
        return startIndexCellX;
    }

    protected int getStartIndexCellY() {
        return startIndexCellY;
    }

    public String getOrientation() {
        return orientation;
    }

    public int getVitesseMax() {
        return vitesseMax;
    }

    protected void setVitesseMax(int vitesseMax) {
        this.vitesseMax = vitesseMax;
    }

    protected void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    protected void setLargeLongueur(int largeur, int longueur) {
        this.largeur = largeur;
        this.longueur = longueur;
        setTailles();
    }

    protected void peupler() {

    }

    private void setTailles() {
        if (orientation == "VERTICALE") {
            tailleX = largeur;
            tailleY = longueur;
        } else if (orientation == "HORIZONTALE") {
            tailleX = longueur;
            tailleY = largeur;
        } else
            throw new IllegalArgumentException(
                    "L'orientation doit être soit \"VERTICALE\" ou \"HORIZONTALE\"");
    }

    protected void construire() {
        dessinerStructure();

        peupler();
        ville.add(this);
    }

    private double multipleDimension(int nbCellules, String dimension) {
        double multiple;
        if (dimension == "LARGEUR")
            multiple = nbCellules / (double) Ville.nbColonnes;
        else if (dimension == "LONGUEUR")
            multiple = nbCellules / (double) Ville.nbLignes;
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
