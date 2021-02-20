public class SegmentTest {
    
    public static void main(String[] args){
        double x = 1;
        double y = 2;
        double a = 3;
        double b = 0;
        Point p = new Point(x, y);
        Point q = new Point(a, b);
        Segment pq = new Segment(p, q);
        print(pq);
    }
}