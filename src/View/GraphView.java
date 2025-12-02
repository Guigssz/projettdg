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

    // ----- Onglet COLLECTIVITE -----
    public JComboBox<String> fileComboCollectivite;
    public JComboBox<String> algoComboCollectivite;
    public JTextField departFieldCollectivite;
    public JTextField arriveeFieldCollectivite;
    public JTextArea outputAreaCollectivite;
    public JButton runButtonCollectivite;

    // ----- Onglet ENTREPRISE -----
    public JComboBox<String> fileComboEntreprise;
    public JComboBox<String> algoComboEntreprise;
    public JTextField departFieldEntreprise;
    public JTextField arriveeFieldEntreprise;
    public JTextArea outputAreaEntreprise;
    public JButton runButtonEntreprise;

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

        outputAreaTest = new JTextArea();
        outputAreaTest.setEditable(false);
        outputAreaTest.setLineWrap(true);
        outputAreaTest.setWrapStyleWord(true);
        outputAreaTest.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonTest = new JButton("Lancer le test");

        JPanel top = new JPanel(new GridLayout(4, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboTest);

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboTest);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldTest);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldTest);

        JScrollPane scroll = new JScrollPane(outputAreaTest);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultats du test"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonTest);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
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

        outputAreaCollectivite = new JTextArea();
        outputAreaCollectivite.setEditable(false);
        outputAreaCollectivite.setLineWrap(true);
        outputAreaCollectivite.setWrapStyleWord(true);
        outputAreaCollectivite.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonCollectivite = new JButton("Calculer");

        JPanel top = new JPanel(new GridLayout(4, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboCollectivite);

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboCollectivite);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldCollectivite);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldCollectivite);

        JScrollPane scroll = new JScrollPane(outputAreaCollectivite);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonCollectivite);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
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

        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setLineWrap(true);
        outputAreaEntreprise.setWrapStyleWord(true);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonEntreprise = new JButton("Calculer");

        JPanel top = new JPanel(new GridLayout(4, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Fichier de graphe :"));
        top.add(fileComboEntreprise);

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboEntreprise);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldEntreprise);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldEntreprise);

        JScrollPane scroll = new JScrollPane(outputAreaEntreprise);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonEntreprise);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }
}
