package View;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JFrame {

    // Onglets
    private JTabbedPane tabbedPane;

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
        super("Projet TDG - Collecte de déchets ");

        initComponents();
        layoutComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // ---------- Onglet Collectivité ----------
        fileComboCollectivite = new JComboBox<>();
        algoComboCollectivite = new JComboBox<>(new String[]{"Dijkstra", "BFS"});
        departFieldCollectivite = new JTextField("0");
        arriveeFieldCollectivite = new JTextField("1");
        outputAreaCollectivite = new JTextArea();
        outputAreaCollectivite.setEditable(false);
        outputAreaCollectivite.setFont(new Font("Monospaced", Font.PLAIN, 12));
        runButtonCollectivite = new JButton("Calculer l'itinéraire");

        // ---------- Onglet Entreprise ----------
        fileComboEntreprise = new JComboBox<>();
        algoComboEntreprise = new JComboBox<>(new String[]{"Dijkstra", "BFS"});
        departFieldEntreprise = new JTextField("0");
        arriveeFieldEntreprise = new JTextField("1");
        outputAreaEntreprise = new JTextArea();
        outputAreaEntreprise.setEditable(false);
        outputAreaEntreprise.setFont(new Font("Monospaced", Font.PLAIN, 12));
        runButtonEntreprise = new JButton("Calculer l'itinéraire");
    }

    private void layoutComponents() {

        JPanel collectivitePanel = new JPanel(new BorderLayout());

        JPanel topCollect = new JPanel(new GridLayout(5, 2, 8, 8));
        topCollect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topCollect.add(new JLabel("Fichier de graphe :"));
        topCollect.add(fileComboCollectivite);

        topCollect.add(new JLabel("Algorithme :"));
        topCollect.add(algoComboCollectivite);

        topCollect.add(new JLabel("Point de depart :"));
        topCollect.add(departFieldCollectivite);

        topCollect.add(new JLabel("Sommet arrive :"));
        topCollect.add(arriveeFieldCollectivite);

        // Centre : résultats
        JScrollPane scrollCollect = new JScrollPane(outputAreaCollectivite);
        scrollCollect.setBorder(BorderFactory.createTitledBorder("Résultat (itinéraire + distance)"));

        // Bas : bouton
        JPanel bottomCollect = new JPanel();
        bottomCollect.add(runButtonCollectivite);

        collectivitePanel.add(topCollect, BorderLayout.NORTH);
        collectivitePanel.add(scrollCollect, BorderLayout.CENTER);
        collectivitePanel.add(bottomCollect, BorderLayout.SOUTH);

        // ----- Panel Entreprise -----
        JPanel entreprisePanel = new JPanel(new BorderLayout());

        JPanel topEnt = new JPanel(new GridLayout(5, 2, 8, 8));
        topEnt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topEnt.add(new JLabel("Fichier de graphe :"));
        topEnt.add(fileComboEntreprise);

        topEnt.add(new JLabel("Algorithme :"));
        topEnt.add(algoComboEntreprise);

        topEnt.add(new JLabel("Depart :"));
        topEnt.add(departFieldEntreprise);

        topEnt.add(new JLabel("Point de collecte :"));
        topEnt.add(arriveeFieldEntreprise);


        JScrollPane scrollEnt = new JScrollPane(outputAreaEntreprise);
        scrollEnt.setBorder(BorderFactory.createTitledBorder("Résultat (itinéraire + distance)"));

        JPanel bottomEnt = new JPanel();
        bottomEnt.add(runButtonEntreprise);

        entreprisePanel.add(topEnt, BorderLayout.NORTH);
        entreprisePanel.add(scrollEnt, BorderLayout.CENTER);
        entreprisePanel.add(bottomEnt, BorderLayout.SOUTH);

        // ----- Onglets -----
        tabbedPane.addTab("Collectivité", collectivitePanel);
        tabbedPane.addTab("Entreprise de collecte", entreprisePanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
}
