package components.Cellule;

import javax.swing.JPanel;

import components.Ville.Coordonnee;

import java.awt.Color;
import java.util.Random;

public class Cellule extends JPanel {
    private Coordonnee position;
    private boolean goStatus;
    private boolean occupeStatus;
    private int direction;

    protected Cellule(Coordonnee position, int direction) {
        this.position = position;
        goStatus = true;
        occupeStatus = false;
        setVoie(direction);
        setBackground(Color.GRAY);
    }

    public Coordonnee getPosition() {
        return position;
    }

    public boolean getGoStatus() {
        return goStatus;
    }

    public boolean getOccupeStatus() {
        return occupeStatus;
    }

    public int getDirection() {
        return direction;
    }

    public void setGoStatus(boolean goStatus) {
        this.goStatus = goStatus;
    }

    public void setOccupeStatus(boolean occupeStatus) {
        this.occupeStatus = occupeStatus;
    }

    private void setVoie(int direction) {
        if (direction != 0 && direction != -1 && direction != 1)
            throw new IllegalArgumentException(
                    "La variable \"direction\" doit être donné une valeur de 0, -1 ou 1");
        this.direction = direction;
    }
}
