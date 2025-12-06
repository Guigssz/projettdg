package Model.Theme1;

import Model.Graphe.*;
import Model.ResultatCommun.*;
import Model.Theme2.Tournee;

import java.util.*;

import static Model.Graphe.Graphe.*;

public class Pb2 {

    // cas ideal, tout les sommets sont pairs, H01
    public static TourneePb2 casIdeal(Graphe g, Sommet depot) {

        if (!sommetouspairs(g)) {
            System.out.println("Le graphe n'est pas eulérien !");
            return null;
        }

        List<Liaison> cycle = cycleEulerien(g, depot);
        List<Sommet> ordre = convertirAretesEnSommets(cycle,depot);
        double dist = distanceTotale(ordre, g);

        return new TourneePb2(depot, ordre, dist);
    }

    // =========== CAS 2 : Deux sommets impairs ===========
    public static TourneePb2 casDeuxImpairs(Graphe g, Sommet depot) {
        // 1. Identifier les sommets impairs
        // 2. Construire chemin eulérien
        // 3. Ajouter retour au dépôt avec plus court chemin
        // 4. Construire tournée finale
        return null;
    }

    // =========== CAS 3 : Cas Général (plusieurs impairs) ===========
    public static TourneePb2 casGeneral(Graphe g, Sommet depot) {
        // 1. Identifier sommets impairs
        // 2. Calculer couplage minimal -> transformation en graphe eulérien
        // 3. Appliquer Hierholzer
        // 4. Retourner la tournée
        return null;
    }

    // creer le cycle Eulerien
    private static List<Liaison> cycleEulerien(Graphe g, Sommet depot) {

       // copie pour par casser l'original
        Map<Sommet, List<Liaison>> adj = g.adjancopy();

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

    private static List<Sommet> convertirAretesEnSommets(List<Liaison> cycle, Sommet depot) {

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
                System.out.println("Erreur : pas de tournee !");
            }

        } catch (Exception e) {
            System.err.println("Erreur H01 : " + e.getMessage());
        }


    }


    private static double distanceTotale(List<Sommet> ordre, Graphe g) {
        double d = 0;

        for (int i = 0; i < ordre.size() - 1; i++) {
            Liaison e = g.getLiaisonAB(ordre.get(i), ordre.get(i+1));
            d += e.getPoids();
        }

        return d;
    }


    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.println("=== MENU THEME 1 PB2 ===");
        System.out.println("1. Cas ideal 0 sommet impairs");
        System.out.println("2. Cas autre");
        System.out.println("3. Cas autre2");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();

        switch (choix){
            case 1:
                maincasIdeal();
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

}
