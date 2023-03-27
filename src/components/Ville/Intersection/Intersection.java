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
        super(vile, startPosition, null, connexions);
        largeur = 2;
        longueur = largeur;

        setLayout(null);

        peuplerArrierePlan();
        add(arrierePlan);

        int largeurBorder = 5;
        setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                Color.WHITE));
        ville.add(this);
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

        arrierePlan.setLayout(new GridLayout(longueur, largeur));

        Coordonnee position;
        for (int l = 0; l < longueur; l++) {
            for (int c = 0; c < largeur; c++) {
                position = new Coordonnee(startX + c, startY + l);
                Cellule cellule = new Cellule(position);
                arrierePlan.add(cellule, l, c);
            }
        }
    }
}
