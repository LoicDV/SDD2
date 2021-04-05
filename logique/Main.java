package logique;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Segment> set = new ArrayList<Segment>();

        Segment s1 = new Segment(new Point(200, 200), new Point(1, 1));
        Segment s2 = new Segment(new Point(200, 1), new Point(1, 200));

        set.add(s1);
        set.add(s2);

        Intersections setIntersection = new Intersections(set);
        ArrayList<Point> sol = setIntersection.FindIntersections();

        for (Point p : sol) {
            p.print();
        }
    }
}
