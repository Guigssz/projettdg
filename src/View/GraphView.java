package View;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JFrame {

    private JTabbedPane tabbedPane;

    // ----- Onglet TEST -----
    public JComboBox<String> fileComboTest;
    public JComboBox<String> algoComboTest;
    public JTextField departFieldTest;
    public JTextField arriveeFieldTest;
    public JTextArea outputAreaTest;
    public JButton runButtonTest;
    public JTextField capaciteCamionFieldTest;
    public JTextField contenancesFieldTest;
    private JLabel capaciteCamionLabelTest;
    private JLabel contenancesLabelTest;

    // ----- Onglet COLLECTIVITE -----
    public JComboBox<String> fileComboCollectivite;
    public JComboBox<String> algoComboCollectivite;
    public JTextField departFieldCollectivite;
    public JTextField arriveeFieldCollectivite;
    public JTextArea outputAreaCollectivite;
    public JButton runButtonCollectivite;

    public JTextArea secteursConfigAreaCollectivite;
    public JTextField capaciteCamionFieldCollectivite;
    public JTextField contenancesFieldCollectivite;

    private JLabel departLabelCollectivite;
    private JLabel arriveeLabelCollectivite;
    private JLabel capaciteCamionLabelCollectivite;
    private JLabel contenancesLabelCollectivite;
    private JPanel secteursConfigPanelCollectivite;

    // ----- Onglet ENTREPRISE -----
    public JComboBox<String> fileComboEntreprise;
    public JComboBox<String> algoComboEntreprise;
    public JTextField departFieldEntreprise;
    public JTextField arriveeFieldEntreprise;
    public JTextArea outputAreaEntreprise;
    public JButton runButtonEntreprise;

    public JTextField capaciteCamionFieldEntreprise;
    public JTextField contenancesFieldEntreprise;
    private JLabel capaciteCamionLabelEntreprise;
    private JLabel contenancesLabelEntreprise;

    public JList<String> liaisonsListEntreprise;
    public JPanel liaisonsPanelEntreprise;

    public GraphView() {
        super("Projet TG - Gestion déchets");

        tabbedPane = new JTabbedPane();

        JPanel testPanel = buildTestTab();
        JPanel collectivitePanel = buildCollectiviteTab();
        JPanel entreprisePanel = buildEntrepriseTab();

        tabbedPane.addTab("Test", testPanel);
        tabbedPane.addTab("Collectivité", collectivitePanel);
        tabbedPane.addTab("Entreprise de collecte", entreprisePanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
    }

    // =====================================================================
    // Onglet TEST
    // =====================================================================
    private JPanel buildTestTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboTest = new JComboBox<>();
        algoComboTest = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS",
                "Thème 2 - Hypothèse 2"
        });
        departFieldTest = new JTextField("0");
        arriveeFieldTest = new JTextField("1");

        capaciteCamionFieldTest = new JTextField("10");
        contenancesFieldTest = new JTextField("2,3,2,4,3,5");

        capaciteCamionLabelTest = new JLabel("Capacité camion :");
        contenancesLabelTest = new JLabel("Contenances (séparées par des virgules) :");

        outputAreaTest = new JTextArea();
        outputAreaTest.setEditable(false);
        outputAreaTest.setLineWrap(true);
        outputAreaTest.setWrapStyleWord(true);
        outputAreaTest.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonTest = new JButton("Lancer le test");

        JPanel top = new JPanel(new GridLayout(6, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboTest);

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboTest);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldTest);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldTest);

        top.add(capaciteCamionLabelTest);
        top.add(capaciteCamionFieldTest);

        top.add(contenancesLabelTest);
        top.add(contenancesFieldTest);

        capaciteCamionLabelTest.setVisible(false);
        capaciteCamionFieldTest.setVisible(false);
        contenancesLabelTest.setVisible(false);
        contenancesFieldTest.setVisible(false);

        algoComboTest.addActionListener(e -> {
            boolean isTheme2 = "Thème 2 - Hypothèse 2".equals(algoComboTest.getSelectedItem());
            capaciteCamionLabelTest.setVisible(isTheme2);
            capaciteCamionFieldTest.setVisible(isTheme2);
            contenancesLabelTest.setVisible(isTheme2);
            contenancesFieldTest.setVisible(isTheme2);
        });

        JScrollPane scroll = new JScrollPane(outputAreaTest);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultats du test"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonTest);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // =====================================================================
    // Onglet COLLECTIVITE
    // =====================================================================
    private JPanel buildCollectiviteTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboCollectivite = new JComboBox<>();

        algoComboCollectivite = new JComboBox<>(new String[]{
                "Thème 1 - PB2 - Cas idéal (0 sommets impairs)",
                "Thème 1 - PB2 - Cas 2 sommets impairs",
                "Thème 1 - PB2 - Cas général",
                "Thème 3 - Hypothèse 1",
                "Thème 3 - Hypothèse 2"
        });

        departFieldCollectivite = new JTextField("0");
        arriveeFieldCollectivite = new JTextField("1");

        departLabelCollectivite = new JLabel("Sommet de départ :");
        arriveeLabelCollectivite = new JLabel("Sommet d'arrivée :");

        capaciteCamionFieldCollectivite = new JTextField("10");
        contenancesFieldCollectivite = new JTextField("2,3,2,4,3,5");
        capaciteCamionLabelCollectivite = new JLabel("Capacité :");
        contenancesLabelCollectivite = new JLabel("Contenances :");

        capaciteCamionLabelCollectivite.setVisible(false);
        capaciteCamionFieldCollectivite.setVisible(false);
        contenancesLabelCollectivite.setVisible(false);
        contenancesFieldCollectivite.setVisible(false);

        JPanel top = new JPanel(new GridLayout(4, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboCollectivite);

        top.add(new JLabel("Problème / Thème :"));
        top.add(algoComboCollectivite);

        top.add(departLabelCollectivite);
        top.add(departFieldCollectivite);

        top.add(arriveeLabelCollectivite);
        top.add(arriveeFieldCollectivite);

        // Config secteurs pour Thème 3 H1
        secteursConfigAreaCollectivite = new JTextArea(5, 30);
        secteursConfigAreaCollectivite.setFont(new Font("Monospaced", Font.PLAIN, 12));
        secteursConfigAreaCollectivite.setText(
                "1: 0,1,6,7\n" +
                        "2: 2,3,4,5\n" +
                        "3: 8,9,10,11\n" +
                        "4: 12,13,14"
        );

        secteursConfigPanelCollectivite = new JPanel(new BorderLayout(5, 5));
        JLabel lab = new JLabel("Secteurs (Thème 3 H1) : idSecteur : sommets séparés par virgules");
        secteursConfigPanelCollectivite.add(lab, BorderLayout.NORTH);
        secteursConfigPanelCollectivite.add(new JScrollPane(secteursConfigAreaCollectivite), BorderLayout.CENTER);
        secteursConfigPanelCollectivite.setBorder(BorderFactory.createTitledBorder("Définition des secteurs"));
        secteursConfigPanelCollectivite.setVisible(false);

        outputAreaCollectivite = new JTextArea();
        outputAreaCollectivite.setEditable(false);
        outputAreaCollectivite.setLineWrap(true);
        outputAreaCollectivite.setWrapStyleWord(true);
        outputAreaCollectivite.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResult = new JScrollPane(outputAreaCollectivite);
        scrollResult.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(secteursConfigPanelCollectivite, BorderLayout.NORTH);
        center.add(scrollResult, BorderLayout.CENTER);

        runButtonCollectivite = new JButton("Calculer");
        JPanel bottom = new JPanel();
        bottom.add(runButtonCollectivite);

        algoComboCollectivite.addActionListener(e -> updateCollectiviteVisibility());
        // appel initial pour régler la visibilité en fonction de la valeur par défaut
        updateCollectiviteVisibility();

        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateCollectiviteVisibility() {
        String sel = (String) algoComboCollectivite.getSelectedItem();

        boolean isPB2 = sel != null && sel.startsWith("Thème 1 - PB2");
        boolean isTheme3H1 = "Thème 3 - Hypothèse 1".equals(sel);
        boolean isTheme3H2 = "Thème 3 - Hypothèse 2".equals(sel);

        // Gestion départ / arrivée
        if (isTheme3H1) {
            // Thème 3 H1 : ni départ ni arrivée (juste la coloration de secteurs)
            departLabelCollectivite.setVisible(false);
            departFieldCollectivite.setVisible(false);
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        } else if (isPB2) {
            // Thème 1 PB2 : seulement le sommet de départ
            departLabelCollectivite.setVisible(true);
            departFieldCollectivite.setVisible(true);
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        } else {
            // Autres cas : départ + arrivée visibles
            departLabelCollectivite.setVisible(true);
            departFieldCollectivite.setVisible(true);
            arriveeLabelCollectivite.setVisible(true);
            arriveeFieldCollectivite.setVisible(true);
        }

        // Panneau secteurs pour Thème 3 H1
        secteursConfigPanelCollectivite.setVisible(isTheme3H1);

        // Capacités pour Thème 3 H2 (optionnel)
        capaciteCamionLabelCollectivite.setVisible(isTheme3H2);
        capaciteCamionFieldCollectivite.setVisible(isTheme3H2);
        contenancesLabelCollectivite.setVisible(isTheme3H2);
        contenancesFieldCollectivite.setVisible(isTheme3H2);
    }

    // =====================================================================
    // Onglet ENTREPRISE
    // =====================================================================
    // À déclarer en haut de ta classe avec les autres champs :
    private JLabel arriveeLabelEntreprise;

    private JPanel buildEntrepriseTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboEntreprise = new JComboBox<>();

        algoComboEntreprise = new JComboBox<>(new String[]{
                "Thème 1 - PB1 - H1 (1 encombrant)",
                "Thème 1 - PB1 - H2 (plusieurs encombrants)",
                "Thème 2 - Approche 1 (Plus proche voisin)",
                "Thème 2 - Approche 2 (MST)",
                "Thème 2 - Hypothèse 2"
        });

        departFieldEntreprise = new JTextField("0");
        arriveeFieldEntreprise = new JTextField("1");

        capaciteCamionFieldEntreprise = new JTextField("10");
        contenancesFieldEntreprise = new JTextField("2,3,2,4,3,5");

        capaciteCamionLabelEntreprise = new JLabel("Capacité camion :");
        contenancesLabelEntreprise = new JLabel("Contenances (séparées par des virgules) :");

        // Zone de résultat
        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setLineWrap(true);
        outputAreaEntreprise.setWrapStyleWord(true);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResult = new JScrollPane(outputAreaEntreprise);
        scrollResult.setBorder(BorderFactory.createTitledBorder("Résultat"));

        // Liste des arêtes pour Thème 1 PB1
        liaisonsListEntreprise = new JList<>(new DefaultListModel<>());
        JScrollPane scrollLiaisons = new JScrollPane(liaisonsListEntreprise);
        scrollLiaisons.setPreferredSize(new Dimension(250, 120));

        liaisonsPanelEntreprise = new JPanel(new BorderLayout(5, 5));
        liaisonsPanelEntreprise.setBorder(
                BorderFactory.createTitledBorder("Arêtes (choix de l'encombrant - Thème 1 PB1)")
        );
        liaisonsPanelEntreprise.add(
                new JLabel("Sélectionnez une arête de la liste :"),
                BorderLayout.NORTH
        );
        liaisonsPanelEntreprise.add(scrollLiaisons, BorderLayout.CENTER);
        liaisonsPanelEntreprise.setVisible(false);

        JPanel top = new JPanel(new GridLayout(6, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboEntreprise);

        top.add(new JLabel("Problème / Thème :"));
        top.add(algoComboEntreprise);

        top.add(new JLabel("Sommet de départ (dépôt) :"));
        top.add(departFieldEntreprise);

        arriveeLabelEntreprise = new JLabel("Sommet d'arrivée :");
        top.add(arriveeLabelEntreprise);
        top.add(arriveeFieldEntreprise);

        top.add(capaciteCamionLabelEntreprise);
        top.add(capaciteCamionFieldEntreprise);

        top.add(contenancesLabelEntreprise);
        top.add(contenancesFieldEntreprise);

        capaciteCamionLabelEntreprise.setVisible(false);
        capaciteCamionFieldEntreprise.setVisible(false);
        contenancesLabelEntreprise.setVisible(false);
        contenancesFieldEntreprise.setVisible(false);

        algoComboEntreprise.addActionListener(e -> updateEntrepriseVisibility());
        updateEntrepriseVisibility();

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(liaisonsPanelEntreprise, BorderLayout.NORTH);
        center.add(scrollResult, BorderLayout.CENTER);

        runButtonEntreprise = new JButton("Calculer");
        JPanel bottom = new JPanel();
        bottom.add(runButtonEntreprise);

        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateEntrepriseVisibility() {
        String sel = (String) algoComboEntreprise.getSelectedItem();
        if (sel == null) return;

        boolean isTheme2Hyp2 = "Thème 2 - Hypothèse 2".equals(sel);
        boolean isPB1 = sel.startsWith("Thème 1 - PB1");

        // Capacité / contenances seulement pour Thème 2 Hyp 2
        capaciteCamionLabelEntreprise.setVisible(isTheme2Hyp2);
        capaciteCamionFieldEntreprise.setVisible(isTheme2Hyp2);
        contenancesLabelEntreprise.setVisible(isTheme2Hyp2);
        contenancesFieldEntreprise.setVisible(isTheme2Hyp2);

        // Sommet d'arrivée : caché pour PB1, visible sinon
        if (isPB1) {
            arriveeLabelEntreprise.setVisible(false);
            arriveeFieldEntreprise.setVisible(false);
        } else {
            arriveeLabelEntreprise.setVisible(true);
            arriveeFieldEntreprise.setVisible(true);
        }

        // Panneau arêtes pour Thème 1 PB1 (H1 ou H2)
        liaisonsPanelEntreprise.setVisible(isPB1);
    }
}

