package Model.Graphe;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graphe {

    private boolean oriente;
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
        int n = Integer.parseInt(lines.get(0).trim());

        Graphe graphe = new Graphe(n);

        for (int i = 1; i < lines.size(); i++) {

            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] info = line.split("\\s+");

            // On veut 4 colonnes : pred succ poids oriente(0/1)
            if (info.length < 4) {
                throw new Exception("Ligne " + (i + 1) + " invalide dans le fichier : \"" + line + "\"");
            }

            int id_pred = Integer.parseInt(info[0]);
            int id_succ = Integer.parseInt(info[1]);
            double poids = Double.parseDouble(info[2]);
            int flag = Integer.parseInt(info[3]);      // 0 ou 1

            boolean estOriente = (flag == 1);         // true si sens unique

            Sommet pred = graphe.getSommet(id_pred);
            Sommet succ = graphe.getSommet(id_succ);

            if (estOriente) {
                // sens unique pred -> succ
                graphe.ajouterLiaisonOriente(pred, succ, poids);
                // si tu veux garder l'info globalement :
                graphe.oriente = true; // (champ à ajouter dans la classe)
            } else {
                // double sens
                graphe.ajouterLiaison(pred, succ, poids);
            }
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

    // Pour les rues à sens unique pred -> succ
    public void ajouterLiaisonOriente(Sommet pred, Sommet succ, double poids) {

        Liaison l1 = new Liaison(pred, succ, poids, true);

        liaisons.add(l1);

        // on n'ajoute que dans la liste des sorties de pred
        adjlist.get(pred).add(l1);
    }



    public Sommet getSommet(int id){
        return sommets.get(id);
    }

    public Map<Sommet, List<Liaison>> getAdj() {
        return adjlist;
    }

    public void afficherLiaisons() {

        int index = 1;
        System.out.println("Liaisons du graphe:");
        for (Liaison l : liaisons) {
            System.out.println(index + ".  " + l.getPred().getId() + " -- " + l.getSucc().getId() + " Poids : " + l.getPoids());
            index++;
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

    public Liaison getArrete(int a, int b) {

        for (Liaison l : liaisons) {

            int u = l.getPred().getId();
            int v = l.getSucc().getId();

            if ((u == a && v == b) || (u == b && v == a)) {
                return l;
            }
        }

        throw new RuntimeException("erreur");
    }

    public static boolean sommetouspairs(Graphe g) {
        for (Sommet s : g.getSommets()) {
            if (g.getVoisins(s).size() % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public List<Sommet> getVoisins(Sommet s){
        List<Sommet> voisins = new ArrayList<>();
        for (Liaison l : adjlist.get(s)) {
            voisins.add(l.getlautre(s));
        }
        return voisins;
    }


    public Map<Sommet, List<Liaison>> adjcopyeuler() {

        // sans doublon !!!!

        Map<Sommet, List<Liaison>> copy = new HashMap<>();


        for (Sommet s : adjlist.keySet()) {
            copy.put(s, new ArrayList<>());
        }
        
        for (Liaison l : liaisons) {
            Sommet a = l.getPred();
            Sommet b = l.getSucc();

            copy.get(a).add(l);
            copy.get(b).add(l);
        }

        return copy;
    }


    public Liaison getLiaisonAB(Sommet a, Sommet b){
        for (Liaison l : adjlist.get(a)) {
            if (l.getlautre(a).equals(b)) return l;
        }
        return null;
    }

    public List<Liaison> getLiaison(){
        return liaisons;
    }

    public List<Sommet> getSommets() {
        return sommets;
    }

    public static void main(String[] args){



    }
}
