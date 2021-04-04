package logique;

public class Segment {

    // Variables globales.
    private Point upper, lower;
    
    // Constructeur.
    public Segment(Point p1, Point p2) {
        if (p1.isUpper(p2)) {
            this.upper = p1; 
            this.lower = p2;
            p1.getIsUpperOf().add(this);
        }
        else {
            this.upper = p2;
            this.lower = p1;
            p2.getIsUpperOf().add(this);
            if (p1.equalPoint(p2)){
                p1.getIsUpperOf().add(this);
            }
        }  
    }

    
    /** 
     * @return Point
     */
    // Assesseur/getteur.
    public Point getUpper() {
        return this.upper;
    }
    
    
    /** 
     * @return Point
     */
    public Point getLower() {
        return this.lower;
    }

    
    /** 
     * @param new_point
     */
    public void setUpper(Point new_point) {
        if (this.getLower().isUpper(new_point)) {
            this.upper = this.lower;
            this.lower = new_point;
        }
        else {
            this.upper = new_point;
        }
    }
    
    
    /** 
     * @param new_point
     */
    public void setLower(Point new_point) {
        if (new_point.isUpper(this.getUpper())) {
            this.lower = this.upper;
            this.upper = new_point;
        }
        else {
            this.lower = new_point;
        }
    }

    
    /** 
     * @return String
     */
    // écriture de nos segments et retourne un string de notre segment.
    public String toString() {
        String chaine = this.getUpper().toString() + " "  + this.getLower().toString();
        return chaine;
    }

    
    /** 
     * @return double
     */
    // Calcule la pente de notre segment et retourne un double qui est la pente
    public double getPente() {
        double pente = (this.getUpper().getY() - this.getLower().getY()) / (this.getUpper().getX() - this.getLower().getX());
        return pente;
    }

    
    /** 
     * @return double
     */
    // Calcule le parametre p de l'équation y = mx + p et retourne un double qui est le p.
    public double getParam_P() {
        double pente = this.getPente();
        double param_p = this.getUpper().getY() - pente * this.getUpper().getX();
        return param_p;
    }

    
    /** 
     * @param p
     * @return boolean
     */
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

    
    /** 
     * @param p
     * @return boolean
     */
    // Regarde si le point est dans le segment VERTICAL.
    public boolean isInVertical(Point p) {
        boolean verif = false;

        if ((Math.abs(p.getX() - this.getLower().getX()) <= 0.00000001) &&
        ((this.getLower().getY() <= p.getY()) || (this.getLower().getY() - p.getY() <= 0.00000001)) && 
        ((p.getY() <= this.getUpper().getY()) || (p.getY() - this.getUpper().getY() <= 0.00000001))) {
            verif = true;
        }
        return verif;
    }
    
    
    /** 
     * @param p
     * @return boolean
     */
    public boolean isInHorizontale(Point p) {
        boolean verif = false;
        if ((Math.abs(p.getY() - this.getLower().getY()) <= 0.00000001) &&
        ((this.getUpper().getX() <= p.getX()) || (this.getUpper().getX() - p.getX() <= 0.00000001)) && 
        ((p.getX() <= this.getLower().getX()) || (p.getX() - this.getLower().getX() <= 0.00000001))) {
            verif = true;
        }
        return verif;
    }
    

    
    /** 
     * @param s
     * @return Point
     */
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

    
    /** 
     * @param s
     * @return boolean
     */
    // Regarde si un segment n'est pas un prolongment de l'autre.
    public boolean isIntersectionExtremite(Segment s) {
        boolean verif = false;
        if (this.getUpper().comparePoint(s.getLower()) || this.getLower().comparePoint(s.getUpper()) || this.getUpper().comparePoint(s.getUpper()) || this.getLower().comparePoint(s.getLower())) {
            verif = true;
        }
        return verif;
    }
    
    
    /** 
     * @param s
     * @return Point
     */
    // Calcule l'intersection entre nos 2 segments (qui est une extremité).
    public Point intersectionExtremite(Segment s) {
        Point point_intersection = new Point(0, 0);
        
        if (this.getUpper().comparePoint(s.getLower()) || this.getUpper().comparePoint((s.getUpper()))) {
            point_intersection = this.getUpper();
        }

        else {
            point_intersection = this.getLower();
        }
        
        return point_intersection;
    }

    
    /** 
     * @return boolean
     */
    // Regarde si le segment est vertical ou non.
    public boolean isVertical() {
        boolean verif = false;
        if (this.getUpper().getX() == this.getLower().getX()) {
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @param s
     * @return boolean
     */
    // Teste s'il y a une intersection ou non avec nos segments.
    public boolean isIntersection(Segment s) {
        boolean verif = false;
        Point point_intersection_droite = new Point(0, 0);

        if (this.isHorizontal()){
            if (s.isHorizontal()){
                if (this.isIntersectionExtremite(s)){
                    verif = true;
                }
            }
            else {
                if (s.isVertical()){
                    if ((this.getUpper().getX() <= s.getLower().getX() && s.getLower().getX() <= this.getLower().getX()) &&
                    (s.getLower().getY() <= this.getUpper().getY() && this.getUpper().getY() <= s.getUpper().getY())) {
                        verif = true;
                    }
                }
                else {
                    if (s.getLower().getY() <= this.getUpper().getY() && this.getUpper().getY() <= s.getUpper().getY()){
                        double m = s.getPente();
                        double p = s.getParam_P();
                        double s_x = (this.getUpper().getY() - p) / m;
                        if (this.getUpper().getX() <= s_x && s_x <= this.getLower().getX()) {
                            verif = true;
                        }
                    }
                }
            }
        }
        else if (s.isHorizontal()) {
            if (this.isVertical()){
                if ((s.getUpper().getX() <= this.getLower().getX() && this.getLower().getX() <= s.getLower().getX()) &&
                (this.getLower().getY() <= s.getUpper().getY() && s.getUpper().getY() <= this.getUpper().getY())){
                    verif = true;
                }
            }
            else {
                if (s.getLower().getY() <= this.getUpper().getY() && this.getUpper().getY() <= s.getUpper().getY()){
                    double m = this.getPente();
                    double p = this.getParam_P();
                    double s_x = (s.getUpper().getY() - p) / m;
                    if (s.getUpper().getX() <= s_x && s_x <= s.getLower().getX()) {
                        verif = true;
                    }
                }
            }
        }
        else if (this.isVertical()) {
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
                if ((this.isIn(point_intersection_droite)) && (s.isIn(point_intersection_droite))){
                    verif = true;
                }
            }
        }
        return verif;
    }

    
    /** 
     * @param s
     * @return Point
     */
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

            else if (this.isHorizontal()){
                double x = (s1_param_p - s2_param_p) / s2_pente;
                double y = s1_param_p;
                point_intersection.setXY(x, y);
            }

            else if (s.isHorizontal()) {
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

    
    /** 
     * @param s
     * @param sIsVertical
     * @param point_intersection
     * @return Point
     */
    // Calcule le point d'intersection entre nos segments dont s1 qui est vertical et retourne un Point.
    public Point getIntersectionPointVertical(Segment s, boolean sIsVertical, Point point_intersection) {
        
        if (sIsVertical && s.isVertical()) {
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
    
    
    /** 
     * @param segment
     * @param h
     * @param left
     * @return boolean
     */
    public boolean isLeftOrRight(Segment segment, double h, boolean left) {
        boolean verif = false;
        
        if (this.isHorizontal()){
            if (segment.isHorizontal()){
                if (left){
                    if (this.getLower().getX() <= segment.getUpper().getX()){
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() <= this.getUpper().getX()){
                        verif = true;
                    }
                }
            }
            else if (segment.isVertical()) {
                if (left){
                    if (this.getLower().getX() < segment.getUpper().getX()) {
                        verif = true;
                    }
                }
                else {
                    if (this.getLower().getX() >= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
            }
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h - segment_param_p) / segment_pente;
                if (left) {
                    if (this.getLower().getX() < segment_x){
                        verif = true;
                    }
                }
                else {
                    if (this.getLower().getX() >= segment_x){
                        verif = true; 
                    }
                }
            }
        }
        else if (segment.isHorizontal()) {
            if (this.isVertical()) {
                if (left){
                    if (segment.getLower().getX() >= this.getUpper().getX()) {
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() < this.getUpper().getX()) {
                        verif = true;
                    }
                }
            }
            else {
                double this_pente = this.getPente();
                double this_param_p = this.getParam_P();
                double this_x = (h - this_param_p) / this_pente;
                if (left) {
                    if (segment.getLower().getX() >= this_x){
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() < this_x){
                        verif = true; 
                    }
                }
            }
        }
        else if (this.isVertical()) {
            if (segment.isVertical()) {
                if (left) {
                    if (this.getUpper().getX() <= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
                else {
                    if (this.getUpper().getX() >= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
            }
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h - segment_param_p) / segment_pente;
                double head_x = this.getUpper().getX();
                if (left) {
                    if (head_x <= segment_x) {
                        verif = true;
                    }
                }
                else {
                    if (head_x >= segment_x) {
                        verif = true;
                    }
                }
            }
        }
        
        else if (segment.isVertical()) {
            double head_pente = this.getPente();
            double head_param_p = this.getParam_P();
            double head_x = (h - head_param_p) / head_pente;
            double segment_x = segment.getUpper().getX();
            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }

        else {
            double head_pente = this.getPente();
            double segment_pente = segment.getPente();
            double head_param_p= this.getParam_P();
            double segment_param_p = segment.getParam_P();

            double head_x = (h - head_param_p) / head_pente;
            double segment_x = (h - segment_param_p) / segment_pente;

            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }
        return verif;
    }

    
    /** 
     * @param s
     * @return boolean
     */
    public boolean equalSegment(Segment s){
        boolean verif = false;
        if  (this.getUpper().equalPoint(s.getUpper()) && this.getLower().equalPoint(s.getLower())){
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @return boolean
     */
    public boolean isHorizontal(){
        boolean verif = false;
        if (this.getUpper().getY() == this.getLower().getY()){
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @param s
     * @return boolean
     */
    public boolean doesOverlap(Segment s){
        boolean verif = false;
        if (this.isVertical() && (!this.getUpper().equalPoint(this.getLower()))){
            if (s.isVertical() && (!s.getUpper().equalPoint(s.getLower())) && s.getLower().getX() == this.getLower().getX()
            && (((this.getUpper().getY() > s.getLower().getY()) 
            || this.getLower().getY() < s.getUpper().getY())
            || (s.getUpper().getY() > this.getLower().getY() 
            || s.getLower().getY() < this.getUpper().getY()))){
                verif = true;
            }
        }
        else if (this.isHorizontal() && (!this.getUpper().equalPoint(this.getLower()))) {
            if (s.isHorizontal() && (!s.getUpper().equalPoint(s.getLower())) && s.getLower().getY() == this.getLower().getY()
            && (((this.getUpper().getX() < s.getLower().getX()) 
            || this.getLower().getX() > s.getUpper().getX())
            || (s.getUpper().getX() < this.getLower().getX() 
            || s.getLower().getX() > this.getUpper().getX()))){
                verif = true;
            }
        }
        else {
            if (this.getPente() == s.getPente() && this.getParam_P() == s.getParam_P()){
                if (((this.getUpper().getY() > s.getLower().getY()) 
                || this.getLower().getY() < s.getUpper().getY())
                || (s.getUpper().getY() > this.getLower().getY() 
                || s.getLower().getY() < this.getUpper().getY())) {
                    verif = true;
                }
            }
        }
        return verif;
    }
}