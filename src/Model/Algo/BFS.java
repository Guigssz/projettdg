package Model.Algo;


import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BFS {


    public static Itineraire bfs(Graphe g , Sommet depart, Sommet arrivee) {


        if (depart == arrivee) return new Itineraire(depart, arrivee, new ArrayList<>(), 0 );

        Map< Sommet , Liaison> predecesseur = new HashMap<>();

        predecesseur.put(depart, null); é""

        // file d'arcs a explorer en attente fifo
        LinkedList<Arc> file = new LinkedList<>();
        return Itineraire;
    }




}
