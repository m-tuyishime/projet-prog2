package components.Ville.Intersection;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class FeuxCirculation extends JPanel {
    private Feu feuHaut = new Feu();
    private Feu feuGauche = new Feu();
    private Feu feuDroite = new Feu();
    private Feu feuBas = new Feu();

    public FeuxCirculation(Intersection intersection) {
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
