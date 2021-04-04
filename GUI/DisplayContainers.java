package GUI;

import java.util.ArrayList;
import logique.Point;
import logique.Segment;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DisplayContainers extends ScrollPane {

    public void displayContainers() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        this.setContent(grid);
        this.setPrefSize(250, 300);
        ArrayList<Point[]> containers = Main.getContainers();
        int compteur = 0;
        for (Point[] tab : containers) {
            TextField textField = new TextField();
            textField.setPrefSize(248, 50);
            String string = (new Segment(tab[0], tab[1])).toString();
            textField.setText(string);
            textField.setOnMouseClicked(event -> {
                new ChangeSegment(textField.getText());
            });
            grid.add(textField, 0, compteur);
            compteur++;
        }
    }
}