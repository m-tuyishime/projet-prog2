package components.Ville.Intersection;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import components.Cellule.CelluleIntersection;
import components.Ville.Coordonnee;
import components.Ville.Structure;
import components.Ville.Ville;

public class Intersection extends Structure {
    public static final int vitesseMax = 2000;
    public static final int maxRotations = 2;
    public static final int largeur = 2;
    private JPanel arrierePlan;
    private CelluleIntersection[][] cellules = new CelluleIntersection[largeur][largeur];

    public Intersection(Ville ville, Coordonnee startPosition) {
        super(ville, startPosition);
        setVitesseMax(vitesseMax);
        arrierePlan = new JPanel();

        setOrientation("VERTICALE");
        setLargeLongueur(largeur, largeur);
        setLayout(null);

        int largeurBorder = 4;
        setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                Color.WHITE));

        construire();
    }

    public CelluleIntersection[][] getCellules() {
        return cellules;
    }

    @Override
    protected void peupler() {
        peuplerArrierePlan();
        add(arrierePlan);
        FeuxCirculation feuxCirculation = new FeuxCirculation(this);
        add(feuxCirculation);
        setComponentZOrder(feuxCirculation, 0);
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
                CelluleIntersection cellule = new CelluleIntersection(this, position, 0);
                arrierePlan.add(cellule);
                cellules[y][x] = cellule;
                Ville.setCellule(position, cellule);
            }
        }
    }
}
