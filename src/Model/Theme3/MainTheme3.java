package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Liaison;
import Model.Graphe.Sommet;

import java.util.*;

public class MainTheme3 {

    public static void main(String[] args) {
        try {

            String fichier = "data/test/adjmarc.txt";
            Graphe graphePhysique = Graphe.chargerGraphe(fichier);

            System.out.println("Graphe charge. " + graphePhysique.getSommets().size() + " sommets.");

            GestionsSecteurs gestion = new GestionsSecteurs();

            // Creation des secteurs a la main
            creerSecteur(gestion, graphePhysique, 1, Arrays.asList(0, 1, 6, 7));
            creerSecteur(gestion, graphePhysique, 2, Arrays.asList(2, 3, 4, 5));
            creerSecteur(gestion, graphePhysique, 3, Arrays.asList(8, 9, 10, 11));
            creerSecteur(gestion, graphePhysique, 4, Arrays.asList(12, 13, 14));

            // Depots
            gestion.getSecteurById(1).setDepot(graphePhysique.getSommet(0));
            gestion.getSecteurById(2).setDepot(graphePhysique.getSommet(2));
            gestion.getSecteurById(3).setDepot(graphePhysique.getSommet(8));
            gestion.getSecteurById(4).setDepot(graphePhysique.getSommet(12));


            gestion.calculerAdjacenceSecteurs(graphePhysique);


            Scanner sc = new Scanner(System.in);
            System.out.print("Capacite camion : ");
            double capacite = sc.nextDouble();

            System.out.print("Quantité dechets (x distance) : ");
            double densite = sc.nextDouble();


            GestionDechetSecteur.calculerMetriquesSecteurs(gestion.getListeSecteurs(), graphePhysique, capacite, densite);


            AlgoColoration.colorierWelshPowell(gestion);


            System.out.println("\n planning :");

            String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

            for (Secteur s : gestion.getListeSecteurs()) {

                String jour = (s.getJourCollecte() >= 0 && s.getJourCollecte() < jours.length) ? jours[s.getJourCollecte()] : "J" + s.getJourCollecte();

                System.out.println("\n---");
                System.out.println("SECTEUR " + s.getId() + " - " + jour);
                System.out.println("Depot : Sommet " + s.getDepot().getId());
                System.out.println("Total a ramasser : " + s.getQuantiteTotaleDechets() + " kg");
                System.out.println("---");



                List<Liaison> ruesDuSecteur = new ArrayList<>();
                for (Liaison l : graphePhysique.getLiaison()) {
                    boolean uDansSecteur = s.contientSommet(l.getPred());
                    boolean vDansSecteur = s.contientSommet(l.getSucc());


                    if (uDansSecteur && vDansSecteur) {
                        ruesDuSecteur.add(l);
                    } else if (uDansSecteur && !vDansSecteur) {
                        // Frontiere, on la prend
                        ruesDuSecteur.add(l);
                    }


                }

                double chargeActuelle = 0;
                int numTour = 1;
                System.out.println("Tournee " + numTour + " (Depart " + s.getDepot().getId() + ")");

                for (Liaison rue : ruesDuSecteur) {
                    double dechetsRue = rue.getPoids() * densite;


                    if (chargeActuelle + dechetsRue > capacite) {
                        System.out.println("   >>> Camion plein (" + String.format("%.1f", chargeActuelle) + "kg) ! Retour au depot " + s.getDepot().getId());

                        numTour++;
                        chargeActuelle = 0;
                        System.out.println("Tournee " + numTour + " (Depart " + s.getDepot().getId() + ")");
                    }


                    chargeActuelle += dechetsRue;
                    System.out.println("   - Passage rue " + rue.getPred().getId() + "->" + rue.getSucc().getId() + " : ramasse " + String.format("%.1f", dechetsRue) + "kg");
                }


                System.out.println("   >>> Retour final depot " + s.getDepot().getId());
                System.out.println("Fin secteur " + s.getId() + " (" + numTour + " tournees).");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void creerSecteur(GestionsSecteurs gs, Graphe g, int idSecteur, List<Integer> idsSommets) {
        Secteur s = new Secteur(idSecteur);
        for (int id : idsSommets) {
            Sommet som = g.getSommet(id);
            if (som != null) s.ajouterSommet(som);
        }
        gs.ajouterSecteur(s);
    }
}