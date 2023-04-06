package components.Voiture;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import components.Cellule.Cellule;
import components.Cellule.CelluleIntersection;
import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Rue;
import components.Ville.Structure;
import components.Ville.Ville;
import components.Ville.Intersection.Intersection;
import serveur.Serveur;

// Classe qui représente une voiture
public class Voiture extends JPanel {
    private static Random random = new Random();

    // Taux de recherche d'un stationnement
    private static double tauxRecherche = 0;
    // Compteur locale du nombre de voitures dans la ville
    private static int nombreVoitures = Ville.getNombreVoitures();
    // Nombre de rotations (virages) de la voiture
    private int nombreRotations = 0;
    // Si la voiture est en train de chercher un stationnement
    private boolean chercher = false;
    // Si la voiture doit faire demi-tour
    private boolean uTurn = false;
    // Si la voiture est dans une intersection
    private boolean dansIntersection = false;
    // Si la voiture est orientée horizontalement
    private boolean horizontale = false;
    // Initialisation du serveur
    private Serveur serveur = new Serveur();

    // Cellule où se trouve la voiture
    private Cellule cellActuelle;
    // Structure où se trouve la voiture
    private Structure structure;
    // Coordonnées de la voiture
    private Coordonnee positionActuelle;
    // Coordonnées de la voiture x et y
    private int xActuelle, yActuelle;
    // Direction de la voiture dependament de la voie de sa dernière rue
    private int derniereDirection;
    // Le chemin vers le stationnement le plus court
    private ArrayList<Structure> cheminParking;

    public Voiture() throws InterruptedException {
        // Si la voiture retourne une exception, on en crée une nouvelle
        try {
            // incremente le compteur de voitures locale
            nombreVoitures++;

            // Definit la couleur de la voiture et sa taille
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(20, 30));
            // Ajoute les contraintes gridbaglayout de la classe Cellule
            Cellule.grid.setConstraints(this, Cellule.constraints);

            // Choisi une cellule de départ aléatoire pour la voiture
            premierArrangement();

            // fait circuler la voiture
            circule();

            // décrémente le compteur de voitures locale
            // quand la voiture est sortie de la ville
            nombreVoitures--;

            // Si le nombre de voiture locales est inférieur au nombre de voitures dans la
            // ville
            // on crée une nouvelle voiture après un délai aléatoire
            if (nombreVoitures < Ville.getNombreVoitures()) {
                Thread.sleep(3000 + random.nextInt(5000));
                new Voiture();
            }
        } catch (Exception e) {
            enlever();
            new Voiture();
        }
    }

    public static double getTauxRecherche() {
        return tauxRecherche;
    }

    public static void setTauxRecherche(double nouvTaux) {
        tauxRecherche = nouvTaux;
    }

    // Fait un appel au serveur pour obtenir le chemin vers le stationnement le plus
    // rapide
    private void chercherParking() {
        // Si la voiture n'est pas en train de chercher un stationnement
        if (!chercher) {
            // Si le taux de recherche est inférieur à 0, on ne fait rien
            if (tauxRecherche <= 0.0) {
                return;
            } else if (tauxRecherche >= 1.0) {
                // Si le taux de recherche est supérieur à 1, on cherche un stationnement
            } else {
                // Sinon on génère un nombre aléatoire entre 0 et 100
                int randomInt = random.nextInt(100);
                if (randomInt < tauxRecherche * 100) {
                    // Si le nombre aléatoire est inférieur au taux de recherche, on cherche un
                    // stationnement
                } else {
                    // Sinon on ne fait rien
                    return;
                }
            }

            // On commence à chercher un stationnement
            chercher = true;
            cheminParking = serveur.getCheminParking(positionActuelle, derniereDirection);

            // Change la couleur de la bordure de la voiture pour indiquer vers quel
            // stationnement elle se dirige
            Color couleurBordure = serveur.getCouleurParking();
            int largeurBorder = 3;
            setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                    couleurBordure));
        }
    }

    // Quand on a fini de chercher un stationnement
    private void stopChercher() {
        // On remet la bordure de la voiture à la couleur par défaut
        chercher = false;
        Color couleurBordure = null;
        int largeurBorder = 3;
        setBorder(BorderFactory.createMatteBorder(largeurBorder, largeurBorder, largeurBorder, largeurBorder,
                couleurBordure));
    }

    // Choisi l'entrée de la voiture aléatoirement
    private void premierArrangement() {
        positionActuelle = Ville.entrees[random.nextInt(Ville.entrees.length)];
        cellActuelle = Ville.getCellule(positionActuelle);
        structure = cellActuelle.getStructure();

        // Change l'orientation de la voiture si la voiture va apparaître sur une
        // structure horizontale
        if (structure.getOrientation() == "HORIZONTALE")
            horizontale = true;
    }

    // Methode recursive qui fait circuler la voiture dans la ville
    private void circule() throws InterruptedException {
        // Tourne la voiture pour reflecter son orientation
        tourne();

        // Cherche la nouvelle cellule
        Cellule cellSuivante = Ville.getCellule(positionActuelle);

        // Si la nouvelle cellule est une intersection
        // on la cast en CelluleIntersection
        CelluleIntersection cellIntersection = null;
        if (cellSuivante instanceof CelluleIntersection)
            cellIntersection = (CelluleIntersection) cellSuivante;
        else
            // On essaye de chercher un stationnement
            chercherParking();

        // On fait dormir la voiture si la ville est en pause ou
        // la nouvelle cellule est occupée ou
        // est une intersection qui n'a pas le feu vert ou qui a plus d'une voiture

        // On sort de la boucle si la ville est en train de reset
        while (!Ville.getResetStatus() &&
                (cellSuivante.estOccupe() ||
                        (cellIntersection != null && !dansIntersection &&
                                (!cellIntersection.estGo() ||
                                        cellIntersection.getStructure().getOccupation() > 0)))) {
            Thread.sleep(500);
            update();
        }

        // Si la ville est en train de reset, on enlève la voiture de la cellule
        // et on arrête de la faire circuler
        if (Ville.getResetStatus()) {
            enlever();
            return;
        }

        // On enlève la voiture de la dernière cellule
        enlever();

        // On assigne la cellule actuelle à la cellule suivante
        cellActuelle = cellSuivante;
        // On cherche la structure de la cellule actuelle
        structure = cellActuelle.getStructure();
        // On cherche la position de la cellule actuelle
        xActuelle = positionActuelle.getX();
        yActuelle = positionActuelle.getY();
        // On cherche si la cellule actuelle est adjacent à un parking
        Parking parking = cellActuelle.getParking();

        // On ajoute la voiture à la nouvelle cellule
        ajouter();

        // On fait dormir la voiture pour simuler la vitesse de la voiture
        Thread.sleep(structure.getVitesseMax());

        // Si la ville est en pause sans être en train de reset
        while (!Ville.getCirculationStatus() && !Ville.getResetStatus()) {
            // On fait dormir la voiture indéfiniment
            Thread.sleep(500);
        }

        // Si la position actuelle de la voiture n'est pas une sortie
        if (!Ville.estSortie(positionActuelle)) {
            // Si on cherche un stationnement et
            // qu'on est adjacent à un
            if (chercher && parking != null) {
                // On rentre dans le parking
                circulationParking(parking);
            }

            // On cherche la prochaine position de la voiture
            if (structure instanceof Rue)
                circulationRue();
            else if (structure instanceof Intersection)
                circulationIntersection();

            // On fait circuler la voiture avec la nouvelle position
            circule();

            // Sinon
        } else {
            // Si on ne cherche pas de stationnement, on sort de la ville
            if (!chercher) {
                enlever();
                return;
            }
            // Sinon on continue de circuler à partir de l'entrée
            // qui correspond à la sortie
            positionActuelle = Ville.getEntree(positionActuelle);
            circule();
        }
    }

    // Fait rentrer la voiture dans un parking, dort, et ressort du parking
    private void circulationParking(Parking parking) throws InterruptedException {
        // On enlève la voiture de la cellule
        enlever();

        // On reset les variables pour l'intersection
        nombreRotations = 0;
        uTurn = false;

        // On change l'orientation de la voiture pour qu'elle fait face au parking
        horizontale = !horizontale;
        tourne();
        ajouter();

        // On fait dormir la voiture pour simuler la vitesse de la voiture
        Thread.sleep(structure.getVitesseMax());
        enlever();

        // On ajoute la voiture nombre de voiture dans le parking
        parking.addOccupation();

        // On remet la bordure de la voiture à la couleur par défaut
        stopChercher();

        // On fait dormir la voiture pour simuler un temps aléatoire de stationnement
        // Si la ville n'est pas en train de reset
        int compteTemps = 0;
        while (compteTemps < random.nextInt(3000, Parking.maxTemps) && !Ville.getResetStatus()) {
            Thread.sleep(1);
            compteTemps++;
        }

        // On ajoute la voiture à la cellule de sortie du parking
        ajouter();

        // Enleve la voiture du nombre de voiture dans le parking
        parking.removeOccupation();
        Thread.sleep(structure.getVitesseMax());

        // On met la voiture à son orientation d'origine
        horizontale = !horizontale;
        tourne();
        enlever();
        ajouter();
    }

    // Fait circuler la voiture dans une rue
    private void circulationRue() {
        // Reinitialise les variables pour l'intersection
        nombreRotations = 0;
        // Si la voiture ne cherche pas de stationnement ou
        // la voiture n'est pas sur la dernière rue de son chemin
        if (!chercher || !structure.equals(cheminParking.get(cheminParking.size() - 1)))
            // reinitialise le uTurn
            uTurn = false;
        else
            uTurn = true;

        // Bouge la voiture selon l'orientation de la rue
        if (structure.getOrientation() == "VERTICALE")
            yActuelle += cellActuelle.getDirection();
        else
            xActuelle += cellActuelle.getDirection();

        // Assigne la nouvelle position de la voiture
        positionActuelle = new Coordonnee(xActuelle, yActuelle);
        // sauvegarde la direction de la voie de la rue ou la voiture est
        derniereDirection = cellActuelle.getDirection();
    }

    // Fait circuler la voiture dans une intersection
    private void circulationIntersection() {
        // Decide aléatoirement vers quel direction tourner 0 = gauche, 1 = continue,
        // 2 = droite
        int randomDirection;
        if (dansIntersection)
            randomDirection = random.nextInt(2);
        else {
            int pourcentage = random.nextInt(10);

            // si le nombre aléatoire est inférieur à 1, renvoie 0 avec 10 % de chance
            if (pourcentage < 1) {
                randomDirection = 0;
            } else if (pourcentage < 6) {
                // si le nombre aléatoire est compris entre 1 et 5, renvoie 1
                randomDirection = 1;
            } else {
                // si le nombre aléatoire est compris entre 6 et 9, renvoie 2
                randomDirection = 2;
            }
        }

        // Fait un uTurn selon l'assignement de la variable uTurn
        if (uTurn && dansIntersection) {
            // uTurn assigne par circulationIntersection
            randomDirection = 1;
            changerDirection();
        } else if (uTurn) {
            // uTurn assigne par circulationRue
            randomDirection = 0;
        } else if (nombreRotations == 1) {
            if (!horizontale)
                changerDirection();
            randomDirection = 1;
        }

        // Si la voiture cherche un stationnement
        if (chercher) {
            // Cherche la prochaine structure son le chemin

            int cheminIndex = cheminParking.indexOf(structure);
            Structure structureSuivante = cheminParking.get(cheminIndex + 1);
            int direction = getDirectionSuivante(structureSuivante);

            // Tourne la voiture selon la direction de la prochaine structure
            if (direction != -1)
                randomDirection = direction;
            else if (!dansIntersection && !uTurn)
                randomDirection = 1;
            else
                randomDirection = 0;
        }

        // Si la voiture tourne à gauche
        if (randomDirection == 0) {
            // Si c'est ça première fois dans l'intersection
            // fait un uTurn
            if (!dansIntersection)
                uTurn = true;
            // Si son orientation est horizontale
            // monte ou descend selon la direction de la cellule
            if (horizontale)
                yActuelle -= derniereDirection;
            else {
                // Sinon, tourne selon la direction de la cellule
                xActuelle += derniereDirection;
                // Incrémente le nombre de rotation
                nombreRotations++;
            }

            // Change l'orientation de la voiture
            horizontale = !horizontale;
            // Assigne la nouvelle position de la voiture
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
            // Si la voiture continue tout droit
        } else if (randomDirection == 1) {
            // Si la voiture fait un uTurn
            if (uTurn)
                // Change l'orientation de la voiture
                horizontale = !horizontale;
            // Si l'orientation de la voiture est horizontale
            if (horizontale) {
                // tourne selon sa dernière direction
                xActuelle += derniereDirection;
                // Incrémente le nombre de rotation
                nombreRotations++;
            } else
                // Sinon, monte ou descend selon la direction de la cellule
                yActuelle += derniereDirection;

            // Assigne la nouvelle position de la voiture
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
            // Si la voiture tourne à droite
        } else if (randomDirection == 2) {
            // Si l'orientation de la voiture est horizontale
            if (horizontale)
                // monte ou descend selon la direction de la cellule
                yActuelle += derniereDirection;
            else
                // Sinon tourne selon la direction de la cellule
                xActuelle -= derniereDirection;

            // Change l'orientation de la voiture
            horizontale = !horizontale;

            // Assigne la nouvelle position de la voiture
            positionActuelle = new Coordonnee(xActuelle, yActuelle);
        }

        // Si la voiture est toujours dans l'intersection
        if (Ville.getCellule(positionActuelle) instanceof CelluleIntersection)
            // Se rappeller
            dansIntersection = true;
        else
            dansIntersection = false;
    }

    // Cherche la direction de la prochaine structure sur le chemin
    private int getDirectionSuivante(Structure destination) {
        int compte = 3;
        Coordonnee positionActuelle = new Coordonnee(this.positionActuelle.getX(), this.positionActuelle.getY());
        int directionSuivante = -1;

        for (int direction = 0; direction < compte; direction++) {
            int yActuelle = this.yActuelle;
            int xActuelle = this.xActuelle;

            if (direction == 0) {
                if (horizontale)
                    yActuelle -= derniereDirection;
                else
                    xActuelle += derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            } else if (direction == 1) {
                if (horizontale)
                    xActuelle += derniereDirection;
                else
                    yActuelle += derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            } else if (direction == 2) {
                if (horizontale)
                    yActuelle += derniereDirection;
                else
                    xActuelle -= derniereDirection;

                positionActuelle = new Coordonnee(xActuelle, yActuelle);
            }
            Cellule celluleSuivante = Ville.getCellule(positionActuelle);
            if (celluleSuivante != null && celluleSuivante.getStructure().equals(destination)) {
                directionSuivante = direction;
                break;
            }
        }
        return directionSuivante;
    }

    // Change les dimensions de la voiture selon son orientation
    private void tourne() {
        if (horizontale) {
            setPreferredSize(new Dimension(30, 20));
        } else
            setPreferredSize(new Dimension(20, 30));

        update();
    }

    // Change la direction de la voiture
    private void changerDirection() {
        if (derniereDirection == -1)
            derniereDirection = 1;
        else
            derniereDirection = -1;
    }

    // Met à jour la cellule actuelle pour afficher la voiture
    private void update() {
        cellActuelle.validate();
        cellActuelle.repaint();
    }

    // Ajoute la voiture à la cellule actuelle
    private void ajouter() throws InterruptedException {
        // Si la cellule est occupée
        while (cellActuelle.estOccupe()) {
            // Si la ville est en train de reset
            if (Ville.getResetStatus())
                return;
            // on attend
            Thread.sleep(500);
        }
        cellActuelle.setOccupe(true);
        cellActuelle.add(this);
        update();
    }

    // Enlève la voiture de la cellule actuelle
    private void enlever() {
        cellActuelle.remove(this);
        cellActuelle.setOccupe(false);
        update();
    }

}
