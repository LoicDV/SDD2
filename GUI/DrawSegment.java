package GUI;

// Imports de base
import java.io.File;
import java.util.ArrayList;
import logique.Point;
import logique.Segment;

// Imports JavaFX
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.ScrollPane;

public class DrawSegment extends ScrollPane{

    // Variable globale.
    private static int zoom = 3;
    
    /**
     * @param file File.
     * Fonction utilisee lors de l'ouverture d'un fichier.
     * Permet de dessiner chaque segment s'y trouvant.
     */
    public static void draw(File file) {
        // ArrayList contenant tous les segments du fichier.
        ArrayList<Point[]> tab = ReadFile.read(file);
        
        ArrayList<Point[]> containers = Main.getContainers();
        ArrayList<Line> set = Main.getSet();
        
        // On itere sur l'ArrayList pour creer nos segments et les rajouter à la fois à notre ArrayList de segment + la zone de dessin.
        for (Point[] smallPoints : tab) {
            Line line = new Line(smallPoints[0].getX() * zoom, smallPoints[0].getY() * zoom,
                                 smallPoints[1].getX() * zoom, smallPoints[1].getY() * zoom);
            Main.getPane().getChildren().add(line);
            containers.add(smallPoints);
            set.add(line);
        }
        // MAJ du display des segments et de la ScrollPane.
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
        updatScrollPane();
    }

    /**
     * @param point1X double.
     * @param point1Y double.
     * @param point2X double.
     * @param point2Y double.
     * Rajoute un segment à notre zone de dessin.
     */
    public static void addLineDouble(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        // Enleve tout les cercles et tant donne qu'il faudra recompiler pour avoir potentiellement d'autres intersections.
        Compilation.noCircle();
        Line line = new Line(point1X * zoom, point1Y * zoom, point2X * zoom, point2Y * zoom);
        // Check si pas de Overlap. Si non, on rajoute le segment à notre fenetre.
        if (noDoubleLine(line, point1X, point1Y, point2X, point2Y, false)) {
            Main.getPane().getChildren().add(line);
            Main.getSet().add(line);
            // On rajoute les coordonnees dans le display.
            addContainers(point1X, point1Y, point2X, point2Y);
            // MAJ de la ScrollPane.
            updatScrollPane();
        }
        else {
            // Si oui, c'est qu'il y a un overlap et donc une exception est lancée.
            Exception.doubleLineException();
        }
    }

    /**
     * @param point1X douvble.
     * @param point1Y double.
     * @param point2X double.
     * @param point2Y double.
     * Rajoute un tableau Point à notre display.
     */
    public static void addContainers(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        // Le tableau de Point est ajouté au display.
        Point[] tab = new Point[]{new Point(point1X, point1Y), new Point(point2X, point2Y)};
        Main.getContainers().add(tab);
        // MAJ du display.
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    /**
     * @param linePane Line.
     * @param point1X double.
     * @param point1Y double.
     * @param point2X double.
     * @param point2Y double.
     * @param flag boolean
     * @return boolean
     * Check si les Segments ne s'overlap pas.
     * flag = true : on remplace un segment. Donc un segment en moins a regarder.
     * flag = false : on ajoute un segment.
     */
    public static boolean noDoubleLine(Line linePane, Double point1X, Double point1Y, Double point2X, Double point2Y, boolean flag) {
        ArrayList<Point[]> containers = Main.getContainers();
        Segment s = convertLineToSegment(linePane);
        Segment newSegment = new Segment(new Point(point1X, point1Y), new Point(point2X, point2Y));
        boolean verif = true;
        // On itere sur notre ArrayList pour check chaque segment.
        for (Point[] tab : containers) {
            Segment segment = new Segment(new Point(tab[0].getX(), tab[0].getY()), new Point(tab[1].getX(), tab[1].getY()));
            // Partie remplacement.
            if (flag && !s.equalSegment(segment) && segment.doesOverlap(newSegment)) {
                verif = false;
            }
            // Partie Ajout.
            else if (!flag) {
                if (segment.doesOverlap(newSegment)) {
                    verif = false;
                }
            }
        }
        return verif;
    }

    /**
     * @param posX double.
     * @param posY double.
     * @param red Color.
     * Dessine des cercles de couleurs pour les intersections et les stocke dans une ArrayList.
     */
    public static void drawCircle(double posX, double posY, Color red) {
        Circle circle = new Circle(posX * zoom, posY * zoom, 5);
        // Remplis les cercles.
        circle.setFill(red);
        // Ajout du cercle.
        Main.getPane().getChildren().add(circle);
        Main.getSetCircle().add(circle);
    }

    /**
     * @param line Line.
     * @return Segment.
     * Convertit un objet Line en un objet Segment.
     */
    public static Segment convertLineToSegment(Line line) {
        return new Segment(new Point(line.getStartX(), line.getStartY()), new Point(line.getEndX(), line.getEndY()));
    }

    /**
     * Permet de mettre à jour notre ScrollPane.
     */
    private static void updatScrollPane() {
        Main.getScrollPane().setPrefViewportWidth(Main.getPane().getPrefWidth());
        Main.getScrollPane().setPrefViewportHeight(Main.getPane().getPrefHeight());
    }
}