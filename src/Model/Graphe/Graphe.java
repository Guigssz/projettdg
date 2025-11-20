package Model.Graphe;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graphe {

    private final int nb_sommet;
    private final List<Sommet> sommets;
    private final List<Liaison> liaisons;

    public Graphe(int nb_sommet) {

        this.nb_sommet = nb_sommet;
        this.sommets = new ArrayList<>();
        this.liaisons = new ArrayList<>();

        for (int i = 0; i < nb_sommet; i++) {
            sommets.add(new Sommet(i));
        }

    }

    public static Graphe chargerGraphe(String fichierin) throws Exception {

        List<String> lines = Files.readAllLines(Paths.get(fichierin));
        int n = Integer.parseInt(lines.get(0));

        Graphe graphe = new Graphe(n);

        for (int i = 1; i < lines.size(); i++) {

            String line = lines.get(i).trim();

            String[] info = line.split("\\s+");

            int id_pred = Integer.parseInt(info[0]);
            int id_succ = Integer.parseInt(info[1]);
            double poids = Double.parseDouble(info[2]);

            Sommet pred = graphe.getSommet(id_pred);
            Sommet succ = graphe.getSommet(id_succ);


            graphe.ajouterLiaison(pred,succ,poids);

        }

        return graphe;
    }

    public void ajouterLiaison(Sommet pred, Sommet succ, double poids) {
        Liaison liaison = new Liaison(pred, succ, poids,false);
        liaisons.add(liaison);
    }


    public Sommet getSommet(int id){
        return sommets.get(id);
    }

    public void afficherLiaisons() {

        System.out.println("Liaisons du graphe:");
        for (Liaison l : liaisons) {
            System.out.println(l.getPred().getId() + " -- " + l.getSucc().getId() + " Poids : " + l.getPoids());
        }
    }


    public static void main(String[] args){

        String fichierin = "data/test/adj1.txt";

        try {
            Graphe graphe = chargerGraphe(fichierin);
            graphe.afficherLiaisons();
        } catch (Exception e) {
            System.err.println("Oupsidoupsi : " + e.getMessage());
        }


    }
}
