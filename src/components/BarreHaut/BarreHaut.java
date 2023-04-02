package components.BarreHaut;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JTextField;

import components.Ville.Ville;
import components.Voiture.Voiture;

public class BarreHaut extends JPanel {
    JTextField val1;
    JLabel lab1, lab2, lab3;
    JButton start, pause, reset, aj, en;

    public BarreHaut() {
        setBackground(Color.darkGray);
        setLayout(null);// Organisation des composantes. En envoyant null on dit qu'il n'y aura pas de
                        // gestionnaire et qu'on organisera les composantes de façon manuelle.

        val1 = new JTextField();
        val1.setText(Voiture.getTauxRecherche() + "");
        start = new JButton("start");
        pause = new JButton("pause");
        reset = new JButton("reset");
        aj = new JButton("ajouter voiture");
        en = new JButton("enlever voiture");
        lab1 = new JLabel("Veuillez entrer un taux de recherche:");
        lab2 = new JLabel("Le taux de recherche entré est de: 0");
        lab3 = new JLabel("Nombre de voitures: 0");
        lab1.setForeground(Color.red);
        lab2.setForeground(Color.red);
        lab3.setForeground(Color.red);

        lab1.setBounds(0, 0, 210, 30);
        val1.setBounds(210, 0, 30, 30);
        lab2.setBounds(0, 50, 300, 40);
        lab3.setBounds(0, 100, 300, 40);
        start.setBounds(250, 0, 100, 40);
        pause.setBounds(250, 50, 100, 40);
        reset.setBounds(350, 0, 100, 40);
        aj.setBounds(500, 0, 200, 40);
        en.setBounds(500, 50, 200, 40);

        add(val1);
        add(start);
        add(lab1);
        add(lab2);
        add(lab3);
        add(pause);
        add(reset);
        add(aj);
        add(en);

        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    double nouvTaux = Double.parseDouble(val1.getText());
                    Voiture.setTauxRecherche(nouvTaux);
                    lab2.setText("Le taux de recherche entré est de: " + (Voiture.getTauxRecherche()));
                } catch (Exception e) {
                }

                if (Ville.getCirculationStatus())
                    return;

                if (Ville.getResetStatus()) {
                    Ville.setNombreVoitures(Ville.getNombreVoitures() + 1);
                    lab3.setText("Nombre de voitures: " + (Ville.getNombreVoitures()));
                    Ville.setResetStatus(false);

                    nouvVoiture();
                }

                Ville.setCirculationStatus(true);
            }
        });

        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                Ville.setCirculationStatus(false);
            }
        });

        reset.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                Ville.setResetStatus(true);
                Ville.setCirculationStatus(false);
                Ville.setNombreVoitures(0);
                lab3.setText("Nombre de voitures: " + (Ville.getNombreVoitures()));
            }
        });

        aj.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                if (!Ville.getCirculationStatus() || Ville.getResetStatus())
                    return;

                Ville.setNombreVoitures(Ville.getNombreVoitures() + 1);
                lab3.setText("Nombre de voitures: " + (Ville.getNombreVoitures()));
                nouvVoiture();
            }
        });
        en.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                if (Ville.getNombreVoitures() <= 0)
                    return;

                Ville.setNombreVoitures(Ville.getNombreVoitures() - 1);
                lab3.setText("Nombre de voitures: " + (Ville.getNombreVoitures()));
            }
        });
    }

    private void nouvVoiture() {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    new Voiture();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
