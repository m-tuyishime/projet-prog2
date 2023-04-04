package components.Cellule;

import javax.swing.JPanel;

import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Structure;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

// Classe qui représente une cellule de la ville
public class Cellule extends JPanel {
    // Constantes pour la grille
    public static final GridBagLayout grid = new GridBagLayout();
    public static final GridBagConstraints constraints = new GridBagConstraints();

    // la structure qui contient la cellule
    private Structure structure;
    // la position de la cellule dans la ville
    private Coordonnee position;
    // l'occupation de la cellule
    private boolean occupeStatus;
    // la direction de la voie ou se trouve la cellule
    private int direction;
    // le parking est adjacent à la cellule
    private Parking parking;

    protected Cellule(Structure stucture, Coordonnee position, int direction) {
        // initialise les attributs
        this.structure = stucture;
        this.position = position;
        occupeStatus = false;

        // Initialise la grille
        setLayout(new GridBagLayout());

        // Création des contraintes pour la deuxième ligne
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1; // Fixe la hauteur max de la deuxième ligne a 80% du JFrame
        constraints.weightx = 1; // Fixe la longueur max de la deuxième ligne a 100% du JFrame

        setDirection(direction);
        setBackground(Color.GRAY);
    }

    // getters

    public Structure getStructure() {
        return structure;
    }

    public Coordonnee getPosition() {
        return position;
    }

    public boolean estOccupe() {
        return occupeStatus;
    }

    public int getDirection() {
        return direction;
    }

    public Parking getParking() {
        return parking;
    }

    // setters

    public void setParking(Parking parking) {
        this.parking = parking;
        // ajoute le parking à la liste des parkings adjacents à la structure
        structure.addParking(parking);
    }

    public void setOccupe(boolean occupeStatus) {
        this.occupeStatus = occupeStatus;
        // si le nouveau status est occupé, ajoute une voiture à la structure
        if (occupeStatus)
            structure.addOccupation();
        // sinon, retire une voiture de la structure
        else
            structure.removeOccupation();
    }

    private void setDirection(int direction) {
        // vérifie que la direction est valide
        if (direction != 0 && direction != -1 && direction != 1)
            throw new IllegalArgumentException(
                    "La variable \"direction\" doit être donné une valeur de 0, -1 ou 1");
        this.direction = direction;
    }
}
