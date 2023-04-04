package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;
import components.Ville.Structure;

// Classe qui représente une cellule d'intersection
public class CelluleIntersection extends Cellule {
    // si le feu est vert pour la cellule
    private boolean goStatus = false;

    public CelluleIntersection(Structure structure, Coordonnee position, int direction) {
        super(structure, position, direction);
        goStatus = false;
        // initialise la couleur de la cellule
        setBackground(Color.GRAY);
    }

    public boolean estGo() {
        return goStatus;
    }

    public void setGoStatus(boolean goStatus) {
        this.goStatus = goStatus;
    }
}
