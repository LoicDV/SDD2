package src.main.java.logique;

import java.util.ArrayList;

/**
 * Sert a manipuler des Objets Point.
 */
public class Point {

    //Variables d'instances.
    //Coordonnees du point.
    private double x, y;
    //Liste dans laquelle on insere les segments dont le point est l'extremite superieure.
    private ArrayList<Segment> isUpperOf;
    //Liste dans laquelle on insere les segments dont le point est l'intersection de ce segment
    //avec au moins un autre segment.
    private ArrayList<Segment> isIntersectionOf;


    //Constructeurs.
    /**
     * Constructeur de la classe Point qui specifie uniquement les coordonnees x et y du point.
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
     * Constructeur de la classe Point qui specifie les coordonnees x et y,
     * la liste isUpperOf et la liste isIntersectionOf du point.
     * @param x double.
     * @param y double.
     * @param isUpperOf ArrayList de Segment.
     * @param isIntersectionOf ArrayList de Segment.
     */
    public Point(double x, double y, ArrayList<Segment> isUpperOf, ArrayList<Segment> isIntersectionOf) {
        this.x = x;
        this.y = y;
        this.isUpperOf = isUpperOf;
        this.isIntersectionOf = isIntersectionOf;
    }


    //Assesseurs et getteurs.
    /**
     * Getteur de l'abscisse du point qui retourne cette abscisse.
     * @return double.
     */
    public double getX() {
        return this.x;
    }


    /**
     * Getteur de l'ordonnee du point qui retourne cette ordonnee.
     * @return double.
     */
    public double getY() {
        return this.y;
    }


    /**
     * Getteur de la liste IsUpperOf du point qui retourne cette liste.
     * @return ArrayList de Segment.
     */
    public ArrayList<Segment> getIsUpperOf(){
        return this.isUpperOf;
    }


    /**
     * Getteur de la liste isIntersectionOf du point qui retourne cette liste.
     * @return ArrayList de Segment.
     */
    public ArrayList<Segment> getIsIntersectionOf(){
        return this.isIntersectionOf;
    }


    /**
     * Assesseur de l'abscisse du point qui remplace cette abscisse par new_x.
     * @param new_x double.
     */
    public void setX(double new_x) {
        this.x = new_x;
    }


    /**
     * Assesseur de l'ordonnee du point qui remplace cette ordonnee par new_y.
     * @param new_y double.
     */
    public void setY(double new_y) {
        this.y = new_y;
    }


    /**
     * Assesseur qui remplaces les coordonnees x et y par new_x et new_y.
     * @param new_x double.
     * @param new_y double.
     */
    public void setXY(double new_x, double new_y) {
        setX(new_x);
        setY(new_y);
    }


    /**
     * Assesseur qui remplace tous les attributs du point par ceux du point ajout.
     * @param ajout Point.
     */
    public void setPoint(Point ajout) {
        this.x = ajout.x;
        this.y = ajout.y;
        this.isUpperOf = ajout.isUpperOf;
        this.isIntersectionOf = ajout.isIntersectionOf;
    }


    /**
     * Assesseur qui remplace la liste isUpperOf du point par new_list.
     * @param new_list ArrayList de Segment.
     */
    public void setIsUpperOf(ArrayList<Segment> new_list){
        this.isUpperOf = new_list;
    }


    /**
     * Assesseur qui remplace la liste isIntersectionOf du point par new_list.
     * @param new_list ArrayList de Segment.
     */
    public void setIsIntersectionOf(ArrayList<Segment> new_list){
        this.isIntersectionOf = new_list;
    }


    /**
     * Ecriture du point en un string sous le format "abscisse du point ordonnee du point"
     * (avec un espace entre l'abscisse et l'ordonnee) et on retourne ce string.
     * @return String.
     */
    public String toString() {
        String chaine = this.x + " " + this.y;
        return chaine;
    }


    /**
     * Retourne true si notre point est plus haut ou de meme hauteur mais plus a gauche que le point p donne
     * en argument et retourne false sinon.
     * @param p Point.
     * @return boolean.
     */
    public boolean isUpper(Point p) {
        boolean verif = false;
        if ((this.getY() > p.getY()) || ((this.getY() == p.getY()) && (this.getX() < p.getX()))) {
            verif = true;
        }
        return verif;
    }


    /**
     * Affiche notre point dans la console sous le format donne par la fonction toString.
     */
    public void print() {
        System.out.println(this.toString());
    }


    /**
     * Compare les deux points (celui sur lequel la fonction est appellee et p) et retourne le booleen true
     * s'ils sont identiques (a une erreur de precision pres) et retourne le booleen false sinon.
     * @param p Point.
     * @return boolean.
     */
    public boolean equalPoint(Point p){
        boolean verif = false;
        if (Math.abs(this.getY() - p.getY()) <= 0.00000001 && Math.abs(this.getX() - p.getX()) <= 0.00000001){
            verif = true;
        }
        return verif;
    }
}