package src.main.java.GUI;

// Import de la logique.
import src.main.java.logique.Point;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ClearPane {

    /**
     * Objet qui va completement remettre a zero le programme.
     */
    public ClearPane() {
        // On recupere nos ArrayList.
        ArrayList<Point[]> containers = Main.getContainers();
        ArrayList<Circle> setCircle = Main.getSetCircle();
        ArrayList<Line> set = Main.getSet();
        
        // Notre valeur pour iterer.
        int lengthCircle = setCircle.size();
        int lengthSegment = containers.size();
        
        // Partie effacement des donnees.
        for (int i = 0; i < lengthCircle; i++) {
            setCircle.remove(i);
        }
        for (int i = 0; i < lengthSegment; i++) {
            containers.remove(0);
            set.remove(0);
        }
        Main.getPane().getChildren().clear();

        // On enleve le fichier du programme pour la remise a zero.
        Main.setFile(null);

        // On remet a jour 
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    /**
     * @param line Line.
     * Retire un segment de notre zone de dessin.
     */
    public static void removeSegment(Line line) {
        // On retire tous les cercles.
        Compilation.noCircle();
        
        // Notre ArrayList de ligne
        ArrayList<Line> set = Main.getSet();
        
        // On boucle sur une ArrayList pour trouver le segment qu'on doit retirer.
        for (int i = 0; i < set.size(); i++) {
            // On check pour trouver la ligne qu'on veut retirer.
            
            if (checkEquals(set.get(i), line)) {
                // On procede a l'effacement complete de la donnee
                Main.getContainers().remove(i);
                Node object = Main.getPane().getChildren().get(i);
                Main.getPane().getChildren().remove(object);
                set.remove(i);
                // on update notre liste de segment.
                DisplayContainers grid = Main.getDisplayContainers();
                grid.displayContainers();
            }
        }
    }

    /**
     * @param line1 Line.
     * @param line2 Line.
     * @return boolean.
     * Regarde si 2 lignes sont équivalentes ou non.
     */
    public static boolean checkEquals(Line line1, Line line2) {
        // Verifie avec une certaine erreur de precision. (10^-8).
        if (((Math.abs(line1.getStartX() - line2.getStartX()) <= 0.00000001) &&
            (Math.abs(line1.getStartY() - line2.getStartY()) <= 0.00000001) &&
            (Math.abs(line1.getEndX() - line2.getEndX()) <= 0.00000001) &&
            (Math.abs(line1.getEndY() - line2.getEndY()) <= 0.00000001)) ||
            ((Math.abs(line1.getStartX() - line2.getEndX()) <= 0.00000001) &&
            (Math.abs(line1.getStartY() - line2.getEndY()) <= 0.00000001) &&
            (Math.abs(line1.getEndX() - line2.getStartX()) <= 0.00000001) &&
            (Math.abs(line1.getEndY() - line2.getStartY()) <= 0.00000001))) {
            return true;
        }
        else {
            return false;
        }
    }
}