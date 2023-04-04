package serveur;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import components.Cellule.Cellule;
import components.Ville.Coordonnee;
import components.Ville.Parking;
import components.Ville.Structure;
import components.Ville.Ville;

// Classe qui représente le serveur
public class Serveur {
    private Parking parkingTrouve;
    private ArrayList<Parking> parkingsTrouves;
    private int startDirection;

    public Serveur() {

    }

    // La methode qui retourne le chemin le plus court vers un parking
    public ArrayList<Structure> getCheminParking(Coordonnee position, int startDirection) {
        // nettoie les variables de la derniere recherche
        parkingsTrouves = new ArrayList<Parking>();
        if (parkingTrouve != null) {
            // enleve la derniere reservation
            parkingTrouve.setReservations(parkingTrouve.getReservations() - 1);
            parkingTrouve = null;
        }
        this.startDirection = startDirection;

        // Cherche le chemin le plus court vers un parking
        ArrayList<Structure> chemin = new ArrayList<Structure>();
        ArrayList<Structure> nouvChemin = checkCellSuivantes(position, chemin);

        // reserve le parking trouvé
        parkingTrouve.setReservations(parkingTrouve.getReservations() + 1);

        // retourne le chemin le plus court
        return nouvChemin;
    }

    // Methode recursive qui cherche le chemin le plus court vers un parking
    private ArrayList<Structure> checkCellSuivantes(Coordonnee position, ArrayList<Structure> dernierChemin) {
        // Si la position est null ou on a deja trouve plus que 8 parkings
        if (position == null || parkingsTrouves.size() > 8)
            return null;

        // cherche la cellule a la position actuelle
        Cellule cellActuelle = Ville.getCellule(position);

        // Si la cellule est null
        if (cellActuelle == null)
            return null;

        // Cherche la structure dont la cellule fait partie
        Structure structure = cellActuelle.getStructure();

        // Crée un nouveau tableau de chemins et y ajoute le dernier chemin
        ArrayList<Structure> nouvChemin = new ArrayList<Structure>(dernierChemin);

        nouvChemin.add(structure);

        // enleve les chemins de plus que 6 structures
        if (nouvChemin.size() > 6)
            return null;

        // Si la structure est adjacente a un parking
        if (structure.getParkings().size() > 0) {
            for (Parking parking : structure.getParkings()) {
                // Si le parking n'a pas deja ete trouve plus que 2 fois
                if (Collections.frequency(parkingsTrouves, parking) < 2 && parking.estLibre()) {
                    parkingsTrouves.add(parking);
                    parkingTrouve = parking;
                    return nouvChemin;
                }
            }
        }

        // les positions de la structure actuelle
        int startXActuelle = structure.getStartIndexCellX();
        int startYActuelle = structure.getStartIndexCellY();

        // Crée un tableau de chemins menant a un parking
        ArrayList<Structure>[] cheminsPossible = new ArrayList[4];

        Coordonnee coordonnee;

        // Rajoute les chemins qui vont vers le haut de la structure actuelle
        coordonnee = coordonneeAdjacente(startXActuelle, startYActuelle, -1, "Y");
        cheminsPossible[2] = checkCellSuivantes(coordonnee, nouvChemin);

        // Rajoute les chemins qui vont vers la droite de la structure actuelle
        coordonnee = coordonneeAdjacente(startXActuelle, startYActuelle, structure.getTailleX(), "X");
        cheminsPossible[0] = checkCellSuivantes(coordonnee, nouvChemin);

        // Rajoute les chemins qui vont vers la gauche de la structure actuelle
        coordonnee = coordonneeAdjacente(startXActuelle, startYActuelle, -1, "X");
        cheminsPossible[1] = checkCellSuivantes(coordonnee, nouvChemin);

        // Rajoute les chemins qui vont vers le bas de la structure actuelle
        coordonnee = coordonneeAdjacente(startXActuelle, startYActuelle, structure.getTailleY(), "Y");
        cheminsPossible[3] = checkCellSuivantes(coordonnee, nouvChemin);

        // Les variables pour calculer le meilleur chemin
        Double meilleurScore = Double.MAX_VALUE;
        ArrayList<Structure> meilleurChemin = null;

        // Calcule le meilleur chemin parmis les chemins possibles
        for (ArrayList<Structure> chemin : cheminsPossible) {
            // Si le chemin est null
            if (chemin == null)
                continue;

            // le score du chemin actuel
            double score = 0;
            // les poids pour calculer le score
            final double poidsLongueur = 0.5;
            final double poidsVirage = 0.2;
            final double poidsAutresVoitures = 0.3;

            // loop sur les structures du chemin
            for (int i = 0; i < chemin.size(); i++) {
                // la structure actuelle
                structure = chemin.get(i);

                // Si c'est la derniere structure du chemin
                if (structure == chemin.get(chemin.size() - 1)) {
                    // Calcule si il ya eu des virages dans le chemin
                    Parking parking = structure.getParkings().get(0);
                    score += Math.abs(parking.getDirection() - startDirection) * poidsVirage;

                    // Calcule la distance vers le parking depuis le debut de la derniere structure
                    // (rue) dans le chemin

                    // Les variables pour calculer la distance
                    Structure derniereStructure = chemin.get(chemin.size() - 2);
                    int distanceParking = 0;
                    int structureStartX = structure.getStartIndexCellX();
                    int dernierStartX = derniereStructure.getStartIndexCellX();
                    int structureStartY = structure.getStartIndexCellY();
                    int dernierStartY = derniereStructure.getStartIndexCellY();

                    // Si la derniere structure (intersection) et la structure actuelle (rue) sont
                    // alignes verticalement
                    if (dernierStartX == structureStartX) {
                        // Si le parking est au dessus de la derniere structure
                        if (dernierStartY > structureStartY) {
                            boolean trouvee = false;
                            for (int x = structureStartX; x <= structureStartX + 1; x++) {
                                if (trouvee)
                                    break;
                                distanceParking = 0;
                                for (int y = dernierStartY; y >= structureStartY + parking.getTailleY() - 1; y--) {
                                    Cellule cellule = Ville.getCellule(new Coordonnee(x, y));
                                    if (cellule.getParking() != null) {
                                        trouvee = true;
                                        break;
                                    }
                                    distanceParking++;
                                }
                            }
                            // Si le parking est en dessous de la derniere structure
                        } else {
                            boolean trouvee = false;
                            for (int x = structureStartX; x <= structureStartX + 1; x++) {
                                if (trouvee)
                                    break;
                                distanceParking = 0;
                                for (int y = structureStartY; y < (structureStartY + structure.getTailleY())
                                        - parking.getTailleY(); y++) {
                                    Cellule cellule = Ville.getCellule(new Coordonnee(x, y));
                                    if (cellule.getParking() != null) {
                                        trouvee = true;
                                        break;
                                    }
                                    distanceParking++;
                                }
                            }
                        }

                        // Si la derniere structure (intersection) et la structure actuelle (rue) sont
                        // alignes horizontalement
                    } else if (dernierStartY == structureStartY) {
                        // Si le parking est a gauche de la derniere structure
                        if (dernierStartX > structureStartX) {
                            boolean trouvee = false;
                            for (int y = structureStartY; y <= structureStartY + 1; y++) {
                                if (trouvee)
                                    break;
                                distanceParking = 0;
                                for (int x = dernierStartX; x >= structureStartX + parking.getTailleX() - 1; x--) {
                                    Cellule cellule = Ville.getCellule(new Coordonnee(x, y));
                                    if (cellule.getParking() != null) {
                                        trouvee = true;
                                        break;
                                    }
                                    distanceParking++;
                                }
                            }
                            // Si le parking est en dessous de la derniere structure
                        } else {
                            boolean trouvee = false;
                            for (int y = structureStartY; y <= structureStartY + 1; y++) {
                                if (trouvee)
                                    break;
                                distanceParking = 0;
                                for (int x = structureStartX; x < (structureStartX + structure.getTailleX())
                                        - parking.getTailleX(); x++) {
                                    Cellule cellule = Ville.getCellule(new Coordonnee(x, y));
                                    if (cellule.getParking() != null) {
                                        trouvee = true;
                                        break;
                                    }
                                    distanceParking++;
                                }
                            }
                        }
                    }

                    score += distanceParking * poidsLongueur;
                } else
                    score += structure.getLongueur() * poidsLongueur;

                score += structure.getOccupation() * poidsAutresVoitures;
            }

            // Si le score du chemin actuel est meilleur que le meilleur score
            if (score < meilleurScore) {
                meilleurScore = score;
                meilleurChemin = chemin;
                parkingTrouve = chemin.get(chemin.size() - 1).getParkings().get(0);
            }
        }

        return meilleurChemin;
    }

    // Retourne la direction de la structure suivante
    private static Coordonnee coordonneeAdjacente(int startXActuelle, int startYActuelle, int addition,
            String ajouterA) {
        Coordonnee coordonnee;
        int retour = 1;
        if (addition < 0)
            retour = -1;

        if (ajouterA == "X") {
            startXActuelle += addition;
        } else if (ajouterA == "Y") {
            startYActuelle += addition;
        } else
            throw new IllegalArgumentException("La valeur de la variable ajouterA doit être soit \"X\" ou \"Y\"");

        try {
            try {
                // cherche la coordonne adjacente à la structure
                coordonnee = new Coordonnee(startXActuelle, startYActuelle);
            } catch (IllegalArgumentException e) {
                // Si l'avant derniere coordonne est une sortie connectée à une entrée, prendre
                // sa coordonée
                coordonnee = Ville.getEntree(new Coordonnee(startXActuelle - retour, startYActuelle));
            }
        } catch (IllegalArgumentException e) {
            // Si la coordonne adjacente mène nulle part retourner null
            coordonnee = null;
        }

        return coordonnee;
    }

    // Retourne la couleur du parking trouvé
    public Color getCouleurParking() {
        return parkingTrouve.getCouleur();
    }
}
