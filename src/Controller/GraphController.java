package Controller;

import View.GraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Graphe.Liaison;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;

// Thème 1 PB2
import Model.Theme1.Pb2;
import Model.Theme1.TourneePb2;

// Thème 1 PB1
import Model.Theme1.CalculItineraire;
import Model.Theme1.Encombrant;

// Thème 2 Hyp2
import Model.Theme2.AlgoTheme2Hyp2;
import Model.Theme2.PointCollecteSpb2;

// Thème 3 H1
import Model.Theme3.GestionsSecteurs;
import Model.Theme3.Secteur;
import Model.Theme3.AlgoColoration;

public class GraphController {

    private final GraphView view;
    private String[] allFiles;

    // cache du graphe utilisé pour la liste d'arêtes (Entreprise)
    private Graphe lastGraphEntrepriseEdges;
    private String lastGraphEntrepriseFile;

    public GraphController(GraphView view) {
        this.view = view;

        allFiles = scanTestFiles("data/test");

        if (view.fileComboTest != null) {
            view.fileComboTest.setModel(new DefaultComboBoxModel<>(allFiles));
        }
        if (view.fileComboEntreprise != null) {
            view.fileComboEntreprise.setModel(new DefaultComboBoxModel<>(allFiles));
        }
        if (view.fileComboCollectivite != null) {
            view.fileComboCollectivite.setModel(new DefaultComboBoxModel<>(allFiles));
        }

        // Listeners onglet Test
        if (view.runButtonTest != null) {
            view.runButtonTest.addActionListener(this::onRunClickedTest);
        }

        // Listeners onglet Collectivité
        if (view.runButtonCollectivite != null) {
            view.runButtonCollectivite.addActionListener(this::onRunClickedCollectivite);
        }
        if (view.algoComboCollectivite != null) {
            view.algoComboCollectivite.addActionListener(e -> updateCollectiviteFiles());
        }

        // Listeners onglet Entreprise
        if (view.runButtonEntreprise != null) {
            view.runButtonEntreprise.addActionListener(this::onRunClickedEntreprise);
        }
        if (view.fileComboEntreprise != null) {
            view.fileComboEntreprise.addActionListener(e -> updateEntrepriseEdgesList());
        }
        if (view.algoComboEntreprise != null) {
            view.algoComboEntreprise.addActionListener(e -> {
                String sel = (String) view.algoComboEntreprise.getSelectedItem();
                if (sel != null && sel.startsWith("Thème 1 - PB1")) {
                    updateEntrepriseEdgesList();
                }
            });
        }
    }

    // =====================================================================
    // ONGLET TEST
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
    // ONGLET COLLECTIVITE
    // =====================================================================
    private void onRunClickedCollectivite(ActionEvent e) {
        String fileName = (String) view.fileComboCollectivite.getSelectedItem();
        String algoName = (String) view.algoComboCollectivite.getSelectedItem();
        String departText = view.departFieldCollectivite.getText();

        if (algoName == null) {
            view.outputAreaCollectivite.setText("Veuillez choisir un problème / thème.");
            return;
        }

        // Thème 1 - PB2
        if (algoName.startsWith("Thème 1 - PB2 - Cas idéal")) {
            runPb2CasIdeal(fileName, departText, view.outputAreaCollectivite);
            return;
        }
        if (algoName.startsWith("Thème 1 - PB2 - Cas 2 sommets impairs")) {
            runPb2Cas2Impairs(fileName, departText, view.outputAreaCollectivite);
            return;
        }
        if (algoName.startsWith("Thème 1 - PB2 - Cas général")) {
            runPb2CasGeneral(fileName, departText, view.outputAreaCollectivite);
            return;
        }

        // Thème 3 H1
        if ("Thème 3 - Hypothèse 1".equals(algoName)) {
            runTheme3H1(
                    fileName,
                    view.secteursConfigAreaCollectivite.getText(),
                    view.outputAreaCollectivite
            );
            return;
        }

        // Thème 3 H2 : non implémenté pour l'instant
        if ("Thème 3 - Hypothèse 2".equals(algoName)) {
            view.outputAreaCollectivite.setText(
                    "Thème 3 - Hypothèse 2 : pas encore implémenté dans le contrôleur."
            );
            return;
        }

        view.outputAreaCollectivite.setText("Problème non reconnu.");
    }

    private void updateCollectiviteFiles() {
        String sel = (String) view.algoComboCollectivite.getSelectedItem();
        if (sel == null) return;

        if (sel.startsWith("Thème 1 - PB2 - Cas idéal")) {
            setCollectiviteFilesFilter("zeroimpairs.txt");
        } else if (sel.startsWith("Thème 1 - PB2 - Cas 2 sommets impairs")) {
            setCollectiviteFilesFilter("deuxsommetimpairs.txt");
        } else if (sel.startsWith("Thème 1 - PB2 - Cas général")) {
            view.fileComboCollectivite.setModel(new DefaultComboBoxModel<>(allFiles));
        } else {
            // Thème 3 H1 / H2
            view.fileComboCollectivite.setModel(new DefaultComboBoxModel<>(allFiles));
        }
    }

    private void setCollectiviteFilesFilter(String fileNameWanted) {
        List<String> list = new ArrayList<>();
        for (String f : allFiles) {
            if (f.equals(fileNameWanted)) {
                list.add(f);
            }
        }
        if (list.isEmpty()) {
            list.add("(fichier " + fileNameWanted + " introuvable)");
        }
        view.fileComboCollectivite.setModel(
                new DefaultComboBoxModel<>(list.toArray(new String[0]))
        );
    }

    // ---------- Thème 1 PB2 : cas idéal ----------
    private void runPb2CasIdeal(String fileName, String depotText, JTextArea out) {
        if (fileName == null || fileName.startsWith("(fichier")) {
            out.setText("Fichier zeroimpairs.txt introuvable dans data/test.");
            return;
        }

        int idDepot;
        try {
            idDepot = Integer.parseInt(depotText.trim());
        } catch (NumberFormatException ex) {
            showError("L'id du dépôt doit être un entier.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);
            Sommet depot = g.getSommet(idDepot);

            TourneePb2 tournee = Pb2.casIdeal(g, depot);

            if (tournee == null) {
                out.setText("Le graphe n'est pas eulérien (cas idéal impossible).");
                return;
            }

            String affichage = captureAffichageTourneePb2(tournee);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 1 - PB2 - Cas idéal (0 sommets impairs)\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Dépôt : sommet ").append(idDepot).append("\n");
            sb.append("====================================\n\n");
            sb.append(affichage);

            out.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            out.setText("Erreur cas idéal : " + ex.getMessage());
        }
    }

    // ---------- Thème 1 PB2 : cas 2 impairs ----------
    private void runPb2Cas2Impairs(String fileName, String depotText, JTextArea out) {
        if (fileName == null || fileName.startsWith("(fichier")) {
            out.setText("Fichier deuxsommetimpairs.txt introuvable dans data/test.");
            return;
        }

        int idDepot;
        try {
            idDepot = Integer.parseInt(depotText.trim());
        } catch (NumberFormatException ex) {
            showError("L'id du dépôt doit être un entier.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);
            Sommet depot = g.getSommet(idDepot);

            TourneePb2 tournee = Pb2.casDeuxImpairs(g, depot);

            if (tournee == null) {
                out.setText("Erreur : le graphe n'a pas exactement 2 sommets impairs.");
                return;
            }

            String affichage = captureAffichageTourneePb2(tournee);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 1 - PB2 - Cas 2 sommets impairs\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Dépôt : sommet ").append(idDepot).append("\n");
            sb.append("====================================\n\n");
            sb.append(affichage);

            out.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            out.setText("Erreur cas 2 impairs : " + ex.getMessage());
        }
    }

    // ---------- Thème 1 PB2 : cas général ----------
    private void runPb2CasGeneral(String fileName, String depotText, JTextArea out) {
        if (fileName == null || fileName.startsWith("(aucun")) {
            out.setText("Aucun fichier de graphe sélectionné.");
            return;
        }

        int idDepot;
        try {
            idDepot = Integer.parseInt(depotText.trim());
        } catch (NumberFormatException ex) {
            showError("L'id du dépôt doit être un entier.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);
            Sommet depot = g.getSommet(idDepot);

            TourneePb2 tournee = Pb2.casGeneral(g, depot);

            if (tournee == null) {
                out.setText("Erreur dans le cas général (matching / eulérisation).");
                return;
            }

            String affichage = captureAffichageTourneePb2(tournee);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 1 - PB2 - Cas général\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Dépôt : sommet ").append(idDepot).append("\n");
            sb.append("====================================\n\n");
            sb.append(affichage);

            out.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            out.setText("Erreur cas général : " + ex.getMessage());
        }
    }

    // =====================================================================
    // ONGLET ENTREPRISE
    // =====================================================================
    private void onRunClickedEntreprise(ActionEvent e) {
        String fileName = (String) view.fileComboEntreprise.getSelectedItem();
        String algoName = (String) view.algoComboEntreprise.getSelectedItem();

        if (algoName == null) {
            view.outputAreaEntreprise.setText("Veuillez choisir un problème / thème.");
            return;
        }

        // Thème 1 - PB1 - H1
        if ("Thème 1 - PB1 - H1 (1 encombrant)".equals(algoName)) {
            runTheme1PB1H1(fileName);
            return;
        }

        // (plus tard : Thème 1 PB1 H2 ici)

        // Thème 2 - Hypothèse 2
        if ("Thème 2 - Hypothèse 2".equals(algoName)) {
            runTheme2Hyp2(
                    fileName,
                    view.capaciteCamionFieldEntreprise.getText(),
                    view.contenancesFieldEntreprise.getText(),
                    view.outputAreaEntreprise,
                    "Entreprise de collecte"
            );
            return;
        }

        view.outputAreaEntreprise.setText(
                "Le problème sélectionné n'est pas encore implémenté dans l'onglet Entreprise.\n" +
                        "Tu pourras le brancher plus tard (Thème 1 PB1 H2, Thème 2 AP1/AP2, etc.)."
        );
    }

    private void updateEntrepriseEdgesList() {
        if (view.fileComboEntreprise == null || view.liaisonsListEntreprise == null) return;

        String fileName = (String) view.fileComboEntreprise.getSelectedItem();
        if (fileName == null || fileName.startsWith("(aucun")) {
            DefaultListModel<String> empty = new DefaultListModel<>();
            view.liaisonsListEntreprise.setModel(empty);
            lastGraphEntrepriseEdges = null;
            lastGraphEntrepriseFile = null;
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);

            lastGraphEntrepriseEdges = g;
            lastGraphEntrepriseFile = fileName;

            DefaultListModel<String> model = new DefaultListModel<>();
            List<Liaison> liaisons = g.getLiaison();

            for (int i = 0; i < liaisons.size(); i++) {
                Liaison l = liaisons.get(i);
                int u = l.getPred().getId();
                int v = l.getSucc().getId();
                double w = l.getPoids();
                String line = (i + 1) + " : " + u + " - " + v + " (poids = " + w + ")";
                model.addElement(line);
            }

            view.liaisonsListEntreprise.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            DefaultListModel<String> empty = new DefaultListModel<>();
            empty.addElement("Erreur de chargement du graphe.");
            view.liaisonsListEntreprise.setModel(empty);
            lastGraphEntrepriseEdges = null;
            lastGraphEntrepriseFile = null;
        }
    }

    private void runTheme1PB1H1(String fileName) {
        JTextArea out = view.outputAreaEntreprise;

        if (fileName == null || fileName.startsWith("(aucun")) {
            out.setText("Aucun fichier de graphe sélectionné.");
            return;
        }

        if (lastGraphEntrepriseEdges == null || !fileName.equals(lastGraphEntrepriseFile)) {
            updateEntrepriseEdgesList();
        }

        Graphe g = lastGraphEntrepriseEdges;
        if (g == null) {
            out.setText("Impossible de charger le graphe pour Thème 1 PB1 H1.");
            return;
        }

        int idDepot;
        try {
            idDepot = Integer.parseInt(view.departFieldEntreprise.getText().trim());
        } catch (NumberFormatException ex) {
            showError("L'id du dépôt doit être un entier.");
            return;
        }

        Sommet depot = g.getSommet(idDepot);
        if (depot == null) {
            out.setText("Sommet de dépôt " + idDepot + " introuvable dans le graphe.");
            return;
        }

        int index = view.liaisonsListEntreprise.getSelectedIndex();
        if (index < 0) {
            out.setText("Veuillez sélectionner une arête dans la liste pour placer l'encombrant.");
            return;
        }

        List<Liaison> liaisons = g.getLiaison();
        if (index >= liaisons.size()) {
            out.setText("Index d'arête invalide.");
            return;
        }

        Liaison liaisonChoisie = liaisons.get(index);
        Encombrant encombrant = new Encombrant(liaisonChoisie);

        try {
            Itineraire itin = CalculItineraire.itineraireVersEncombrantavecretour(g, depot, encombrant);

            StringBuilder sb = new StringBuilder();
            sb.append("Thème 1 - PB1 - Hypothèse 1 (1 encombrant)\n");
            sb.append("Fichier : ").append(fileName).append("\n");
            sb.append("Dépôt : sommet ").append(idDepot).append("\n");
            sb.append("Encombrant sur arête : ")
                    .append(liaisonChoisie.getPred().getId())
                    .append(" - ")
                    .append(liaisonChoisie.getSucc().getId())
                    .append("\n");
            sb.append("====================================\n\n");

            sb.append(captureAffichageItineraire(itin));

            out.setText(sb.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            out.setText("Erreur Thème 1 PB1 H1 : " + ex.getMessage());
        }
    }

    // =====================================================================
    // Commun : Dijkstra / BFS sur Test
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
    // Thème 2 - Hypothèse 2
    // =====================================================================
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

            Sommet depot = g.getSommet(0);

            String[] tokens = contenancesStr.split(",");
            List<PointCollecteSpb2> points = new ArrayList<>();

            for (int i = 0; i < tokens.length; i++) {
                String t = tokens[i].trim();
                if (t.isEmpty()) continue;
                int cont = Integer.parseInt(t);
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

    // =====================================================================
    // Thème 3 - Hypothèse 1
    // =====================================================================
    private void runTheme3H1(String fileName, String secteursConfig, JTextArea outputArea) {
        if (fileName == null || fileName.startsWith("(aucun")) {
            outputArea.setText("Aucun fichier de graphe sélectionné.");
            return;
        }

        try {
            String path = "data/test/" + fileName;
            Graphe g = Graphe.chargerGraphe(path);

            GestionsSecteurs gestion = new GestionsSecteurs();

            String[] lignes = secteursConfig.split("\\R");

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

            gestion.calculerAdjacenceSecteurs(g);
            AlgoColoration.colorierWelshPowell(gestion);

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
    // Utils
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

    private String captureAffichageTourneePb2(TourneePb2 tournee) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            tournee.afficher();
        } finally {
            System.out.flush();
            System.setOut(oldOut);
        }

        return baos.toString();
    }
}

