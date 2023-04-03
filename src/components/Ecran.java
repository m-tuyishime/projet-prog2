package components;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import components.BarreHaut.BarreHaut;
import components.Ville.Ville;

public class Ecran extends JFrame {
    // Définition du layout de la fenêtre avec le GridBagLayout
    private GridBagLayout grid = new GridBagLayout();
    // Définition de la taille de l'écran avec la classe Toolkit
    private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
    // Création d'un nouveau JPanel pour la barre du haut avec la classe BarreHaut
    private static final JPanel barreHaut = new BarreHaut();
    // Recherche du JPanel avec la classe Ville
    private static Ville ville = new Ville();

    public Ecran() {
        super("Conduite"); // Appel du constructeur de la classe JFrame avec un titre pour la fenêtre
        setSize(1100, 900); // Définition de la taille de la fenêtre
        // Définition de la taille de la fenêtre en fonction de la taille de l'écran
        // setSize(tailleEcran.height, tailleEcran.height - 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture de la fenêtre lorsqu'on clique sur la croix
        organiser(); // Appel de la méthode organiser
        setVisible(true); // Affichage de la fenêtre
    }

    public static Ville getVille() {
        return ville;
    }

    private void organiser() {
        // Définition du layout de la fenêtre avec le GridBagLayout
        setLayout(grid);

        // Création des contraintes pour la première ligne
        GridBagConstraints barreHautConstraints = new GridBagConstraints();
        barreHautConstraints.gridx = 0;
        barreHautConstraints.gridy = 0;
        barreHautConstraints.fill = GridBagConstraints.BOTH;
        barreHautConstraints.weighty = 0.2; // Fixe la hauteur max de la première ligne a 20% du JFrame
        barreHautConstraints.weightx = 1; // Fixe la longueur max de la première ligne a 100% du JFrame
        grid.setConstraints(barreHaut, barreHautConstraints); // Ajout des contraintes de la première ligne à la
                                                              // barre du haut

        // Création des contraintes pour la deuxième ligne
        GridBagConstraints villeConstraints = new GridBagConstraints();
        villeConstraints.gridx = 0;
        villeConstraints.gridy = 1;
        villeConstraints.fill = GridBagConstraints.BOTH;
        villeConstraints.weighty = 0.8; // Fixe la hauteur max de la deuxième ligne a 80% du JFrame
        villeConstraints.weightx = 1; // Fixe la longueur max de la deuxième ligne a 100% du JFrame
        grid.setConstraints(ville, villeConstraints); // Ajout des contraintes de la deuxième ligne à la ville

        // Ajout des JPanels à la fenêtre
        add(barreHaut);
        add(ville);
    }
}