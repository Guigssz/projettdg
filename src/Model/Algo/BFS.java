package Model.Algo;

import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class BFS {


    public static Itineraire bfs(Graphe g, Sommet depart, Sommet arrivee) {


        if (depart == arrivee) return new Itineraire(depart, arrivee, new ArrayList<>(), 0);

        Map<Sommet, Liaison> predecesseur = new HashMap<>();

        predecesseur.put(depart, null);

        // fil fifo
        LinkedList<Liaison> file = new LinkedList<>();

        // liaison départ
        for (Liaison lien : g.getAdj().get(depart)) {
            file.addLast(lien);

        if (lien.getOriente() && lien.getPred() != depart) {
            continue;
        }

        file.addLast(lien);
    }



        boolean trouve = false;

        // bfs
        while (!file.isEmpty() && !trouve) {

            // On retire liaison
            Liaison l = file.removeFirst();

            // sommet atteint par ce lien liaison
            Sommet actuelle = l.getSucc();

            // Si pas encore découvert
            if (!predecesseur.containsKey(actuelle)) {

                // On dit que pour atteindre 'courant', on a utilisé l
                predecesseur.put(actuelle, l);

                // fini
                if (actuelle == arrivee) {
                    trouve = true;
                    break;
                }

                // Sinon on ajoute all liaisons sortantes de actuelle
                for (Liaison lien2 : g.getAdj().get(actuelle)) {
                    file.addLast(lien2);


                        // ⚠️ graphe orienté : respecter le sens
                        if (lien2.getOriente() && lien2.getPred() != actuelle) {
                            continue; // mauvais sens
                        }

                        file.addLast(lien2);
                }
            }
        }

        if (!trouve) return null;

        // Reconstruction du chemin (liste liaisons)
        List<Sommet> cheminInverse = new ArrayList<>();

        double poidsTotal = 0.0;

        Sommet courant = arrivee;

        //  ajout del'arrivée
        cheminInverse.add(courant);

        // on remonte jusqu'au départ
        while (courant != depart) {

            Liaison l = predecesseur.get(courant);
            if (l == null) {
                // sécurité, normalement n'arrive pas si trouve == true
                break;
            }

            // on ajoute le poids de cette liaison
            poidsTotal += l.getPoids();

            // on remonte au prédécesseur
            courant = l.getPred();

            // on ajoute ce sommet
            cheminInverse.add(courant);
        }


        Collections.reverse(cheminInverse);

        // Renvoi de l'itinéraire final
        return new Itineraire(depart, arrivee, cheminInverse, poidsTotal);
    }



    public static void main(String[] args) {

        String fichierin = "data/test/adj1.txt";

        try {
            // Charger le graphe
            Graphe graphe = Graphe.chargerGraphe(fichierin);

            // Afficher pour debug
            graphe.afficherLiaisons();
            System.out.println();

            // (Décommente si tu as une méthode afficherAdj)
            // graphe.afficherAdj();

            System.out.println();

            // Choisir deux sommets pour tester
            Sommet depart = graphe.getSommet(0);
            Sommet arrivee = graphe.getSommet(3);

            // Lancer BFS
            Itineraire itin = BFS.bfs(graphe, depart, arrivee);

            // Affichage du résultat
            if (itin == null) {
                System.out.println("Aucun chemin trouvé !");
            } else {
                itin.afficher();
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
