package logique;

import java.util.ArrayList;

public class Intersections {

    private ArrayList<Segment> intersections;

    public Intersections(){
        this.intersections = new ArrayList<Segment>();
    }

    public Intersections(ArrayList<Segment> intersection){
        this.intersections = intersection;
    }

    
    /** 
     * @return ArrayList<Segment>
     */
    public ArrayList<Segment> getIntersections(){
        return this.intersections;
    }

    
    /** 
     * @param new_list
     */
    public void setIntersections(ArrayList<Segment> new_list){
        this.intersections = new_list;
    }

    
    /** 
     * @return ArrayList<Point>
     */
    public ArrayList<Point> FindIntersections(){
        ArrayList<Point> solutions = new ArrayList<Point>();
        Q treeQ = new Q();
        for (int i = 0; i < this.getIntersections().size(); i++){
            Segment new_segment = this.getIntersections().get(i);
            treeQ.insertQ(new_segment.getUpper(), true, new_segment); // on devrait aussi savoir que new_segment contient son upper
            treeQ.insertQ(new_segment.getLower(), false, new_segment);
        }
        T treeT = new T();
        while (!treeQ.isEmpty()){
            Point nextEvent = treeQ.suppressQMin();
            HandleEventPoint(nextEvent, treeQ, treeT, solutions);
        }
        return solutions;
    }

    
    /** 
     * @param s1
     * @param s2
     * @param p
     * @param h
     * @param treeQ
     */
    public void FindNewEvent(Segment s1, Segment s2, Point p, double h, Q treeQ){
        if (s1.isIntersection(s2)){
            Point new_event = s1.getIntersectionPoint(s2);
            if ((new_event.getY() < h) || (new_event.getY() == h && new_event.getX() > p.getX())) {
                treeQ.insertQ(new_event, false, null);
            }
        }
    }

    
    /** 
     * @param p
     * @param treeQ
     * @param treeT
     * @param solutions
     */
    public void HandleEventPoint(Point p, Q treeQ, T treeT, ArrayList<Point> solutions){
        ArrayList<Segment> uP = p.getIsUpperOf();
        ArrayList<Segment> lP = new ArrayList<Segment>();
        ArrayList<Segment> cP = new ArrayList<Segment>();
        addLowerAndInside(lP, cP, p, treeT);
        int i = 0;
        if (uP.size() + lP.size() + cP.size() > 1){
            while (uP.get(i) != null) {
                p.getIsIntersectionOf().add(uP.get(i));
                i++;
            }
            i = 0;
            while (lP.get(i) != null) {
                p.getIsIntersectionOf().add(lP.get(i));
                i++;
            }
            i = 0;
            while (cP.get(i) != null) {
                p.getIsIntersectionOf().add(cP.get(i));
                i++;
            }
            i = 0;
            solutions.add(p);
        }
        while (lP.get(i) != null) {
            treeT.suppressT(lP.get(i), p.getY());
            i++;
        }
        i = 0;
        while (cP.get(i) != null) {
            treeT.suppressT(cP.get(i), p.getY());
            i++;
        }
        i = 0;
        double h = p.getY() - 1;
        while (uP.get(i) != null) { // on insère à p.getY()-1 pour bien avoir l'ordre en dessous de l'intersection
            treeT.insertT(uP.get(i), h);
            i++;
        }
        i = 0;
        while (cP.get(i) != null) {
            treeT.insertT(cP.get(i), h);
            i++;
        }
        i = 0;
        if (uP.isEmpty()|| cP.isEmpty()){
            Segment s_l = getLeftNeighborPoint(treeT, p);
            Segment s_r = getRightNeighborPoint(treeT, p);
            if (s_l != null && s_r != null){
                FindNewEvent(s_l, s_r, p, p.getY(), treeQ);
            }
        }
        else{
            if (uP.size() > 0){
                Segment s_lm = getLeftMost(treeT, p, uP);
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY());
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, uP);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY());
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
            else {
                Segment s_lm = getLeftMost(treeT, p, cP);
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY());
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, cP);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY());
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
        }
    }

    
    /** 
     * @param lP
     * @param cP
     * @param p
     * @param treeT
     */
    public void addLowerAndInside(ArrayList<Segment> lP,ArrayList<Segment> cP, Point p, T treeT){
        Segment segmentIterable = new Segment(p, p);
        //notons que le segment suivant si il est dans treeT appartient à U(p) et L(p) mais comme il est déjà dans U(p) pas besoin de le rajouter dans L(p)
        Segment segmentMemoire = new Segment(p, p); 
        boolean stop = false;
        while(!stop){
            Segment segmentLeft = getLeftNeighborSegment(treeT, segmentIterable, null, p.getY());
            if (segmentLeft == null || !(segmentLeft.isIn(p))){
                stop = true;
            }
            else{
                if (segmentLeft.getLower().getX() == p.getX() && segmentLeft.getLower().getY() == p.getY()){
                    lP.add(segmentLeft);
                }
                else if (!(segmentLeft.getUpper().getX() == p.getX() && segmentLeft.getUpper().getY() == p.getY())){
                    cP.add(segmentLeft);
                }
                segmentIterable = segmentLeft;
            }
        }
        stop = false;
        segmentIterable = segmentMemoire;
        while(!stop){
            Segment segmentRight = getRightNeighborSegment(treeT, segmentIterable, null, p.getY());
            if (segmentRight == null || !(segmentRight.isIn(p))){
                stop = true;
            }
            else{
                if (segmentRight.getLower().getX() == p.getX() && segmentRight.getLower().getY() == p.getY()){
                    lP.add(segmentRight);
                }
                else if (!(segmentRight.getUpper().getX() == p.getX() && segmentRight.getUpper().getY() == p.getY())){
                    cP.add(segmentRight);
                }
                segmentIterable = segmentRight;
            }
        }
    }

    
    /** 
     * @param treeT
     * @param p
     * @return Segment
     */
    public Segment getLeftNeighborPoint(T treeT, Point p){
        Segment leftNeighbor = new Segment(p, p);
        return getLeftNeighborSegment(treeT, leftNeighbor, null, p.getY());
    }

    
    /** 
     * @param treeT
     * @param p
     * @return Segment
     */
    public Segment getRightNeighborPoint(T treeT, Point p){
        Segment rightNeighbor = new Segment(p, p);
        return getRightNeighborSegment(treeT, rightNeighbor, null, p.getY());
    }

    
    /** 
     * @param treeT
     * @param s
     * @param saved
     * @param h
     * @return Segment
     */
    public Segment getLeftNeighborSegment(T treeT, Segment s, Segment saved, double h){
        if (treeT.isEmpty()){
            return saved;
        }
        else {
            if (treeT.isLeftOrRight(s, h, false)){
                if ((treeT.isLeftOrRight(s, h, true) && treeT.isLeaf()) || treeT.leftIsEmpty()){
                    return saved;
                }
                else if (treeT.getData().equalSegment(s)){
                    return treeT.getLeftAVL().findMax();
                }
                else {
                    return getLeftNeighborSegment((T) treeT.getLeftAVL(), s, saved, h);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.rightIsEmpty()){
                    return saved;
                }
                else {
                    return getLeftNeighborSegment((T)treeT.getRightAVL(), s, saved, h);
                }
            }
        }
    }

    
    /** 
     * @param treeT
     * @param s
     * @param saved
     * @param h
     * @return Segment
     */
    public Segment getRightNeighborSegment(T treeT, Segment s, Segment saved, double h){
        if (treeT.isEmpty()){
            return saved;
        }
        else {
            if (treeT.isLeftOrRight(s, h, true)){
                if ((treeT.isLeftOrRight(s, h, false) && treeT.isLeaf()) || treeT.rightIsEmpty()){
                    return saved;
                }
                else if (treeT.getData().equalSegment(s)){
                    return treeT.getRightAVL().findMin();
                }
                else {
                    return getRightNeighborSegment((T) treeT.getRightAVL(), s, saved, h);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.leftIsEmpty()){
                    return saved;
                }
                else {
                    return getRightNeighborSegment((T)treeT.getLeftAVL(), s, saved, h);
                }
            }
        }
    }

    
    /** 
     * @param treeT
     * @param p
     * @param set
     * @return Segment
     */
    public Segment getLeftMost(T treeT, Point p, ArrayList<Segment> set){
        Segment s = set.get(0);
        if (treeT.isLeftOrRight(s, p.getY(), false)){
            if (treeT.isLeftOrRight(s, p.getY(), true)){
                if (treeT.leftIsEmpty()){
                    return treeT.getData();
                }
                else {
                    return getLeftMost((T) treeT.getLeftAVL(), p, set);
                }
            }
            else {
                return getLeftMost((T) treeT.getLeftAVL(), p, set);
            }
        }
        else {
            return getLeftMost((T) treeT.getRightAVL(), p, set);
        }
    }

    
    /** 
     * @param treeT
     * @param p
     * @param set
     * @return Segment
     */
    public Segment getRightMost(T treeT, Point p, ArrayList<Segment> set){
        Segment s = set.get(0);
        if (treeT.isLeftOrRight(s, p.getY(), true) || (treeT.getData().isHorizontal() && treeT.getData().isIntersection(s) && treeT.getData().getIntersectionPoint(s).equalPoint(p))){
            if (treeT.isLeftOrRight(s, p.getY(), false)){
                if (treeT.rightIsEmpty()){
                    return treeT.getData();
                }
                else {
                    return getRightMost((T) treeT.getRightAVL(), p, set);
                }
            }
            else {
                return getRightMost((T) treeT.getRightAVL(), p, set);
            }
        }
        else {
            return getRightMost((T) treeT.getLeftAVL(), p, set);
        }
    }
}