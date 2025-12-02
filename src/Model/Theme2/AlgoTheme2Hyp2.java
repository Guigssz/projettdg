package Model.Theme2;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.ResultatCommun.Itineraire;

import java.util.*;

/**
 * Thème 2 - Hypothèse 2
 *
 * 1. Graphe complet entre {dépôt + points de collecte} avec distances = plus courts chemins
 * 2. MST (Prim)
 * 3. Parcours préfixe du MST -> ordre de visite
 * 4. Découpage par capacité du camion
 * 5. Reconstruction des tournées complètes dans le graphe d'origine
 */
public class AlgoTheme2Hyp2 {

    // --------------------------------------------------------------------
    // PROGRAMME PRINCIPAL
    // --------------------------------------------------------------------
    public static List<Itineraire> calculerTournees(
            Graphe g,
            Sommet depot,
            List<PointCollecteSpb2> points,
            int capaciteCamion) {

        // 1. Distances complètes via Dijkstra
        Map<Pair, Integer> distances = construireDistances(g, depot, points);

        // 2. MST (Prim)
        List<Pair> mst = primMST(depot, points, distances);

        // 3. Ordre de visite via DFS préfixe
        List<PointCollecteSpb2> ordre = parcoursPrefixe(depot, mst, points);

        // 4. Découpage selon la capacité
        List<List<PointCollecteSpb2>> tourneesDecoupees =
                decouper(ordre, capaciteCamion);

        // 5. Reconstruction réelle
        return reconstruireItineraires(g, depot, tourneesDecoupees);
    }

    // --------------------------------------------------------------------
    // 1. Construction des distances via Dijkstra
    // --------------------------------------------------------------------
    private static Map<Pair, Integer> construireDistances(
            Graphe g,
            Sommet depot,
            List<PointCollecteSpb2> points) {

        List<Sommet> tous = new ArrayList<>();
        tous.add(depot);
        for (PointCollecteSpb2 pc : points)
            tous.add(pc.getSommet());

        Map<Pair, Integer> distances = new HashMap<>();

        for (Sommet a : tous) {
            for (Sommet b : tous) {
                if (a == b) continue;

                int d = Dijkstra.dijkstraDistance(g, a, b);
                distances.put(new Pair(a, b), d);
            }
        }

        return distances;
    }

    // --------------------------------------------------------------------
    // 2. MST avec Prim
    // --------------------------------------------------------------------
    private static List<Pair> primMST(
            Sommet depot,
            List<PointCollecteSpb2> points,
            Map<Pair, Integer> dist) {

        List<Sommet> tous = new ArrayList<>();
        tous.add(depot);
        for (PointCollecteSpb2 pc : points)
            tous.add(pc.getSommet());

        Set<Sommet> visited = new HashSet<>();
        visited.add(depot);

        List<Pair> mst = new ArrayList<>();

        while (visited.size() < tous.size()) {

            Pair best = null;
            int bestD = Integer.MAX_VALUE;

            for (Sommet u : visited) {
                for (Sommet v : tous) {
                    if (visited.contains(v)) continue;

                    int d = dist.get(new Pair(u, v));
                    if (d < bestD) {
                        bestD = d;
                        best = new Pair(u, v);
                    }
                }
            }

            mst.add(best);
            visited.add(best.b);
        }

        return mst;
    }

    // --------------------------------------------------------------------
    // 3. Parcours préfixe du MST (DFS)
    // --------------------------------------------------------------------
    private static List<PointCollecteSpb2> parcoursPrefixe(
            Sommet depot,
            List<Pair> mst,
            List<PointCollecteSpb2> points) {

        Map<Sommet, List<Sommet>> adj = new HashMap<>();

        for (Pair p : mst) {
            adj.computeIfAbsent(p.a, k -> new ArrayList<>()).add(p.b);
            adj.computeIfAbsent(p.b, k -> new ArrayList<>()).add(p.a);
        }

        List<Sommet> ordreSommets = new ArrayList<>();
        dfs(depot, adj, new HashSet<>(), ordreSommets);

        List<PointCollecteSpb2> ordre = new ArrayList<>();

        for (Sommet s : ordreSommets) {
            if (s.equals(depot)) continue;

            for (PointCollecteSpb2 pc : points) {
                if (pc.getSommet().equals(s))
                    ordre.add(pc);
            }
        }

        return ordre;
    }

    private static void dfs(
            Sommet u,
            Map<Sommet, List<Sommet>> adj,
            Set<Sommet> visited,
            List<Sommet> ordre) {

        visited.add(u);
        ordre.add(u);

        if (!adj.containsKey(u)) return;

        for (Sommet v : adj.get(u)) {
            if (!visited.contains(v))
                dfs(v, adj, visited, ordre);
        }
    }

    // --------------------------------------------------------------------
    // 4. Découpage en tournées selon la capacité
    // --------------------------------------------------------------------
    private static List<List<PointCollecteSpb2>> decouper(
            List<PointCollecteSpb2> ordre,
            int C) {

        List<List<PointCollecteSpb2>> tournees = new ArrayList<>();

        List<PointCollecteSpb2> current = new ArrayList<>();
        int charge = 0;

        for (PointCollecteSpb2 pc : ordre) {

            if (charge + pc.getContenance() <= C) {
                current.add(pc);
                charge += pc.getContenance();
            } else {
                // on ferme la tournée courante
                tournees.add(current);

                // nouvelle tournée
                current = new ArrayList<>();
                current.add(pc);
                charge = pc.getContenance();
            }
        }

        if (!current.isEmpty())
            tournees.add(current);

        return tournees;
    }

    // --------------------------------------------------------------------
    // 5. Reconstruction réelle des itinéraires (SANS concatener)
    // --------------------------------------------------------------------
    private static List<Itineraire> reconstruireItineraires(
            Graphe g,
            Sommet depot,
            List<List<PointCollecteSpb2>> tournees) {

        List<Itineraire> res = new ArrayList<>();

        for (List<PointCollecteSpb2> tournee : tournees) {

            Sommet cur = depot;
            List<Sommet> cheminGlobal = new ArrayList<>();
            double distanceTotale = 0.0;
            boolean premierSegment = true;

            // D -> P1 -> P2 -> ... -> Pk
            for (PointCollecteSpb2 pc : tournee) {
                Itineraire step = Dijkstra.dijkstra(g, cur, pc.getSommet());
                List<Sommet> cheminStep = step.getListSommet();

                if (cheminStep == null || cheminStep.isEmpty()) continue;

                if (premierSegment) {
                    // on prend tout le chemin
                    cheminGlobal.addAll(cheminStep);
                    premierSegment = false;
                } else {
                    // on évite de répéter le sommet de départ (déjà dernier du cheminGlobal)
                    cheminGlobal.addAll(cheminStep.subList(1, cheminStep.size()));
                }

                distanceTotale += step.getDistanceTotal();
                cur = pc.getSommet();
            }

            // Retour au dépôt : Pk -> D
            Itineraire retour = Dijkstra.dijkstra(g, cur, depot);
            List<Sommet> cheminRetour = retour.getListSommet();

            if (cheminRetour != null && !cheminRetour.isEmpty()) {
                if (cheminGlobal.isEmpty()) {
                    cheminGlobal.addAll(cheminRetour);
                } else {
                    cheminGlobal.addAll(cheminRetour.subList(1, cheminRetour.size()));
                }
            }

            distanceTotale += retour.getDistanceTotal();

            // Itinéraire complet de D à D via tous les points de la tournée
            Itineraire itin = new Itineraire(depot, depot, cheminGlobal, distanceTotale);
            res.add(itin);
        }

        return res;
    }

    // --------------------------------------------------------------------
    // Utilitaire Pair(a,b)
    // --------------------------------------------------------------------
    public static class Pair {
        public Sommet a, b;

        public Pair(Sommet a, Sommet b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return a.equals(p.a) && b.equals(p.b);
        }

        @Override
        public int hashCode() {
            return a.hashCode() * 31 + b.hashCode();
        }
    }

    // --------------------------------------------------------------------
    // Petit main de test console (optionnel)
    // --------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            Graphe g = Graphe.chargerGraphe("data/test/theme2_test.txt");
            Sommet depot = g.getSommet(0);

            List<PointCollecteSpb2> points = new ArrayList<>();
            points.add(new PointCollecteSpb2(g.getSommet(1), 4));
            points.add(new PointCollecteSpb2(g.getSommet(2), 3));
            points.add(new PointCollecteSpb2(g.getSommet(3), 3));
            points.add(new PointCollecteSpb2(g.getSommet(4), 5));

            int C = 7;

            List<Itineraire> tours = calculerTournees(g, depot, points, C);

            int i = 1;
            for (Itineraire it : tours) {
                System.out.println("=== Tournee " + i + " ===");
                it.afficher();
                System.out.println();
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


