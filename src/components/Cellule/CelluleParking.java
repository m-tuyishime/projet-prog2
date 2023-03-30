package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;
import components.Ville.Structure;

public class CelluleParking extends Cellule {
    public CelluleParking(Structure structure, Coordonnee position, Color couleur) {
        super(structure, position, 0);
        setBackground(couleur);
    }
}
