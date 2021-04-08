package src.main.java.GUI;

// Import logique.
import src.main.java.logique.Point;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;

public class ChangeSegment {
    
    // Variables globales.
    private static int zoom = 3;
    private static Stage stage;
    
    /**
     * @param text String.
     * Object qui va lancer la fonction start().
     */
    public ChangeSegment(String text) {
        start(text);
    }

    
    /** 
     * @param text String.
     * Cette fonction va creer une fenetre possedant 3 boutons :
     *      Change : permet de modifier un segment.
     *      Remove : retire un segment.
     *      Nothing : Ne fais rien.
     */
    public static void start(String text) {
        // Notre fenetre.
        stage = new Stage();

        // Nos 3 boutons.
        Button btnChange = new Button("Change");
        Button btnRemove = new Button("Remove");
        Button btnNothing = new Button("Nothing");
        btnChange.setPrefSize(175, 50);
        btnRemove.setPrefSize(175, 50);
        btnNothing.setPrefSize(175, 50);


        String[] tabText = text.split(" ");
        // Notre Segment.
        Line line = new Line(Double.parseDouble(tabText[0]) * zoom,
                             Double.parseDouble(tabText[1]) * zoom,
                             Double.parseDouble(tabText[2]) * zoom,
                             Double.parseDouble(tabText[3]) * zoom);

        // Différentes action pour les boutons.
        btnChange.setOnAction(event -> {
            stage.close();
            changeSegment(line);
        });
        btnRemove.setOnAction(event -> {
            stage.close();
            ClearPane.removeSegment(line);
        });
        btnNothing.setOnAction(event -> {
            stage.close();
        });

        // Notre Pane pour mettre les boutons en 1 ligne.
        HBox layout = new HBox(3);
        layout.getChildren().addAll(btnChange, btnRemove, btnNothing);
        layout.setSpacing(50);
        
        // Le contenu de la fenetre
        Scene scene = new Scene(layout, 325, 50);

        // Setup de la fenetre.
        stage.setScene(scene);
        stage.setTitle("Effect on a segment");
        stage.setResizable(false);
        stage.getIcons().add(new Image("settings.jpg"));
        // Empeche l'utilisateur d'utiliser la fenetre principale lorsque celle-ci est a l'ecran.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    
    /** 
     * @param line
     */
    public static void changeSegment(Line line) {

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

        // Notre bouton change.
        Button button = new Button("Change");
        button.setOnAction(e -> {
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
        stage.setTitle("Change Segment");
        stage.setResizable(false);
        stage.getIcons().add(new Image("settings.jpg"));
        stage.showAndWait();

        try {
            // Nos coordonnees.
            Double point1X = Double.parseDouble(textField_x1.getText());
            Double point1Y = Double.parseDouble(textField_y1.getText());
            Double point2X = Double.parseDouble(textField_x2.getText());
            Double point2Y = Double.parseDouble(textField_y2.getText());
            
            // Check pour savoir si pas de 'overlap'.
            if (DrawSegment.noDoubleLine(line, point1X, point1Y, point2X, point2Y, true)) {
                coordSegment(line, point1X, point1Y, point2X, point2Y);
            }
            
            else {
                // Exception car il y a un 'overlap'.
                Exception.doubleLineException();
            }

        } catch (NumberFormatException e) {
            // Empty sting + pas un double dans les cases (int accepte).
            Exception.noAddSegment();
        }
    }

    
    /** 
     * @param line Line.
     * @param point1X double.
     * @param point1Y double.
     * @param point2X double.
     * @param point2Y double.
     * Rajoute notre segment au dessin en supprimant la ligne selectionnee precedemment.
     */
    public static void coordSegment(Line line, Double point1X, Double point1Y, Double point2X, Double point2Y) {
        // On cree le nouveau segment qui va etre ajoute.
        Line newLine = new Line(point1X * zoom, point1Y * zoom, point2X * zoom, point2Y * zoom);
        
        // On recupere nos objets de notre Main.
        ArrayList<Line> set = Main.getSet();
        ArrayList<Point[]> containers = Main.getContainers();
        DisplayContainers grid = Main.getDisplayContainers();
        Pane pane = Main.getPane();
        
        // On boucle sur une ArrayList pour trouver le segment qu'on doit remplacer.
        for (int i = 0; i < set.size(); i++) {
            
            // Check si les segments sont identiques.
            if (ClearPane.checkEquals(line, set.get(i))) {
                
                // Remplacement total du segment.
                set.set(i, newLine);
                containers.set(i, new Point[]{new Point(point1X, point1Y), new Point(point2X, point2Y)});
                pane.getChildren().remove(i);
                pane.getChildren().add(i, newLine);;
                grid.displayContainers();
            }
        }
    }
}