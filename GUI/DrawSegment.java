package GUI;

// Imports de base
import java.io.File;
import java.util.ArrayList;
import logique.Point;
import logique.Segment;

// Imports JavaFX
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DrawSegment extends Pane {

    private static int zoom = 3;
    
    public static void draw(File file) {
        ArrayList<Point[]> tab = ReadFile.read(file);
        
        ArrayList<Point[]> containers = Main.getContainers();
        ArrayList<Line> set = Main.getSet();
        
        for (Point[] smallPoints : tab) {
            Line line = new Line(smallPoints[0].getX() * zoom, smallPoints[0].getY() * zoom,
                                 smallPoints[1].getX() * zoom, smallPoints[1].getY() * zoom);
            Main.getPane().getChildren().add(line);
            containers.add(smallPoints);
            set.add(line);
        }
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    public static void addLine(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        if (noDoubleLine(point1X, point1Y, point2X, point2Y)) {
            Line line = new Line(point1X * zoom, point1Y * zoom, point2X * zoom, point2Y * zoom);
            Main.getPane().getChildren().add(line);
            ArrayList<Line> set = Main.getSet();
            set.add(line);
            addContainers(point1X, point1Y, point2X, point2Y);
        }
        else {
            AlertType alertType = AlertType.ERROR;
            Alert alert = new Alert(alertType, "Warning in place");
            alert.getDialogPane().setContentText("You can't add this Segment because you overlap another Segment !");
            alert.getDialogPane().setHeaderText("AddSegment Warning");
            alert.showAndWait();
        }
    }

    public static void addContainers(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        ArrayList<Point[]> containers = Main.getContainers();
        Point[] tab = new Point[]{new Point(point1X, point1Y), new Point(point2X, point2Y)};
        containers.add(tab);
        
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    public static boolean noDoubleLine(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        ArrayList<Point[]> containers = Main.getContainers();
        Segment newSegment = new Segment(new Point(point1X, point1Y), new Point(point2X, point2Y));
        for (Point[] tab : containers) {
            Segment segment = new Segment(new Point(tab[0].getX(), tab[0].getY()), new Point(tab[1].getX(), tab[1].getY()));
            if (segment.doesOverlap(newSegment)) {
                return false;
            }
        }
        return true;
    }

    public static void drawCircle(double posX, double posY, Color red) {
        Circle circle = new Circle(posX * zoom, posY * zoom, 5);
        circle.setFill(red);
        Main.getPane().getChildren().add(circle);
        Main.getSetCircle().add(circle);
    }
}