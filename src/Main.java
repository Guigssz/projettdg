

import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphView view = new GraphView();
            new GraphController(view);
            view.setVisible(true);
        });
    }
}


