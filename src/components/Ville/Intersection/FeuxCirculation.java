package components.Ville.Intersection;

import javax.swing.JPanel;

import components.Cellule.CelluleIntersection;
import components.Ville.Ville;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;
import java.util.TimerTask;

// Classe qui représente les feux de circulation
public class FeuxCirculation extends JPanel {
    // La vitesse des feux de circulation
    private int vitesseFeux = Intersection.vitesseMax * 8;
    // La grille de cellules de l'intersection
    private CelluleIntersection[][] cellules;
    // Les feux de circulation
    private Feu feuHaut = new Feu("GO");
    private Feu feuGauche = new Feu();
    private Feu feuDroite = new Feu();
    private Feu feuBas = new Feu("GO");
    // Si les feux de circulation verticaux sont vert
    private boolean goX = true;
    // Le minuteur qui gère les feux de circulation
    private Timer minuteur = new Timer();
    // La tâche qui gère les feux de circulation
    private TimerTask tache = new TimerTask() {
        public void run() {
            // Si la circulation est arrêtée, on arrête la tâche
            if (!Ville.getCirculationStatus())
                return;

            // Si les feux de circulation verticaux sont vert
            // on les fait passer au rouge
            if (goX) {
                goX = false;
                goX();
                // Sinon on les fait passer au vert
            } else {
                goX = true;
                goY();
            }
        }
    };

    public FeuxCirculation(Intersection intersection) {
        cellules = intersection.getCellules();
        cellules[0][0].setGoStatus(true);
        cellules[1][1].setGoStatus(true);
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
        minuteur.schedule(tache, 0, vitesseFeux);
    }

    // Fait passer les feux de circulation verticaux au vert
    public void goY() {
        cellules[0][1].setGoStatus(false);
        cellules[1][0].setGoStatus(false);
        feuDroite.setState("SLOW");
        feuGauche.setState("SLOW");
        minuteur.schedule(new TimerTask() {
            public void run() {
                if (!Ville.getCirculationStatus())
                    return;

                feuDroite.setState("STOP");
                feuGauche.setState("STOP");

                cellules[0][0].setGoStatus(true);
                cellules[1][1].setGoStatus(true);
                feuHaut.setState("GO");
                feuBas.setState("GO");
            }
        }, 8 * 1000);
    }

    // Fait passer les feux de circulation horizontales au vert
    public void goX() {
        cellules[0][0].setGoStatus(false);
        cellules[1][1].setGoStatus(false);
        feuHaut.setState("SLOW");
        feuBas.setState("SLOW");
        minuteur.schedule(new TimerTask() {
            public void run() {
                if (!Ville.getCirculationStatus())
                    return;

                feuHaut.setState("STOP");
                feuBas.setState("STOP");

                cellules[0][1].setGoStatus(true);
                cellules[1][0].setGoStatus(true);
                feuDroite.setState("GO");
                feuGauche.setState("GO");
            }
        }, 8 * 1000);
    }

    // organise les feux de circulation dans la grille
    public void peupler() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(feuHaut, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(feuGauche, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(feuDroite, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(feuBas, gbc);
    }
}
