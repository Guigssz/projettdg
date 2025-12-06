package Model.Theme2;

import Model.Graphe.*;

import java.util.*;

public class Tournee {

    private Sommet depot;
    private List<PointCollecte> ordre;
    private double total;

    public Tournee(Sommet depot, List<PointCollecte> ordre, double total) {
        this.depot = depot;
        this.ordre = ordre;
        this.total = total;
    }

    public void afficher() {
        System.out.println("Départ du dépôt : " + depot.getId());
        System.out.println("Ordre de visite des PC :");

        for (PointCollecte pc : ordre) {
            System.out.println("- PC sur liaison "
                    + pc.getU().getId() + " → " + pc.getV().getId()
                    + (pc.getArete().getOriente() ? " (sens unique)" : ""));
        }

        System.out.println("Distance totale : " + total);
    }
}

