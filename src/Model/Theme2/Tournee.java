package Model.Theme2;

import Model.Graphe.*;

import java.util.*;

public class Tournee {

    private Sommet depot;
    private List<Integer> ordrePoints; // index des PC visités
    private double distanceTotale;

    public Tournee(Sommet depot, List<Integer> ordrePoints, double distanceTotale) {
        this.depot = depot;
        this.ordrePoints = ordrePoints;
        this.distanceTotale = distanceTotale;
    }

    public void afficher() {

        System.out.print("Tournée : D(" + depot.getId() + ") -> ");

        for (Integer i : ordrePoints) {
            System.out.print("PC" + i + " -> ");
        }

        System.out.println("D(" + depot.getId() + ")");
        System.out.println("Distance totale : " + distanceTotale);
    }
}
