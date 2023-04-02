package components.Ville;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class Structure extends JPanel {
    private Ville ville;
    private int tailleX, tailleY;
    private int startIndexCellX, startIndexCellY;
    private String orientation;
    private int largeur, longueur;
    private int vitesseMax = 1000;
    private ArrayList<Parking> parkings = new ArrayList<Parking>();
    protected int occupation = 0;

    protected Structure(Ville ville, Coordonnee startPosition) {
        this.ville = ville;
        startIndexCellX = startPosition.getX();
        startIndexCellY = startPosition.getY();

    }

    protected Ville getVille() {
        return ville;
    }

    public int getTailleY() {
        return tailleY;
    }

    public int getTailleX() {
        return tailleX;
    }

    public int getStartIndexCellX() {
        return startIndexCellX;
    }

    public int getStartIndexCellY() {
        return startIndexCellY;
    }

    public String getOrientation() {
        return orientation;
    }

    public int getVitesseMax() {
        return vitesseMax;
    }

    public ArrayList<Parking> getParkings() {
        return parkings;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getOccupation() {
        return occupation;
    }

    public void addOccupation() {
        occupation++;
    }

    public void removeOccupation() {
        occupation--;
    }

    public void addParking(Parking parking) {
        if (!parkings.contains(parking))
            parkings.add(parking);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Structure other = (Structure) obj;
        return this.tailleX == other.tailleX
                && this.tailleY == other.tailleY
                && this.startIndexCellX == other.startIndexCellX
                && this.startIndexCellY == other.startIndexCellY;
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
