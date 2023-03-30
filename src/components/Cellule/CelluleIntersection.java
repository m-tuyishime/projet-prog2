package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;
import components.Ville.Structure;

public class CelluleIntersection extends Cellule {
    public CelluleIntersection(Structure structure, Coordonnee position, int direction) {
        super(structure, position, direction);
        setBackground(Color.GRAY);
    }
}
