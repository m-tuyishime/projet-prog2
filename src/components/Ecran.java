package components;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import components.BarreHaut.BarreHaut;
import memoire.Memoire;

public class Ecran extends JFrame {
    // Définition du layout de la fenêtre avec le GridBagLayout
    private GridBagLayout grid = new GridBagLayout();
    private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
    // Création d'un nouveau JPanel pour la barre du haut avec la classe BarreHaut
    JPanel barreHautPanel = new BarreHaut();
    // Recherche du JPanel avec la classe Ville
    JPanel villePanel = Memoire.getVille();

    public Ecran() {
        super("Conduite"); // Appel du constructeur de la classe JFrame avec un titre pour la fenêtre
        setSize(1100, 900); // Définition de la taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture de la fenêtre lorsqu'on clique sur la croix
        organiser();
        setVisible(true); // Affichage de la fenêtre
    }

    private void organiser() {
        setLayout(grid);

        // Création des contraintes pour la première ligne
        GridBagConstraints barreHautConstraints = new GridBagConstraints();
        barreHautConstraints.gridx = 0;
        barreHautConstraints.gridy = 0;
        barreHautConstraints.fill = GridBagConstraints.BOTH;
        barreHautConstraints.weighty = 0.2; // Fixe la hauteur max de la première ligne a 20% du JFrame
        barreHautConstraints.weightx = 1; // Fixe la longueur max de la première ligne a 100% du JFrame
        grid.setConstraints(barreHautPanel, barreHautConstraints); // Ajout des contraintes de la première ligne à la
                                                                   // barre du haut

        // Création des contraintes pour la deuxième ligne
        GridBagConstraints villeConstraints = new GridBagConstraints();
        villeConstraints.gridx = 0;
        villeConstraints.gridy = 1;
        villeConstraints.fill = GridBagConstraints.BOTH;
        villeConstraints.weighty = 0.8; // Fixe la hauteur max de la deuxième ligne a 80% du JFrame
        villeConstraints.weightx = 1; // Fixe la longueur max de la deuxième ligne a 100% du JFrame
        grid.setConstraints(villePanel, villeConstraints); // Ajout des contraintes de la deuxième ligne à la ville

        // Ajout des JPanels à la fenêtre
        add(barreHautPanel);
        add(villePanel);
    }
}