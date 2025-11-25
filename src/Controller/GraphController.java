package Controller;

import java.awt.event.ActionEvent;
import java.io.*;

import View.GraphView;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;

public class GraphController {

    private final GraphView view;

    public GraphController(GraphView view) {
        this.view = view;

        view.fileComboBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(scanTestFiles("data/test"))
        );

        view.runButton.addActionListener(this::onRunClicked);
    }

    private void onRunClicked(ActionEvent e) {
        String fileName = (String) view.fileComboBox.getSelectedItem();
        String algoName = (String) view.algoComboBox.getSelectedItem();

        if (fileName == null || fileName.startsWith("(aucun")) {
            showError("Aucun fichier de graphe trouvé dans data/test.");
            return;
        }

        int idDepart, idArrivee;
        try {
            idDepart = Integer.parseInt(view.departField.getText().trim());
            idArrivee = Integer.parseInt(view.arriveeField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Les ids de départ / arrivée doivent être des entiers.");
            return;
        }

        try {
            Graphe g = Graphe.chargerGraphe("data/test/" + fileName);
            Sommet depart = g.getSommet(idDepart);
            Sommet arrivee = g.getSommet(idArrivee);

            Itineraire itineraire = null;
            if ("Dijkstra".equals(algoName)) {
                itineraire = Dijkstra.dijkstra(g, depart, arrivee);
            } else if ("BFS".equals(algoName)) {
                itineraire = BFS.bfs(g, depart, arrivee);
            }

            if (itineraire == null) {
                view.outputArea.setText("Aucun chemin trouvé.");
            } else {
                view.outputArea.setText(captureAffichageItineraire(itineraire));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            view.outputArea.setText("Erreur : " + ex.getMessage());
        }
    }

    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(
                null,
                message,
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }

    private String[] scanTestFiles(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new String[]{"(aucun fichier trouvé)"};
        }
        String[] files = folder.list((dir, name) -> new File(dir, name).isFile());
        return (files == null || files.length == 0)
                ? new String[]{"(aucun fichier trouvé)"}
                : files;
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

