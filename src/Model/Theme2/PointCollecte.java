package Model.Theme2;

import Model.Graphe.*;

import java.util.Map;

public class PointCollecte {

    private final Liaison arete;   // arête (u, v)
    private final double position; // distance depuis u

    public PointCollecte(Liaison arete, double position) {
        if (position < 0 || position > arete.getPoids()) {
            throw new IllegalArgumentException("Position hors de l'arête !");
        }
        this.arete = arete;
        this.position = position;
    }

    public Liaison getArete() { return arete; }
    public double getPosition() { return position; }

    public Sommet getU() { return arete.getPred(); }
    public Sommet getV() { return arete.getSucc(); }
    public double getLongueurArete() { return arete.getPoids(); }

    public static double distSommetToPC(
            Map<Sommet, Map<Sommet, Double>> matDist,
            Sommet s,
            PointCollecte pc)
    {
        Sommet u = pc.getU();
        Sommet v = pc.getV();
        double d = pc.getPosition();
        double L = pc.getLongueurArete();

        double viaU = matDist.get(s).get(u) + d;
        double viaV = matDist.get(s).get(v) + (L - d);

        return Math.min(viaU, viaV);
    }


    public static double distPCtoPC(
            Map<Sommet, Map<Sommet, Double>> matDist,
            PointCollecte a,
            PointCollecte b)
    {
        // ancres de a
        Sommet au = a.getU();
        Sommet av = a.getV();
        double da = a.getPosition();
        double La = a.getLongueurArete();

        // ancres de b
        Sommet bu = b.getU();
        Sommet bv = b.getV();
        double db = b.getPosition();
        double Lb = b.getLongueurArete();

        double opt1 = matDist.get(au).get(bu) + da + db;
        double opt2 = matDist.get(au).get(bv) + da + (Lb - db);
        double opt3 = matDist.get(av).get(bu) + (La - da) + db;
        double opt4 = matDist.get(av).get(bv) + (La - da) + (Lb - db);

        return Math.min(Math.min(opt1, opt2), Math.min(opt3, opt4));
    }

}
