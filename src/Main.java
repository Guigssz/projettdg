import javax.swing.SwingUtilities;
import View.GraphView;
import Controller.GraphController;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphView view = new GraphView();
            new GraphController(view);
            view.setVisible(true);
        });
    }
}



