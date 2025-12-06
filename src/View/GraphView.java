package View;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JFrame {

    private JTabbedPane tabbedPane;

    // ==========================
    // Onglet TEST
    // ==========================
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

    // ==========================
    // Onglet COLLECTIVITE
    // ==========================
    public JComboBox<String> fileComboCollectivite;
    public JComboBox<String> algoComboCollectivite;
    public JTextField departFieldCollectivite;
    public JTextField arriveeFieldCollectivite;
    public JTextArea outputAreaCollectivite;
    public JButton runButtonCollectivite;

    private JLabel departLabelCollectivite;
    private JLabel arriveeLabelCollectivite;

    public JTextArea secteursConfigAreaCollectivite;
    public JPanel secteursConfigPanelCollectivite;

    public JTextField capaciteCamionFieldCollectivite;
    public JTextField contenancesFieldCollectivite;
    private JLabel capaciteCamionLabelCollectivite;
    private JLabel contenancesLabelCollectivite;

    // ==========================
    // Onglet ENTREPRISE
    // ==========================
    // ----- Onglet ENTREPRISE -----
    public JComboBox<String> fileComboEntreprise;
    public JComboBox<String> algoComboEntreprise;
    public JTextField departFieldEntreprise;
    public JTextField arriveeFieldEntreprise;
    public JLabel departLabelEntreprise;
    public JLabel arriveeLabelEntreprise;
    public JTextArea outputAreaEntreprise;
    public JButton runButtonEntreprise;


    public JTextField capaciteCamionFieldEntreprise;
    public JTextField contenancesFieldEntreprise;
    private JLabel capaciteCamionLabelEntreprise;
    private JLabel contenancesLabelEntreprise;

    // Liste d'arêtes (pour PB1 & Thème 2 AP1)
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
    // ONGLET TEST
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
    // ONGLET COLLECTIVITE
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

        if (isTheme3H1) {
            // Thème 3 H1 : ni départ ni arrivée
            departLabelCollectivite.setVisible(false);
            departFieldCollectivite.setVisible(false);
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        } else if (isPB2) {
            // PB2 : seulement départ
            departLabelCollectivite.setVisible(true);
            departFieldCollectivite.setVisible(true);
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        } else {
            // Autres cas (ex: Thème 3 H2)
            departLabelCollectivite.setVisible(true);
            departFieldCollectivite.setVisible(true);
            arriveeLabelCollectivite.setVisible(true);
            arriveeFieldCollectivite.setVisible(true);
        }

        secteursConfigPanelCollectivite.setVisible(isTheme3H1);

        capaciteCamionLabelCollectivite.setVisible(isTheme3H2);
        capaciteCamionFieldCollectivite.setVisible(isTheme3H2);
        contenancesLabelCollectivite.setVisible(isTheme3H2);
        contenancesFieldCollectivite.setVisible(isTheme3H2);
    }

    // =====================================================================
    // ONGLET ENTREPRISE
    // =====================================================================
    private JPanel buildEntrepriseTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboEntreprise = new JComboBox<>();

        algoComboEntreprise = new JComboBox<>(new String[]{
                "Thème 1 - PB1 - H1 (1 encombrant)",
                "Thème 1 - PB1 - H2 (plusieurs encombrants)",
                "Thème 2 - Approche 1 (Plus proche voisin)",
                "Thème 2 - Approche 2 (MST)"
        });

        departFieldEntreprise = new JTextField("0");
        arriveeFieldEntreprise = new JTextField("1");

        departLabelEntreprise = new JLabel("Sommet de départ (dépôt) :");
        arriveeLabelEntreprise = new JLabel("Sommet d'arrivée :");

        capaciteCamionFieldEntreprise = new JTextField("10");
        contenancesFieldEntreprise = new JTextField("2,3,2,4,3,5");

        capaciteCamionLabelEntreprise = new JLabel("Capacité camion :");
        contenancesLabelEntreprise = new JLabel("Contenances (séparées par des virgules) :");

        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setLineWrap(true);
        outputAreaEntreprise.setWrapStyleWord(true);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResult = new JScrollPane(outputAreaEntreprise);
        scrollResult.setBorder(BorderFactory.createTitledBorder("Résultat"));

        // Liste d'arêtes
        liaisonsListEntreprise = new JList<>(new DefaultListModel<>());
        liaisonsListEntreprise.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollLiaisons = new JScrollPane(liaisonsListEntreprise);
        scrollLiaisons.setPreferredSize(new Dimension(280, 140));

        liaisonsPanelEntreprise = new JPanel(new BorderLayout(5, 5));
        liaisonsPanelEntreprise.setBorder(
                BorderFactory.createTitledBorder("Arêtes (PB1 & Thème 2 AP1)")
        );
        liaisonsPanelEntreprise.add(
                new JLabel("Sélectionnez une ou plusieurs arêtes :"),
                BorderLayout.NORTH
        );
        liaisonsPanelEntreprise.add(scrollLiaisons, BorderLayout.CENTER);
        liaisonsPanelEntreprise.setVisible(false);

        capaciteCamionLabelEntreprise.setVisible(false);
        capaciteCamionFieldEntreprise.setVisible(false);
        contenancesLabelEntreprise.setVisible(false);
        contenancesFieldEntreprise.setVisible(false);

        JPanel top = new JPanel(new GridLayout(6, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboEntreprise);

        top.add(new JLabel("Problème / Thème :"));
        top.add(algoComboEntreprise);

        top.add(departLabelEntreprise);
        top.add(departFieldEntreprise);

        top.add(arriveeLabelEntreprise);
        top.add(arriveeFieldEntreprise);

        top.add(capaciteCamionLabelEntreprise);
        top.add(capaciteCamionFieldEntreprise);

        top.add(contenancesLabelEntreprise);
        top.add(contenancesFieldEntreprise);

        algoComboEntreprise.addActionListener(e -> updateEntrepriseVisibility());

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

        boolean isPB1 = sel.startsWith("Thème 1 - PB1");
        boolean isPPV = "Thème 2 - Approche 1 (Plus proche voisin)".equals(sel);
        boolean isMST = "Thème 2 - Approche 2 (MST)".equals(sel);

        // Liste des arêtes : PB1 H1 + PB1 H2 + Thème 2 AP1
        boolean needsEdges = isPB1 || isPPV;
        liaisonsPanelEntreprise.setVisible(needsEdges);

        // Capacité / contenances : seulement pour Thème 2 MST
        capaciteCamionLabelEntreprise.setVisible(isMST);
        capaciteCamionFieldEntreprise.setVisible(isMST);
        contenancesLabelEntreprise.setVisible(isMST);
        contenancesFieldEntreprise.setVisible(isMST);

        // Sommet d'arrivée : on le cache pour PB1 H1, PB1 H2, PPV, MST (il ne sert jamais)
        boolean hideArrival = isPB1 || isPPV || isMST;
        arriveeLabelEntreprise.setVisible(!hideArrival);
        arriveeFieldEntreprise.setVisible(!hideArrival);
    }
}
