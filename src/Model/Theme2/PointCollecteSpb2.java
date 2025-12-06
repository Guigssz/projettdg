package Model.Theme2;

import Model.Graphe.Sommet;

/**
 * Représente un point de collecte pour le Thème 2 hypothèse 2.
 * Il encapsule :
 *  - un sommet du graphe routier,
 *  - une contenance (quantité de déchets générée).
 */
public class PointCollecteSpb2 {

    private Sommet sommet;
    private int contenance;

    public PointCollecteSpb2(Sommet sommet, int contenance) {
        this.sommet = sommet;
        this.contenance = contenance;
    }

    public Sommet getSommet() {
        return sommet;
    }

    public int getContenance() {
        return contenance;
    }

    public void setContenance(int contenance) {
        this.contenance = contenance;
    }

    @Override
    public String toString() {
        return "PointCollecte(sommet=" + sommet.getId() +
                ", cont=" + contenance + ")";
    }
}


