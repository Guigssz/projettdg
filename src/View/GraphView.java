package View;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JFrame {

    // Onglets
    private JTabbedPane tabbedPane;

    // ----- Onglet TEST -----
    public JComboBox<String> fileComboTest;
    public JComboBox<String> algoComboTest;
    public JTextField departFieldTest;
    public JTextField arriveeFieldTest;
    public JTextArea outputAreaTest;
    public JButton runButtonTest;

    // champs spécifiques Thème 2 Hyp 2 (TEST)
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

    // champs spécifiques (potentiellement utiles plus tard)
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

    // champs spécifiques Thème 2 Hyp 2 (ENTREPRISE)
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
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    // ============================================================
    // Onglet TEST
    // ============================================================
    private JPanel buildTestTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboTest = new JComboBox<>();

        // On laisse tout pour les tests généraux
        algoComboTest = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS",
                "Thème 2 - Hypothèse 2"
        });

        departFieldTest = new JTextField("0");
        arriveeFieldTest = new JTextField("1");

        // champs spécifiques Thème 2 Hyp2 (TEST, si tu veux tester ici aussi)
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

        // Au départ on cache les champs spécifique Thème 2 Hyp2
        capaciteCamionLabelTest.setVisible(false);
        capaciteCamionFieldTest.setVisible(false);
        contenancesLabelTest.setVisible(false);
        contenancesFieldTest.setVisible(false);

        algoComboTest.addActionListener(e -> updateTestTheme2Visibility());

        JScrollPane scroll = new JScrollPane(outputAreaTest);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultats du test"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonTest);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateTestTheme2Visibility() {
        boolean isTheme2 = "Thème 2 - Hypothèse 2".equals(algoComboTest.getSelectedItem());
        capaciteCamionLabelTest.setVisible(isTheme2);
        capaciteCamionFieldTest.setVisible(isTheme2);
        contenancesLabelTest.setVisible(isTheme2);
        contenancesFieldTest.setVisible(isTheme2);
    }

    // ============================================================
    // Onglet COLLECTIVITE
    // ============================================================
    private JPanel buildCollectiviteTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboCollectivite = new JComboBox<>();

        // Ici on met seulement les problèmes "collectivité"
        algoComboCollectivite = new JComboBox<>(new String[]{
                "Thème 1 - PB2 - Cas idéal (0 sommets impairs)",
                "Thème 1 - PB2 - Cas 2 sommets impairs",
                "Thème 1 - PB2 - Cas général",
                "Thème 3 - Hypothèse 1",
                "Thème 3 - Hypothèse 2"
        });

        departFieldCollectivite = new JTextField("0");
        arriveeFieldCollectivite = new JTextField("1");

        // champs potentiels (par ex. pour Thème 3 H2, plus tard)
        capaciteCamionFieldCollectivite = new JTextField("10");
        contenancesFieldCollectivite = new JTextField("2,3,2,4,3,5");

        capaciteCamionLabelCollectivite = new JLabel("Capacité camion :");
        contenancesLabelCollectivite = new JLabel("Contenances (séparées par des virgules) :");

        outputAreaCollectivite = new JTextArea();
        outputAreaCollectivite.setEditable(false);
        outputAreaCollectivite.setLineWrap(true);
        outputAreaCollectivite.setWrapStyleWord(true);
        outputAreaCollectivite.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonCollectivite = new JButton("Calculer");

        JPanel top = new JPanel(new GridLayout(6, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboCollectivite);

        top.add(new JLabel("Problème / Thème :"));
        top.add(algoComboCollectivite);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldCollectivite);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldCollectivite);

        top.add(capaciteCamionLabelCollectivite);
        top.add(capaciteCamionFieldCollectivite);

        top.add(contenancesLabelCollectivite);
        top.add(contenancesFieldCollectivite);

        // Pour l’instant, on les laisse cachés (on les branchera quand on codera Thème 3 H2)
        capaciteCamionLabelCollectivite.setVisible(false);
        capaciteCamionFieldCollectivite.setVisible(false);
        contenancesLabelCollectivite.setVisible(false);
        contenancesFieldCollectivite.setVisible(false);

        // Si plus tard on veut les afficher selon l'algo choisi, on utilisera ceci :
        algoComboCollectivite.addActionListener(e -> updateCollectiviteExtraVisibility());

        JScrollPane scroll = new JScrollPane(outputAreaCollectivite);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonCollectivite);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateCollectiviteExtraVisibility() {
        // Exemple : si un jour tu veux que Thème 3 - H2 ait capacité,
        // tu peux activer ici :
        boolean isTheme3H2 = "Thème 3 - Hypothèse 2".equals(algoComboCollectivite.getSelectedItem());
        capaciteCamionLabelCollectivite.setVisible(isTheme3H2);
        capaciteCamionFieldCollectivite.setVisible(isTheme3H2);
        contenancesLabelCollectivite.setVisible(isTheme3H2);
        contenancesFieldCollectivite.setVisible(isTheme3H2);
    }

    // ============================================================
    // Onglet ENTREPRISE
    // ============================================================
    private JPanel buildEntrepriseTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboEntreprise = new JComboBox<>();

        // Onglet Entreprise : Thème 1 PB1 + Thème 2
        algoComboEntreprise = new JComboBox<>(new String[]{
                "Thème 1 - PB1 - H1 (1 encombrant)",
                "Thème 1 - PB1 - H2 (plusieurs encombrants)",
                "Thème 2 - Approche 1 (Plus proche voisin)",
                "Thème 2 - Approche 2 (MST)",
                "Thème 2 - Hypothèse 2" // on garde le libellé exact pour la détection
        });

        departFieldEntreprise = new JTextField("0");
        arriveeFieldEntreprise = new JTextField("1");

        // champs spécifiques pour Thème 2 Hyp 2
        capaciteCamionFieldEntreprise = new JTextField("10");
        contenancesFieldEntreprise = new JTextField("2,3,2,4,3,5");

        capaciteCamionLabelEntreprise = new JLabel("Capacité camion :");
        contenancesLabelEntreprise = new JLabel("Contenances (séparées par des virgules) :");

        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setLineWrap(true);
        outputAreaEntreprise.setWrapStyleWord(true);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));

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

        algoComboEntreprise.addActionListener(e -> updateEntrepriseTheme2Visibility());

        JScrollPane scroll = new JScrollPane(outputAreaEntreprise);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonEntreprise);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateEntrepriseTheme2Visibility() {
        boolean isTheme2Hyp2 = "Thème 2 - Hypothèse 2".equals(algoComboEntreprise.getSelectedItem());
        capaciteCamionLabelEntreprise.setVisible(isTheme2Hyp2);
        capaciteCamionFieldEntreprise.setVisible(isTheme2Hyp2);
        contenancesLabelEntreprise.setVisible(isTheme2Hyp2);
        contenancesFieldEntreprise.setVisible(isTheme2Hyp2);
        // Tu pourras aussi désactiver départ/arrivée plus tard si besoin
    }
}

