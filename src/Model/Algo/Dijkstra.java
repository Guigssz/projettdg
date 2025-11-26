package Model.Algo;

import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class Dijkstra {


    public static Itineraire dijkstra(Graphe graphe, Sommet depart, Sommet arrivee){


        Map<Sommet, Double> dist = new HashMap<>();
        Map<Sommet, Sommet> precedent = new HashMap<>();


        // on met la distance très très haute des points à découvrir
        for (Sommet s : graphe.getAdj().keySet()) {
            dist.put(s, 1000000000.0);
            precedent.put(s, null);
        }
        dist.put(depart, 0.0);

        PriorityQueue<Sommet> listprio = new PriorityQueue<>(Comparator.comparing(dist::get));
        listprio.add(depart);

        while(listprio.isEmpty() == false) {

            Sommet actuel = listprio.poll();

            if (actuel.equals(arrivee)) break;

            for (Liaison l : graphe.getAdj().get(actuel)) {

                Sommet voisin = l.getSucc();
                double nouvDistance = dist.get(actuel) + l.getPoids();

                if (nouvDistance < dist.get(voisin)) {
                    dist.put(voisin, nouvDistance);
                    precedent.put(voisin, actuel);
                    listprio.add(voisin);
                }
            }

        }

        List<Sommet> chemin = refaireChemin(graphe, depart, arrivee, precedent);

        return new Itineraire(depart, arrivee, chemin, dist.get(arrivee));

    }


    // recopier coller du dijkstra mais retour différent, a reprendre plus tard pour faire plus propre
    public static Map<Sommet,Double> dijkstratheme2matdistance(Graphe graphe, Sommet depart){

        Map<Sommet, Double> dist = new HashMap<>();
        Map<Sommet, Sommet> precedent = new HashMap<>();


        // on met la distance très très haute des points à découvrir
        for (Sommet s : graphe.getAdj().keySet()) {
            dist.put(s, 1000000000.0);
            precedent.put(s, null);
        }
        dist.put(depart, 0.0);

        PriorityQueue<Sommet> listprio = new PriorityQueue<>(Comparator.comparing(dist::get));
        listprio.add(depart);

        while(listprio.isEmpty() == false) {

            Sommet actuel = listprio.poll();

            for (Liaison l : graphe.getAdj().get(actuel)) {

                Sommet voisin = l.getSucc();
                double nouvDistance = dist.get(actuel) + l.getPoids();

                if (nouvDistance < dist.get(voisin)) {
                    dist.put(voisin, nouvDistance);
                    precedent.put(voisin, actuel);
                    listprio.add(voisin);
                }
            }

        }

        return dist;

    }

    private static List<Sommet> refaireChemin(Graphe graphe, Sommet depart, Sommet arrivee, Map<Sommet, Sommet> precedent) {

        List<Sommet> chemin = new ArrayList<>();
        Sommet courant = arrivee;

        while (courant != null) {
            chemin.add(courant);
            courant = precedent.get(courant);
        }

        Collections.reverse(chemin);
        return chemin;
    }

    public static void main(String[] args){

        String fichierin = "data/test/adj1.txt";

        // test de dijkstra
        try {
            Graphe graphe = Graphe.chargerGraphe(fichierin);
            graphe.afficherLiaisons();
            System.out.println();
            graphe.afficherAdj();

            System.out.println();

            Sommet depart = graphe.getSommet(0);
            Sommet arrive = graphe.getSommet(2);

            Itineraire itin = Dijkstra.dijkstra(graphe, depart, arrive);

            itin.afficher();

        } catch (Exception e) {
            System.err.println("Oupsidoupsi : " + e.getMessage());
        }


    }


}
