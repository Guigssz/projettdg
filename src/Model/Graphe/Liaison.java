package Model.Graphe;

public class Liaison {

    private final Sommet  pred;
    private final Sommet succ;
    private final double poids;
    private final boolean oriente;


    public Liaison(Sommet pred, Sommet succ, double poids, boolean oriente){

        this.pred = pred;
        this.succ = succ;
        this.poids = poids;
        this.oriente = oriente;

    }


    public Sommet getPred() {
        return pred;
    }

    public Sommet getSucc() {
        return succ;
    }

    public Sommet getlautre(Sommet s){
        if (s.equals(succ)) return pred;
        if (s.equals(pred)) return succ;
        return null;
    }

    public double getPoids() {
        return poids;
    }

    public boolean getOriente() {
        return oriente;
    }


}
