package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;

public class CelluleIntersection extends Cellule {
    public CelluleIntersection(Coordonnee position, int direction) {
        super(position, direction);
        setBackground(Color.GRAY);
    }
}
