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

    // -----------------------------
    // Distance Sommet -> PointCollecte
    // -----------------------------
    public static double distSommetToPC(
            Map<Sommet, Map<Sommet, Double>> matDist,
            Sommet s,
            PointCollecte pc)
    {
        Sommet u = pc.getU();
        Sommet v = pc.getV();

        double L = pc.getLongueurArete();

        // On traverse toute l'arête du PC
        double viaU = matDist.get(s).get(u) + L;
        double viaV = matDist.get(s).get(v) + L;

        return Math.min(viaU, viaV);
    }

    // -----------------------------
    // Distance PointCollecte -> PointCollecte
    // -----------------------------
    public static double distPCtoPC(
            Map<Sommet, Map<Sommet, Double>> matDist,
            PointCollecte a,
            PointCollecte b)
    {
        Sommet au = a.getU();
        Sommet av = a.getV();
        Sommet bu = b.getU();
        Sommet bv = b.getV();

        double La = a.getLongueurArete();
        double Lb = b.getLongueurArete();

        // Cas 1 : a(U→V) puis U→V pour b
        double opt1 = matDist.get(au).get(bu) + La + Lb;

        // Cas 2 : a(U→V) -> B→U
        double opt2 = matDist.get(au).get(bv) + La + Lb;

        // Cas 3 : a(V→U) -> U→V
        double opt3 = matDist.get(av).get(bu) + La + Lb;

        // Cas 4 : a(V→U) -> V→U
        double opt4 = matDist.get(av).get(bv) + La + Lb;

        return Math.min(Math.min(opt1, opt2), Math.min(opt3, opt4));
    }
}
