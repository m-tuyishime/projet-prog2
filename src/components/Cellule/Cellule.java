package components.Cellule;

import javax.swing.JPanel;

import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Structure;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Cellule extends JPanel {
    public static final GridBagLayout grid = new GridBagLayout();
    public static final GridBagConstraints constraints = new GridBagConstraints();

    private Structure structure;
    private Coordonnee position;
    private boolean occupeStatus;
    private int direction;
    private Parking parking;

    protected Cellule(Structure stucture, Coordonnee position, int direction) {
        this.structure = stucture;
        this.position = position;
        occupeStatus = false;

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

    public void setParking(Parking parking) {
        this.parking = parking;
        structure.addParking(parking);
    }

    public void setOccupe(boolean occupeStatus) {
        this.occupeStatus = occupeStatus;
        if (occupeStatus)
            structure.addOccupation();
        else
            structure.removeOccupation();
    }

    private void setDirection(int direction) {
        if (direction != 0 && direction != -1 && direction != 1)
            throw new IllegalArgumentException(
                    "La variable \"direction\" doit être donné une valeur de 0, -1 ou 1");
        this.direction = direction;
    }
}
