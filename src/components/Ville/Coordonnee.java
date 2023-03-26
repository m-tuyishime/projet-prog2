package components.Ville;

public class Coordonnee {
    private int positionX;
    private int positionY;

    public Coordonnee(int x, int y) {
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
