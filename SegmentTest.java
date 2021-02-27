public class SegmentTest {
    
    public static void main(String[] args){
        
        // Segment 1
        double x = -500;
        double y = 500;
        double a = 500;
        double b = -500;
        Point p = new Point(x, y);
        Point q = new Point(a, b);
        Segment pq = new Segment(p, q);
        Segment.print(pq);

        // Segment 2
        double x_ = -150;
        double y_ = -1889.102409;
        double a_ = 210;
        double b_ = 2770.507813;
        Point p_ = new Point(x_, y_);
        Point q_ = new Point(a_, b_);
        Segment pq_ = new Segment(p_, q_);
        Segment.print(pq_);

        // Calcul
        boolean res = Segment.isIntersection(pq, pq_);
        System.out.println(res);
    }
}