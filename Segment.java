public class Segment {

    // Variables globales
    private Point upper, lower;
    
    // Constructeur
    public Segment(Point p1, Point p2){
        if ((Point.getY(p1) > Point.getY(p2)) || ((Point.getY(p1) == Point.getY(p2)) && (Point.getX(p1) < (Point.getX(p2))))) {
            this.upper = p1;
            this.lower = p2;
        }
        else {
            this.upper = p2;
            this.lower = p1;
        }  
    }

    // Assesseur/getteur
    public static Point getUpper(Segment s){
        return s.upper;
    }
    
    public static Point getLower(Segment s){
        return s.lower;
    }

    public void setUpper(Segment s, Point new_point){
        if ((Point.getY(getLower(s)) > Point.getY(new_point)) || ((Point.getY(getLower(s)) == Point.getY(new_point)) && (Point.getX(getLower(s)) < (Point.getX(new_point))))) {
            s.upper = s.lower;
            s.lower = new_point;
        }
        else {
            s.upper = new_point;
        }
    }
    
    public void setLower(Segment s, Point new_point){
        if ((Point.getY(getUpper(s)) < Point.getY(new_point)) || ((Point.getY(getUpper(s)) == Point.getY(new_point)) && (Point.getX(getUpper(s)) > (Point.getX(new_point))))) {
            s.lower = s.upper;
            s.upper = new_point;
        }
        else {
            s.lower = new_point;
        }
    }

    public static String toString(Segment s) {
        String chaine = "(" + Point.toString(getUpper(s)) + ", "  + Point.toString(getLower(s)) + ")";
        return chaine;
    }

    public static void print(Segment s){
        System.out.println(toString(s));
    }
}