package components.Ville.Intersection;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import components.Cellule.Cellule;
import components.Ville.Coordonnee;
import components.Ville.Structure;
import components.Ville.Ville;

public class Intersection extends Structure {
    private JPanel arrierePlan = new JPanel();

    public Intersection(Ville vile, Coordonnee startPosition, Coordonnee[][] connexions) {
        super(vile, startPosition, connexions);
        setOrientation("VERTICALE");
        setLargeLongueur(2, 2);

        setLayout(null);

        int largeurBorder = 4;
        setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                Color.WHITE));

        construire();
    }

    @Override
    protected void peupler() {
        peuplerArrierePlan();
        add(arrierePlan);
    }

    private void peuplerArrierePlan() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int largeurContenu = getWidth() - getInsets().left - getInsets().right;
                int longueurContenu = getHeight() - getInsets().top - getInsets().bottom;
                arrierePlan.setBounds(getInsets().left, getInsets().top, largeurContenu, longueurContenu);
            }
        });

        arrierePlan.setLayout(new GridLayout(getTailleY(), getTailleX()));

        Coordonnee position;
        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() +
                        y);
                Cellule cellule = new Cellule(position);
                arrierePlan.add(cellule, y, x);
            }
        }
    }
}
