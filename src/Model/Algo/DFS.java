package Model.Algo;

import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class DFS {

    public static Itineraire dfs(Graphe g, Sommet depart, Sommet arrivee) {

        if (depart == arrivee) {
            List<Sommet> cheminSeul = new ArrayList<>();
            cheminSeul.add(depart);
            return new Itineraire(depart, arrivee, cheminSeul, 0);
        }

        Stack<Sommet> pile = new Stack<>();

        Map<Sommet, Liaison> provenance = new HashMap<>();

        Set<Sommet> visite = new HashSet<>();

        pile.push(depart);
        visite.add(depart);

        boolean trouve = false;

        while (!pile.isEmpty()) {
            Sommet courant = pile.pop();

            if (courant == arrivee) {
                trouve = true;
                break;
            }

            List<Liaison> voisins = g.getAdj().get(courant);
            if (voisins != null) {
                for (Liaison l : voisins) {
                    Sommet voisin = l.getSucc();

                    if (!visite.contains(voisin)) {
                        visite.add(voisin);
                        provenance.put(voisin, l);
                        pile.push(voisin);


                        if (voisin == arrivee) {
                            trouve = true;
                            break;
                        }
                    }
                }
            }
            if (trouve) break; // Sortie du while si trouvé dans le for
        }

        if (!trouve) {
            return null;
        }

        List<Sommet> chemin = new ArrayList<>();
        double distanceTotale = 0.0;
        Sommet courantReconstruction = arrivee;

        chemin.add(courantReconstruction);

        while (courantReconstruction != depart) {
            Liaison l = provenance.get(courantReconstruction);

            if (l == null) break; // Sécurité

            distanceTotale += l.getPoids();
            courantReconstruction = l.getPred();
            chemin.add(courantReconstruction);
        }

        Collections.reverse(chemin);

        return new Itineraire(depart, arrivee, chemin, distanceTotale);
    }

    public static void main(String[] args) {
        String fichierin = "data/test/adj1.txt";

        try {
            Graphe graphe = Graphe.chargerGraphe(fichierin);

            Sommet depart = graphe.getSommet(0);
            Sommet arrivee = graphe.getSommet(3);

            System.out.println("--- Lancement DFS ---");
            Itineraire itin = DFS.dfs(graphe, depart, arrivee);

            if (itin != null) {
                itin.afficher();
            } else {
                System.out.println("Aucun chemin trouvé.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}