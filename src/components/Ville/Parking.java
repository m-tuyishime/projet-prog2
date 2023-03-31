package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;

import components.Cellule.Cellule;
import components.Cellule.CelluleParking;
import components.Ville.Intersection.Intersection;

public class Parking extends Structure {
    public static final int maxTemps = 1000 * 30;
    private int longueur = 4;
    private int largeur = 2;
    private Color couleur;
    private static int capacite = 4;
    private int occupation = 0;

    public Parking(Ville ville, Coordonnee startPosition, String orientation, Color couleur) {
        super(ville, startPosition);
        this.couleur = couleur;
        setOrientation(orientation);
        setLargeLongueur(largeur, longueur);
        setLayout(new GridLayout(getTailleY(), getTailleX()));
        construire();
    }

    public void addOccupation() {
        occupation++;
    }

    public void removeOccupation() {
        occupation--;
    }

    @Override
    protected void peupler() {
        Coordonnee position;

        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                int xActuelle = getStartIndexCellX() + x;
                int yActuelle = getStartIndexCellY() + y;
                position = new Coordonnee(xActuelle, yActuelle);

                CelluleParking cellule = new CelluleParking(this, position, couleur);
                add(cellule);
                Ville.setCellule(position, cellule);

                Cellule[] cellulesAutour = {
                        Ville.getCellule(new Coordonnee(xActuelle - 1, yActuelle)),
                        Ville.getCellule(new Coordonnee(xActuelle, yActuelle + 1)),
                        Ville.getCellule(new Coordonnee(xActuelle + 1, yActuelle)),
                        Ville.getCellule(new Coordonnee(xActuelle, yActuelle - 1))
                };
                for (Cellule celluleAutour : cellulesAutour) {
                    if ((celluleAutour != null) && (celluleAutour.getStructure() instanceof Rue
                            || celluleAutour.getStructure() instanceof Intersection))
                        celluleAutour.setParking(this);
                }
            }
        }
    }
}
