package Model.Theme2;

import Model.Graphe.*;
import java.util.Map;

public class PointCollecte {

    private final Liaison arete;   // l'arête où se trouve le PC

    public PointCollecte(Liaison arete) {
        this.arete = arete;
    }

    public Liaison getArete() { return arete; }

    public Sommet getU() { return arete.getPred(); }
    public Sommet getV() { return arete.getSucc(); }
    public double getLongueurArete() { return arete.getPoids(); }


    public static double distSommetToPC(Map<Sommet, Map<Sommet, Double>> matDist, Sommet s, PointCollecte pc)
    {
        Sommet u = pc.getU();
        Sommet v = pc.getV();

        double L = pc.getLongueurArete();

        if (pc.getArete().getOriente()) {
            return matDist.get(s).get(u) + L;
        }

        // On traverse toute l'arete du PC
        double viaU = matDist.get(s).get(u) + L;
        double viaV = matDist.get(s).get(v) + L;

        return Math.min(viaU, viaV);
    }

    public static double distPCtoPC(Map<Sommet, Map<Sommet, Double>> matDist, PointCollecte a, PointCollecte b)
    {
        // Déterminer les points d’entrée possibles en A
        Sommet aU = a.getU();
        Sommet aV = a.getV();
        Sommet bU = b.getU();
        Sommet bV = b.getV();

        double La = a.getLongueurArete();
        double Lb = b.getLongueurArete();

        double best = Double.MAX_VALUE;

        // Définir les extrémités valides selon HO1/HO2
        Sommet[] entreesA = a.getArete().getOriente()
                ? new Sommet[]{ aU }              // HO2 : entrée obligatoirement par U
                : new Sommet[]{ aU, aV };         // HO1 : entrée libre

        Sommet[] entreesB = b.getArete().getOriente()
                ? new Sommet[]{ bU }              // HO2 : entrée obligatoirement par U
                : new Sommet[]{ bU, bV };         // HO1 : entrée libre

        // Calculer toutes les combinaisons valides
        for (Sommet sa : entreesA) {
            for (Sommet sb : entreesB) {
                double d = matDist.get(sa).get(sb) + La + Lb;
                best = Math.min(best, d);
            }
        }

        return best;
    }




}
