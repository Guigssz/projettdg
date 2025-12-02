package Model.Theme2;

import Model.Algo.Dijkstra;
import Model.Graphe.*;
import java.util.*;

public class CalcDistances {

    private final Graphe graphe;
    private Map<Sommet, Map<Sommet, Double>> dist = new HashMap<>();

    public CalcDistances(Graphe graphe){
        this.graphe = graphe;
    }

    public void calcMatDistance(){

        for (Sommet s : graphe.getSommets()) {
            Map<Sommet, Double> ligne = Dijkstra.dijkstratheme2matdistance(graphe, s);
            dist.put(s, ligne);
        }
    }

    public Map<Sommet, Map<Sommet, Double>> getMatDistance(){
        return dist;
    }
}

