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

/**
 * Affiche les intersections presentes dans la zone de dessin.
 */
public class DisplayIntersection {

    // Variable instance.
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
     * @param sweepLine1 Line.
     * @param sweepLine2 Line.
     * @param setLowerAndUpperPoints ArrayList de Point.
     * @param setIntersection ArrayList de Point.
     * @param compteurLowerUpper int.
     * @param compteurIntersection int.
     * @param flag boolean.
     * @return Tableau de int.
     * Produit la compilation avec la sweepLine
     */
    public static int[] displayIntersectionSweep(Line sweepLine1, Line sweepLine2, ArrayList<Point> setLowerAndUpperPoints, ArrayList<Point> setIntersection, int compteurLowerUpper, int compteurIntersection, boolean flag) {

        // Etape par etape.
        if (flag) {
            // Si les 2 listes ne sont pas vides.
            if (setLowerAndUpperPoints.size() != compteurLowerUpper && setIntersection.size() != compteurIntersection) {
                
                // Au debut, pas retirer la sweepLine.
                if ((compteurIntersection + compteurLowerUpper) == 0) {

                    if (setLowerAndUpperPoints.get(compteurLowerUpper).equalPoint(setIntersection.get(compteurIntersection))) {
                        
                        // Rajout du point d'intersection.
                        intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);
                        
                        // On incremente les 2 listes car meme point.
                        compteurLowerUpper++;
                        compteurIntersection++;

                    }
                    else if (setLowerAndUpperPoints.get(compteurLowerUpper).isUpper(setIntersection.get(compteurIntersection))) {
                    
                        // Modifie la sweep Line.
                        LowerAndUpperSweepLine(sweepLine1, sweepLine2, setLowerAndUpperPoints, compteurLowerUpper);

                        compteurLowerUpper++;
                    }
                    else {

                        // Rajout du point d'intersection.
                        intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);
                        
                        compteurIntersection++;
                    }

                }

                else {

                    // On retire la sweep line.
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                    if (setLowerAndUpperPoints.get(compteurLowerUpper).equalPoint(setIntersection.get(compteurIntersection))) {
                        
                        // Rajout du point d'intersection.
                        intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);
                        
                        // On incremente les 2 listes car meme point.
                        compteurLowerUpper++;
                        compteurIntersection++;
                    }
                    else if (setLowerAndUpperPoints.get(compteurLowerUpper).isUpper(setIntersection.get(compteurIntersection))) {

                        // Modifie la sweep Line.
                        LowerAndUpperSweepLine(sweepLine1, sweepLine2, setLowerAndUpperPoints, compteurLowerUpper);

                        compteurLowerUpper++;
                    }
                    else {

                        // Rajout du point d'intersection.
                        intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);
                        
                        compteurIntersection++;
                    }
                }
            }

            // Quand il n'y a plus de points d'intersections.
            else if (setLowerAndUpperPoints.size() != compteurLowerUpper) {

                // Au debut, pas retirer la sweepLine.
                if (compteurLowerUpper == 0) {
                    
                    // Modifie la sweep Line.
                    LowerAndUpperSweepLine(sweepLine1, sweepLine2, setLowerAndUpperPoints, compteurLowerUpper);

                    compteurLowerUpper++;
                }
                else {

                    // On retire la sweep line.
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);

                    // Modifie la sweep Line.
                    LowerAndUpperSweepLine(sweepLine1, sweepLine2, setLowerAndUpperPoints, compteurLowerUpper);

                    compteurLowerUpper++;
                }
            }

            // Quand il n'y a plus de points Uppers et Lowers.
            else if (setIntersection.size() != compteurIntersection) {

                // Au debut, pas retirer la sweepLine.
                if (compteurIntersection == 0) {

                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);

                    compteurIntersection++;
                }
                else {

                    // On retire la sweep line.
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                    Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);

                    // Rajout du point d'intersection.
                    intersectionSweepLine(sweepLine1, sweepLine2, setIntersection, compteurIntersection);

                    compteurIntersection++;
                }
            }
            else {
                // On retire la sweep line.
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
            }
        }
        // Tout d'un coup.
        else {

            // Si on a fait des étapes avant.
            if (compteurIntersection + compteurLowerUpper != 0) {
                // On retire la sweep line.
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
                Main.getPane().getChildren().remove(Main.getPane().getChildren().size()-1);
            }
            // On itere sur les listes jusqu'a ne plus avoir de points a traiter.
            while (setLowerAndUpperPoints.size() != compteurLowerUpper && setIntersection.size() != compteurIntersection) {
                if (setLowerAndUpperPoints.get(compteurLowerUpper).equalPoint(setIntersection.get(compteurIntersection))) {
                        
                    // Rajout du point d'intersection.
                    DrawSegment.drawCircle(setIntersection.get(compteurIntersection).getX(), setIntersection.get(compteurIntersection).getY(), Color.rgb(255, 0, 0));
                    
                    // On incremente les 2 listes car meme point.
                    compteurLowerUpper++;
                    compteurIntersection++;
                }
                else if (setLowerAndUpperPoints.get(compteurLowerUpper).isUpper(setIntersection.get(compteurIntersection))) {
                    
                    compteurLowerUpper++;
                }
                else {

                    // Rajout du point d'intersection.
                    DrawSegment.drawCircle(setIntersection.get(compteurIntersection).getX(), setIntersection.get(compteurIntersection).getY(), Color.rgb(255, 0, 0));
                    
                    compteurIntersection++;
                }
            }
            while (setLowerAndUpperPoints.size() != compteurLowerUpper) {

                    compteurLowerUpper++;
            }
            while (setIntersection.size() != compteurIntersection) {

                // Rajout du point d'intersection.
                DrawSegment.drawCircle(setIntersection.get(compteurIntersection).getX(), setIntersection.get(compteurIntersection).getY(), Color.rgb(255, 0, 0));
                    
                compteurIntersection++;
            }
        }
        return new int[]{compteurLowerUpper, compteurIntersection};
    }

    /**
     * @param treeQ Q.
     * @param tab Arraylist de Point.
     * Transforme un arbre Q en ArrayList de Point (le premier etant le plus haut et le dernier le plus bas).
     */
    public static void addInordreQ(Q treeQ, ArrayList<Point> tab) {
        // Base sur l'affichage inordre des AVL.
        if (treeQ != null) {
            if (treeQ.getLeftAVL() != null) {
                addInordreQ(treeQ.getLeftQ(), tab);
            }
            if (treeQ.getDataQ() != null) {
                tab.add(treeQ.getDataQ());
            }
            if (treeQ.getRightAVL() != null) {
                addInordreQ(treeQ.getRightQ(), tab);
            }
		}
    }

    /**
     * @param sweepLine1 Line.
     * @param sweepLine2 Line.
     * @param setIntersection ArrayList de Point.
     * @param compteurIntersection int.
     * Affiche sur notre fenetre le point d'intersection ainsi que la sweep line.
     */
    public static void intersectionSweepLine(Line sweepLine1, Line sweepLine2, ArrayList<Point> setIntersection, int compteurIntersection) {
        // Point d'intersection donc on le marque.
        DrawSegment.drawCircle(setIntersection.get(compteurIntersection).getX(), setIntersection.get(compteurIntersection).getY(), Color.rgb(255, 0, 0));
                    
        // On remet la sweepLine au bon endroit.
        sweepLine1.setStartX(setIntersection.get(compteurIntersection).getX() * zoom - 200);
        sweepLine1.setEndX(setIntersection.get(compteurIntersection).getX() * zoom + 200);
        sweepLine1.setStartY(setIntersection.get(compteurIntersection).getY() * zoom);
        sweepLine1.setEndY(setIntersection.get(compteurIntersection).getY() * zoom);
        Main.getPane().getChildren().add(sweepLine1);

        sweepLine2.setStartX(setIntersection.get(compteurIntersection).getX() * zoom);
        sweepLine2.setEndX(setIntersection.get(compteurIntersection).getX() * zoom);
        sweepLine2.setStartY(setIntersection.get(compteurIntersection).getY() * zoom - 10);
        sweepLine2.setEndY(setIntersection.get(compteurIntersection).getY() * zoom + 10);
        Main.getPane().getChildren().add(sweepLine2);
    }

    /**
     * @param sweepLine1 Line.
     * @param sweepLine2 Line.
     * @param setLowerAndUpperPoints ArrayList de Point.
     * @param compteurLowerUpper int.
     * Affiche sur notre fenetre un point Lower ou Upper ainsi que la sweep line.
     */
    public static void LowerAndUpperSweepLine (Line sweepLine1, Line sweepLine2, ArrayList<Point> setLowerAndUpperPoints, int compteurLowerUpper) {
        // On remet la sweepLine au bon endroit.
        sweepLine1.setStartX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom - 200);
        sweepLine1.setEndX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom + 200);
        sweepLine1.setStartY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom);
        sweepLine1.setEndY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom);
        Main.getPane().getChildren().add(sweepLine1);

        sweepLine2.setStartX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom);
        sweepLine2.setEndX(setLowerAndUpperPoints.get(compteurLowerUpper).getX() * zoom);
        sweepLine2.setStartY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom - 10);
        sweepLine2.setEndY(setLowerAndUpperPoints.get(compteurLowerUpper).getY() * zoom + 10);
        Main.getPane().getChildren().add(sweepLine2);
    }
}