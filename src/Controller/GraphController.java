package Controller;

import View.GraphView;

import java.awt.event.ActionEvent;
import java.io.*;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;

import javax.swing.*;

public class GraphController {

    private final GraphView view;

    public GraphController(GraphView view) {
        this.view = view;

        // On charge la liste des fichiers dans les deux onglets
        String[] files = scanTestFiles("data/test");
        view.fileComboCollectivite.setModel(new DefaultComboBoxModel<>(files));
        view.fileComboEntreprise.setModel(new DefaultComboBoxModel<>(files));

        // On branche les deux boutons "Calculer"
        view.runButtonCollectivite.addActionListener(e -> onRunClickedCollectivite(e));
        view.runButtonEntreprise.addActionListener(e -> onRunClickedEntreprise(e));
    }

    // ----- LOGIQUE : Collectivité -----
    private void onRunClickedCollectivite(ActionEvent e) {
        String fileName = (String) view.fileComboCollectivite.getSelectedItem();
        String algoName = (String) view.algoComboCollectivite.getSelectedItem();
        String departText = view.departFieldCollectivite.getText();
        String arriveeText = view.arriveeFieldCollectivite.getText();

        runItineraire(
                fileName,
                algoName,
                departText,
                arriveeText,
                view.outputAreaCollectivite,
                "Collectivité --> Particulier"
        );
    }

    // ----- LOGIQUE : Entreprise -----
    private void onRunClickedEntreprise(ActionEvent e) {
        String fileName = (String) view.fileComboEntreprise.getSelectedItem();
        String algoName = (String) view.algoComboEntreprise.getSelectedItem();
        String departText = view.departFieldEntreprise.getText();
        String arriveeText = view.arriveeFieldEntreprise.getText();

        runItineraire(
                fileName,
                algoName,
                departText,
                arriveeText,
                view.outputAreaEntreprise,
                "Depot --> Point de collecte"
        );
    }

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
                sb.append(headerContexte).append("\n");
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

    // ----------- Utilitaires -----------

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
