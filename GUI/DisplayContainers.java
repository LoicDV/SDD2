package GUI;

import java.util.ArrayList;
import logique.Point;
import logique.Segment;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class DisplayContainers extends ScrollPane {

    public void displayContainers() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        this.setContent(grid);
        this.setPrefSize(165, 300);
        ArrayList<Point[]> containers = Main.getContainers();
        int compteur = 0;
        for (Point[] tab : containers) {
            Text text = new Text();
            String string = (new Segment(tab[0], tab[1])).toString();
            text.setText(string);
            text.setOnMouseClicked(event -> {
                new ChangeSegment(text.getText());
            });
            grid.add(text, 0, compteur);
            compteur++;
        }
    }
}