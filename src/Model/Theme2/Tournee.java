package Model.Theme2;

import Model.Graphe.*;
import java.util.*;

public class Tournee {

    private Sommet depot;
    private List<Sommet> ordrepassage;
    private double distancetotal;

    public Tournee(Sommet depot, List<Sommet> ordrepassage, double distancetotal) {

        this.depot = depot;
        this.ordrepassage = ordrepassage;
        this.distancetotal = distancetotal;

    }

    public void afficher() {

        System.out.print("Tournee, point de depart (dépot : " + depot.getId() + ") : ");
        for (Sommet s : ordrepassage) {
            System.out.print(s.getId() + " -> ");
        }
        System.out.println("Distance total : " + distancetotal);
    }


    public Sommet getDepot() {
        return depot;
    }

    public List<Sommet> getOrdrepassage() {
        return ordrepassage;
    }

    public double getDistanceTotale() {
        return distancetotal;
    }

}
