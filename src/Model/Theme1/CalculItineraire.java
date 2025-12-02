package Model.Theme1;

import Model.Algo.*;
import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class CalculItineraire {



    public static Itineraire itineraireVersEncombrant(Graphe g, Sommet depot, Encombrant e) {

        Sommet A = e.getLiaison().getPred();
        Sommet B = e.getLiaison().getSucc();
        double w = e.getLiaison().getPoids();

        // 1️⃣ On utilise TON dijkstra pour calculer D→A et D→B
        Itineraire itinDA = Dijkstra.dijkstra(g, depot, A);
        Itineraire itinDB = Dijkstra.dijkstra(g, depot, B);

        double cout1 = itinDA.getDistanceTotal() + w; // D -> A -> B
        double cout2 = itinDB.getDistanceTotal() + w; // D -> B -> A

        // 2️⃣ Choix du meilleur sens
        if (cout1 <= cout2) {
            // partir vers A puis traverser A→B
            List<Sommet> chemin = new ArrayList<>(itinDA.getListSommet());
            chemin.add(B);
            return new Itineraire(depot, B, chemin, cout1);

        } else {
            // partir vers B puis traverser B→A
            List<Sommet> chemin = new ArrayList<>(itinDB.getListSommet());
            chemin.add(A);
            return new Itineraire(depot, A, chemin, cout2);
        }
    }

    public static Itineraire itineraireTSPEncombrants(Graphe g, Sommet depot, List<Encombrant> encombrants) {

        List<Sommet> cheminTotal = new ArrayList<>();
        double distanceTotal = 0.0;

        // Position courante = depot
        Sommet positionCourante = depot;

        // Liste mutable
        List<Encombrant> restants = new ArrayList<>(encombrants);

        cheminTotal.add(depot);

        while (!restants.isEmpty()) {

            Encombrant meilleur = null;
            Itineraire meilleurItin = null;
            double meilleurCout = Double.MAX_VALUE;

            // 1️⃣ Chercher l'encombrant le plus proche
            for (Encombrant e : restants) {

                // Aller de la position courante vers cet encombrant
                Itineraire itin = CalculItineraire.itineraireVersEncombrant(g, positionCourante, e);

                if (itin.getDistanceTotal() < meilleurCout) {
                    meilleurCout = itin.getDistanceTotal();
                    meilleur = e;
                    meilleurItin = itin;
                }
            }

            // 2️⃣ Ajouter le chemin trouvé au chemin total
            // éviter de dupliquer le premier sommet
            List<Sommet> ch = meilleurItin.getListSommet();
            for (int i = 1; i < ch.size(); i++) {
                cheminTotal.add(ch.get(i));
            }

            distanceTotal += meilleurCout;

            // 3️⃣ Mise à jour de la position courante (extrémité de l'arête traversée)
            positionCourante = meilleurItin.getArrivee();

            // 4️⃣ On enlève cet encombrant de la liste
            restants.remove(meilleur);
        }

        // Itinéraire global
        return new Itineraire(depot, positionCourante, cheminTotal, distanceTotal);
    }

    public static void mainHypothese1() {

        //Un seul ramassage à la fois. Il faut déterminer
        //l’itinéraire le plus court permettant de se rendre chez un
        //particulier.


        try {
            Scanner sc = new Scanner(System.in);

            String fichier = "data/test/adjmarc.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            // Choix du dépôt
            System.out.print("Entrez l'ID du sommet de départ (dépôt) : ");
            int idDepot = sc.nextInt();
            Sommet depot = g.getSommet(idDepot);

            // Choix A et B
            System.out.print("Entrez l'ID du sommet A (extrémité 1 de l'arête) : ");
            Sommet A = g.getSommet(sc.nextInt());

            System.out.print("Entrez l'ID du sommet B (extrémité 2 de l'arête) : ");
            Sommet B = g.getSommet(sc.nextInt());

            // Vérification existence arête
            Liaison AB = null;
            for (Liaison l : g.getAdj().get(A)) {
                if (l.getSucc().equals(B)) {
                    AB = l;
                    break;
                }
            }

            if (AB == null) {
                System.out.println("❌ Erreur : l'arête A-B n'existe pas.");
                return;
            }

            // Encombrant
            Encombrant e = new Encombrant(AB);

            // Calcul
            Itineraire itin = itineraireVersEncombrant(g, depot, e);

            // Affichage
            System.out.println("\n=== Résultat Hypothèse 1 ===");
            itin.afficher();

        } catch (Exception e) {
            System.err.println("Erreur Hypothèse 1 : " + e.getMessage());
        }
    }

    public static void mainHypothese2() {

        //La mairie regroupe les demandes et propose des
        //dates de tournées de ramassages. Une tournée est limitée à une
        //dizaine de ramassages. Il faut calculer l’itinéraire le plus
        //court permettant de passer chez chaque particulier de la liste
        //en une seule tournée

        try {
            Scanner sc = new Scanner(System.in);

            String fichier = "data/test/adjmarc.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            // Choix du dépôt
            System.out.print("Entrez l'ID du sommet de départ (dépôt) : ");
            Sommet depot = g.getSommet(sc.nextInt());

            // Nombre d’encombrants
            System.out.print("Combien d'encombrants voulez-vous créer ? ");
            int nb = sc.nextInt();

            List<Encombrant> liste = new ArrayList<>();

            for (int i = 1; i <= nb; i++) {
                System.out.println("\n--- Encombrant " + i + " ---");

                System.out.print("Sommet A : ");
                Sommet A = g.getSommet(sc.nextInt());

                System.out.print("Sommet B : ");
                Sommet B = g.getSommet(sc.nextInt());

                // Chercher l'arête
                Liaison AB = null;
                for (Liaison l : g.getAdj().get(A)) {
                    if (l.getSucc().equals(B)) {
                        AB = l;
                        break;
                    }
                }

                if (AB == null) {
                    System.out.println("❌ L'arête n'existe pas, je passe.");
                } else {
                    liste.add(new Encombrant(AB));
                }
            }

            if (liste.isEmpty()) {
                System.out.println("Aucun encombrant valide.");
                return;
            }

            // Calcul PPV
            Itineraire itin = itineraireTSPEncombrants(g, depot, liste);

            // Affichage
            System.out.println("\n=== Résultat Hypothèse 2 (Tournée) ===");
            itin.afficher();

        } catch (Exception e) {
            System.err.println("Erreur Hypothèse 2 : " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== MENU THEME 1 ===");
        System.out.println("1. Hypothèse 1 : aller récupérer un encombrant");
        System.out.println("2. Hypothèse 2 : tournée d’encombrants (PPV)");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();

        if (choix == 1) {
            mainHypothese1();
        }
        else if (choix == 2) {
            mainHypothese2();
        }
        else {
            System.out.println("Choix invalide.");
        }
    }


}
