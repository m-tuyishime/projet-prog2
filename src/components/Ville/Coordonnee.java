package components.Ville;

public class Coordonnee {
    private int positionX;
    private int positionY;

    public Coordonnee(Coordonnee coordonnee) {
        new Coordonnee(coordonnee.getX(), getY());
    }

    public Coordonnee(int x, int y) {
        if (x >= Ville.nbColonnes)
            throw new IllegalArgumentException("La variable x dépasse la largeur de la ville");
        else if (y >= Ville.nbLignes)
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
