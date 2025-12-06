package Model.Theme1;

import Model.Graphe.*;
import java.util.*;

public class TourneePb2 {

    private Sommet depot;
    private List<Sommet> ordre; // ordre complet de la tournée
    private double distanceTotale;

    public TourneePb2(Sommet depot, List<Sommet> ordre, double distanceTotale) {
        this.depot = depot;
        this.ordre = ordre;
        this.distanceTotale = distanceTotale;
    }

    public void afficher() {
        System.out.println("Tournée complète depuis le dépôt " + depot.getId());
        for (Sommet s : ordre) {
            System.out.print(s.getId() + " -> ");
        }
        System.out.println("Distance totale = " + distanceTotale);
    }

}
