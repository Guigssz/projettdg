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

    // labels pour pouvoir cacher/afficher l'arrivée
    private JLabel departLabelCollectivite;
    private JLabel arriveeLabelCollectivite;

    // Zone pour configuration des secteurs (Thème 3 H1)
    public JTextArea secteursConfigAreaCollectivite;
    private JPanel secteursConfigPanelCollectivite;

    // (éventuels champs pour Thème 3 H2)
    public JTextField capaciteCamionFieldCollectivite;
    public JTextField contenancesFieldCollectivite;
    private JLabel capaciteCamionLabelCollectivite;
    private JLabel contenancesLabelCollectivite;

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
    // Onglet TEST (inchangé globalement)
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
    // Onglet COLLECTIVITE (modifié ici)
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

        // Listener pour gérer l'affichage arrivée / secteurs / capacités
        algoComboCollectivite.addActionListener(e -> updateCollectiviteVisibility());

        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Appelé quand on change le problème dans l'onglet Collectivité.
     * Ici on gère :
     *  - afficher/cacher le sommet d'arrivée
     *  - afficher/cacher panneau secteurs (Thème 3 H1)
     *  - afficher/cacher capacité/contenances pour Thème 3 H2 (si tu le veux plus tard)
     */
    private void updateCollectiviteVisibility() {
        String sel = (String) algoComboCollectivite.getSelectedItem();

        boolean isPB2 = sel != null && sel.startsWith("Thème 1 - PB2");
        boolean isTheme3H1 = "Thème 3 - Hypothèse 1".equals(sel);
        boolean isTheme3H2 = "Thème 3 - Hypothèse 2".equals(sel);

        // Thème 1 PB2 : on NE veut que le sommet de départ (pas d'arrivée)
        if (isPB2) {
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        } else {
            arriveeLabelCollectivite.setVisible(true);
            arriveeFieldCollectivite.setVisible(true);
        }

        if (isTheme3H1){
            departLabelCollectivite.setVisible(false);
            departFieldCollectivite.setVisible(false);
            arriveeLabelCollectivite.setVisible(false);
            arriveeFieldCollectivite.setVisible(false);
        }

        // Panneau secteurs seulement pour Thème 3 H1
        secteursConfigPanelCollectivite.setVisible(isTheme3H1);

        // Capacités pour Thème 3 H2 (optionnel)
        capaciteCamionLabelCollectivite.setVisible(isTheme3H2);
        capaciteCamionFieldCollectivite.setVisible(isTheme3H2);
        contenancesLabelCollectivite.setVisible(isTheme3H2);
        contenancesFieldCollectivite.setVisible(isTheme3H2);
    }

    // =====================================================================
    // Onglet ENTREPRISE (comme avant)
    // =====================================================================
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

        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setLineWrap(true);
        outputAreaEntreprise.setWrapStyleWord(true);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(outputAreaEntreprise);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        runButtonEntreprise = new JButton("Calculer");

        JPanel top = new JPanel(new GridLayout(6, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboEntreprise);

        top.add(new JLabel("Problème / Thème :"));
        top.add(algoComboEntreprise);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldEntreprise);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldEntreprise);

        top.add(capaciteCamionLabelEntreprise);
        top.add(capaciteCamionFieldEntreprise);

        top.add(contenancesLabelEntreprise);
        top.add(contenancesFieldEntreprise);

        capaciteCamionLabelEntreprise.setVisible(false);
        capaciteCamionFieldEntreprise.setVisible(false);
        contenancesLabelEntreprise.setVisible(false);
        contenancesFieldEntreprise.setVisible(false);

        algoComboEntreprise.addActionListener(e -> {
            boolean isTheme2Hyp2 = "Thème 2 - Hypothèse 2".equals(algoComboEntreprise.getSelectedItem());
            capaciteCamionLabelEntreprise.setVisible(isTheme2Hyp2);
            capaciteCamionFieldEntreprise.setVisible(isTheme2Hyp2);
            contenancesLabelEntreprise.setVisible(isTheme2Hyp2);
            contenancesFieldEntreprise.setVisible(isTheme2Hyp2);
        });

        JPanel bottom = new JPanel();
        bottom.add(runButtonEntreprise);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }
}

