package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;
import components.Ville.Structure;

public class CelluleIntersection extends Cellule {
    private boolean goStatus;

    public CelluleIntersection(Structure structure, Coordonnee position, int direction) {
        super(structure, position, direction);
        goStatus = false;
        setBackground(Color.GRAY);
    }

    public boolean getGoStatus() {
        return goStatus;
    }

    public void setGoStatus(boolean goStatus) {
        this.goStatus = goStatus;
    }
}
