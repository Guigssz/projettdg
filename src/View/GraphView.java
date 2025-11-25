import javax.swing.*;
import java.awt.*;

public class GraphView extends JFrame {

    JComboBox<String> fileComboBox;
    JComboBox<String> algoComboBox;
    JTextField departField;
    JTextField arriveeField;
    JTextArea outputArea;
    JButton runButton;

    public GraphView() {
        super("Projet TDG - Interface graphique (MVC)");

        initComponents();
        layoutComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        fileComboBox = new JComboBox<>();
        algoComboBox = new JComboBox<>(new String[]{"Dijkstra", "BFS"});
        departField = new JTextField("0");
        arriveeField = new JTextField("1");
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
}
