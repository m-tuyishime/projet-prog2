package components.Ville;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import components.Cellule.CelluleRue;

// Classe qui représente une rue
public class Rue extends Structure {

    public Rue(Ville ville, Coordonnee startPosition, int longueur, String orientation) {
        super(ville, startPosition);
        setOrientation(orientation);
        setLargeLongueur(2, longueur);
        setLayout(new GridLayout(getTailleY(), getTailleX()));
        construire();
    }

    // Redéfini la méthode qui crée les cellules
    // qui composent la rue
    @Override
    protected void peupler() {
        Coordonnee position;
        // Loop pour créer les cellules dans la rue
        for (int y = 0; y < getTailleY(); y++) {
            for (int x = 0; x < getTailleX(); x++) {
                int direction = 0;

                // calcul la position des lignes de délimitation de la voie de la rue
                int tailleBordureCell = 2;
                int cellBordureDroite = 0, cellBordureGauche = 0, cellBordureHaut = 0, cellBordureBas = 0;
                if (getOrientation() == "VERTICALE") {
                    if (x == 0) {
                        direction = 1;

                        if (y % 2 == 0)
                            cellBordureDroite = tailleBordureCell;
                    } else if (x == 1) {
                        direction = -1;

                        if (y % 2 == 0)
                            cellBordureGauche = tailleBordureCell;
                    }
                } else if (getOrientation() == "HORIZONTALE") {
                    if (y == 0) {
                        direction = -1;

                        if (x % 2 == 0)
                            cellBordureBas = tailleBordureCell;
                    } else if (y == 1) {
                        direction = 1;

                        if (x % 2 == 0)
                            cellBordureHaut = tailleBordureCell;
                    }
                }

                // Création de la cellule
                position = new Coordonnee(getStartIndexCellX() + x, getStartIndexCellY() + y);
                CelluleRue cellule = new CelluleRue(this, position, direction);

                // Ajout de la ligne de délimitation de la voie de la rue
                cellule.setBorder(BorderFactory.createMatteBorder(cellBordureHaut, cellBordureGauche, cellBordureBas,
                        cellBordureDroite,
                        Color.WHITE));
                // Ajout de la cellule à la rue
                add(cellule);
                // Ajout de la cellule à grille de la ville
                Ville.setCellule(position, cellule);
            }
        }
    }
}
