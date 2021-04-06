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

    public static void addLineDouble(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        Line line = new Line(point1X * zoom, point1Y * zoom, point2X * zoom, point2Y * zoom);
        if (noDoubleLine(line, point1X, point1Y, point2X, point2Y, false)) {
            Main.getPane().getChildren().add(line);
            ArrayList<Line> set = Main.getSet();
            set.add(line);
            addContainers(point1X, point1Y, point2X, point2Y);
        }
        else {
            Exception.doubleLineException();
        }
    }
    
    public static void addLineLine(Line line) {
        Main.getPane().getChildren().add(line);
    }

    public static void addContainers(Double point1X, Double point1Y, Double point2X, Double point2Y) {
        ArrayList<Point[]> containers = Main.getContainers();
        Point[] tab = new Point[]{new Point(point1X, point1Y), new Point(point2X, point2Y)};
        containers.add(tab);
        
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    public static boolean noDoubleLine(Line linePane, Double point1X, Double point1Y, Double point2X, Double point2Y, boolean flag) {
        ArrayList<Point[]> containers = Main.getContainers();
        Segment s = convertLineToSegment(linePane);
        Segment newSegment = new Segment(new Point(point1X, point1Y), new Point(point2X, point2Y));
        for (Point[] tab : containers) {
            Segment segment = new Segment(new Point(tab[0].getX(), tab[0].getY()), new Point(tab[1].getX(), tab[1].getY()));
            if (flag && s.equalSegment(segment) && segment.doesOverlap(newSegment)) {
                return false;
            }
            else if (!flag && segment.doesOverlap(newSegment)) {
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

    public static Segment convertLineToSegment(Line line) {
        return new Segment(new Point(line.getStartX(), line.getStartY()), new Point(line.getEndX(), line.getEndY()));
    }
}