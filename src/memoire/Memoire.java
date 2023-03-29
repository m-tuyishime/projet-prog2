package memoire;

import components.Ville.Ville;

public class Memoire {
    // Création d'un nouveau JPanel pour la ville avec la classe Ville
    private static Ville ville = new Ville();
    // Taux de recherche d'un stationnement
    private static double tauxRecherche = 0.5;

    public static Ville getVille() {
        return ville;
    }

    public static double getTauxRecherche() {
        return tauxRecherche;
    }

    public static void setTauxRecherche(double nouvTaux) {
        tauxRecherche = nouvTaux;
    }
}
