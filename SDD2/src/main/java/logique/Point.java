package src.main.java.logique;

import java.util.ArrayList;

/**
 * Sert a manipuler des Objets Point.
 */
public class Point {
    
    //Variables d'instances.
    //Coordonnées du point.
    private double x, y;
    //Liste dans laquelle on insère les segments dont le point est l'extrémité supérieure.
    private ArrayList<Segment> isUpperOf;
    //Liste dans laquelle on insère les segments dont le point est l'intersection de ce segment 
    //avec au moins un autre segment.
    private ArrayList<Segment> isIntersectionOf;


    //Constructeurs.
    /** 
     * @param x double.
     * @param y double.
     * Constructeur de la classe Point qui spécifie uniquement les coordonnées x et y du point.
     */ 
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.isUpperOf = new ArrayList<Segment>();
        this.isIntersectionOf = new ArrayList<Segment>();
    }


    /**  
     * @param x double.
     * @param y double.
     * @param isUpperOf ArrayList de Segment.
     * @param isIntersectionOf ArrayList de Segment.
     * Constructeur de la classe Point qui spécifie les coordonnées x et y, 
     * la liste isUpperOf et la liste isIntersectionOf du point.
     */
    public Point(double x, double y, ArrayList<Segment> isUpperOf, ArrayList<Segment> isIntersectionOf) {
        this.x = x;
        this.y = y;
        this.isUpperOf = isUpperOf;
        this.isIntersectionOf = isIntersectionOf;
    }


    //Assesseurs et getteurs.
    /** 
     * @return double.
     * Getteur de l'abscisse du point qui retourne cette abscisse.
     */
    public double getX() {
        return this.x;
    }
    
    
    /** 
     * @return double.
     * Getteur de l'ordonnee du point qui retourne cette ordonnée.
     */
    public double getY() {
        return this.y;
    }

    
    /** 
     * @return ArrayList de Segment.
     * Getteur de la liste IsUpperOf du point qui retourne cette liste. 
     */
    public ArrayList<Segment> getIsUpperOf(){
        return this.isUpperOf;
    }

    
    /** 
     * @return ArrayList de Segment.
     * Getteur de la liste isIntersectionOf du point qui retourne cette liste.
     */
    public ArrayList<Segment> getIsIntersectionOf(){
        return this.isIntersectionOf;
    }

    
    /** 
     * @param new_x double.
     * Assesseur de l'abscisse du point qui remplace cette abscisse par new_x.
     */
    public void setX(double new_x) {
        this.x = new_x;
    }
    
    
    /** 
     * @param new_y double.
     * Assesseur de l'ordonnée du point qui remplace cette ordonnée par new_y . 
     */
    public void setY(double new_y) {
        this.y = new_y;
    }

    
    /** 
     * @param new_x double.
     * @param new_y double.
     * Assesseur qui remplaces les coordonnées x et y par new_x et new_y.
     */
    public void setXY(double new_x, double new_y) {
        setX(new_x);
        setY(new_y);
    }

    
    /** 
     * @param ajout Point.
     * Assesseur qui remplace tous les attributs du point par ceux du point ajout.
     */
    public void setPoint(Point ajout) {
        this.x = ajout.x;
        this.y = ajout.y;
        this.isUpperOf = ajout.isUpperOf;
        this.isIntersectionOf = ajout.isIntersectionOf;
    }

    
    /** 
     * @param new_list ArrayList de Segment.
     * Assesseur qui remplace la liste isUpperOf du point par new_list.
     */
    public void setIsUpperOf(ArrayList<Segment> new_list){
        this.isUpperOf = new_list;
    }

    
    /** 
     * @param new_list ArrayList de Segment.
     * Assesseur qui remplace la liste isIntersectionOf du point par new_list.
     */
    public void setIsIntersectionOf(ArrayList<Segment> new_list){
        this.isIntersectionOf = new_list;
    }

    
    /** 
     * @return String.
     * Ecriture du point en un string sous le format "abscisse du point ordonnée du point" 
     * (avec un espace entre l'abscisse et l'ordonnée) et on retourne ce string.
     */
    public String toString() {
        String chaine = this.x + " " + this.y;
        return chaine;
    }


    /** 
     * @param p Point.
     * @return boolean.
     * Retourne true si notre point est plus haut ou de même hauteur mais plus à gauche que le point p donné 
     * en argument et retourne false sinon.
     */
    public boolean isUpper(Point p) {
        boolean verif = false;
        if ((this.getY() > p.getY()) || ((this.getY() == p.getY()) && (this.getX() < p.getX()))) {
            verif = true;
        }
        return verif;
    }


    /**
     * Affiche notre point dans la console sous le format donné par la fonction toString.
     */
    public void print() {
        System.out.println(this.toString());
    }

    
    /** 
     * @param p Point.
     * @return boolean.
     * Compare les deux points (celui sur lequel la fonction est appellée et p) et retourne le booléen true 
     * s'ils sont identiques (à une erreur de précision près) et retourne le booléen false sinon.
     */
    public boolean equalPoint(Point p){
        boolean verif = false;
        if (Math.abs(this.getY() - p.getY()) <= 0.00000001 && Math.abs(this.getX() - p.getX()) <= 0.00000001){
            verif = true;
        }
        return verif;
    }
}