public class Point {
    
    // Variables globales
    private double x, y;

    // Constructeur
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static double getX(Point point){
        return point.x;
    }
    
    public static double getY(Point point){
        return point.y;
    }

    public static void setX(Point point, double new_x){
        point.x = new_x;
    }
    
    public static void setY(Point point, double new_y){
        point.y = new_y;
    }

    public static void setXY(Point point, double new_x, double new_y) {
        setX(point, new_x);
        setY(point, new_y);
    }

    public static void setPoint(Point point, Point ajout) {
        point.x = ajout.x;
        point.y = ajout.y;
    }

    public static String toString(Point point) {
        String chaine = "(" + point.x + ", "  + point.y + ")";
        return chaine;
    }

    public static boolean comparePoint(Point p1, Point p2) {
        if ((p1.x == p2.x) && (p1.y == p2.y)) {
            return true;
        }
        return false;
    }

    public static void print(Point point){
        System.out.println(toString(point));
    }
}