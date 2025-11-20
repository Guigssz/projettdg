package Model.ResultatCommun;

import Model.Graphe.*;
import java.util.*;

public class Itineraire {

    private Sommet depart;
    private Sommet arrive;
    private List<Liaison> listliaison;
    private double distancetotal;
    
    public Itineraire(Sommet depart, Sommet arrive, List<Liaison> listliaison, double distancetotal) {
        
        this.depart = depart;
        this.arrive = arrive;
        this.listliaison = listliaison;
        this.distancetotal = distancetotal;
        
    }




}
