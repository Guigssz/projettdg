package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Liaison;
import Model.Graphe.Sommet;

import java.util.List;

public class GestionDechetSecteur {

    public static void calculerMetriquesSecteurs(List<Secteur> secteurs, Graphe grapheGlobal, double capaciteCamion, double densiteDechets) {

        // 1. Réinitialiser les compteurs
        for (Secteur s : secteurs) {
            s.setQuantiteTotaleDechets(0);
            s.setNombreToursCamion(0);
        }

        // 2. Parcourir TOUTES les liaisons du graphe (pour n'en oublier aucune)
        for (Liaison l : grapheGlobal.getLiaison()) {

            Sommet u = l.getPred();
            Sommet v = l.getSucc();

            Secteur secU = trouverSecteur(secteurs, u);
            Secteur secV = trouverSecteur(secteurs, v);

            Secteur secteurChoisi = null;

            // CAS 1 : Rue interne (les deux bouts sont dans le même secteur)
            if (secU != null && secU == secV) {
                secteurChoisi = secU;
            }
            // CAS 2 : Rue frontière (relie deux secteurs différents)
            // DECISION : On l'attribue au secteur du sommet "Pred" (U)
            else if (secU != null) {
                secteurChoisi = secU;
            }
            // Si U n'a pas de secteur mais V oui (cas rare si tout le graphe est couvert), on donne à V
            else if (secV != null) {
                secteurChoisi = secV;
            }

            // Si on a trouvé un propriétaire pour cette rue, on ajoute les déchets
            if (secteurChoisi != null) {
                double poidsArete = l.getPoids();
                double dechetsArete = poidsArete * densiteDechets;

                // On ajoute au total du secteur
                secteurChoisi.setQuantiteTotaleDechets(secteurChoisi.getQuantiteTotaleDechets() + dechetsArete);
            }
        }

        // 3. Calcul final des tours
        for (Secteur sec : secteurs) {
            double q = sec.getQuantiteTotaleDechets();
            int nbTours = (int) Math.ceil(q / capaciteCamion);
            if (q > 0 && nbTours == 0) nbTours = 1;
            sec.setNombreToursCamion(nbTours);
        }
    }

    // Petite méthode helper pour retrouver le secteur d'un sommet
    private static Secteur trouverSecteur(List<Secteur> secteurs, Sommet s) {
        for (Secteur sec : secteurs) {
            if (sec.contientSommet(s)) return sec;
        }
        return null;
    }
}