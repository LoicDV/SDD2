package src.main.java.logique;

import java.util.ArrayList;

public class Point {
    
    // Variables d'instances
    private double x, y;
    private ArrayList<Segment> isUpperOf;
    private ArrayList<Segment> isIntersectionOf;

    // Constructeur
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.isUpperOf = new ArrayList<Segment>();
        this.isIntersectionOf = new ArrayList<Segment>();
    }
    
    public Point(double x, double y, ArrayList<Segment> isUpperOf, ArrayList<Segment> isIntersectionOf) {
        this.x = x;
        this.y = y;
        this.isUpperOf = isUpperOf;
        this.isIntersectionOf = isIntersectionOf;
    }

    // Assesseur/getteur
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }

    public ArrayList<Segment> getIsUpperOf(){
        return this.isUpperOf;
    }

    public ArrayList<Segment> getIsIntersectionOf(){
        return this.isIntersectionOf;
    }

    public void setX(double new_x) {
        this.x = new_x;
    }
    
    public void setY(double new_y) {
        this.y = new_y;
    }

    public void setXY(double new_x, double new_y) {
        setX(new_x);
        setY(new_y);
    }

    public void setPoint(Point ajout) {
        this.x = ajout.x;
        this.y = ajout.y;
        this.isUpperOf = ajout.isUpperOf;
        this.isIntersectionOf = ajout.isIntersectionOf;
    }

    public void setIsUpperOf(ArrayList<Segment> new_list){
        this.isUpperOf = new_list;
    }

    public void setIsIntersectionOf(ArrayList<Segment> new_list){
        this.isIntersectionOf = new_list;
    }

    // écriture de nos points et retourne un string de notre point.
    public String toString() {
        String chaine = this.x + " " + this.y;
        return chaine;
    }

    // Compare les 2 points et retournent un booléen s'ils sont identiques.
    public boolean comparePoint(Point p) {
        if ((this.x == p.x) && (this.y == p.y)) {
            return true;
        }
        return false;
    }

    // Retourne true si notre point est plus haut ou de même hauteur mais plus à gauche que celui en argument.
    public boolean isUpper(Point p) {
        boolean verif = false;
        if ((this.getY() > p.getY()) || ((this.getY() == p.getY()) && (this.getX() < p.getX()))) {
            verif = true;
        }
        return verif;
    }

    // Affiche notre point dans la console.
    public void print() {
        System.out.println(this.toString());
    }

    public boolean equalPoint(Point p){
        boolean verif = false;
        if (Math.abs(this.getY() - p.getY()) <= 0.00000001 && Math.abs(this.getX() - p.getX()) <= 0.00000001){
            verif = true;
        }
        return verif;
    }
}