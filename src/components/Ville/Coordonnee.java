package components.Ville;

import java.awt.Dimension;

public class Coordonnee {
    private int positionX;
    private int positionY;

    public Coordonnee(int x, int y) {
        Dimension dimensionVille = Ville.getGridDimension();
        int villeLargeur = dimensionVille.width;
        int villeLongueur = dimensionVille.height;
        if (x > villeLargeur)
            throw new IllegalArgumentException("La variable x dépasse la largeur de la ville");
        else if (y > villeLongueur)
            throw new IllegalArgumentException("La variable y dépasse la longueur de la ville");

        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Les variables x et y doivent être positives");
        positionX = x;
        positionY = y;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }
}
