package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainTheme3 {

    public static void main(String[] args) {
        try {
            // ==========================================
            // ETAPE 1 : Initialisation
            // ==========================================
            String fichier = "data/test/adjmarc.txt";
            Graphe graphePhysique = Graphe.chargerGraphe(fichier);
            GestionsSecteurs gestion = new GestionsSecteurs();

            System.out.println("--- 1. Création des Secteurs ---");

            // Création de tes 4 secteurs (comme demandé)
            creerSecteur(gestion, graphePhysique, 1, Arrays.asList(0, 1, 6, 7));
            creerSecteur(gestion, graphePhysique, 2, Arrays.asList(2, 3, 4, 5));
            creerSecteur(gestion, graphePhysique, 3, Arrays.asList(8, 9, 10, 11));
            creerSecteur(gestion, graphePhysique, 4, Arrays.asList(12, 13, 14));

            // Définition manuelle des dépôts (Hypothèse : on choisit le premier point du secteur comme dépôt)
            // Tu pourras le faire via l'IHM plus tard
            gestion.getSecteurById(1).setDepot(graphePhysique.getSommet(0));
            gestion.getSecteurById(2).setDepot(graphePhysique.getSommet(2));
            gestion.getSecteurById(3).setDepot(graphePhysique.getSommet(8));
            gestion.getSecteurById(4).setDepot(graphePhysique.getSommet(12));

            // Calcul des voisins (Qui touche qui ?)
            gestion.calculerAdjacenceSecteurs(graphePhysique);


            // ==========================================
            // ETAPE 2 : Configuration Camions (Console)
            // ==========================================
            Scanner sc = new Scanner(System.in);
            System.out.println("\n--- Configuration Logistique ---");
            System.out.print("Capacité du camion (en kg) : ");
            double capacite = sc.nextDouble(); // ex: 100

            System.out.print("Densité déchets (kg par unité de distance) : ");
            double densite = sc.nextDouble(); // ex: 10 (Si la route fait 5km, ça fait 50kg de déchets)


            // ==========================================
            // ETAPE 3 : Calcul des Déchets et Rotations
            // ==========================================
            System.out.println("\n--- 2. Calcul des charges par Secteur ---");

            GestionDechetSecteur.calculerMetriquesSecteurs(
                    gestion.getListeSecteurs(),
                    graphePhysique,
                    capacite,
                    densite
            );

            // Affichage des besoins AVANT planification
            for (Secteur s : gestion.getListeSecteurs()) {
                System.out.println("Secteur " + s.getId() + " :");
                System.out.println("   - Déchets estimés : " + s.getQuantiteTotaleDechets() + " kg");
                System.out.println("   - Dépôt assigné : Sommet " + s.getDepot().getId());
                System.out.println("   - Besoin : " + s.getNombreToursCamion() + " rotation(s) de camion");
            }


            // ==========================================
            // ETAPE 4 : Planification (Coloration)
            // ==========================================
            System.out.println("\n--- 3. Planification des Jours (Coloration) ---");

            // On applique Welsh-Powell pour éviter que deux voisins soient collectés le même jour
            AlgoColoration.colorierWelshPowell(gestion);

            String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

            for (Secteur s : gestion.getListeSecteurs()) {
                String jourNom = (s.getJourCollecte() >= 0 && s.getJourCollecte() < jours.length)
                        ? jours[s.getJourCollecte()]
                        : "Jour inconnu (" + s.getJourCollecte() + ")";

                System.out.println(">> Secteur " + s.getId() + " sera collecté le : " + jourNom);
                System.out.println("   (Voisins à éviter : " + formatVoisins(gestion, s) + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Utilitaires ---

    private static void creerSecteur(GestionsSecteurs gs, Graphe g, int idSecteur, List<Integer> idsSommets) {
        Secteur s = new Secteur(idSecteur);
        for (int id : idsSommets) {
            Sommet som = g.getSommet(id);
            if (som != null) s.ajouterSommet(som);
        }
        gs.ajouterSecteur(s);
    }

    private static String formatVoisins(GestionsSecteurs gs, Secteur s) {
        StringBuilder sb = new StringBuilder();
        for (Secteur v : gs.getVoisins(s)) {
            sb.append(v.getId()).append(" ");
        }
        return sb.toString();
    }
}