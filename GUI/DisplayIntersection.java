package GUI;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayIntersection {

    private static int zoom = 3;

    public static void displayIntersection() {
        Stage stage = new Stage();

        GridPane gridPane = new GridPane();

        ArrayList<Circle> set = Main.getSetCircle();

        for (int i = 0; i < set.size(); i++) {
            Circle circle = set.get(i);
            String posX = String.valueOf(circle.getCenterX() / zoom);
            String posY = String.valueOf(circle.getCenterY() / zoom);
            Text text = new Text(posX + " " + posY);
            gridPane.add(text, 0, i);
        }

        Scene scene = new Scene(gridPane, 400, 300);
        
        stage.setTitle("Intersection Points");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public static void displayIntersectionSweep() {
        
    }
}