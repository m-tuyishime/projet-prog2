package components.Ville;

import javax.swing.JPanel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

// Classe qui représente une structure (rue, intersection, ou parking) de la ville
public class Structure extends JPanel {
    // l'instance principale de la ville
    private Ville ville;
    // la taille de la structure en nombre de cellules
    // dans la grille de la ville
    private int tailleX, tailleY;
    // la position de la cellule en haut à gauche de la structure
    private int startIndexCellX, startIndexCellY;
    // l'orientation de la structure horizontale ou verticale
    private String orientation;
    // la largeur et la longueur de la structure
    private int largeur, longueur;
    // la vitesse maximale autorisée sur la structure
    private int vitesseMax = 1000;
    // la liste des parkings adjacents à la structure
    private ArrayList<Parking> parkings = new ArrayList<Parking>();
    // le nombre de voitures présentes sur la structure
    protected int occupation = 0;

    protected Structure(Ville ville, Coordonnee startPosition) {
        // initialise les attributs
        this.ville = ville;
        startIndexCellX = startPosition.getX();
        startIndexCellY = startPosition.getY();

    }

    // getters

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

    // setters

    public void addOccupation() {
        occupation++;
    }

    public void removeOccupation() {
        occupation--;
    }

    public void addParking(Parking parking) {
        // check si le parking n'est pas déjà dans la liste
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

    // méthode à redéfinir dans les classes enfants
    // crée les cellules qui composent la structure
    protected void peupler() {

    }

    // Définit la taille de la structure en nombre de cellules
    // dans la grille de la ville
    // dépend de l'orientation de la structure
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

    // Construit la structure et
    // l'affiche dans la ville
    protected void construire() {
        dessinerStructure();

        peupler();
        ville.add(this);
    }

    // Calcule le multiple de la dimension donnée afin de
    // convertir la taille de la ville en nombre de cellules
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

    // Permet de comparer deux structures
    @Override
    public boolean equals(Object obj) {
        // compare les intances enfants
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Structure other = (Structure) obj;
        // les attributs à comparer
        return this.tailleX == other.tailleX
                && this.tailleY == other.tailleY
                && this.startIndexCellX == other.startIndexCellX
                && this.startIndexCellY == other.startIndexCellY;
    }

    private void dessinerStructure() {
        // recalcule les bounds quand le JFrame est redimensionné
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
