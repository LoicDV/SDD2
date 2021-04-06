package logique;

import java.util.ArrayList;

public class Point {
    
    // Variables globales.
    private double x, y;
    private ArrayList<Segment> isUpperOf;
    private ArrayList<Segment> isIntersectionOf;

    // Constructeur.
    /**
     * @param x double.
     * @param y double.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.isUpperOf = new ArrayList<Segment>();
        this.isIntersectionOf = new ArrayList<Segment>();
    }
    
    /**
     * 
     * @param x double.
     * @param y double.
     * @param isUpperOf ArrayList<Segment>.
     * @param isIntersectionOf ArrayList<Segment>.
     */
    public Point(double x, double y, ArrayList<Segment> isUpperOf, ArrayList<Segment> isIntersectionOf) {
        this.x = x;
        this.y = y;
        this.isUpperOf = isUpperOf;
        this.isIntersectionOf = isIntersectionOf;
    }

    // Assesseur/getteur.

    /** 
     * @return double.
     */ 
    public double getX() {
        return this.x;
    }
    
    /** 
     * @return double.
     */
    public double getY() {
        return this.y;
    }
    
    /** 
     * @return ArrayList<Segment>.
     */
    public ArrayList<Segment> getIsUpperOf(){
        return this.isUpperOf;
    }
    
    /** 
     * @return ArrayList<Segment>.
     */
    public ArrayList<Segment> getIsIntersectionOf(){
        return this.isIntersectionOf;
    }
    
    /** 
     * @param new_x double.
     */
    public void setX(double new_x) {
        this.x = new_x;
    }
    
    /** 
     * @param new_y double.
     */
    public void setY(double new_y) {
        this.y = new_y;
    }
    
    /** 
     * @param new_x double.
     * @param new_y double.
     */
    public void setXY(double new_x, double new_y) {
        setX(new_x);
        setY(new_y);
    }
    
    /** 
     * @param ajout Point.
     */
    public void setPoint(Point ajout) {
        this.x = ajout.x;
        this.y = ajout.y;
        this.isUpperOf = ajout.isUpperOf;
        this.isIntersectionOf = ajout.isIntersectionOf;
    }
    
    /** 
     * @param new_list ArrayList<Segment>.
     */
    public void setIsUpperOf(ArrayList<Segment> new_list) {
        this.isUpperOf = new_list;
    }
    
    /** 
     * @param new_list ArrayList<Segment>.
     */
    public void setIsIntersectionOf(ArrayList<Segment> new_list) {
        this.isIntersectionOf = new_list;
    }
    
    /** 
     * @return String.
     * Ecriture de nos points et retourne un string de notre point.
     */
    public String toString() {
        String chaine = this.x + " " + this.y;
        return chaine;
    }
    
    /** 
     * @param p Point.
     * @return boolean.
     * Compare les 2 points et retournent un booleen s'ils sont identiques.
     */ 
    public boolean comparePoint(Point p) {
        if ((this.x == p.x) && (this.y == p.y)) {
            return true;
        }
        return false;
    }
    
    /** 
     * @param p Point.
     * @return boolean.
     * Retourne true si notre point est plus haut ou de meme hauteur mais plus a gauche que celui en argument.
     */
    public boolean isUpper(Point p) {
        boolean verif = false;
        if ((this.getY() > p.getY()) || ((this.getY() == p.getY()) && (this.getX() < p.getX()))) {
            verif = true;
        }
        return verif;
    }

    /**
     * Affiche notre point dans la console.
     */
    public void print() {
        System.out.println(this.toString());
    }
    
    /** 
     * @param p Point.
     * @return boolean.
     */
    public boolean equalPoint(Point p){
        boolean verif = false;
        if (this.getY() == p.getY() && this.getX() == p.getX()){
            verif = true;
        }
        return verif;
    }
}