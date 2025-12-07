package Model.Theme3;

import Model.Graphe.Sommet;
import java.util.ArrayList;
import java.util.List;

public class Secteur {

    private final int id;
    private final List<Sommet> sommetsDuSecteur;
    private int jourCollecte; // La "Couleur" (0=Lundi, 1=Mardi...)

    //  Hypothèse 2
    private Sommet depot;
    private double quantiteTotaleDechets;
    private int nombreToursCamion;

    public Secteur(int id) {
        this.id = id;
        this.sommetsDuSecteur = new ArrayList<>();
        this.jourCollecte = -1;
        this.quantiteTotaleDechets = 0;
        this.nombreToursCamion = 0;
    }

    public void ajouterSommet(Sommet s) {
        if (!sommetsDuSecteur.contains(s)) {
            sommetsDuSecteur.add(s);
        }
    }



    public int getId() { return id; }
    public List<Sommet> getSommetsDuSecteur() { return sommetsDuSecteur; }

    public int getJourCollecte() { return jourCollecte; }
    public void setJourCollecte(int jourCollecte) { this.jourCollecte = jourCollecte; }

    public Sommet getDepot() { return depot; }
    public void setDepot(Sommet depot) { this.depot = depot; }

    public double getQuantiteTotaleDechets() { return quantiteTotaleDechets; }
    public void setQuantiteTotaleDechets(double q) { this.quantiteTotaleDechets = q; }

    public int getNombreToursCamion() { return nombreToursCamion; }
    public void setNombreToursCamion(int n) { this.nombreToursCamion = n; }

    // Méthode utilitaire pour vérifier si un sommet est dans ce secteur
    public boolean contientSommet(Sommet s) {
        return sommetsDuSecteur.contains(s);
    }

    @Override
    public String toString() {
        return "Secteur " + id + " [Déchets: " + quantiteTotaleDechets + "kg | Tours: " + nombreToursCamion + " | Jour: " + jourCollecte + "]";
    }
}