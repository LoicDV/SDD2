package GUI;

// Import logique.
import logique.Point;
import logique.Segment;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class DisplayContainers extends ScrollPane {

    /**
     * Affiche sur la fenetre la liste des segments presents dans la zone de dessin.
     */
    public void displayContainers() {
        // Notre GridPane qui va être affiché en écrasant l'ancien.
        GridPane grid = new GridPane();

        // Affiche les cases.
        grid.setGridLinesVisible(true);
        this.setContent(grid);
        this.setPrefSize(165, 300);

        // On regarde l'ArrayList de tous nos segments.
        ArrayList<Point[]> containers = Main.getContainers();
        int compteur = 0;
        
        // On itere sur chaque element de cette ArrayList pour les transformer
        // en texte et les afficher dans la GridPane.
        for (Point[] tab : containers) {
            Text text = new Text();
            String string = (new Segment(tab[0], tab[1])).toString();
            text.setText(string);
            
            // Permet de rendre la case clickable et permettre le changement de ce segment.
            text.setOnMouseClicked(event -> {
                new ChangeSegment(text.getText());
            });

            grid.add(text, 0, compteur);
            compteur++;
        }
    }
}