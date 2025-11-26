package Model.Theme2;

import Model.Algo.Dijkstra;
import Model.Graphe.*;
import java.util.*;

public class CalcDistances {

    private final Graphe graphe;
    private final Sommet depot;
    private final List<Sommet> pCollectes;
    private Map<Sommet, Map<Sommet, Double>> dist;

    public CalcDistances(Graphe graphe, Sommet depot,  List<Sommet> pCollectes){

        this.graphe = graphe;
        this.depot = depot;
        this.pCollectes = pCollectes;
        this.dist = new HashMap<>();

    }

    public void calcMatDistance(){

        // sommet à check = le dépot et les points de collectes
        List<Sommet> sommetToCheck = new  ArrayList<>();
        sommetToCheck.add(depot); // on ajoute le dépot
        sommetToCheck.addAll(pCollectes); // on ajoute la liste de sommet qui sont des points de collectes


        // Pour chaque sommet à check on va déterminer la distance entre ce sommet et tout les autres sommet à check
        for(Sommet temp : sommetToCheck ){

            Map<Sommet, Double> distDij = Dijkstra.dijkstratheme2matdistance(graphe, temp);

            Map<Sommet, Double> ligneDist = new HashMap<>();

            for (Sommet destination : sommetToCheck){
                double distance = distDij.get(destination);
                ligneDist.put(destination, distance);
            }

            dist.put(temp, ligneDist);

        }

    }


    public Map<Sommet, Map<Sommet, Double>> getMatDistance(){
        return dist;
    }

    public void afficherMatDist(){

        System.out.println("Mat distance : ");

        List<Sommet> lignes = new ArrayList<>(dist.keySet());
        lignes.sort(Comparator.comparing(Sommet::getId));

        for (Sommet temp : dist.keySet()){

            System.out.println(temp.getId() + " : ");

            Map<Sommet, Double> ligne = dist.get(temp);

            List<Sommet> colonnes = new ArrayList<>(ligne.keySet());
            colonnes.sort(Comparator.comparing(Sommet::getId));

            for (Sommet temp2 : colonnes){

                System.out.println("-- " + temp2.getId() + " : " + ligne.get(temp2));

            }

            System.out.println();

        }

    }




}
