package Model.Theme1;

import Model.Algo.*;
import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class CalculItineraire {



    public static Itineraire itineraireVersEncombrant(Graphe g, Sommet depot, Encombrant e) {

        Sommet A = e.getLiaison().getPred();
        Sommet B = e.getLiaison().getSucc();
        double w = e.getLiaison().getPoids();

        // 1️⃣ On utilise TON dijkstra pour calculer D→A et D→B
        Itineraire itinDA = Dijkstra.dijkstra(g, depot, A);
        Itineraire itinDB = Dijkstra.dijkstra(g, depot, B);

        double cout1 = itinDA.getDistanceTotal() + w; // D -> A -> B
        double cout2 = itinDB.getDistanceTotal() + w; // D -> B -> A

        // 2️⃣ Choix du meilleur sens
        if (cout1 <= cout2) {
            // partir vers A puis traverser A→B
            List<Sommet> chemin = new ArrayList<>(itinDA.getListSommet());
            chemin.add(B);
            return new Itineraire(depot, B, chemin, cout1);

        } else {
            // partir vers B puis traverser B→A
            List<Sommet> chemin = new ArrayList<>(itinDB.getListSommet());
            chemin.add(A);
            return new Itineraire(depot, A, chemin, cout2);
        }
    }



    public static void main(String[] args) {

        try {
            // Charger un graphe simple
            String fichier = "data/test/adjmarc.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Liaisons du graphe ===");
            g.afficherLiaisons();
            System.out.println();

            System.out.println("=== Liste d'adjacence ===");
            g.afficherAdj();
            System.out.println();

            // Choisir le dépôt et une arête où se trouve l'encombrant
            Sommet depot = g.getSommet(8);
            Sommet A = g.getSommet(10);
            Sommet B = g.getSommet(12);

            // On récupère la liaison A-B dans la liste
            Liaison AB = null;
            for (Liaison l : g.getAdj().get(A)) {
                if (l.getSucc().equals(B)) {
                    AB = l;
                    break;
                }
            }

            if (AB == null) {
                System.out.println("Erreur : l'arête A-B n'existe pas dans le graphe !");
                return;
            }

            // Création de l'encombrant situé sur A-B
            Encombrant e = new Encombrant(AB);

            // Calcul de l’itinéraire
            Itineraire itin = CalculItineraire.itineraireVersEncombrant(g, depot, e);

            // Affichage
            System.out.println("=== Itineraire vers encombrant sur A-B ===");
            itin.afficher();

        } catch (Exception e) {
            System.err.println("Erreur lors du test : " + e.getMessage());
        }
    }


}
