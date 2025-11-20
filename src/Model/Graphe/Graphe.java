package Model.Graphe;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graphe {

    private final int nb_sommet;
    private final List<Sommet> sommets;
    private final List<Liaison> liaisons;
    private final Map<Sommet, List<Liaison>> adjlist;

    public Graphe(int nb_sommet) {

        this.nb_sommet = nb_sommet;
        this.sommets = new ArrayList<>();
        this.liaisons = new ArrayList<>();
        this.adjlist = new HashMap<>();

        for (int i = 0; i < nb_sommet; i++) {
            Sommet s = new Sommet(i);
            sommets.add(s);
            adjlist.put(s, new ArrayList<>());
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

        Liaison l1 = new Liaison(pred, succ, poids, false);
        Liaison l2 = new Liaison(succ, pred, poids, false); // pour l’autre sens


        liaisons.add(l1);


        adjlist.get(pred).add(l1);
        adjlist.get(succ).add(l2);
    }


    public Sommet getSommet(int id){
        return sommets.get(id);
    }

    public Map<Sommet, List<Liaison>> getAdj() {
        return adjlist;
    }

    public void afficherLiaisons() {

        System.out.println("Liaisons du graphe:");
        for (Liaison l : liaisons) {
            System.out.println(l.getPred().getId() + " -- " + l.getSucc().getId() + " Poids : " + l.getPoids());
        }
    }

    public void afficherAdj() {
        System.out.println("Adjacency list :");
        for (Sommet s : sommets) {
            System.out.print(s.getId() + " : ");
            for (Liaison l : adjlist.get(s)) {
                System.out.print(l.getSucc().getId() + " Poids : " + l.getPoids() + " | ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args){

        String fichierin = "data/test/adj1.txt";

        try {
            Graphe graphe = chargerGraphe(fichierin);
            graphe.afficherLiaisons();
            System.out.println();
            graphe.afficherAdj();
        } catch (Exception e) {
            System.err.println("Oupsidoupsi : " + e.getMessage());
        }


    }
}
