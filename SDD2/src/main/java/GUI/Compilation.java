package src.main.java.GUI;

// Import logique.
import src.main.java.logique.*;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Permet de compiler le programme pour trouver les points d'intersections.
 */
public class Compilation {

    // Variables instances.
    // Pour iterer sur nos ArrayLists. (Sweep Line).
    private int compteurLowerUpper = 0;
    private int compteurIntersection = 0;
    
    /**
     * Objet qui va compiler notre code logique pour trouver les intersections.
     * @param flag boolean.
     */
    public Compilation(boolean flag) {
        // Reinitialise les cercles.
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
            Line sweepLine1 = new Line();
            sweepLine1.setStroke(Color.rgb(0, 190, 0));
            Line sweepLine2 = new Line();
            sweepLine2.setStroke(Color.rgb(0, 190, 0));

            // Ensemble des Uppers et Lowers.
            Q treeQ = set.constructQ();
            ArrayList<Point> setLowerAndUpperPoints = new ArrayList<Point>();
            DisplayIntersection.addInordreQ(treeQ, setLowerAndUpperPoints);

            // Fenetre pour faire avancer les etapes.
            Stage stage = new Stage();

            // Zone pour mettre notre texte qu'on va creer.
            GridPane gridPane = new GridPane();

            // Nos 2 boutons.
            Button btnNextStep = new Button(">");
            Button btnAllStep = new Button(">>>");
            btnNextStep.setPrefSize(136, 50);
            btnAllStep.setPrefSize(136, 50);
            
            // Differentes action pour les boutons.
            btnNextStep.setOnAction(event -> {
                // Si pas de segments ni d'intersection.
                if ((setLowerAndUpperPoints.size() == 0)) {
                    stage.close();
                }
                // Continue notre algo pour la sweep line.
                else if ((setLowerAndUpperPoints.size() + setIntersection.size()) != (compteurLowerUpper + compteurIntersection)) {
                    int[] tab = DisplayIntersection.displayIntersectionSweep(sweepLine1, sweepLine2, setLowerAndUpperPoints, setIntersection, compteurLowerUpper, compteurIntersection, true);
                    // Si on a eu une intersection, afficher le point.
                    if (compteurIntersection != tab[1]) {
                        Text text = new Text(setIntersection.get(compteurIntersection).toString());
                        gridPane.add(text, 0, compteurIntersection);
                    }
                    compteurLowerUpper = tab[0];
                    compteurIntersection = tab[1];
                }
                // Fin de l'algo donc retirer la sweep line.
                else {
                    DisplayIntersection.displayIntersectionSweep(sweepLine1, sweepLine2, setLowerAndUpperPoints, setIntersection, compteurLowerUpper, compteurIntersection, true);
                    stage.close();
                    if (setIntersection.size() != 0 ) {
                        DisplayIntersection.displayIntersection();
                    }
                }
            });
            btnAllStep.setOnAction(event -> {
                // Si pas de segments ni d'intersection.
                if ((setLowerAndUpperPoints.size() == 0)) {
                    stage.close();
                }
                else {
                    // On execute l'algo sans afficher les points d'intersections (juste dessiner).
                    if ((setLowerAndUpperPoints.size() + setIntersection.size()) != (compteurLowerUpper + compteurIntersection)) {
                        DisplayIntersection.displayIntersectionSweep(sweepLine1, sweepLine2, setLowerAndUpperPoints, setIntersection, compteurLowerUpper, compteurIntersection, false);
                    }
                    stage.close();
                    // S'il y a au moins un point d'intersection, on la montre.
                    if (setIntersection.size() != 0 ) {
                        DisplayIntersection.displayIntersection();
                    }
                }
            });

            // Notre Pane pour mettre les boutons en 1 ligne.
            HBox layout = new HBox(2);
            layout.getChildren().addAll(btnNextStep, btnAllStep);
            layout.setSpacing(50);

            // Rajouter un barre de defilement.
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(gridPane);

            // La Pane qui regroupe les boutons et le texte.
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(scrollPane);
            borderPane.setTop(layout);

            // Le contenu de la fenetre
            Scene scene = new Scene(borderPane, 325, 150);

            // Empeche de garder la sweep Line si on quitte en cours d'execution.
            stage.setOnCloseRequest(event -> {
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size() - 1);
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size() - 1);
            });
            
            // Setup de la fenetre.
            stage.setScene(scene);
            stage.setTitle("Sweep Line");
            stage.setResizable(false);
            stage.getIcons().add(new Image("rightArrow.jpg"));
            // Empeche l'utilisateur d'utiliser la fenetre principale lorsque celle-ci est a l'ecran.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            
        }
    }

    /**
     * Eneleve tous les cercles du dessin.
     */
    public static void noCircle() {
        // On recupere notre zone de dessin.
        Pane pane = Main.getPane();
        
        boolean flag = true;
        // On itere jusqu'a ne plus avoir de cercle.
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