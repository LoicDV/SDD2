package GUI;

import java.util.ArrayList;

import logique.*;
import javafx.scene.paint.Color;

public class Compilation {

    public Compilation(boolean flag) {
        Main.setSetCircle();
        ArrayList<Point[]> contains = Main.getContainers();
        ArrayList<Segment> newContains = new ArrayList<Segment>(contains.size());
        for (Point[] tab : contains) {
            Point p1 = new Point(tab[0].getX(), tab[0].getY());
            Point p2 = new Point(tab[1].getX(), tab[1].getY());
            Segment segment = new Segment(p1, p2);
            newContains.add(segment);
        }
        Intersections set = new Intersections(newContains);
        ArrayList<Point> setIntersection = set.FindIntersections();
        
        if (!flag) {
            // Simple Run.
            for (Point p : setIntersection) {
                DrawSegment.drawCircle(p.getX(), p.getY(), Color.rgb(255, 0, 0));
            }
        }
        else {
            // Sweep Line.
            
            for (Point p : setIntersection) {
                DrawSegment.drawCircle(p.getX(), p.getY(), Color.rgb(255, 0, 0));
            }
        }
    }
}