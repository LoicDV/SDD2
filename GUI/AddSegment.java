package GUI;

// Import de base.
import javafx.stage.Stage;
import javafx.scene.Scene;

// Zone de saisie.
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


public class AddSegment extends Stage {

    public AddSegment() {
        start();
    }

    public void start() {

        Stage stage = new Stage();

        // Pour le x1.
        TextField textField_x1 = new TextField();
        textField_x1.setPromptText("x de p1");
        textField_x1.setFocusTraversable(false);
        textField_x1.setPrefSize(100, 50);


        // Pour le y1.
        TextField textField_y1 = new TextField();
        textField_y1.setPromptText("y de p1");
        textField_y1.setFocusTraversable(false);
        textField_y1.setPrefSize(100, 50);


        // Pour le x2.
        TextField textField_x2 = new TextField();
        textField_x2.setPromptText("x de p2");
        textField_x2.setFocusTraversable(false);
        textField_x2.setPrefSize(100, 50);


        // Pour le y2.
        TextField textField_y2 = new TextField();
        textField_y2.setPromptText("y de p2");
        textField_y2.setFocusTraversable(false);      
        textField_y2.setPrefSize(100, 50);

        Button button = new Button("Add");
        button.setOnAction(e -> {
            stage.close();
        });
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(button);
        borderPane.setPrefSize(100, 100);

        VBox layout = new VBox();
        layout.getChildren().addAll(textField_x1, textField_y1, textField_x2, textField_y2, borderPane);

        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Add Segment");
        stage.setResizable(false);
        stage.showAndWait();

        try {
            Double point1X = Double.parseDouble(textField_x1.getText());
            Double point1Y = Double.parseDouble(textField_y1.getText());
            Double point2X = Double.parseDouble(textField_x2.getText());
            Double point2Y = Double.parseDouble(textField_y2.getText());
            DrawSegment.addLineDouble(point1X, point1Y, point2X, point2Y);
        } catch (NumberFormatException e) {
            noAddSegment();
        }
    }

    public static void noAddSegment() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("No numbers in the field !");
        alert.getDialogPane().setHeaderText("Numbers Error");
        alert.showAndWait();
    }
}