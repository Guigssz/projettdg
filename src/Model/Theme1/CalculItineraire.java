package Model.Theme1;

import Model.Algo.*;
import Model.Graphe.*;
import Model.ResultatCommun.*;

import java.util.*;

public class CalculItineraire {


    public static Itineraire itineraireVersEncombrantavecretour(Graphe g, Sommet depot, Encombrant e) {

        // version PAREIL mais avec le  retour au depot en plus

        Sommet A = e.getLiaison().getPred();
        Sommet B = e.getLiaison().getSucc();
        double poids = e.getLiaison().getPoids();

        Itineraire itinDA = Dijkstra.dijkstra(g, depot, A);
        Itineraire itinDB = Dijkstra.dijkstra(g, depot, B);

        double option1 = itinDA.getDistanceTotal() + poids;
        double option2 = itinDB.getDistanceTotal() + poids;

        List<Sommet> cheminFinal = new ArrayList<>();
        double distanceTotale;

        Sommet fin;

        if (g.getOriente()) {
            cheminFinal.addAll(itinDA.getListSommet());
            cheminFinal.add(B);
            fin = B;
            distanceTotale = option1;
        }
        else {
            if (option1 <= option2) {
                cheminFinal.addAll(itinDA.getListSommet());
                cheminFinal.add(B);
                fin = B;
                distanceTotale = option1;

            } else {
                cheminFinal.addAll(itinDB.getListSommet());
                cheminFinal.add(A);
                fin = A;
                distanceTotale = option2;
            }
        }


        // retour au depot
        Itineraire retour = Dijkstra.dijkstra(g, fin, depot);


        List<Sommet> chRetour = retour.getListSommet();
        for (int i = 1; i < chRetour.size(); i++) {
            cheminFinal.add(chRetour.get(i));
        }

        distanceTotale += retour.getDistanceTotal();

        return new Itineraire(depot, depot, cheminFinal, distanceTotale);
    }


    public static Itineraire itineraireVersEncombrant(Graphe g, Sommet depot, Encombrant e) {

        // utiliser pour la liste d'encombrant

        Sommet A = e.getLiaison().getPred();
        Sommet B = e.getLiaison().getSucc();
        double poids = e.getLiaison().getPoids();

       // dijkstra pour choisir de quel côté de l'arrête est le plus opti par lequel commencer
        Itineraire itinDA = Dijkstra.dijkstra(g, depot, A);
        Itineraire itinDB = Dijkstra.dijkstra(g, depot, B);

        double option1 = itinDA.getDistanceTotal() + poids; // Depot puis A puis B
        double option2 = itinDB.getDistanceTotal() + poids; // Depot puis B puis A


        if (g.getOriente()) {
            // on DOIT entrer par A
            List<Sommet> ch = new ArrayList<>(itinDA.getListSommet());
            ch.add(B);
            return new Itineraire(depot, B, ch, option1);
        }


        //on choisi le meilleur
        if (option1 <= option2) {
            // on commence par le sommet A de l'arete AB

            List<Sommet> chemin = new ArrayList<>(itinDA.getListSommet());
            chemin.add(B);
            return new Itineraire(depot, B, chemin, option1);

        } else {
            // on commence par le sommet B de l'arete AB

            List<Sommet> chemin = new ArrayList<>(itinDB.getListSommet());
            chemin.add(A);
            return new Itineraire(depot, A, chemin, option2);
        }

    }

    public static Itineraire itineraireversListeEncombrants(Graphe g, Sommet depot, List<Encombrant> encombrants) {

        List<Sommet> cheminTotal = new ArrayList<>();
        double distanceTotal = 0.0;

        // Position depart = depot
        Sommet position = depot;


        List<Encombrant> restants = new ArrayList<>(encombrants);

        cheminTotal.add(depot);

        while (!restants.isEmpty()) {

            Encombrant meilleur = null;
            Itineraire meilleurItin = null;
            double meilleurPoids = 1000000.0;

            // chercher l'encombrant le plus proche
            for (Encombrant e : restants) {

                // on va collecter cet encombrant avec l'autre méthode
                Itineraire itin = CalculItineraire.itineraireVersEncombrant(g, position, e);

                if (itin.getDistanceTotal() < meilleurPoids) {
                    meilleurPoids = itin.getDistanceTotal();
                    meilleur = e;
                    meilleurItin = itin;
                }
            }

            // Pour avoir l'itiniraire total après
            List<Sommet> ch = meilleurItin.getListSommet();
            for (int i = 1; i < ch.size(); i++) {
                cheminTotal.add(ch.get(i));
            }

            distanceTotal += meilleurPoids;

           // la position de vient l'extremité de l'arrete de l'encombrant collecté
            position = meilleurItin.getArrivee();

            restants.remove(meilleur);
        }

        // Pour le retour au depot

        Itineraire retour = Dijkstra.dijkstra(g, position, depot);

        // On ajoute les sommets du retour sans dupliquer le premier
        List<Sommet> chRetour = retour.getListSommet();
        for (int i = 1; i < chRetour.size(); i++) {
            cheminTotal.add(chRetour.get(i));
        }

        // Ajouter la distance du retour
        distanceTotal += retour.getDistanceTotal();

        // Itinéraire final = départ ET arrivée = dépôt
        return new Itineraire(depot, depot, cheminTotal, distanceTotal);
    }

    public static void mainH01() {

        //Un seul ramassage à la fois. Il faut déterminer
        //l’itinéraire le plus court permettant de se rendre chez un
        //particulier.

        try {
            Scanner sc = new Scanner(System.in);

            String fichier = "data/test/marcoriente.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            // choix depot
            System.out.print("Entrez id du sommet de depart/depot : ");
            int idDepot = sc.nextInt();
            Sommet depot = g.getSommet(idDepot);

            // Choix de l'arete ou se trouve l'encombrant

            g.afficherLiaisons();

            Liaison liasionchoisi = chosirLiaison(g);

            if (liasionchoisi == null) {
                System.out.println("arete pas trouvé");
            }

            // Encombrant
            Encombrant e = new Encombrant(liasionchoisi);

            System.out.println("Encombrant ajoutée à la liaison : " + liasionchoisi.getPred().getId() + liasionchoisi.getSucc().getId());

            Itineraire itin = itineraireVersEncombrantavecretour(g, depot, e);


            System.out.println("\n=== Résultat H01 ===");
            System.out.println("On est allé chercher l'encombrant à l'arête : " + e.getLiaison().getPred().getId() + e.getLiaison().getSucc().getId());
            itin.afficher();


        } catch (Exception e) {
            System.err.println("Erreur H01 : " + e.getMessage());
        }
    }

    public static void mainH02() {

        //La mairie regroupe les demandes et propose des
        //dates de tournées de ramassages. Une tournée est limitée à une
        //dizaine de ramassages. Il faut calculer l’itinéraire le plus
        //court permettant de passer chez chaque particulier de la liste
        //en une seule tournée

        try {
            Scanner sc = new Scanner(System.in);

            String fichier = "data/test/marcoriente.txt";
            Graphe g = Graphe.chargerGraphe(fichier);

            System.out.println("=== Graphe chargé ===");
            g.afficherAdj();
            System.out.println();

            // choix depot
            System.out.print("Entrez id du sommet de depart/depot : ");
            Sommet depot = g.getSommet(sc.nextInt());

            // choisir les encombrants
            System.out.print("Combien d'encombrants voulez-vous créer ? ");
            int nb = sc.nextInt();

            List<Encombrant> liste = new ArrayList<>();

            g.afficherLiaisons();


            for (int i = 1; i <= nb; i++) {
                System.out.println("\nEncombrant " + i + ": ");


                Liaison liasionchoisi = chosirLiaison(g);


                if (liasionchoisi == null) {
                    System.out.println("arete pas trouvé");
                } else {
                    liste.add(new Encombrant(liasionchoisi));
                }

                System.out.println("Encombrant " + i + " ajoutee à la liaison : " + liasionchoisi.getPred().getId() + liasionchoisi.getSucc().getId());

            }

            if (liste.isEmpty()) {
                System.out.println("liste d'e vide");
                return;
            }


            Itineraire itin = itineraireversListeEncombrants(g, depot, liste);

            System.out.println("\n=== Résultat H02 ===");
            System.out.println("On est alle chercher les encombrants au aretes : " );
            for (Encombrant e : liste) {
                System.out.println(e.getLiaison().getPred().getId() + "" +  e.getLiaison().getSucc().getId());
            }


            itin.afficher();

        } catch (Exception e) {
            System.err.println("Erreur Hypothèse 2 : " + e.getMessage());
        }
    }


    public static Liaison chosirLiaison(Graphe g){

        Scanner sc = new Scanner(System.in);

        System.out.print("Choisissez une arête (1-" + g.getLiaison().size() + ") : ");
        int choix = sc.nextInt();

        if (choix < 1 || choix > g.getLiaison().size()) {
            System.out.println("pas arete");
        }

        return  g.getLiaison().get(choix - 1);

    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== MENU THEME 1 ===");
        System.out.println("1. H01 : aller récupérer un encombrant");
        System.out.println("2. H02 : recup plusieur encombrants");
        System.out.print("Votre choix : ");

        int choix = sc.nextInt();

        if (choix == 1) {
            mainH01();
        }
        else if (choix == 2) {
            mainH02();
        }
        else {
            System.out.println("nop");
        }
    }

}
