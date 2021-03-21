package logique;

public class Segment {

    // Variables globales.
    private Point upper, lower;
    
    // Constructeur.
    public Segment(Point p1, Point p2) {
        if (p1.isUpper(p2)) {
            this.upper = p1;
            this.lower = p2;
        }
        else {
            this.upper = p2;
            this.lower = p1;
        }  
    }

    // Assesseur/getteur.
    public Point getUpper() {
        return this.upper;
    }
    
    public Point getLower() {
        return this.lower;
    }

    public void setUpper(Point new_point) {
        if (this.getLower().isUpper(new_point)) {
            this.upper = this.lower;
            this.lower = new_point;
        }
        else {
            this.upper = new_point;
        }
    }
    
    public void setLower(Point new_point) {
        if (this.getUpper().isUpper(new_point)) {
            this.lower = this.upper;
            this.upper = new_point;
        }
        else {
            this.lower = new_point;
        }
    }

    // écriture de nos segments et retourne un string de notre segment.
    public String toString() {
        String chaine = "(" + this.getUpper().toString() + ", "  + this.getLower().toString() + ")";
        return chaine;
    }

    // Calcule la pente de notre segment et retourne un double qui est la pente
    public double getPente() {
        double pente = (this.getUpper().getY() - this.getLower().getY()) / (this.getUpper().getX() - this.getLower().getX());
        return pente;
    }

    // Calcule le parametre p de l'équation y = mx + p et retourne un double qui est le p.
    public double getParam_P() {
        double pente = this.getPente();
        double param_p = this.getUpper().getY() - pente * this.getUpper().getX();
        return param_p;
    }

    // Regarde si le point est dans le segment NON VERTICAL.
    public boolean isIn(Point p) {
        boolean verif = false;
        double pente = this.getPente();
        double param_p = this.getParam_P();
        
        if ((Math.abs(p.getY() - (pente * p.getX() + param_p)) <= 0.00000001)
        && (((this.getLower().getY() <= p.getY()) || (this.getLower().getY() - p.getY()) <= 0.00000001) && ((p.getY() <= this.getUpper().getY()) || (p.getY() - this.getUpper().getY()) <= 0.00000001))
        && ((((this.getLower().getX() <= p.getX()) || (this.getLower().getX() - p.getX()) <= 0.00000001) && ((p.getX() <= this.getUpper().getX())) || (p.getX() - this.getUpper().getX()) <= 0.00000001)
        || (((this.getUpper().getX() <= p.getX()) || (this.getUpper().getX() - p.getX()) <= 0.00000001) && ((p.getX() <= this.getLower().getX())) || (p.getX() - this.getLower().getX()) <= 0.00000001))) {
            verif = true;
        }
        return verif;
    }

    // Regarde si le point est dans le segment VERTICAL.
    public boolean isInVertical(Point p) {
        boolean verif = false;

        if ((Math.abs(p.getX() - this.getLower().getX()) <= 0.00000001) &&
        ((this.getLower().getY() <= p.getY()) || (this.getLower().getY() - p.getY()) <= 0.00000001) && ((p.getY() <= this.getUpper().getY())) || ((p.getY() - this.getUpper().getY()) <= 0.00000001)) {
            verif = true;
        }
        return verif;
    }

    // Calcule l'intersection de nos segments PROLONGES (droites) et le retourne.
    public Point getIntersectionDroite(Segment s) {
        Point point_intersection = new Point(0, 0);
        double s1_pente = this.getPente();
        double s2_pente = s.getPente();
        double s1_param_p = this.getParam_P();
        double s2_param_p = s.getParam_P();

        if (s2_pente == 0) {
            double x = (s2_param_p - s1_param_p) / s1_pente;
            double y = s2_param_p;
            point_intersection.setXY(x, y);
        }

        else {
            double x = (s2_param_p - s1_param_p) / (s1_pente - s2_pente);
            double y = (s1_pente * x) + s1_param_p;
            point_intersection.setXY(x, y);
        }        
        return point_intersection;
    }

    // Regarde si un segment n'est pas un prolongment de l'autre.
    public boolean isIntersectionExtremite(Segment s) {
        boolean verif = false;
        if (this.getUpper().comparePoint(s.getLower()) || this.getLower().comparePoint(s.getUpper())) {
            verif = true;
        }
        return verif;
    }
    
    // Calcule l'intersection entre nos 2 segments (qui est une extremité).
    public Point intersectionExtremite(Segment s) {
        Point point_intersection = new Point(0, 0);
        
        if (this.getUpper().comparePoint(s.getLower())) {
            point_intersection = this.getUpper();
        }

        else {
            point_intersection = s.getUpper();
        }
        
        return point_intersection;
    }

    // Regarde si le segment est vertical ou non.
    public boolean isVertical() {
        boolean verif = false;
        if (this.getUpper().getX() == this.getLower().getX()) {
            verif = true;
        }
        return verif;
    }

    // Teste s'il y a une intersection ou non avec nos segments.
    public boolean isIntersection(Segment s) {
        boolean verif = false;
        boolean isOK = true;
        Point point_intersection_droite = new Point(0, 0);
        
        if (this.isVertical()) {
            isOK = false;
            if (s.isVertical()) {
                if (this.isIntersectionExtremite(s)) {
                    verif = true;
                }
            }

            else {
                point_intersection_droite = this.getIntersectionPointVertical(s, false, point_intersection_droite);
                if ((this.isInVertical(point_intersection_droite)) && s.isIn(point_intersection_droite)) {
                    verif = true;
                }
            }
        }
    
        else if (s.isVertical()) {
            isOK = false;
            point_intersection_droite = s.getIntersectionPointVertical(this, false, point_intersection_droite);
            if ((this.isIn(point_intersection_droite)) && s.isInVertical(point_intersection_droite)) {
                verif = true;
            }
        }

        else {
            double s1_pente = this.getPente();
            double s2_pente = s.getPente();

            if (s1_pente == s2_pente) {
                if (this.isIntersectionExtremite(s)) {
                    verif = true;
                }
            }

            else {
                point_intersection_droite = this.getIntersectionDroite(s);
            }
        }

        if (isOK && (this.isIn(point_intersection_droite)) && (s.isIn(point_intersection_droite))) {
            verif = true;
        }
        return verif;
    }

    // Calcule l'intersection EXISTANTE de nos segments et retourne un objet Point.
    public Point getIntersectionPoint(Segment s) {
        Point point_intersection = new Point(0, 0);

        if (this.isVertical()) {
            point_intersection = this.getIntersectionPointVertical(s, true, point_intersection);
        }

        else if (s.isVertical()) {
            point_intersection = s.getIntersectionPointVertical(this, false, point_intersection);
        }

        else {
            double s1_pente = this.getPente();
            double s2_pente = s.getPente();
            double s1_param_p = this.getParam_P();
            double s2_param_p = s.getParam_P();

            if (s1_pente == s2_pente) {
                point_intersection = this.intersectionExtremite(s);
            }

            else if (s2_pente == 0) {
                double x = (s2_param_p - s1_param_p) / s1_pente;
                double y = s2_param_p;
                point_intersection.setXY(x, y);
            }

            else {
                double x = (s2_param_p - s1_param_p) / (s1_pente - s2_pente);
                double y = (s1_pente * x) + s1_param_p;
                point_intersection.setXY(x, y);
            } 
        }
        return point_intersection;
    }

    // Calcule le point d'intersection entre nos segments dont s1 qui est vertical et retourne un Point.
    public Point getIntersectionPointVertical(Segment s, boolean change, Point point_intersection) {
        
        if (change && s.isVertical()) {
            point_intersection = this.intersectionExtremite(s);
        }

        else {
            double pente = s.getPente();
            double param_p = s.getParam_P();
            double y = pente * this.getUpper().getX() + param_p;
            point_intersection.setXY(this.getUpper().getX(), y);
        }
        return point_intersection;
    }
    
    // Affiche notre segment dans la console.
    public void print() {
        System.out.println(this.toString());
    } 
}