package GUI;

// Import logique.
import logique.*;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Compilation {

    /**
     * @param flag boolean.
     * Objet qui va compiler notre code logique pour trouver les intersections.
     */
    public Compilation(boolean flag) {
        // Réinitialise les cercles.
        noCircle();
        
        // Adaptation car on a besoin d'une ArrayList de Segment et non d'une ArrayList de Point[].
        ArrayList<Point[]> contains = Main.getContainers();
        ArrayList<Segment> newContains = new ArrayList<Segment>(contains.size());
        for (Point[] tab : contains) {
            Point p1 = new Point(tab[0].getX(), tab[0].getY());
            Point p2 = new Point(tab[1].getX(), tab[1].getY());
            Segment segment = new Segment(p1, p2);
            newContains.add(segment);
        }

        // Partie Logique.
        Intersections set = new Intersections(newContains);
        ArrayList<Point> setIntersection = set.FindIntersections();

        // Quel compilation ?
        if (flag) {
            // Simple Run.
            for (Point p : setIntersection) {
                DrawSegment.drawCircle(p.getX(), p.getY(), Color.rgb(255, 0, 0));
            }
            // Cree une pop-up pour avoir les coordonnees des points d'intersection.
            DisplayIntersection.displayIntersection();
        }
        else {
            // Sweep Line.
            DisplayIntersection.displayIntersectionSweep();
        }
    }

    /**
     * Eneleve tous les cercles du dessin.
     */
    public static void noCircle() {
        // On recupere notre zone de dessin.
        Pane pane = Main.getPane();
        
        boolean flag = true;
        // On itere jusqu'à ne plus avoir de cercle.
        while (flag) {
            // Taille de la Pane.
            int index = Main.getSetCircle().size() + Main.getSet().size();
            
            // Test pour savoir s'il y a au moins un segment.
            if (index == 0) {
                flag = false;
            }
            // On regarde si l'objet est un cercle.
            else if (pane.getChildren().get(index - 1) instanceof Circle) {
                pane.getChildren().remove(index - 1);
                Main.getSetCircle().remove(Main.getSetCircle().size() - 1);
            }
            else {
                flag = false;
            }
        }
    }
}