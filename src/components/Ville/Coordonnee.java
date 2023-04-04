package components.Ville;

// Classe qui représente une coordonnée dans la ville
public class Coordonnee {
    private int positionX;
    private int positionY;

    public Coordonnee(int x, int y) {
        // vérifie que les coordonnées sont valides
        if (x >= Ville.nbColonnes)
            throw new IllegalArgumentException("La variable x dépasse la largeur de la ville");
        else if (y >= Ville.nbLignes)
            throw new IllegalArgumentException("La variable y dépasse la longueur de la ville");

        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Les variables x et y doivent être positives");

        // initialise les coordonnées
        positionX = x;
        positionY = y;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }

    // permet de comparer deux coordonnées par leur position x et y
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Coordonnee)) {
            return false;
        }

        Coordonnee coordonnee = (Coordonnee) obj;
        return positionX == coordonnee.getX() && positionY == coordonnee.getY();
    }
}
