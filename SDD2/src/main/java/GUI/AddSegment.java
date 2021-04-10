package src.main.java.GUI;

// Import JavaFX
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * Classe pour ajouter des segments.
 */
public class AddSegment extends Stage {

    /**
     * Objet qui va lancer la fonction start().
     */
    public AddSegment() {
        start();
    }

    /**
     * Cree une fenetre pour rajouter un segment au dessin.
     */
    public void start() {

        // Notre fenêtre
        Stage stage = new Stage();

        // Pour le x1.
        TextField textField_x1 = new TextField();
        textField_x1.setPrefSize(100, 50);
        // Pour mettre notre texte en arriere plan et donner une idee à l'utilisateur
        // ce qu'il doit mettre dans la case.
        textField_x1.setPromptText("x de p1");
        textField_x1.setFocusTraversable(false);

        // Pour le y1.
        TextField textField_y1 = new TextField();
        textField_y1.setPrefSize(100, 50);
        // Pour mettre notre texte en arriere plan et donner une idee à l'utilisateur
        // ce qu'il doit mettre dans la case.
        textField_y1.setPromptText("y de p1");
        textField_y1.setFocusTraversable(false);

        // Pour le x2.
        TextField textField_x2 = new TextField();
        textField_x2.setPrefSize(100, 50);
        // Pour mettre notre texte en arriere plan et donner une idee à l'utilisateur
        // ce qu'il doit mettre dans la case.
        textField_x2.setPromptText("x de p2");
        textField_x2.setFocusTraversable(false);

        // Pour le y2.
        TextField textField_y2 = new TextField();    
        textField_y2.setPrefSize(100, 50);
        // Pour mettre notre texte en arriere plan et donner une idee à l'utilisateur
        // ce qu'il doit mettre dans la case.
        textField_y2.setPromptText("y de p2");
        textField_y2.setFocusTraversable(false);  

        // Notre bouton add.
        Button button = new Button("Add");
        button.setOnAction(e -> {
            addSegment(textField_x1, textField_y1, textField_x2, textField_y2);
            stage.close();
        });
        
        // Notre Pane pour stocker notre bouton et le centrer.
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(button);
        borderPane.setPrefSize(100, 100);

        // Une autre Pane pour mettre un element par ligne.
        VBox layout = new VBox();
        layout.getChildren().addAll(textField_x1, textField_y1, textField_x2, textField_y2, borderPane);

        // Le contenu de la fenetre
        Scene scene = new Scene(layout, 400, 300);

        // Setup de la fenetre.
        stage.setScene(scene);
        stage.setTitle("Add Segment");
        stage.setResizable(false);
        stage.getIcons().add(new Image("settings.jpg"));
        // Empeche l'utilisateur d'utiliser la fenetre principale lorsque celle-ci est a l'ecran.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * @param textField_x1 TestField.
     * @param textField_y1 TextField.
     * @param textField_x2 TextField.
     * @param textField_y2 TextField.
     * Teste si ce qui est mis dans les TextFields sont des nombres et ensuite dessine le segment.
     */
    public static void addSegment(TextField textField_x1, TextField textField_y1, TextField textField_x2, TextField textField_y2) {
        try {
            // Nos coordonnees.
            Double point1X = Double.parseDouble(textField_x1.getText());
            Double point1Y = Double.parseDouble(textField_y1.getText());
            Double point2X = Double.parseDouble(textField_x2.getText());
            Double point2Y = Double.parseDouble(textField_y2.getText());
            // Tracer le segment.
            DrawSegment.addLineDouble(point1X, point1Y, point2X, point2Y);
        } catch (NumberFormatException e) {
            // Empty sting + pas un double dans les cases (int accepte).
            Exception.noAddSegment();
        }
    }
}