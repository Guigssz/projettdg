package Model.Algo;


import Model.Graphe.*;
import Model.Theme2.*;

import java.util.*;

public class PlusProcheVoisin {

    public static Tournee ppv(
            Map<Sommet, Map<Sommet, Double>> matDist,
            Sommet depot,
            List<PointCollecte> pcs)
    {
        List<PointCollecte> restants = new ArrayList<>(pcs);
        List<Integer> ordrePoints = new ArrayList<>();
        double total = 0;

        PointCollecte actuel = null; // null = au dépôt

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

        // Retour au dépôt
        total += PointCollecte.distSommetToPC(matDist, depot, actuel);

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

            for (int i = 0; i < nb; i++) {

                System.out.println("\nPoint de collecte " + (i + 1));

                System.out.print("  Sommet U de l'arête : ");
                int a = sc.nextInt();

                System.out.print("  Sommet V de l'arête : ");
                int b = sc.nextInt();

                // on récupère l’arête
                Liaison arete = graphe.getArrete(a, b);

                System.out.println("  Longueur de l'arête " + a + "-" + b + " : " + arete.getPoids());

                System.out.print("  Distance depuis U (" + a + ") : ");
                double pos = sc.nextDouble();

                // création du point
                points.add(new PointCollecte(arete, pos));
                System.out.println("  -> PC ajouté sur " + a + "-" + b + " à position " + pos);
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
