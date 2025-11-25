import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import Model.Graphe.Graphe;
import Model.Graphe.Sommet;
import Model.Algo.Dijkstra;
import Model.Algo.BFS;
import Model.ResultatCommun.Itineraire;

public class Main extends JFrame {

    private JComboBox<String> fileComboBox;
    private JComboBox<String> algoComboBox;
    private JTextField departField;
    private JTextField arriveeField;
    private JTextArea outputArea;
    private JButton runButton;

    public Main() {
        super("Projet TDG - Interface graphique");

        initComponents();
        layoutComponents();
        initListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // centre la fenêtre
    }

    private void initComponents() {
        // Liste des fichiers dans data/test
        fileComboBox = new JComboBox<>(scanTestFiles("data/test"));

        algoComboBox = new JComboBox<>(new String[]{
                "Dijkstra",
                "BFS"
                // DFS 
        });

        departField = new JTextField("0"); // sommet de départ par défaut
        arriveeField = new JTextField("1"); // sommet d'arrivée par défaut

        runButton = new JButton("Lancer");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void layoutComponents() {
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Fichier de graphe :"));
        topPanel.add(fileComboBox);

        topPanel.add(new JLabel("Algorithme :"));
        topPanel.add(algoComboBox);

        topPanel.add(new JLabel("Sommet de départ (id) :"));
        topPanel.add(departField);

        topPanel.add(new JLabel("Sommet d'arrivée (id) :"));
        topPanel.add(arriveeField);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Résultat"));

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(runButton, BorderLayout.SOUTH);
    }

    private void initListeners() {
        runButton.addActionListener(this::onRunClicked);
    }

    private void onRunClicked(ActionEvent e) {
        String fileName = (String) fileComboBox.getSelectedItem();
        String algoName = (String) algoComboBox.getSelectedItem();

        if (fileName == null || fileName.startsWith("(aucun")) {
            JOptionPane.showMessageDialog(this,
                    "Aucun fichier de graphe trouvé dans data/test.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idDepart;
        int idArrivee;

        try {
            idDepart = Integer.parseInt(departField.getText().trim());
            idArrivee = Integer.parseInt(arriveeField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Les ids de départ / arrivée doivent être des entiers.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Chargement du graphe
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
                outputArea.setText(texte);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            outputArea.setText("Erreur : " + ex.getMessage());
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
