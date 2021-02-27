public class Segment {

    // Variables globales.
    private Point upper, lower;
    
    // Constructeur.
    public Segment(Point p1, Point p2) {
        if ((Point.getY(p1) > Point.getY(p2)) || ((Point.getY(p1) == Point.getY(p2)) && (Point.getX(p1) < (Point.getX(p2))))) {
            this.upper = p1;
            this.lower = p2;
        }
        else {
            this.upper = p2;
            this.lower = p1;
        }  
    }

    // Assesseur/getteur.
    public static Point getUpper(Segment s) {
        return s.upper;
    }
    
    public static Point getLower(Segment s) {
        return s.lower;
    }

    public void setUpper(Segment s, Point new_point) {
        if ((Point.getY(getLower(s)) > Point.getY(new_point)) || ((Point.getY(getLower(s)) == Point.getY(new_point)) && (Point.getX(getLower(s)) < (Point.getX(new_point))))) {
            s.upper = s.lower;
            s.lower = new_point;
        }
        else {
            s.upper = new_point;
        }
    }
    
    public void setLower(Segment s, Point new_point) {
        if ((Point.getY(getUpper(s)) < Point.getY(new_point)) || ((Point.getY(getUpper(s)) == Point.getY(new_point)) && (Point.getX(getUpper(s)) > (Point.getX(new_point))))) {
            s.lower = s.upper;
            s.upper = new_point;
        }
        else {
            s.lower = new_point;
        }
    }

    // écriture de nos segments et retourne un string de notre segment.
    public static String toString(Segment s) {
        String chaine = "(" + Point.toString(getUpper(s)) + ", "  + Point.toString(getLower(s)) + ")";
        return chaine;
    }

    // Calcule la pente de notre segment et retourne un double qui est la pente
    public static double getPente(Segment s) {
        double pente = (Point.getY(s.upper) - Point.getY(s.lower)) / (Point.getX(s.upper) - Point.getX(s.lower));
        return pente;
    }

    // Calcule le parametre p de l'équation y = mx + p et retourne un double qui est le p.
    public static double getParam_P(Segment s) {
        double pente = getPente(s);
        double param_p = Point.getY(s.upper) - pente * Point.getX(s.upper);
        return param_p;
    }

    // Regarde si le point est dans le segment NON VERTICAL.
    public static boolean isIn(Segment s, Point p) {
        boolean verif = false;
        double pente = getPente(s);
        double param_p = getParam_P(s);
        
        if ((Math.abs(Point.getY(p) - (pente * Point.getX(p) + param_p)) <= 0.00000001)
        && (((Point.getY(s.lower) <= Point.getY(p)) || (Point.getY(s.lower) - Point.getY(p)) <= 0.00000001) && ((Point.getY(p) <= Point.getY(s.upper)) || (Point.getY(p) - Point.getY(s.upper)) <= 0.00000001))
        && ((((Point.getX(s.lower) <= Point.getX(p)) || (Point.getX(s.lower) - Point.getX(p)) <= 0.00000001) && ((Point.getX(p) <= Point.getX(s.upper))) || (Point.getX(p) - Point.getX(s.upper)) <= 0.00000001)
        || (((Point.getX(s.upper) <= Point.getX(p)) || (Point.getX(s.upper) - Point.getX(p)) <= 0.00000001) && ((Point.getX(p) <= Point.getX(s.lower))) || (Point.getX(p) - Point.getX(s.lower)) <= 0.00000001))) {
            verif = true;
        }
        return verif;
    }

    // Regarde si le point est dans le segment VERTICAL.
    public static boolean isInVertical(Segment s, Point p) {
        boolean verif = false;

        if ((Math.abs(Point.getX(p) - Point.getX(s.lower)) <= 0.00000001) &&
        ((Point.getY(s.lower) <= Point.getY(p)) || (Point.getY(s.lower) - Point.getY(p)) <= 0.00000001) && ((Point.getY(p) <= Point.getY(s.upper))) || ((Point.getY(p) - Point.getY(s.upper)) <= 0.00000001)) {
            verif = true;
        }
        return verif;
    }

    // Calcule l'intersection de nos segments PROLONGES (droites) et le retourne.
    public static Point getIntersectionDroite(Segment s1, Segment s2) {
        Point point_intersection = new Point(0, 0);
        double s1_pente = getPente(s1);
        double s2_pente = getPente(s2);
        double s1_param_p = getParam_P(s1);
        double s2_param_p = getParam_P(s2);

        if (s2_pente == 0) {
            double x = (s2_param_p - s1_param_p) / s1_pente;
            double y = s2_param_p;
            Point.setXY(point_intersection, x, y);
        }

        else {
            double x = (s2_param_p - s1_param_p) / (s1_pente - s2_pente);
            double y = (s1_pente * x) + s1_param_p;
            Point.setXY(point_intersection, x, y);
        }        
        return point_intersection;
    }

    // Regarde si un segment n'est pas un prolongment de l'autre.
    public static boolean isIntersectionExtremite(Segment s1, Segment s2) {
        boolean verif = false;
        if (Point.comparePoint(s1.upper, s2.lower) || Point.comparePoint(s1.lower, s2.upper)) {
            verif = true;
        }
        return verif;
    }
    
    // Calcule l'intersection entre nos 2 segments (qui est une extremité).
    public static Point intersectionExtremite(Segment s1, Segment s2) {
        Point point_intersection = new Point(0, 0);
        
        if (Point.comparePoint(s1.upper, s2.lower)) {
            point_intersection = s1.upper;
        }

        else {
            point_intersection = s2.upper;
        }
        
        return point_intersection;
    }

    // Regarde si le segment est vertical ou non.
    public static boolean isVertical(Segment s) {
        boolean verif = false;
        if (Point.getX(s.upper) == Point.getX(s.lower)) {
            verif = true;
        }
        return verif;
    }

    // Teste s'il y a une intersection ou non avec nos segments.
    public static boolean isIntersection(Segment s1, Segment s2) {
        boolean verif = false;
        boolean isOK = true;
        Point point_intersection_droite = new Point(0, 0);
        
        if (isVertical(s1)) {
            isOK = false;
            if (isVertical(s2)) {
                if (isIntersectionExtremite(s1, s2)) {
                    verif = true;
                }
            }

            else {
                point_intersection_droite = getIntersectionPointVertical(s1, s2, false, point_intersection_droite);
                Point.print(point_intersection_droite);
                if ((isInVertical(s1, point_intersection_droite)) && isIn(s2, point_intersection_droite)) {
                    verif = true;
                }
            }
        }
    
        else if (isVertical(s2)) {
            isOK = false;
            point_intersection_droite = getIntersectionPointVertical(s2, s1, false, point_intersection_droite);
            Point.print(point_intersection_droite);
            if ((isIn(s1, point_intersection_droite)) && isInVertical(s2, point_intersection_droite)) {
                verif = true;
            }
        }

        else {
            double s1_pente = getPente(s1);
            double s2_pente = getPente(s2);

            if (s1_pente == s2_pente) {
                if (isIntersectionExtremite(s1, s2)) {
                    verif = true;
                }
            }

            else {
                point_intersection_droite = getIntersectionDroite(s1, s2);
                Point.print(point_intersection_droite);
            }
        }

        if (isOK && (isIn(s1, point_intersection_droite)) && (isIn(s2, point_intersection_droite))) {
            verif = true;
        }
        return verif;
    }

    // Calcule l'intersection EXISTANTE de nos segments et retourne un objet Point.
    public static Point getIntersectionPoint(Segment s1, Segment s2) {
        Point point_intersection = new Point(0, 0);

        if (isVertical(s1)) {
            point_intersection = getIntersectionPointVertical(s1, s2, true, point_intersection);
        }

        else if (isVertical(s2)) {
            point_intersection = getIntersectionPointVertical(s2, s1, false, point_intersection);
        }

        else {
            double s1_pente = getPente(s1);
            double s2_pente = getPente(s2);
            double s1_param_p = getParam_P(s1);
            double s2_param_p = getParam_P(s2);

            if (s1_pente == s2_pente) {
                point_intersection = intersectionExtremite(s1, s2);
            }
            
            /* Cas Inutile car traité avec le else
            else if (s1_pente == 0) {
                double x = (s1_param_p - s2_param_p) / s2_pente;
                double y = s1_param_p;
                Point.setXY(point_intersection, x, y);
            }
            */

            else if (s2_pente == 0) {
                double x = (s2_param_p - s1_param_p) / s1_pente;
                double y = s2_param_p;
                Point.setXY(point_intersection, x, y);
            }

            else {
                double x = (s2_param_p - s1_param_p) / (s1_pente - s2_pente);
                double y = (s1_pente * x) + s1_param_p;
                Point.setXY(point_intersection, x, y);
            } 
        }
        return point_intersection;
    }

    // Calcule le point d'intersection entre nos segments dont s1 qui est vertical et retourne un Point.
    public static Point getIntersectionPointVertical(Segment s1, Segment s2, boolean change, Point point_intersection) {
        
        if (change && isVertical(s2)) {
            point_intersection = intersectionExtremite(s1, s2);
        }

        else {
            double pente = getPente(s2);
            double param_p = getParam_P(s2);
            double y = pente * Point.getX(s1.upper) + param_p;
            Point.setXY(point_intersection, Point.getX(s1.upper), y);
        }
        return point_intersection;
    }
    
    // Affiche notre segment dans la console.
    public static void print(Segment s) {
        System.out.println(toString(s));
    }
}