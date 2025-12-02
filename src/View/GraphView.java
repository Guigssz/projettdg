package View;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application.
 *
 * Onglet 1 : "Test"
 *   - Permet de tester les algos directement sur les fichiers textes
 *     (choix du fichier, choix de l'algo, sommet de départ / d'arrivée,
 *      affichage du résultat brut).
 *
 * Onglet 2 : "Appli"
 *   - Permet de se placer dans la peau d'une collectivité ou d'un particulier
 *     puis de choisir un thème et des hypothèses.
 *   - Le contrôleur se charge ensuite d'appeler les bons traitements
 *     et d'afficher les résultats.
 */
public class GraphView extends JFrame {

    // ---------- Composant racine ----------
    private final JTabbedPane tabbedPane;

    // ---------- Onglet TEST ----------
    public JComboBox<String> fileComboTest;
    public JComboBox<String> algoComboTest;
    public JTextField departFieldTest;
    public JTextField arriveeFieldTest;
    public JTextArea outputAreaTest;
    public JButton runButtonTest;

    // ---------- Onglet APPLI ----------
    public JComboBox<String> roleCombo;      // Collectivité / Particulier
    public JComboBox<String> themeCombo;     // Thème 1 / Thème 2 / Thème 3 ...
    public JComboBox<String> hypoCombo;      // Hypothèses possibles
    public JTextArea outputAreaAppli;
    public JButton runButtonAppli;

    public GraphView() {
        super("Projet Théorie des Graphes - Gestion des déchets");

        tabbedPane = new JTabbedPane();

        // Construction des deux onglets
        buildTestTab();
        buildAppliTab();

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    // ============================================================
    //  Onglet TEST
    // ============================================================
    private void buildTestTab() {
        JPanel testPanel = new JPanel(new BorderLayout(10, 10));

        fileComboTest = new JComboBox<>();
        algoComboTest = new JComboBox<>(new String[]{"Dijkstra", "BFS"});
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

        top.add(new JLabel("Fichier de test :"));
        top.add(fileComboTest);

        top.add(new JLabel("Algorithme :"));
        top.add(algoComboTest);

        top.add(new JLabel("Sommet de départ :"));
        top.add(departFieldTest);

        top.add(new JLabel("Sommet d'arrivée :"));
        top.add(arriveeFieldTest);

        JScrollPane scroll = new JScrollPane(outputAreaTest);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultat du test"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonTest);

        testPanel.add(top, BorderLayout.NORTH);
        testPanel.add(scroll, BorderLayout.CENTER);
        testPanel.add(bottom, BorderLayout.SOUTH);

        tabbedPane.addTab("Test", testPanel);
    }

    // ============================================================
    //  Onglet APPLI
    // ============================================================
    private void buildAppliTab() {
        JPanel appliPanel = new JPanel(new BorderLayout(10, 10));

        // Collectivité / Particulier
        roleCombo = new JComboBox<>(new String[]{
                "Collectivité",
                "Particulier"
        });

        // Thèmes du sujet
        themeCombo = new JComboBox<>(new String[]{
                "Thème 1 : Ramassage aux pieds des habitations",
                "Thème 2 : Ramassage des points de collecte",
                "Thème 3 : Planification des jours de passage"
        });

        // Les hypothèses seront ajustées dynamiquement par le contrôleur
        hypoCombo = new JComboBox<>(new String[]{
                "Hypothèse 1",
                "Hypothèse 2",
                "Hypothèse 3"
        });

        outputAreaAppli = new JTextArea();
        outputAreaAppli.setEditable(false);
        outputAreaAppli.setLineWrap(true);
        outputAreaAppli.setWrapStyleWord(true);
        outputAreaAppli.setFont(new Font("Monospaced", Font.PLAIN, 12));

        runButtonAppli = new JButton("Calculer / Afficher les résultats");

        JPanel top = new JPanel(new GridLayout(3, 2, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        top.add(new JLabel("Type d'utilisateur :"));
        top.add(roleCombo);

        top.add(new JLabel("Thème :"));
        top.add(themeCombo);

        top.add(new JLabel("Hypothèses :"));
        top.add(hypoCombo);

        JScrollPane scroll = new JScrollPane(outputAreaAppli);
        scroll.setBorder(BorderFactory.createTitledBorder("Résultats"));

        JPanel bottom = new JPanel();
        bottom.add(runButtonAppli);

        appliPanel.add(top, BorderLayout.NORTH);
        appliPanel.add(scroll, BorderLayout.CENTER);
        appliPanel.add(bottom, BorderLayout.SOUTH);

        tabbedPane.addTab("Appli", appliPanel);
    }
}

