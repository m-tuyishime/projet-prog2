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

public class FeuxCirculation extends JPanel {
    private int vitesseFeux = Intersection.vitesseMax * 8;
    private CelluleIntersection[][] cellules;
    private Feu feuHaut = new Feu();
    private Feu feuGauche = new Feu();
    private Feu feuDroite = new Feu();
    private Feu feuBas = new Feu();
    private boolean goX = false;
    private Timer minuteur = new Timer();
    private TimerTask tache = new TimerTask() {
        public void run() {
            if (!Ville.getCirculationStatus())
                return;

            if (goX) {
                goX = false;
                goX();
            } else {
                goX = true;
                goY();
            }
        }
    };

    public FeuxCirculation(Intersection intersection) {
        cellules = intersection.getCellules();
        // Met les feux de circulation au millieu de l'intersection
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
        minuteur.schedule(tache, 0, vitesseFeux);
    }

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
        }, (4) * 1000);
    }

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
        }, (4) * 1000);
    }

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
