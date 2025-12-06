package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;

import java.util.Arrays;
import java.util.List;

public class MainTheme3 {

    public static void main(String[] args) {
        try {
            // 1. Charger le graphe physique
            String fichier = "data/test/adjmarc.txt";
            Graphe graphePhysique = Graphe.chargerGraphe(fichier);

            System.out.println("Graphe physique chargé : " + graphePhysique.getSommets().size() + " sommets.");

            // 2. Création du Gestionnaire de Secteurs
            GestionsSecteurs gestion = new GestionsSecteurs();

            // 3. Création MANUELLE des 4 secteurs demandés
            creerSecteur(gestion, graphePhysique, 1, Arrays.asList(0, 1, 6, 7));
            creerSecteur(gestion, graphePhysique, 2, Arrays.asList(2, 3, 4, 5));
            creerSecteur(gestion, graphePhysique, 3, Arrays.asList(8, 9, 10, 11));
            creerSecteur(gestion, graphePhysique, 4, Arrays.asList(12, 13, 14));

            // 4. Calcul des adjacences entre secteurs (qui est voisin de qui ?)
            gestion.calculerAdjacenceSecteurs(graphePhysique);

            System.out.println("\n--- Voisinage des Secteurs ---");
            for (Secteur s : gestion.getListeSecteurs()) {
                System.out.print("Secteur " + s.getId() + " est voisin de : ");
                for (Secteur v : gestion.getVoisins(s)) {
                    System.out.print(v.getId() + " ");
                }
                System.out.println();
            }

            // 5. Coloration (Attribution des jours)
            AlgoColoration.colorierWelshPowell(gestion);

            // 6. Affichage des résultats
            System.out.println("\n--- Résultat Planification (H01) ---");
            String[] joursSemaine = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

            for (Secteur s : gestion.getListeSecteurs()) {
                String jour = (s.getJourCollecte() < joursSemaine.length)
                        ? joursSemaine[s.getJourCollecte()]
                        : "Jour " + s.getJourCollecte();

                System.out.println("Secteur " + s.getId() + " -> " + jour + " (Couleur " + s.getJourCollecte() + ")");
                System.out.print("   Sommets couverts : ");
                for(Sommet som : s.getSommetsDuSecteur()) System.out.print(som.getId() + " ");
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour simplifier la création dans le main
    private static void creerSecteur(GestionsSecteurs gs, Graphe g, int idSecteur, List<Integer> idsSommets) {
        Secteur s = new Secteur(idSecteur);
        for (int id : idsSommets) {
            Sommet som = g.getSommet(id);
            if (som != null) {
                s.ajouterSommet(som);
            } else {
                System.err.println("Attention : Sommet " + id + " introuvable !");
            }
        }
        gs.ajouterSecteur(s);
    }
}
