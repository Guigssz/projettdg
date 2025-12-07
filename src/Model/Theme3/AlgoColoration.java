package Model.Theme3;

import java.util.*;

public class AlgoColoration {

    public static void colorierWelshPowell(GestionsSecteurs gestion) {

        List<Secteur> secteursTries = new ArrayList<>(gestion.getListeSecteurs());


        secteursTries.sort((s1, s2) -> {
            int deg1 = gestion.getVoisins(s1).size();
            int deg2 = gestion.getVoisins(s2).size();
            return Integer.compare(deg2, deg1);
        });

        int couleurActuelle = 0;
        int nbSecteursColoriés = 0;
        int total = secteursTries.size();

        // Tant que tout le monde n'a pas une couleur
        while (nbSecteursColoriés < total) {


            List<Secteur> secteursDeCetteCouleur = new ArrayList<>();

            for (Secteur s : secteursTries) {

                // Si le secteur n'a pas encore de couleur
                if (s.getJourCollecte() == -1) {

                    boolean peutEtreColorie = true;


                    for (Secteur voisin : gestion.getVoisins(s)) {

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
