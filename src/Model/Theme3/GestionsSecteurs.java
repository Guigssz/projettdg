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

    /**
     * Cette méthode parcourt toutes les liaisons physiques du graphe routier.
     * Si une liaison relie un Sommet A (Secteur 1) à un Sommet B (Secteur 2),
     * alors Secteur 1 et Secteur 2 sont voisins.
     */
    public void calculerAdjacenceSecteurs(Graphe graphePhysique) {
        // 1. Créer une map pour retrouver rapidement le secteur d'un sommet
        Map<Sommet, Secteur> mapSommetSecteur = new HashMap<>();

        for (Secteur sec : listeSecteurs) {
            for (Sommet s : sec.getSommetsDuSecteur()) {
                mapSommetSecteur.put(s, sec);
            }
        }

        // 2. Parcourir toutes les liaisons du graphe physique
        for (Liaison l : graphePhysique.getLiaison()) {
            Sommet u = l.getPred();
            Sommet v = l.getSucc();

            Secteur sU = mapSommetSecteur.get(u);
            Secteur sV = mapSommetSecteur.get(v);

            // Si les deux sommets appartiennent à des secteurs et que ce sont des secteurs différents
            if (sU != null && sV != null && !sU.equals(sV)) {
                // Alors ces secteurs sont voisins
                adjacenceSecteurs.get(sU).add(sV);
                adjacenceSecteurs.get(sV).add(sU); // Graphe non orienté pour les voisins
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