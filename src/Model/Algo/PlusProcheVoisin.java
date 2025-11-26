package Model.Algo;

import Model.Graphe.*;
import Model.ResultatCommun.Itineraire;
import Model.Theme2.CalcDistances;
import Model.Theme2.Tournee;
import java.util.*;

public class PlusProcheVoisin {


    public static Tournee ppv(Map<Sommet, Map<Sommet, Double>> matDist, Sommet depot, List<Sommet> pCollectes){



        List<Sommet> ordreTournee = new ArrayList<>();
        ordreTournee.add(depot); // on commence au dépot logique

        Set<Sommet> sommetAttentes = new HashSet<>(pCollectes); //copie de la liste

        Sommet actuel = depot;

        while(sommetAttentes.isEmpty() == false){

            Sommet next = null;
            double minDist = 10000000.0;

            for (Sommet temp : sommetAttentes) {

                double dist = matDist.get(actuel).get(temp);

                if (dist < minDist) {

                    minDist = dist;
                    next = temp;

                }

            }

            ordreTournee.add(next);
            sommetAttentes.remove(next);
            actuel = next;

        }

        ordreTournee.add(depot); // retour au bercail

        //calcul de la distance total
        double distancetotal = 0;
        for (int i = 0; i < ordreTournee.size() - 1; i++) {
            Sommet a = ordreTournee.get(i);
            Sommet b = ordreTournee.get(i+1);
            distancetotal += matDist.get(a).get(b);
        }

        return new Tournee(depot, ordreTournee ,distancetotal);

    }


    public static void main(String[] args){

        //Main de test

        String fichierin = "data/test/adj1.txt";

        // test de ppv
        try {
            Graphe graphe = Graphe.chargerGraphe(fichierin);
            graphe.afficherLiaisons();
            System.out.println();
            graphe.afficherAdj();

            System.out.println();

            // Il faut choisir le sommet de depot et les sommet pCollectes
            Sommet depot = graphe.getSommet(0);

            List<Sommet> pCollectes = List.of(
                    graphe.getSommet(1),
                    graphe.getSommet(2),
                    graphe.getSommet(3)
            );

            // construire la matrice de distance entre les pcollectes et depot
            CalcDistances matDist = new CalcDistances(graphe, depot, pCollectes);
            matDist.calcMatDistance();

            matDist.afficherMatDist();

            Tournee tournee = PlusProcheVoisin.ppv(matDist.getMatDistance(), depot, pCollectes);
            tournee.afficher();


        } catch (Exception e) {
            System.err.println("Oupsidoupsi : " + e.getMessage());
        }



    }



}
