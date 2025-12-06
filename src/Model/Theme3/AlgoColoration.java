package Model.Theme3;

import java.util.*;

public class AlgoColoration {

    public static void colorierWelshPowell(GestionsSecteurs gestion) {

        List<Secteur> secteursTries = new ArrayList<>(gestion.getListeSecteurs());

        // 1. Trier les secteurs par degré décroissant (nombre de secteurs voisins)
        secteursTries.sort((s1, s2) -> {
            int deg1 = gestion.getVoisins(s1).size();
            int deg2 = gestion.getVoisins(s2).size();
            return Integer.compare(deg2, deg1); // Décroissant
        });

        int couleurActuelle = 0;
        int nbSecteursColoriés = 0;
        int total = secteursTries.size();

        // Tant que tout le monde n'a pas une couleur
        while (nbSecteursColoriés < total) {

            // On commence une nouvelle couleur (ex: Lundi = 0, Mardi = 1...)
            // On cherche à attribuer cette couleur à un maximum de secteurs non adjacents
            List<Secteur> secteursDeCetteCouleur = new ArrayList<>();

            for (Secteur s : secteursTries) {

                // Si le secteur n'a pas encore de couleur
                if (s.getJourCollecte() == -1) {

                    boolean peutEtreColorie = true;

                    // Vérifier si aucun de ses voisins A DÉJÀ la couleur actuelle
                    for (Secteur voisin : gestion.getVoisins(s)) {
                        // Attention : ici on vérifie par rapport aux voisins qu'on vient juste de colorier dans ce tour de boucle
                        // OU ceux qui auraient été coloriés précédemment (mais ici on change de couleur à chaque while)
                        if (voisin.getJourCollecte() == couleurActuelle) {
                            peutEtreColorie = false;
                            break;
                        }
                    }

                    if (peutEtreColorie) {
                        s.setJourCollecte(couleurActuelle);
                        secteursDeCetteCouleur.add(s);
                        nbSecteursColoriés++;
                    }
                }
            }

            couleurActuelle++;
        }
    }
}
