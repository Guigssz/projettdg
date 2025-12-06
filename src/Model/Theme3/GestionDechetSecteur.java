package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Liaison;
import Model.Graphe.Sommet;

import java.util.List;

public class GestionDechetSecteur {

    /**
     * Calcule la quantité de déchets et le nombre de camions pour chaque secteur.
     * @param secteurs La liste des secteurs
     * @param grapheGlobal Le graphe entier (pour lire les liaisons)
     * @param capaciteCamion La capacité max du camion (ex: 3000 kg)
     * @param densiteDechets Combien de kg de déchets par unité de distance (ex: 50 kg/km)
     */
    public static void calculerMetriquesSecteurs(List<Secteur> secteurs, Graphe grapheGlobal, double capaciteCamion, double densiteDechets) {

        // 1. Pour chaque secteur...
        for (Secteur sec : secteurs) {

            double totalDistanceSecteur = 0;

            // 2. On parcourt TOUTES les liaisons du graphe global
            // (On pourrait optimiser, mais pour la taille de tes graphes, c'est ok)
            for (Liaison l : grapheGlobal.getLiaison()) {

                Sommet u = l.getPred();
                Sommet v = l.getSucc();

                // 3. Si les DEUX extrémités de la rue sont dans le secteur, on compte la rue
                if (sec.contientSommet(u) && sec.contientSommet(v)) {
                    totalDistanceSecteur += l.getPoids();
                }
            }

            // 4. Calcul de la quantité de déchets (Distance * Densité)
            double quantiteDechets = totalDistanceSecteur * densiteDechets;
            sec.setQuantiteTotaleDechets(quantiteDechets);

            // 5. Calcul du nombre de rotations (Arrondi supérieur)
            // Math.ceil renvoie un double, on cast en int
            int nbTours = (int) Math.ceil(quantiteDechets / capaciteCamion);

            // Sécurité : au moins 1 tour si le secteur n'est pas vide
            if (quantiteDechets > 0 && nbTours == 0) nbTours = 1;

            sec.setNombreToursCamion(nbTours);
        }
    }
}