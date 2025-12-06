package Model.Theme3;

import Model.Graphe.Sommet;
import java.util.ArrayList;
import java.util.List;

public class Secteur {

    private final int id;
    private final List<Sommet> sommetsDuSecteur;
    private int jourCollecte; // -1 si pas encore défini (la "couleur")

    public Secteur(int id) {
        this.id = id;
        this.sommetsDuSecteur = new ArrayList<>();
        this.jourCollecte = -1;
    }

    public void ajouterSommet(Sommet s) {
        if (!sommetsDuSecteur.contains(s)) {
            sommetsDuSecteur.add(s);
        }
    }

    public int getId() {
        return id;
    }

    public List<Sommet> getSommetsDuSecteur() {
        return sommetsDuSecteur;
    }

    public int getJourCollecte() {
        return jourCollecte;
    }

    public void setJourCollecte(int jourCollecte) {
        this.jourCollecte = jourCollecte;
    }

    @Override
    public String toString() {
        return "Secteur " + id + " (J" + jourCollecte + ")";
    }
}
