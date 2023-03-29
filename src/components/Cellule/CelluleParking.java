package components.Cellule;

import java.awt.Color;

import components.Ville.Coordonnee;

public class CelluleParking extends Cellule {
    public CelluleParking(Coordonnee position, Color couleur) {
        super(position, 0);
        setBackground(couleur);
    }
}
