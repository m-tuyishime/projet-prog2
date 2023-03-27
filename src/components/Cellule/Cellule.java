package components.Cellule;

import javax.swing.JPanel;

import components.Ville.Coordonnee;

import java.awt.Color;
import java.util.Random;

public class Cellule extends JPanel {
    public Cellule(Coordonnee position) {
        setBackground(Color.BLACK);
    }

    public static Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }
}
