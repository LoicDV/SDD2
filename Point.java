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

    public void setX(Point point, double new_x){
        point.x = new_x;
    }
    
    public void setY(Point point, double new_y){
        point.y = new_y;
    }

    public static String toString(Point point) {
        String chaine = "(" + point.x + ", "  + point.y + ")";
        return chaine;
    }

    public static void print(Point point){
        System.out.println(toString(point));
    }

}