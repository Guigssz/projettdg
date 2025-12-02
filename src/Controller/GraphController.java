package Controller;

import View.GraphView;

import java.awt.event.ActionEvent;
import java.io.*;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;
import Model.Theme2.AlgoTheme2Hyp2;
import Model.Theme2.PointCollecteSpb2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GraphController {

    private final GraphView view;

    public GraphController(GraphView view) {
        this.view = view;

        // On charge la liste des fichiers disponibles dans data/test
        String[] files = scanTestFiles("data/test");

        // Si certains combos n'existent pas encore dans ta GraphView, tu peux commenter les lignes concernées
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
            // Cas spécial : on ignore le fichier sélectionné et on lance directement
            // le scénario thème 2 hyp 2 sur data/test/theme2_test.txt
            runTheme2Hyp2(view.outputAreaTest);
        } else {
            // Dijkstra / BFS classiques
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

        if ("Thème 2 - Hypothèse 2".equals(algoName)) {
            runTheme2Hyp2(view.outputAreaCollectivite);
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
            runTheme2Hyp2(view.outputAreaEntreprise);
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
     * Lance le scénario Thème 2 - Hypothèse 2 sur le fichier fixe
     * data/test/theme2_test.txt
     */
    private void runTheme2Hyp2(JTextArea outputArea) {

        try {
            String path = "data/test/theme2_test.txt";
            Graphe g = Graphe.chargerGraphe(path);

            // Dépôt : on choisit le sommet 0 (à adapter si besoin)
            Sommet depot = g.getSommet(0);

            // Définition des points de collecte + contenances
            // Ici, on suppose que les sommets 1, 2, 3, 4 sont des points de collecte.
            // Les contenances ci sont choisies pour illustrer la découpe par capacité.
            List<PointCollecteSpb2> points = new ArrayList<>();
            points.add(new PointCollecteSpb2(g.getSommet(1), 4)); // P1 : contenance 4
            points.add(new PointCollecteSpb2(g.getSommet(2), 3)); // P2 : contenance 3
            points.add(new PointCollecteSpb2(g.getSommet(3), 3)); // P3 : contenance 3
            points.add(new PointCollecteSpb2(g.getSommet(4), 5)); // P4 : contenance 5

            // Capacité du camion (à adapter par rapport à l'exemple du prof)
            int capaciteCamion = 7;

            List<Itineraire> tournees =
                    AlgoTheme2Hyp2.calculerTournees(g, depot, points, capaciteCamion);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 2 - Hypothèse 2\n");
            sb.append("Fichier : ").append("theme2_test.txt").append("\n");
            sb.append("Capacité camion : ").append(capaciteCamion).append("\n");
            sb.append("Nombre de points de collecte : ").append(points.size()).append("\n");
            sb.append("====================================\n\n");

            int num = 1;
            for (Itineraire it : tournees) {
                sb.append("Tournée ").append(num).append(" :\n");
                sb.append("Distance totale : ").append(it.getDistanceTotal()).append("\n");
                sb.append("Chemin (sommets) : ");
                for (Sommet s : it.getListSommet()) {
                    sb.append(s.getId()).append(" ");
                }
                sb.append("\n\n");
                num++;
            }

            outputArea.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Erreur Thème 2 Hyp 2 : " + ex.getMessage());
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

