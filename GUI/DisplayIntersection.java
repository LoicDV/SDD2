package GUI;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;


public class DisplayIntersection {

    // Variable globale.
    private static int zoom = 3;

    /**
     * Affiche une pop-up pour montrer l'ensemble des points d'intersections
     * ainsi que leurs coordonnees.
     */
    public static void displayIntersection() {
        // Notre fenetre.
        Stage stage = new Stage();

        // Notre zone de texte.
        GridPane gridPane = new GridPane();

        // L'ArrayList qui contient tous nos points.
        ArrayList<Circle> set = Main.getSetCircle();

        // On itere sur l'ArrayList pour prendre tous les cercles, les mettre en texte
        // et les rajouter dans notre zone de texte.
        for (int i = 0; i < set.size(); i++) {
            Circle circle = set.get(i);
            String posX = String.valueOf(circle.getCenterX() / zoom);
            String posY = String.valueOf(circle.getCenterY() / zoom);
            Text text = new Text(posX + " " + posY);
            gridPane.add(text, 0, i);
        }

        // Contenu de la fenetre
        Scene scene = new Scene(gridPane, 400, 300);
        
        // Settings de la fenetre.
        stage.setTitle("Intersection Points");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * 
     */
    public static void displayIntersectionSweep() {
        
    }
}