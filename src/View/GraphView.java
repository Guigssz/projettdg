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

    // nouveaux champs pour Thème 2 Hyp 2 (TEST)
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

    // nouveaux champs pour Thème 2 Hyp 2 (COLLECTIVITE)
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

    // nouveaux champs pour Thème 2 Hyp 2 (ENTREPRISE)
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
        algoComboTest = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS",
                "Thème 2 - Hypothèse 2"
        });
        departFieldTest = new JTextField("0");
        arriveeFieldTest = new JTextField("1");

        // champs spécifiques Thème 2 Hyp 2
        capaciteCamionFieldTest = new JTextField("10"); // valeur par défaut
        contenancesFieldTest = new JTextField("2,3,2,4,3,5"); // exemple

        capaciteCamionLabelTest = new JLabel("Capacité camion :");
        contenancesLabelTest = new JLabel("Contenances (séparées par des virgules) :");

        // zone de sortie
        outputAreaTest = new JTextArea();
        outputAreaTest.setEditable(false);
        outputAreaTest.setLineWrap(true);
        outputAreaTest.setWrapStyleWord(true);
        outputAreaTest.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonTest = new JButton("Lancer le test");

        // on prévoit 6 lignes (4 classiques + 2 pour Thème 2)
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

        // lignes spécifiques Thème 2 Hyp 2
        top.add(capaciteCamionLabelTest);
        top.add(capaciteCamionFieldTest);

        top.add(contenancesLabelTest);
        top.add(contenancesFieldTest);

        // au départ, on les cache (elles apparaîtront si Thème 2 choisi)
        capaciteCamionLabelTest.setVisible(false);
        capaciteCamionFieldTest.setVisible(false);
        contenancesLabelTest.setVisible(false);
        contenancesFieldTest.setVisible(false);

        // listener pour afficher/cacher les champs selon l'algo choisi
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
        // tu peux décider de désactiver départ/arrivée si tu veux :
        // departFieldTest.setEnabled(!isTheme2);
        // arriveeFieldTest.setEnabled(!isTheme2);
    }

    // ============================================================
    // Onglet COLLECTIVITE
    // ============================================================
    private JPanel buildCollectiviteTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboCollectivite = new JComboBox<>();
        algoComboCollectivite = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS",
                "Thème 2 - Hypothèse 2"
        });
        departFieldCollectivite = new JTextField("0");
        arriveeFieldCollectivite = new JTextField("1");

        // champs spécifiques Thème 2 Hyp 2
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

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboCollectivite);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldCollectivite);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldCollectivite);

        // lignes spécifiques Thème 2 Hyp 2
        top.add(capaciteCamionLabelCollectivite);
        top.add(capaciteCamionFieldCollectivite);

        top.add(contenancesLabelCollectivite);
        top.add(contenancesFieldCollectivite);

        capaciteCamionLabelCollectivite.setVisible(false);
        capaciteCamionFieldCollectivite.setVisible(false);
        contenancesLabelCollectivite.setVisible(false);
        contenancesFieldCollectivite.setVisible(false);

        algoComboCollectivite.addActionListener(e -> updateCollectiviteTheme2Visibility());

        JScrollPane scroll = new JScrollPane(outputAreaCollectivite);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonCollectivite);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void updateCollectiviteTheme2Visibility() {
        boolean isTheme2 = "Thème 2 - Hypothèse 2".equals(algoComboCollectivite.getSelectedItem());
        capaciteCamionLabelCollectivite.setVisible(isTheme2);
        capaciteCamionFieldCollectivite.setVisible(isTheme2);
        contenancesLabelCollectivite.setVisible(isTheme2);
        contenancesFieldCollectivite.setVisible(isTheme2);
        // departFieldCollectivite.setEnabled(!isTheme2);
        // arriveeFieldCollectivite.setEnabled(!isTheme2);
    }

    // ============================================================
    // Onglet ENTREPRISE
    // ============================================================
    private JPanel buildEntrepriseTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        fileComboEntreprise = new JComboBox<>();
        algoComboEntreprise = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS",
                "Thème 2 - Hypothèse 2"
        });
        departFieldEntreprise = new JTextField("0");
        arriveeFieldEntreprise = new JTextField("1");

        // champs spécifiques Thème 2 Hyp 2
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

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboEntreprise);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldEntreprise);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldEntreprise);

        // lignes spécifiques Thème 2 Hyp 2
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
        boolean isTheme2 = "Thème 2 - Hypothèse 2".equals(algoComboEntreprise.getSelectedItem());
        capaciteCamionLabelEntreprise.setVisible(isTheme2);
        capaciteCamionFieldEntreprise.setVisible(isTheme2);
        contenancesLabelEntreprise.setVisible(isTheme2);
        contenancesFieldEntreprise.setVisible(isTheme2);
        // departFieldEntreprise.setEnabled(!isTheme2);
        // arriveeFieldEntreprise.setEnabled(!isTheme2);
    }
}
