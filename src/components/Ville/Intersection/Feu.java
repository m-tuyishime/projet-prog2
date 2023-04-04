package components.Ville.Intersection;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

// Classe qui représente un feu de circulation
public class Feu extends JPanel {
    private Color couleur = Color.RED;

    // Constructeur qui prend initialise directement la couleur du feu
    public Feu(String state) {
        this();
        setFirstState(state);
    }

    // Constructeur par défaut
    public Feu() {
        setOpaque(false);
        addComponentListener((ComponentListener) new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                paintComponent(getGraphics());
            }
        });
    }

    // Change la couleur du feu dependant de l'état
    public void setFirstState(String state) {
        switch (state) {
            case "GO":
                couleur = Color.GREEN;
                break;
            case "SLOW":
                couleur = Color.YELLOW;
                break;
            case "STOP":
                couleur = Color.RED;
                break;
            default:
                throw new IllegalArgumentException("Le state doit être soit \"GO\", \"SLOW\" ou \"STOP\"");
        }
    }

    // Change la couleur du feu et le redessine
    public void setState(String state) {
        setFirstState(state);
        paintComponent(getGraphics());
    }

    // Dessine le cercle de couleur qui représente le feu
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g2d.setColor(couleur);
        g2d.fillOval(x, y, diameter, diameter);

        g2d.drawOval(x, y, diameter, diameter);
    }
}
