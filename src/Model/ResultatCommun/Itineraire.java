package Model.ResultatCommun;

import Model.Graphe.*;
import java.util.*;

public class Itineraire {

    private Sommet depart;
    private Sommet arrivee;
    private List<Sommet> listsommet;
    private double distancetotal;
    
    public Itineraire(Sommet depart, Sommet arrivee, List<Sommet> listsommet, double distancetotal) {
        
        this.depart = depart;
        this.arrivee = arrivee;
        this.listsommet = listsommet;
        this.distancetotal = distancetotal;
        
    }



    public void afficher(){

        System.out.println("Itineraire de " + depart.getId() + " a " + arrivee.getId());

        for (Sommet sommet : listsommet) {
            System.out.println(sommet.getId());
        }

        System.out.println("Distance total : " + distancetotal);


    }


}
