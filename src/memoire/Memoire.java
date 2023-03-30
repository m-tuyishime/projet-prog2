package memoire;

import components.Ville.Coordonnee;
import components.Ville.Ville;

public class Memoire {
    // Création d'un nouveau JPanel pour la ville avec la classe Ville
    private static Ville ville = new Ville();
    // Taux de recherche d'un stationnement
    private static double tauxRecherche = 0.5;

    // Si les voitures bougent
    private static boolean circulation = false;

    private static boolean reset = true;

    private static Coordonnee[] startCoordonnees = {};

    public static Ville getVille() {
        return ville;
    }

    public static double getTauxRecherche() {
        return tauxRecherche;
    }

    public static boolean getCirculation() {
        return circulation;
    }

    public static boolean getResetStatus() {
        return reset;
    }

    public static void setResetStatus(boolean status) {
        reset = status;
    }

    public static void setCirculation(boolean status) {
        circulation = status;
    }

    public static void setTauxRecherche(double nouvTaux) {
        tauxRecherche = nouvTaux;
    }
}
