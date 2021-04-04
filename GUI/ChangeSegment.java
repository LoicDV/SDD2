package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ChangeSegment {
    
    private int zoom = 3;
    
    public ChangeSegment(String text) {
        start(text);
    }

    public void start(String text) {
        Stage stage = new Stage();

        Button btnChange = new Button("Change");
        Button btnRemove = new Button("Remove");
        Button btnNothing = new Button("Nothing");

        btnChange.setOnAction(event -> {
            stage.close();
        });

        btnRemove.setOnAction(event -> {
            String[] tabText = text.split(" ");
            Line line = new Line(Double.parseDouble(tabText[0]) * zoom,
                                 Double.parseDouble(tabText[1]) * zoom,
                                 Double.parseDouble(tabText[2]) * zoom,
                                 Double.parseDouble(tabText[3]) * zoom);
            ClearPane.removeSegment(line);
            stage.close();
        });

        btnNothing.setOnAction(event -> {
            stage.close();
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(btnChange, btnRemove, btnNothing);
        
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
}