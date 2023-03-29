package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;

public class CelluleRue extends Cellule {
    public CelluleRue(Coordonnee position, int direction) {
        super(position, direction);
        setBackground(Color.GRAY);
    }
}