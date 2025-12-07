package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Liaison;
import Model.Graphe.Sommet;

import java.util.List;

public class GestionDechetSecteur {

    public static void calculerMetriquesSecteurs(List<Secteur> secteurs, Graphe grapheGlobal, double capaciteCamion, double densiteDechets) {


        for (Secteur s : secteurs) {
            s.setQuantiteTotaleDechets(0);
            s.setNombreToursCamion(0);
        }}}
