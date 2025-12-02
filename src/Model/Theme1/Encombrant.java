package Model.Theme1;

import Model.Graphe.*;

public class Encombrant {
    private Liaison liaison;  // l’arête où il se trouve

    public Encombrant(Liaison liaison) {
        this.liaison = liaison;
    }

    public Liaison getLiaison() {
        return liaison;
    }
}
