package components.Ville.Intersection;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Feu extends JPanel {
    private Color couleur = Color.RED;
    private Color couleurBordure = Color.WHITE;

    public Feu() {
        setOpaque(false);
        addComponentListener((ComponentListener) new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                paintComponent(getGraphics());
            }
        });
    }

    public void setCouleur(String state) {
        switch (state) {
            case "GO":
                couleur = Color.GREEN;
                break;
            case "STOP":
                couleur = Color.RED;
                break;
            default:
                throw new IllegalArgumentException("Le state doit être soit \"GO\" ou \"STOP\"");
        }
        paintComponent(getGraphics());
    }

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

        g2d.setColor(couleurBordure);
        g2d.setStroke(new BasicStroke(30)); // Set the border thickness
        g2d.drawOval(x, y, diameter, diameter);
    }
}
