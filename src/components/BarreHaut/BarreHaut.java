package components.BarreHaut;

import java.awt.Color;

import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BarreHaut extends JPanel {
    JTextField val1;
    JLabel lab1, lab2;
    JButton start, pause, reset, aj, en;

    public BarreHaut() {
        setBackground(Color.darkGray);
        setLayout(null);// Organisation des composantes. En envoyant null on dit qu'il n'y aura pas de
                        // gestionnaire et qu'on organisera les composantes de façon manuelle.

        val1 = new JTextField();
        start = new JButton("start");
        pause = new JButton("pause");
        reset = new JButton("reset");
        aj = new JButton("ajouter voiture");
        en = new JButton("enlever voiture");
        lab1 = new JLabel("Veuillez entrer un taux de recherche:");
        lab2 = new JLabel("Le taux de recherche entré est de: 0");
        lab1.setForeground(Color.red);
        lab2.setForeground(Color.red);

        lab1.setBounds(0, 0, 210, 30);
        val1.setBounds(210, 0, 30, 30);
        lab2.setBounds(0, 50, 300, 40);
        start.setBounds(250, 0, 100, 40);
        pause.setBounds(250, 50, 100, 40);
        reset.setBounds(350, 0, 100, 40);
        aj.setBounds(500, 0, 200, 40);
        en.setBounds(500, 50, 200, 40);

        add(val1);
        add(start);
        add(lab1);
        add(lab2);
        add(pause);
        add(reset);
        add(aj);
        add(en);

        start.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
                int num1 = Integer.parseInt(val1.getText());
                lab2.setText("Le taux de recherche entré est de: " + (num1));
            }

            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        pause.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {

            }

            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        reset.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {

            }

            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        aj.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {

            }

            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        en.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {

            }

            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
