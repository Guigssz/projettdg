package Controller;

import View.GraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import Model.Theme3.GestionsSecteurs;
import Model.Theme3.Secteur;
import Model.Theme3.AlgoColoration;
import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;
import Model.Theme2.AlgoTheme2Hyp2;
import Model.Theme2.PointCollecteSpb2;

import java.util.ArrayList;
import java.util.List;

public class GraphController {

    private final GraphView view;

    public GraphController(GraphView view) {
        this.view = view;

        // On charge la liste des fichiers disponibles dans data/test
        String[] files = scanTestFiles("data/test");

        if (view.fileComboTest != null) {
            view.fileComboTest.setModel(new DefaultComboBoxModel<>(files));
        }
        if (view.fileComboCollectivite != null) {
            view.fileComboCollectivite.setModel(new DefaultComboBoxModel<>(files));
        }
        if (view.fileComboEntreprise != null) {
            view.fileComboEntreprise.setModel(new DefaultComboBoxModel<>(files));
        }

        // Raccordement des boutons d'action
        if (view.runButtonTest != null) {
            view.runButtonTest.addActionListener(this::onRunClickedTest);
        }
        if (view.runButtonCollectivite != null) {
            view.runButtonCollectivite.addActionListener(this::onRunClickedCollectivite);
        }
        if (view.runButtonEntreprise != null) {
            view.runButtonEntreprise.addActionListener(this::onRunClickedEntreprise);
        }
    }

    // =====================================================================
    //  ONGLET TEST
    // =====================================================================

    private void onRunClickedTest(ActionEvent e) {
        String fileName = (String) view.fileComboTest.getSelectedItem();
        String algoName = (String) view.algoComboTest.getSelectedItem();
        String departText = view.departFieldTest.getText();
        String arriveeText = view.arriveeFieldTest.getText();

        if ("Thème 2 - Hypothèse 2".equals(algoName)) {
            runTheme2Hyp2(
                    fileName,
                    view.capaciteCamionFieldTest.getText(),
                    view.contenancesFieldTest.getText(),
                    view.outputAreaTest,
                    "Mode TEST"
            );
        } else {
            runItineraire(
                    fileName,
                    algoName,
                    departText,
                    arriveeText,
                    view.outputAreaTest,
                    "Mode TEST"
            );
        }
    }

    // =====================================================================
    //  ONGLET COLLECTIVITE
    // =====================================================================

    private void onRunClickedCollectivite(ActionEvent e) {
        String fileName = (String) view.fileComboCollectivite.getSelectedItem();
        String algoName = (String) view.algoComboCollectivite.getSelectedItem();
        String departText = view.departFieldCollectivite.getText();
        String arriveeText = view.arriveeFieldCollectivite.getText();

        if ("Thème 3 - Hypothèse 1".equals(algoName)) {
            runTheme3H1(fileName, view.secteursConfigAreaCollectivite.getText(), view.outputAreaCollectivite);
            return;
        }



        if ("Thème 2 - Hypothèse 2".equals(algoName)) {
            runTheme2Hyp2(
                    fileName,
                    view.capaciteCamionFieldCollectivite.getText(),
                    view.contenancesFieldCollectivite.getText(),
                    view.outputAreaCollectivite,
                    "Collectivité"
            );
        } else {
            runItineraire(
                    fileName,
                    algoName,
                    departText,
                    arriveeText,
                    view.outputAreaCollectivite,
                    "Collectivité"
            );
        }
    }

    // =====================================================================
    //  ONGLET ENTREPRISE
    // =====================================================================

    private void onRunClickedEntreprise(ActionEvent e) {
        String fileName = (String) view.fileComboEntreprise.getSelectedItem();
        String algoName = (String) view.algoComboEntreprise.getSelectedItem();
        String departText = view.departFieldEntreprise.getText();
        String arriveeText = view.arriveeFieldEntreprise.getText();

        if ("Thème 2 - Hypothèse 2".equals(algoName)) {
            runTheme2Hyp2(
                    fileName,
                    view.capaciteCamionFieldEntreprise.getText(),
                    view.contenancesFieldEntreprise.getText(),
                    view.outputAreaEntreprise,
                    "Entreprise de collecte"
            );
        } else {
            runItineraire(
                    fileName,
                    algoName,
                    departText,
                    arriveeText,
                    view.outputAreaEntreprise,
                    "Entreprise de collecte"
            );
        }
    }

    // =====================================================================
    //  Partie commune : Dijkstra / BFS sur un fichier choisi
    // =====================================================================

    private void runItineraire(String fileName,
                               String algoName,
                               String departText,
                               String arriveeText,
                               JTextArea outputArea,
                               String headerContexte) {

        if (fileName == null || fileName.startsWith("(aucun")) {
            showError("Aucun fichier de graphe trouvé dans data/test.");
            return;
        }

        int idDepart;
        int idArrivee;

        try {
            idDepart = Integer.parseInt(departText.trim());
            idArrivee = Integer.parseInt(arriveeText.trim());
        } catch (NumberFormatException ex) {
            showError("Les ids de départ / arrivée doivent être des entiers.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);

            Sommet depart = g.getSommet(idDepart);
            Sommet arrivee = g.getSommet(idArrivee);

            Itineraire itineraire = null;

            if ("Dijkstra".equals(algoName)) {
                itineraire = Dijkstra.dijkstra(g, depart, arrivee);
            } else if ("BFS".equals(algoName)) {
                itineraire = BFS.bfs(g, depart, arrivee);
            }

            outputArea.setText("");

            if (itineraire == null) {
                outputArea.setText("Aucun chemin trouvé.");
            } else {
                String texte = captureAffichageItineraire(itineraire);

                StringBuilder sb = new StringBuilder();
                sb.append(headerContexte).append("\n\n");
                sb.append("Fichier : ").append(fileName).append("\n");
                sb.append("Algorithme : ").append(algoName).append("\n");
                sb.append("----------------------------------------\n");
                sb.append(texte);

                outputArea.setText(sb.toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Erreur : " + ex.getMessage());
        }
    }

    // =====================================================================
    //  Thème 2 - Hypothèse 2
    // =====================================================================

    /**
     * Lance Thème 2 - Hypothèse 2 sur le fichier choisi.
     * - Dépôt = sommet 0 (en supposant 0 = centre de traitement dans ton graphe)
     * - Points de collecte = sommets 1,2,3,... pour lesquels tu donnes une contenance
     *   dans le champ texte (ex: "2,3,2,4,3,5").
     */
    private void runTheme2Hyp2(String fileName,
                               String capaciteStr,
                               String contenancesStr,
                               JTextArea outputArea,
                               String contexte) {

        if (fileName == null || fileName.startsWith("(aucun")) {
            showError("Aucun fichier de graphe trouvé dans data/test.");
            return;
        }

        int capaciteCamion;
        try {
            capaciteCamion = Integer.parseInt(capaciteStr.trim());
        } catch (NumberFormatException ex) {
            showError("La capacité du camion doit être un entier.");
            return;
        }

        if (contenancesStr == null || contenancesStr.trim().isEmpty()) {
            showError("Veuillez saisir les contenances des points de collecte (ex : 2,3,2,4...).");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);

            // Dépôt : sommet 0 (adapter si besoin)
            Sommet depot = g.getSommet(0);

            // Parse des contenances : "2,3,2,4,3,5"
            String[] tokens = contenancesStr.split(",");
            List<PointCollecteSpb2> points = new ArrayList<>();

            for (int i = 0; i < tokens.length; i++) {
                String t = tokens[i].trim();
                if (t.isEmpty()) continue;
                int cont = Integer.parseInt(t);
                // sommet i+1 : on suppose 0 = dépôt, 1..N = points
                Sommet s = g.getSommet(i + 1);
                points.add(new PointCollecteSpb2(s, cont));
            }

            List<AlgoTheme2Hyp2.TourneeTheme2> tourneesDetail =
                    AlgoTheme2Hyp2.calculerTourneesDetail(g, depot, points, capaciteCamion);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 2 - Hypothèse 2 (").append(contexte).append(")\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Dépôt : sommet ").append(depot.getId()).append("\n");
            sb.append("Capacité camion : ").append(capaciteCamion).append("\n");
            sb.append("Nombre de points de collecte : ").append(points.size()).append("\n");
            sb.append("====================================\n\n");

            int num = 1;
            for (AlgoTheme2Hyp2.TourneeTheme2 t : tourneesDetail) {
                Itineraire it = t.getItineraire();

                sb.append("Tournée ").append(num).append(" :\n");
                sb.append("  Points de collecte servis :\n");
                for (PointCollecteSpb2 pc : t.getPoints()) {
                    sb.append("    - sommet ").append(pc.getSommet().getId())
                            .append(" (contenance = ").append(pc.getContenance()).append(")\n");
                }
                sb.append("  Charge totale tournée : ")
                        .append(t.getChargeTotale()).append(" / ").append(capaciteCamion).append("\n");
                sb.append("  Distance totale : ").append(it.getDistanceTotal()).append("\n");
                sb.append("  Chemin (sommets) : ");
                for (Sommet s : it.getListSommet()) {
                    sb.append(s.getId()).append(" ");
                }
                sb.append("\n\n");
                num++;
            }

            outputArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            showError("Erreur de format dans les contenances (utilisez des entiers séparés par des virgules).");
        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Erreur Thème 2 Hyp 2 : " + ex.getMessage());
        }
    }

    private void runTheme3H1(String fileName, String secteursConfig, JTextArea outputArea) {
        if (fileName == null || fileName.startsWith("(aucun")) {
            outputArea.setText("Aucun fichier de graphe sélectionné.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);

            GestionsSecteurs gestion = new GestionsSecteurs();

            // --- Parsing de la config secteurs ---
            // Format attendu :
            // 1: 0,1,6,7
            // 2: 2,3,4,5
            // ...
            String[] lignes = secteursConfig.split("\\R"); // split sur les retours à la ligne

            for (String ligne : lignes) {
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                String[] parts = ligne.split(":");
                if (parts.length != 2) {
                    System.err.println("Ligne secteur invalide : " + ligne);
                    continue;
                }

                int idSecteur = Integer.parseInt(parts[0].trim());
                Secteur s = new Secteur(idSecteur);

                String[] sommetsStr = parts[1].split(",");
                for (String ss : sommetsStr) {
                    ss = ss.trim();
                    if (ss.isEmpty()) continue;
                    int idSommet = Integer.parseInt(ss);
                    Sommet som = g.getSommet(idSommet);
                    if (som != null) {
                        s.ajouterSommet(som);
                    } else {
                        System.err.println("Sommet " + idSommet + " introuvable dans le graphe !");
                    }
                }

                gestion.ajouterSecteur(s);
            }

            // Calculer le voisinage entre secteurs
            gestion.calculerAdjacenceSecteurs(g);

            // Coloration (Welsh-Powell)
            AlgoColoration.colorierWelshPowell(gestion);

            // Construire l'affichage
            StringBuilder sb = new StringBuilder();
            sb.append("Thème 3 - Hypothèse 1 : Planification de secteurs\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Nombre de secteurs : ").append(gestion.getListeSecteurs().size()).append("\n");
            sb.append("====================================\n\n");

            String[] joursSemaine = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

            for (Secteur s : gestion.getListeSecteurs()) {
                int c = s.getJourCollecte();
                String jour = (c >= 0 && c < joursSemaine.length) ? joursSemaine[c] : ("Jour " + c);

                sb.append("Secteur ").append(s.getId())
                        .append(" -> ").append(jour)
                        .append(" (couleur ").append(c).append(")\n");

                sb.append("   Sommets du secteur : ");
                for (Sommet som : s.getSommetsDuSecteur()) {
                    sb.append(som.getId()).append(" ");
                }
                sb.append("\n\n");
            }

            sb.append("--- Voisinage des secteurs ---\n");
            for (Secteur s : gestion.getListeSecteurs()) {
                sb.append("Secteur ").append(s.getId()).append(" est voisin de : ");
                for (Secteur v : gestion.getVoisins(s)) {
                    sb.append(v.getId()).append(" ");
                }
                sb.append("\n");
            }

            outputArea.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Erreur Thème 3 H1 : " + ex.getMessage());
        }
    }

    // =====================================================================
    //  Utils
    // =====================================================================

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String[] scanTestFiles(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new String[]{"(aucun fichier trouvé)"};
        }

        String[] files = folder.list((dir, name) -> new File(dir, name).isFile());
        if (files == null || files.length == 0) {
            return new String[]{"(aucun fichier trouvé)"};
        }
        return files;
    }

    private String captureAffichageItineraire(Itineraire itineraire) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            itineraire.afficher();
        } finally {
            System.out.flush();
            System.setOut(oldOut);
        }

        return baos.toString();
    }
}


