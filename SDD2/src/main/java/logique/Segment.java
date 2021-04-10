package src.main.java.logique;

/**
 * Sert a manipuler des Objects Segment.
 */
public class Segment {

    //Variables d'instances.
    //upper est le point qui est l'extremite superieure du segment et lower est le point
    //qui est l'extremite inferieure du segment.
    private Point upper, lower;
    
    //Constructeur.
    /**
     * @param p1 Point.
     * @param p2 Point.
     * Constructeur de la classe Segment qui prend en argument deux points p1 et p2, le plus haut est designe 
     * comme etant le upper (dans ce cas on ajoute le segment a la liste isUpperOf du point) et le plus bas est 
     * designe comme etant le lower.
     */
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
        }  
    }

    //Assesseurs et getteurs.
    /** 
     * @return Point.
     * Getteur du upper du segment qui retourne ce upper.
     */
    public Point getUpper() {
        return this.upper;
    }
    
    
    /** 
     * @return Point.
     * Getteur du lower du segment qui retourne ce lower.
     */
    public Point getLower() {
        return this.lower;
    }

    
    /** 
     * @param new_point Point.
     * Setteur du upper du segment : on remplace le upper par new_point seulement si il est plus haut ou a la 
     * meme hauteur mais a gauche du lower du segment sinon new_point devient le lower et l'ancien lower 
     * devient le upper.
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
     * @param new_point Point.
     * Setteur du lower du segment : on remplace le lower par new_point seulement si il est plus bas ou a la meme 
     * hauteur et a gauche du upper du segment sinon new_point devient le upper et l'ancien upper devient le lower.
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
     * @return String.
     * Retourne une ecriture du segment en un string sous le format "upper lower" ou upper et lower sont ecrit 
     * selon le format precise dans la classe Point (avec un espace entre upper et lower).
     */
    public String toString() {
        String chaine = this.getUpper().toString() + " " + this.getLower().toString();
        return chaine;
    }

    
    /** 
     * @return double.
     * Calcule la pente de notre segment et retourne un double qui est cette pente.
     */
    public double getPente() {
        double pente = (this.getUpper().getY() - this.getLower().getY()) / (this.getUpper().getX() - this.getLower().getX());
        return pente;
    }

    
    /** 
     * @return double.
     * Calcule le parametre p de l'equation y = mx + p et retourne un double qui est ce p.
     */
    public double getParam_P() {
        double pente = this.getPente();
        double param_p = this.getUpper().getY() - pente * this.getUpper().getX();
        return param_p;
    }

    
    /** 
     * @param p Point.
     * @return boolean.
     * Regarde si le point p est dans le segment non vertical et non horizontal avec lequel on appelle la fonction
     * et retourne true si c'est le cas ou bien false sinon.
     */
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
     * @param p Point.
     * @return boolean.
     * Regarde si le point p est dans le segment vertical avec lequel on appelle la fonction et retourne true 
     * si c'est le cas ou bien false sinon.
     */
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
     * @param p Point.
     * @return boolean.
     * Regarde si le point p est dans le segment horizontal avec lequel on appelle la fonction et retourne true 
     * si c'est le cas et false sinon.
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
     * @param s Segment.
     * @return Point.
     * Calcule l'intersection de nos segments (celui avec lequel on appelle la fonction et s), qui sont non 
     * verticaux, prolonges en droite et la retourne (on appelle cette fonction seulement lorsque l'on sait que 
     * ces deux droites ne sont pas paralleles et ont donc bien une intersection).
     */
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
     * @param s Segment.
     * @return boolean.
     * Retourne true si les deux segements (celui avec lequel on appelle la fonction et s) partagent une 
     * extremite commune et retourne false sinon.
     */
    public boolean isIntersectionExtremite(Segment s) {
        boolean verif = false;
        if (this.getUpper().equalPoint(s.getLower()) || this.getLower().equalPoint(s.getUpper()) || this.getUpper().equalPoint(s.getUpper()) || this.getLower().equalPoint(s.getLower())) {
            verif = true;
        }
        return verif;
    }
    
    
    /** 
     * @param s Segment.
     * @return Point.
     * Calcule l'intersection entre nos deux segments (celui avec lequel on appelle la fonction et s). On appelle
     * cette fonction lorsque l'on sait que cette intersection est une extremite commune des deux segments.
     */ 
    public Point intersectionExtremite(Segment s) {
        Point point_intersection = new Point(0, 0);
        
        if (this.getUpper().equalPoint(s.getLower()) || this.getUpper().equalPoint((s.getUpper()))) {
            point_intersection = this.getUpper();
        }

        else {
            point_intersection = this.getLower();
        }
        
        return point_intersection;
    }

    
    /** 
     * @return boolean.
     * Retourne true si le segment avec leqeul on appelle la fonction est vertical et false sinon.
     */
    public boolean isVertical() {
        boolean verif = false;
        if (this.getUpper().getX() == this.getLower().getX()) {
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @param s Segment.
     * @return boolean.
     * Teste s'il y a une intersection entre les deux segments (celui avec lequel on appelle la fonction et s) 
     * et retourne true si c'est le cas ou bien false sinon.
     */
    public boolean isIntersection(Segment s) {
        boolean verif = false;
        Point point_intersection_droite = new Point(0, 0);
        //Cas segment horizontal.
        if (this.isHorizontal()){
            //Cas s est horizontal.
            if (s.isHorizontal()){
                if (this.isIntersectionExtremite(s)){
                    verif = true;
                }
            }
            else {
                //Cas s est Vertical.
                if (s.isVertical()){
                    if ((this.getUpper().getX() <= s.getLower().getX() && s.getLower().getX() <= this.getLower().getX()) &&
                    (s.getLower().getY() <= this.getUpper().getY() && this.getUpper().getY() <= s.getUpper().getY())) {
                        verif = true;
                    }
                }
                //Cas general pour s.
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
        //Cas s est horizontal.
        else if (s.isHorizontal()) {
            //Cas segment vertical.
            if (this.isVertical()){
                if ((s.getUpper().getX() <= this.getLower().getX() && this.getLower().getX() <= s.getLower().getX()) &&
                (this.getLower().getY() <= s.getUpper().getY() && s.getUpper().getY() <= this.getUpper().getY())){
                    verif = true;
                }
            }
            //Cas general de segment.
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
        //Cas segment vertical.
        else if (this.isVertical()) {
            //Cas s est Vertical.
            if (s.isVertical()) {
                if (this.isIntersectionExtremite(s)) {
                    verif = true;
                }
            }
            //Cas general pour s.
            else {
                point_intersection_droite = this.getIntersectionPointVertical(s, false, point_intersection_droite);
                if ((this.isInVertical(point_intersection_droite)) && s.isIn(point_intersection_droite)) {
                    verif = true;
                }
            }
        }
        //Cas s est Vertical et l'autre segment est ni horizontal ni vertical.
        else if (s.isVertical()) {
            point_intersection_droite = s.getIntersectionPointVertical(this, false, point_intersection_droite);
            if ((this.isIn(point_intersection_droite)) && s.isInVertical(point_intersection_droite)) {
                verif = true;
            }
        }
        //Cas general.
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
     * @param s Segment.
     * @return Point.
     * Calcule l'intersection des deux segments (celui avec lequel on appelle la fonction et s) et retourne 
     * un objet Point qui est cette intersection (on appelle cette fonction uniquement si on est certain 
     * qu'il y a une intersection entre les deux segments).
     */
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
     * @param s Segment.
     * @param sIsVertical boolean.
     * @param point_intersection Point.
     * @return Point.
     * Calcule le point d'intersection entre nos segments ou celui avec lequel on appelle la fonction 
     * est vertical (le second segment utilise est le segment s) et retourne un point qui est cette intersection 
     * (on appelle cette fonction uniquement si on est certain qu'il y a une intersection entre les deux segments).
     */
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
    /**
     * Affiche notre segment dans la console sous le format donne par la fonction toString.
     */
    public void print() {
        System.out.println(this.toString());
    }
    
    
    /** 
     * @param s Segment.
     * @return boolean.
     * Retourne true si le segment avec lequel on appelle la fonction est le meme que le segment s 
     * et retourne false sinon.
     */
    public boolean equalSegment(Segment s){
        boolean verif = false;
        if  (this.getUpper().equalPoint(s.getUpper()) && this.getLower().equalPoint(s.getLower())){
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @return boolean.
     * Retourne true si le segment avec lequel on appelle la fonction est horizontal et retourne false sinon.
     */
    public boolean isHorizontal(){
        boolean verif = false;
        if (this.getUpper().getY() == this.getLower().getY()){
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @param s Segment.
     * @return boolean.
     * Retourne true si le segment sur lequel on appelle la fonction se superpose au segment s (s'ils ont plus 
     * qu'un point d'intersection) et retourne false sinon.
     */
    public boolean doesOverlap(Segment s){
        boolean verif = false;
        if (this.isVertical() && (!this.getUpper().equalPoint(this.getLower()))){
            if (s.isVertical() && (!s.getUpper().equalPoint(s.getLower())) && s.getLower().getX() == this.getLower().getX()
            && ((this.getUpper().getY() <= s.getUpper().getY() && this.getUpper().getY() > s.getLower().getY())
            || (this.getLower().getY() < s.getUpper().getY() && s.getUpper().getY() <= this.getUpper().getY())
            || (this.getUpper().getY() <= s.getUpper().getY() && this.getLower().getY() >= s.getLower().getY())
            || (s.getUpper().getY() <= this.getUpper().getY() && s.getLower().getY() >= this.getLower().getY()))){
                verif = true;
            }
        }
        else if (this.isHorizontal() && (!this.getUpper().equalPoint(this.getLower()))) {
            if (s.isHorizontal() && (!s.getUpper().equalPoint(s.getLower())) && s.getLower().getY() == this.getLower().getY()
            && ((this.getUpper().getX() <= s.getUpper().getX() && s.getUpper().getX() < this.getLower().getX())
            || (s.getUpper().getX() <= this.getUpper().getX() && this.getUpper().getX() < s.getLower().getX())
            || (s.getUpper().getX() <= this.getUpper().getX() && s.getLower().getX() >= this.getLower().getX())
            || (this.getUpper().getX() <= s.getUpper().getX() && this.getLower().getX() >= s.getLower().getX()))){
                verif = true;
            }
        }
        else {
            if (this.getPente() == s.getPente() && this.getParam_P() == s.getParam_P()){
                if ((this.getUpper().getY() <= s.getUpper().getY() && this.getUpper().getY() > s.getLower().getY())
                || (this.getLower().getY() < s.getUpper().getY() && s.getUpper().getY() <= this.getUpper().getY())
                || (this.getUpper().getY() <= s.getUpper().getY() && this.getLower().getY() >= s.getLower().getY())
                || (s.getUpper().getY() <= this.getUpper().getY() && s.getLower().getY() >= this.getLower().getY())) {
                    verif = true;
                }
            }
        }
        return verif;
    }
}