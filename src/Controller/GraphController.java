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

/**
 * Contrôleur principal.
 *
 * - Onglet "Test"  : permet de lancer directement Dijkstra / BFS sur les fichiers de /data/test
 * - Onglet "Appli" : habille ces mêmes algorithmes avec la vision "Collectivité / Particulier"
 *                    et les différents thèmes / hypothèses du sujet.
 */
public class GraphController {

    private final GraphView view;

    public GraphController(GraphView view) {
        this.view = view;

        // ------- Alimentation de l'onglet TEST -------
        String[] files = scanTestFiles("data/test");
        view.fileComboTest.setModel(new DefaultComboBoxModel<>(files));

        // ------- Comportement de l'onglet TEST -------
        view.runButtonTest.addActionListener(this::onRunClickedTest);

        // ------- Comportement de l'onglet APPLI -------
        // On met à jour la liste des hypothèses à chaque changement de thème
        view.themeCombo.addActionListener(e -> updateHypothesesModel());

        // Bouton principal de l'onglet "Appli"
        view.runButtonAppli.addActionListener(this::onRunClickedAppli);

        // Initialisation des hypothèses au démarrage
        updateHypothesesModel();
    }

    // ============================================================
    //  Onglet TEST
    // ============================================================
    private void onRunClickedTest(ActionEvent e) {
        String fileName    = (String) view.fileComboTest.getSelectedItem();
        String algoName    = (String) view.algoComboTest.getSelectedItem();
        String departText  = view.departFieldTest.getText();
        String arriveeText = view.arriveeFieldTest.getText();

        runItineraire(
                fileName,
                algoName,
                departText,
                arriveeText,
                view.outputAreaTest,
                "Mode TEST"
        );
    }

    // ============================================================
    //  Onglet APPLI
    // ============================================================

    /**
     * Met à jour dynamiquement la liste des hypothèses en fonction du thème choisi.
     * (mappage très simple basé sur l'énoncé ; libre à vous d'affiner ensuite)
     */
    private void updateHypothesesModel() {
        Object sel = view.themeCombo.getSelectedItem();
        String theme = (sel == null) ? "" : sel.toString();

        String[] hypos;

        if (theme.startsWith("Thème 1")) {
            hypos = new String[]{
                    "Hypothèse 1 : un seul ramassage (itinéraire le plus court)",
                    "Hypothèse 2 : tournée limitée à une dizaine de ramassages"
            };
        } else if (theme.startsWith("Thème 2")) {
            hypos = new String[]{
                    "Hypothèse 1 : approche plus proche voisin",
                    "Hypothèse 2 : approche MST + découpage par capacité"
            };
        } else if (theme.startsWith("Thème 3")) {
            hypos = new String[]{
                    "Hypothèse 1 : 2 secteurs voisins pas le même jour",
                    "Hypothèse 2 : + contraintes de capacité / nb de camions"
            };
        } else {
            // Cas par défaut
            hypos = new String[]{
                    "Hypothèse 1",
                    "Hypothèse 2",
                    "Hypothèse 3"
            };
        }

        view.hypoCombo.setModel(new DefaultComboBoxModel<>(hypos));
    }

    private void onRunClickedAppli(ActionEvent e) {
        String role  = (String) view.roleCombo.getSelectedItem();   // Collectivité / Particulier
        String theme = (String) view.themeCombo.getSelectedItem();  // Thème 1 / 2 / 3 ...
        String hypo  = (String) view.hypoCombo.getSelectedItem();   // Hypothèse choisie

        // Pour l'instant, on réutilise la même mécanique que l'onglet "Test" :
        // on demande à l'utilisateur de choisir fichier + algo dans l'onglet Test,
        // puis on "contextualise" juste le résultat avec le rôle / thème / hypothèse.
        //
        // Plus tard, si tu crées des méthodes spécifiques pour chaque cas
        // (ex : calculTournéesTheme2(), planificationTheme3(), etc.),
        // tu pourras les appeler ici en fonction du trio (role, theme, hypo).

        String fileName    = (String) view.fileComboTest.getSelectedItem();
        String algoName    = (String) view.algoComboTest.getSelectedItem();
        String departText  = view.departFieldTest.getText();
        String arriveeText = view.arriveeFieldTest.getText();

        if (fileName == null || algoName == null) {
            showError("Veuillez d'abord choisir un fichier et un algorithme dans l'onglet Test.");
            return;
        }

        // On effectue le calcul comme pour le mode test, mais en changeant le header.
        runItineraire(
                fileName,
                algoName,
                departText,
                arriveeText,
                view.outputAreaAppli,
                buildAppliHeader(role, theme, hypo)
        );
    }

    private String buildAppliHeader(String role, String theme, String hypo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Mode APPLI\n");
        sb.append("Utilisateur : ").append(role == null ? "?" : role).append("\n");
        sb.append("Thème      : ").append(theme == null ? "?" : theme).append("\n");
        sb.append("Hypothèses : ").append(hypo == null ? "?" : hypo).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }

    // ============================================================
    //  Partie commune : exécution des algos + gestion fichiers
    // ============================================================

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

