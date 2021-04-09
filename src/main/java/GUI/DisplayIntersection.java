package src.main.java.GUI;

// Import logique.
import src.main.java.logique.Point;
import src.main.java.logique.Q;

// Import basique.
import java.util.ArrayList;

// Import JavaFX.
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

        // Menu deroulant.
        ScrollPane scrollPane = new ScrollPane();

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

        scrollPane.setContent(gridPane);

        // Contenu de la fenetre
        Scene scene = new Scene(scrollPane, 400, 300);
        
        // Settings de la fenetre.
        stage.setTitle("Intersection Points");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("map_overlay.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Produit la compilation avec la sweepLine
     */
    public static int[] displayIntersectionSweep(ArrayList<Point> setLowerAndUpperPoints, ArrayList<Point> setIntersection, int compteurLowerUpper, int compteurIntersection) {
        
        // Notre sweepLine ainsi que notre pane (pour eviter de faire trop de fois le meme appel/creation).
        Line sweepLine = new Line();

        // Si les 2 listes ne sont pas vides.
        if (setLowerAndUpperPoints.size() != compteurLowerUpper && setIntersection.size() != compteurIntersection) {
            
            // Au debut, pas retirer la sweepLine.
            if ((compteurIntersection + compteurLowerUpper) == 0) {

                if (setLowerAndUpperPoints.get(compteurLowerUpper).equalPoint(setIntersection.get(compteurIntersection))) {
                    
                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);
                    
                    // On incremente les 2 listes car meme point.
                    compteurLowerUpper++;
                    compteurIntersection++;

                    displayIntersection();
                }
                else if (setLowerAndUpperPoints.get(compteurLowerUpper).isUpper(setIntersection.get(compteurIntersection))) {
                
                    LowerAndUpperSweepLine(sweepLine, setLowerAndUpperPoints, compteurLowerUpper);

                    compteurLowerUpper++;
                }
                else {

                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);
                    
                    compteurIntersection++;
                    displayIntersection();
                }

            }

            else {

                // On retire la sweep line.
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                if (setLowerAndUpperPoints.get(compteurLowerUpper).equalPoint(setIntersection.get(compteurIntersection))) {
                    
                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);
                    
                    // On incremente les 2 listes car meme point.
                    compteurLowerUpper++;
                    compteurIntersection++;
                    displayIntersection();
                }
                else if (setLowerAndUpperPoints.get(compteurLowerUpper).isUpper(setIntersection.get(compteurIntersection))) {

                    LowerAndUpperSweepLine(sweepLine, setLowerAndUpperPoints, compteurLowerUpper);

                    compteurLowerUpper++;
                }
                else {

                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);
                    
                    compteurIntersection++;
                    displayIntersection();
                }
            }
        }

        // Quand il n'y a plus de points d'intersections.
        else if (setLowerAndUpperPoints.size() != compteurLowerUpper) {

            // Au debut, pas retirer la sweepLine.
            if (compteurLowerUpper == 0) {
                
                LowerAndUpperSweepLine(sweepLine, setLowerAndUpperPoints, compteurLowerUpper);

                compteurLowerUpper++;
            }
            else {

                // On retire la sweep line.
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1); ////////////////////////

                LowerAndUpperSweepLine(sweepLine, setLowerAndUpperPoints, compteurLowerUpper);

                compteurLowerUpper++;
            }
        }

        // Quand il n'y a plus de points Uppers et Lowers.
        else if (setIntersection.size() != compteurIntersection) {

            // Au debut, pas retirer la sweepLine.
            if (compteurIntersection == 0) {

                // Rajout du point d'intersection.
                intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);

                compteurIntersection++;
                displayIntersection();
            }
            else {

                // On retire la sweep line.
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1); ////////////////////////

                // Rajout du point d'intersection.
                intersectionSweepLine(sweepLine, setIntersection, compteurIntersection);

                compteurIntersection++;
                displayIntersection();
            }
        }
        else {
            Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
        }
        return new int[]{compteurLowerUpper, compteurIntersection};
    }

    /**
     * @param treeQ Q.
     * @param tab Arraylist de Point.
     * Transforme un arbre Q en ArrayList de Point (le premier etant le plus haut et le dernier le plus bas).
     */
    public static void addInordreQ(Q treeQ, ArrayList<Point> tab) {
        if (treeQ != null) {
            if (treeQ.getLeftAVL() != null) {
                addInordreQ((Q) treeQ.getLeftAVL(), tab);
            }
            if (treeQ.getData() != null) {
                tab.add(treeQ.getData());
            }
            if (treeQ.getRightAVL() != null) {
                addInordreQ((Q) treeQ.getRightAVL(), tab);
            }
		}
    }

    public static void intersectionSweepLine(Line sweepLine, ArrayList<Point> setIntersection, int compteurIntersection) {
        // Point d'intersection donc on le marque.
        DrawSegment.drawCircle(setIntersection.get(compteurIntersection).getX(), setIntersection.get(compteurIntersection).getY(), Color.rgb(255, 0, 0));
                    
        // On remet la sweepLine au bon endroit.
        sweepLine.setStartX(setIntersection.get(compteurIntersection).getX() * zoom - 200);
        sweepLine.setEndX(setIntersection.get(compteurIntersection).getX() * zoom + 200);
        sweepLine.setStartY(setIntersection.get(compteurIntersection).getY() * zoom);
        sweepLine.setEndY(setIntersection.get(compteurIntersection).getY() * zoom);
        Main.getPane().getChildren().add(sweepLine);
    }

    public static void LowerAndUpperSweepLine (Line sweepLine, ArrayList<Point> setLowerAndUpperPoints, int compteurLowerUpper) {
        // On remet la sweepLine au bon endroit.
        sweepLine.setStartX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom - 200);
        sweepLine.setEndX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom + 200);
        sweepLine.setStartY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom);
        sweepLine.setEndY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom);
        Main.getPane().getChildren().add(sweepLine);
    }
}