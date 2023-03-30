package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;
import components.Ville.Structure;

public class CelluleRue extends Cellule {
    public CelluleRue(Structure structure, Coordonnee position, int direction) {
        super(structure, position, direction);
        setBackground(Color.GRAY);
    }
}