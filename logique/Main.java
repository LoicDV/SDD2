package logique;
public class Main {
    
    public static void main(String[] args) {

        // Arbre.
        T arbre = new T();

        // Segment 1.
        Point p1_1 = new Point(0, 0);
        Point p1_2 = new Point(1, 1);
        Segment s1 = new Segment(p1_1, p1_2);

        // Segment 2.
        Point p2_1 = new Point(2, 0);
        Point p2_2 = new Point(2, 1);
        Segment s2 = new Segment(p2_1, p2_2);

        // Segment 3.
        Point p3_1 = new Point(3, 2);
        Point p3_2 = new Point(4, 1);
        Segment s3 = new Segment(p3_1, p3_2);

        // Segment 4.
        Point p4_1 = new Point(4, 0);
        Point p4_2 = new Point(5, 2);
        Segment s4 = new Segment(p4_1, p4_2);

        // s1 < s2 < s3 < s4.
        // Insertion s1 dans T.
        arbre.insertT(s1, 1);
        arbre.insertT(s2, 1);
        //arbre.insertT(s3, 1);
        //arbre.insertT(s4, 1);

        //arbre.inordre();

        /*
        BSTree arbre = new BSTree();
        arbre.insert(2);
        arbre.insert(3);
        arbre.insert(1);
        arbre.inordre();
        */
    }
}