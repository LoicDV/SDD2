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
            Segment segment = new Segment(tab[0], tab[1]);
            newContains.add(segment);
        }
        Intersections set = new Intersections(newContains);
        ArrayList<Point> setIntersection = set.FindIntersections();
        for (Point p : setIntersection) {
            p.print();
        }
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