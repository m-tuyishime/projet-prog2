package components.Ville.Intersection;

import javax.swing.JPanel;

import components.Cellule.CelluleIntersection;
import components.Ville.Ville;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

// Classe qui représente les feux de circulation
public class FeuxCirculation extends JPanel {
    // La vitesse des feux de circulation
    private int vitesseFeux = Intersection.vitesseMax * 8;
    // La grille de cellules de l'intersection
    private ArrayList<CelluleIntersection> cellules;
    // Les feux de circulation
    private Feu[] feux = new Feu[4];

    public FeuxCirculation(Intersection intersection) {
        // On initialise les feux de circulation
        for (int i = 0; i < 4; i++)
            feux[i] = new Feu();

        // on récupère la grille de cellules de l'intersection
        cellules = intersection.getCellules();

        // Met les feux de circulation au millieu de l'intersection et
        // le redimensionne selon la taille de l'intersection
        intersection.addComponentListener((ComponentListener) new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int largeurContenu = (int) ((intersection.getWidth() - intersection.getInsets().left
                        - intersection.getInsets().right) * 0.3);
                int longueurContenu = (int) ((intersection.getHeight() - intersection.getInsets().top
                        - intersection.getInsets().bottom) * 0.4);
                setBounds((int) (intersection.getWidth() * 0.35), (int) (intersection.getHeight() * 0.3),
                        largeurContenu,
                        longueurContenu);
            }
        });

        setOpaque(false);
        setLayout(new GridBagLayout());
        peupler();

        // On démarre les feux de circulation
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    while (true) {
                        for (int i = 0; i < feux.length; i++) {
                            // Si la circulation est arrêtée, on attend
                            if (!Ville.getCirculationStatus())
                                Thread.sleep(500);

                            // On fait passer le feu de circulation de index i au vert
                            go(i);
                            // On attend la durée de la vitesse des feux de circulation
                            Thread.sleep(vitesseFeux);
                        }
                    }
                    // Si l'exception est levée, on affiche le message d'erreur
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    // Fait passer le feux de circulation de index i au vert
    // et les autres au rouge
    public void go(int i) throws InterruptedException {
        for (int o = 0; o < feux.length; o++) {
            if (o == i) {
                // met la cellule du feu au vert aussi
                cellules.get(i).setGoStatus(true);
                // met le feu de circulation en question au vert
                feux[i].setState("GO");
            } else {
                // met la première cellule de la grille au vert
                cellules.get(o).setGoStatus(false);
                // met le premier feu de circulation au vert
                feux[o].setState("STOP");
            }
        }
    }

    // organise les feux de circulation dans la grille
    public void peupler() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(feux[0], gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(feux[2], gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(feux[1], gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(feux[3], gbc);
    }
}
