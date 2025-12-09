package Model.Theme1;

import Model.Algo.Dijkstra;
import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

import static Model.Graphe.Graphe.*;

public class Pb2 {

    // cas ideal, tout les sommets sont pairs, H01
    public static TourneePb2 casIdeal(Graphe g, Sommet depot) {

        if (!sommetouspairs(g)) {
            System.out.println("graph pas eulerien");
            return null;
        }

        List<Liaison> cycle = cycleEulerien(g, depot);
        List<Sommet> ordre = convarretetosommet(cycle,depot);
        double dist = distanceTotale(ordre, g);

        return new TourneePb2(depot, ordre, dist);
    }

    // cas 2 sommet impairs, faut rajouter des sommets virtuelle
    public static TourneePb2 casDeuxImpairs(Graphe g, Sommet depot) {

        // recup les sommets impairs
        List<Sommet> impairs = g.getsommetImparis();
        if (impairs.size() != 2) {
            System.out.println("Erreur : pas exact 2 sommet impaurs");
            return null;
        }

        Sommet u = impairs.get(0);
        Sommet v = impairs.get(1);

        // dijkstra entre les 2
        Itineraire dij = Dijkstra.dijkstra(g, u, v);
        List<Sommet> cheminUV = dij.getListSommet();

        List<Liaison> cycle = cycleEulerienchemin(g, depot, cheminUV);


        List<Sommet> ordre = convarretetosommet(cycle, depot);

        double dist = distanceTotale(ordre, g);

        return new TourneePb2(depot, ordre, dist);
    }

    // cas général plusieurs impaires
    public static TourneePb2 casGeneral(Graphe g, Sommet depot) {


        List<Sommet> impairs = g.getsommetImparis();


        // on renvoi au cas ou dans le smeilleur cas
        if(impairs.size() == 0){
            return casIdeal(g, depot);
        }
        if(impairs.size() == 2){
            return casDeuxImpairs(g, depot);
        }

        // matching glouton
        List<Sommet[]> matching = matchersommet(g, impairs);

        // nouveau graph
        Map<Sommet,List<Liaison>> adj = copieEulermatchg(g, matching);

        // avec le nouveau graph
        List<Liaison> cycle = cycleEulerienadj(adj, depot, g);

        List<Sommet> ordre = convarretetosommet(cycle, depot);

        double dist = distanceTotale(ordre, g);

        return new TourneePb2(depot, ordre, dist);

    }

    // creer le cycle Eulerien
    private static List<Liaison> cycleEulerien(Graphe g, Sommet depot) {

       // copie pour par casser l'original
        Map<Sommet, List<Liaison>> adj = g.adjcopyeuler();

        Stack<Sommet> pile = new Stack<>();
        List<Liaison> cycle = new ArrayList<>();

        pile.push(depot);

        while (!pile.isEmpty()) {
            Sommet u = pile.peek();

            if (!adj.get(u).isEmpty()) {
                Liaison e = adj.get(u).remove(0);
                Sommet v = e.getlautre(u);

                adj.get(v).remove(e);

                pile.push(v);
            } else {
                pile.pop();
                if (!pile.isEmpty()) {
                    Sommet v = pile.peek();
                    cycle.add(g.getLiaisonAB(u, v));
                }
            }
        }

        return cycle;
    }

    private static List<Sommet> convarretetosommet(List<Liaison> cycle, Sommet depot) {

        List<Sommet> ordre = new ArrayList<>();
        ordre.add(depot);

        Sommet courant = depot;

        for (Liaison l : cycle) {
            Sommet suivant = l.getlautre(courant);
            ordre.add(suivant);
            courant = suivant;
        }

        return ordre;
    }

    private static List<Liaison> cycleEulerienchemin(Graphe g, Sommet depot, List<Sommet> cheminUV){


        Map<Sommet, List<Liaison>> adj = g.adjcopyeuler();

        // arrete du ppc entre les 2 sommet imparis
        for (int i = 0; i < cheminUV.size() - 1; i++) {
            Sommet a = cheminUV.get(i);
            Sommet b = cheminUV.get(i+1);

            Liaison l = g.getLiaisonAB(a, b);
            adj.get(a).add(l);
            adj.get(b).add(l);
        }

        // Hierholzer
        Stack<Sommet> pile = new Stack<>();
        List<Liaison> cycle = new ArrayList<>();

        pile.push(depot);

        while (!pile.isEmpty()) {
            Sommet u = pile.peek();

            if (!adj.get(u).isEmpty()) {
                Liaison e = adj.get(u).remove(0);
                Sommet v = e.getlautre(u);

                adj.get(v).remove(e);
                pile.push(v);

            } else {
                pile.pop();
                if (!pile.isEmpty()) {
                    Sommet v = pile.peek();
                    cycle.add(g.getLiaisonAB(u, v));
                }
            }
        }

        return cycle;
    }

    private static double distanceTotale(List<Sommet> ordre, Graphe g) {
        double d = 0;

        for (int i = 0; i < ordre.size() - 1; i++) {
            Liaison e = g.getLiaisonAB(ordre.get(i), ordre.get(i+1));
            d += e.getPoids();
        }

        return d;
    }

    private static List<Sommet[]> matchersommet(Graphe g, List<Sommet> impairs) {

        List<Sommet[]> match = new ArrayList<>();
        List<Sommet> restants = new ArrayList<>(impairs);

        while(restants.size() > 1){

            Sommet u = restants.get(0);
            Sommet meilleurV = null;
            double meilleurDist = 1000000.0;

            // Chercher le sommet impair le plus proche de u
            for(int i = 1; i < restants.size(); i++){
                Sommet v = restants.get(i);

                Itineraire it = Dijkstra.dijkstra(g, u, v);
                double d = it.getDistanceTotal();

                if(d < meilleurDist){
                    meilleurDist = d;
                    meilleurV = v;
                }
            }


            match.add(new Sommet[]{u, meilleurV});


            restants.remove(u);
            restants.remove(meilleurV);
        }

        return match;
    }

    private static Map<Sommet,List<Liaison>> copieEulermatchg(Graphe g, List<Sommet[]> matching){
        Map<Sommet,List<Liaison>> adj = g.adjcopyeuler();

        for (Sommet[] pair : matching) {

            Sommet u = pair[0];
            Sommet v = pair[1];

            // plus court chemin entre u et v
            Itineraire it = Dijkstra.dijkstra(g, u, v);
            List<Sommet> chemin = it.getListSommet();

            // ajouter virtuellement les liaisons dans la copie
            for(int i = 0; i < chemin.size() - 1; i++){
                Sommet a = chemin.get(i);
                Sommet b = chemin.get(i+1);

                Liaison l = g.getLiaisonAB(a, b);

                adj.get(a).add(l);
                adj.get(b).add(l);
            }
        }

        return adj;
    }

    private static List<Liaison> cycleEulerienadj(Map<Sommet, List<Liaison>> adj, Sommet depot, Graphe g){
        Stack<Sommet> pile = new Stack<>();
        List<Liaison> cycle = new ArrayList<>();

        pile.push(depot);

        while (!pile.isEmpty()) {

            Sommet u = pile.peek();

            if (!adj.get(u).isEmpty()) {

                Liaison e = adj.get(u).remove(0);
                Sommet v = e.getlautre(u);


                adj.get(v).remove(e);


                pile.push(v);

            } else {

                pile.pop();

                if (!pile.isEmpty()) {
                    Sommet v = pile.peek();

                    cycle.add(g.getLiaisonAB(u, v));
                }
            }
        }

        return cycle;
    }


    public static void maincasIdeal() {



        try {

            Scanner sc = new Scanner(System.in);

            String fichier = "data/test/zeroimpairs.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            System.out.print("Entrez id du sommet de depart/depot : ");
            int idDepot = sc.nextInt();
            Sommet depot = g.getSommet(idDepot);

            TourneePb2 tournee = Pb2.casIdeal(g, depot);

            if (tournee != null) {
                tournee.afficher();
            } else {
                System.out.println("erreur pas tourne");
            }

        } catch (Exception e) {
            System.err.println("Erreur H01 : " + e.getMessage());
        }


    }

    public static void maincas2impairs(){

            try {

                Scanner sc = new Scanner(System.in);

                System.out.println("=== TEST CAS 2 : Deux sommets impairs ===");

                String fichier = "data/test/deuxsommetimpairs.txt";
                Graphe g = Graphe.chargerGraphe(fichier);

                System.out.println("\n=== Graphe chargé ===");
                g.afficherAdj();
                System.out.println();

                // verif sommet impairs
                List<Sommet> impairs = g.getsommetImparis();
                System.out.println("Sommets impairs : ");
                for (Sommet s : impairs) System.out.print(s.getId() + " ");
                System.out.println();

                if (impairs.size() != 2) {
                    System.out.println("\nerreur pas 2 sommet impairs");
                    return;
                }


                System.out.print("\nEntrez l'id du depot : ");
                int idDepot = sc.nextInt();
                Sommet depot = g.getSommet(idDepot);

                TourneePb2 tournee = Pb2.casDeuxImpairs(g, depot);


                if (tournee != null) {
                    System.out.println("\n=== TOURNEE PB2 - CAS 2 ===");
                    tournee.afficher();
                } else {
                    System.out.println("erreur");
                }

            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
            }

    }

    public static void maincasquelconque(){


        try {

            Scanner sc = new Scanner(System.in);

            System.out.println("=== TEST CAS GENERAL : +2 sommets impairs ===");

            String fichier = "data/test/adjmarc.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("\n=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            List<Sommet> impairs = g.getsommetImparis();
            System.out.print("Sommets impairs (" + impairs.size() + ") : ");
            for (Sommet s : impairs) System.out.print(s.getId() + " ");
            System.out.println();

            if (impairs.size() <= 2) {
                System.out.println("\nerreur pas bon cas");
                System.out.println("utiliser autre euler");
                return;
            }

            System.out.print("\nEntrez l'id du depot : ");
            int idDepot = sc.nextInt();
            Sommet depot = g.getSommet(idDepot);


            TourneePb2 tournee = Pb2.casGeneral(g, depot);


            if (tournee != null) {

                System.out.println("\n=== TOURNEE PB2 - CAS GENERAL ===");
                tournee.afficher();

            } else {
                System.out.println("erreur ");
            }

        } catch (Exception e) {
            System.err.println("erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.println("=== MENU THEME 1 PB2 ===");
        System.out.println("1. Cas ideal 0 sommet impairs");
        System.out.println("2. Cas 2 sommet impairs");
        System.out.println("3. Cas quelconque");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();

        switch (choix){
            case 1:
                maincasIdeal();
                break;
            case 2:
                maincas2impairs();
                break;
            case 3:
                maincasquelconque();
                break;
        }

    }

}
