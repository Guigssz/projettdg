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
            Scanner sc = new Scanner(System.in);

            // Charger un graphe simple
            String fichier = "data/test/adjmarc.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Liaisons du graphe ===");
            g.afficherLiaisons();
            System.out.println();

            System.out.println("=== Liste d'adjacence ===");
            g.afficherAdj();
            System.out.println();


            // 🎯 Sélection du DEPOT
            System.out.print("Entrez l'ID du sommet 'depot' : ");
            int idDepot = sc.nextInt();
            Sommet depot = g.getSommet(idDepot);

            // 🎯 Sélection des extrémités A et B
            System.out.print("Entrer l'ID du sommet A (extrémité 1 de l'arête) : ");
            int idA = sc.nextInt();
            Sommet A = g.getSommet(idA);

            System.out.print("Entrer l'ID du sommet B (extrémité 2 de l'arête) : ");
            int idB = sc.nextInt();
            Sommet B = g.getSommet(idB);

            // Vérifier si l'arête existe dans AU MOINS un sens
            Liaison AB = null;

            // Tester A → B
            for (Liaison l : g.getAdj().get(A)) {
                if (l.getSucc().equals(B)) {
                    AB = l;
                    break;
                }
            }

            // Si pas trouvé, tester B → A (au cas où ton graphe est orienté ou l'arête est ajoutée dans l'autre sens)
            if (AB == null) {
                for (Liaison l : g.getAdj().get(B)) {
                    if (l.getSucc().equals(A)) {
                        AB = l;
                        break;
                    }
                }
            }

            // Toujours rien → erreur
            if (AB == null) {
                System.out.println("❌ Erreur : l'arête " + idA + " - " + idB + " n'existe pas dans le graphe !");
                return;
            }

            // Création de l'encombrant
            Encombrant e = new Encombrant(AB);

            // Calcul de l’itinéraire
            Itineraire itin = CalculItineraire.itineraireVersEncombrant(g, depot, e);

            // Affichage
            System.out.println("\n=== Itineraire vers l'encombrant situé sur l'arête (" + idA + "," + idB + ") ===");
            itin.afficher();

        } catch (Exception e) {
            System.err.println("Erreur lors du test : " + e.getMessage());
        }
    }


}
