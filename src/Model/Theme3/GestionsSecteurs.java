package Model.Theme3;

import Model.Graphe.Graphe;
import Model.Graphe.Liaison;
import Model.Graphe.Sommet;

import java.util.*;

public class GestionsSecteurs {

    private List<Secteur> listeSecteurs;
    private Map<Secteur, Set<Secteur>> adjacenceSecteurs; // Le "Graphe de secteurs"

    public GestionsSecteurs() {
        this.listeSecteurs = new ArrayList<>();
        this.adjacenceSecteurs = new HashMap<>();
    }

    public void ajouterSecteur(Secteur s) {
        listeSecteurs.add(s);
        adjacenceSecteurs.put(s, new HashSet<>());
    }

    public Secteur getSecteurById(int id) {
        for (Secteur s : listeSecteurs) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public void calculerAdjacenceSecteurs(Graphe graphePhysique) {
        // 1. Créer une map pour retrouver  le secteur d'un sommet
        Map<Sommet, Secteur> mapSommetSecteur = new HashMap<>();

        for (Secteur sec : listeSecteurs) {
            for (Sommet s : sec.getSommetsDuSecteur()) {
                mapSommetSecteur.put(s, sec);
            }
        }

        // 2. Parcourir toutes les liaisons
        for (Liaison l : graphePhysique.getLiaison()) {
            Sommet u = l.getPred();
            Sommet v = l.getSucc();

            Secteur sU = mapSommetSecteur.get(u);
            Secteur sV = mapSommetSecteur.get(v);


            if (sU != null && sV != null && !sU.equals(sV)) {
                // voisins
                adjacenceSecteurs.get(sU).add(sV);
                adjacenceSecteurs.get(sV).add(sU);
            }
        }
    }

    public List<Secteur> getListeSecteurs() {
        return listeSecteurs;
    }

    public Set<Secteur> getVoisins(Secteur s) {
        return adjacenceSecteurs.get(s);
    }
}