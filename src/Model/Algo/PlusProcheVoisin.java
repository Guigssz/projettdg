package Model.Algo;


import Model.Graphe.*;
import Model.Theme1.Encombrant;
import Model.Theme2.*;

import java.util.*;

import static Model.Theme1.CalculItineraire.chosirLiaison;

public class PlusProcheVoisin {

    public static Tournee ppv(
            Map<Sommet, Map<Sommet, Double>> matDist,
            Sommet depot,
            List<PointCollecte> pcs)
    {
        List<PointCollecte> restants = new ArrayList<>(pcs);
        List<Integer> ordrePoints = new ArrayList<>();
        double total = 0;

        PointCollecte actuel = null; // null = on part du dépôt

        while (!restants.isEmpty()) {

            PointCollecte best = null;
            double bestD = Double.MAX_VALUE;

            for (PointCollecte pc : restants) {

                double d = (actuel == null)
                        ? PointCollecte.distSommetToPC(matDist, depot, pc)
                        : PointCollecte.distPCtoPC(matDist, actuel, pc);

                if (d < bestD) {
                    bestD = d;
                    best = pc;
                }
            }

            total += bestD;
            ordrePoints.add(pcs.indexOf(best));

            restants.remove(best);
            actuel = best;
        }

        return new Tournee(depot, ordrePoints, total);
    }

    public static void main(String[] args) {

        String fichierin = "data/test/adjmarc.txt";

        try {
            Graphe graphe = Graphe.chargerGraphe(fichierin);
            graphe.afficherLiaisons();
            System.out.println();
            graphe.afficherAdj();
            System.out.println();

            Scanner sc = new Scanner(System.in);

            // ----- Choix du dépôt -----
            System.out.print("Sommet du dépôt : ");
            int idDepot = sc.nextInt();
            Sommet depot = graphe.getSommet(idDepot);


            // ----- Choix des points de collecte -----
            System.out.print("Nombre de points de collecte : ");
            int nb = sc.nextInt();

            List<PointCollecte> points = new ArrayList<>();

            graphe.afficherLiaisons();

            for (int i = 0; i < nb; i++) {

                System.out.println("\nPoint de collecte " + (i + 1));

                Liaison liasionchoisi = chosirLiaison(graphe);

                if (liasionchoisi == null) {
                    System.out.println("arete pas trouvé");
                } else {
                    points.add(new PointCollecte(liasionchoisi));
                }

                System.out.println("Point de Collecte " + i + " ajoutée à la liaison : " + liasionchoisi.getPred().getId() + liasionchoisi.getSucc().getId());

            }


            // ----- Calcul de la matrice de distances -----
            CalcDistances calc = new CalcDistances(graphe);
            calc.calcMatDistance();
            Map<Sommet, Map<Sommet, Double>> matDist = calc.getMatDistance();


            // ----- Lancement du PPV -----
            Tournee tournee = PlusProcheVoisin.ppv(matDist, depot, points);


            // ----- Affichage -----
            System.out.println("\n===== RÉSULTAT TSP APPROCHE PPV =====");
            tournee.afficher();


        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }




}
