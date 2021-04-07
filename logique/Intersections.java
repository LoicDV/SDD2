package logique;

import java.util.ArrayList;

public class Intersections {

    private ArrayList<Segment> intersections;
    public double compteur = 1;

    public Intersections(){
        this.intersections = new ArrayList<Segment>();
    }

    public Intersections(ArrayList<Segment> intersection){
        this.intersections = intersection;
    }

    public ArrayList<Segment> getIntersections(){
        return this.intersections;
    }

    public void setIntersections(ArrayList<Segment> new_list){
        this.intersections = new_list;
    }

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
            compteur++;
        }
        return solutions;
    }

    public void FindNewEvent(Segment s1, Segment s2, Point p, double h, Q treeQ){
        if (s1.isIntersection(s2)){
            Point new_event = s1.getIntersectionPoint(s2);
            if ((new_event.getY() < h) || (new_event.getY() == h && new_event.getX() > p.getX())) {
                treeQ.insertQ(new_event, false, null);
            }
        }
    }

    public void HandleEventPoint(Point p, Q treeQ, T treeT, ArrayList<Point> solutions){
        ArrayList<Segment> uP = p.getIsUpperOf();
        ArrayList<Segment> lP = new ArrayList<Segment>();
        ArrayList<Segment> cP = new ArrayList<Segment>();
        addLowerAndInside(lP, cP, p, treeT);
        if (uP.size() + lP.size() + cP.size() >= 2){
            for (int i = 0; i< uP.size(); i++) {
                p.getIsIntersectionOf().add(uP.get(i));
            }
            for (int i = 0; i< lP.size(); i++) {
                p.getIsIntersectionOf().add(lP.get(i));
            }
            for (int i = 0; i< cP.size(); i++) {
                p.getIsIntersectionOf().add(cP.get(i));
            }
            solutions.add(p);
        }
        for (int i = 0; i< lP.size(); i++) {
            treeT.suppressT(lP.get(i), p.getY());
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.suppressT(cP.get(i), p.getY());
        }
        double h = p.getY() - 0.001;
        for (int i = 0; i< uP.size(); i++) { // on insère à hauteur h pour bien avoir l'ordre en dessous de l'intersection
            treeT.insertT(uP.get(i), h);
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.insertT(cP.get(i), h);
        }
        if (uP.isEmpty() && cP.isEmpty()){
            Segment s_l = getLeftNeighborPoint(treeT, p);
            Segment s_r = getRightNeighborPoint(treeT, p);
            if (s_l != null && s_r != null){
                FindNewEvent(s_l, s_r, p, p.getY(), treeQ);
            }
        }
        else{
            if (uP.size() > 0){
                Segment s_lm = getLeftMost(treeT, p, uP);
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY(), treeT.findMin().equalSegment(s_lm), true);
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, uP);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY(), treeT.findMax().equalSegment(s_rm), true);
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
            else {
                Segment s_lm = getLeftMost(treeT, p, cP);
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY(), treeT.findMin().equalSegment(s_lm), true);
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, cP);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY(), treeT.findMax().equalSegment(s_rm),true);
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
        }
    }

    public void addLowerAndInside(ArrayList<Segment> lP,ArrayList<Segment> cP, Point p, T treeT){
        Point p1 = new Point(p.getX(), p.getY());
        Point p2 = new Point(p.getX(), p.getY() - 1);
        Segment segmentIterable = new Segment(p1, p2);// on utilise p1 car sinon on rajouterait segment iterable aux upper de p
        //notons que le segment suivant si il est dans treeT appartient à U(p) et L(p) mais comme il est déjà dans U(p) pas besoin de le rajouter dans L(p)
        Segment segmentMemoire = new Segment(p1, p2); 
        boolean stop = false;
        while(!stop){
            Segment segmentLeft = getLeftNeighborSegment(treeT, segmentIterable, null, p.getY(), treeT.isEmpty() || treeT.findMin().equalSegment(segmentIterable), false);
            if (segmentLeft == null || !(segmentLeft.isIn(p))){
                stop = true;
            }
            else {
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
            Segment segmentRight = getRightNeighborSegment(treeT, segmentIterable, null, p.getY(), treeT.isEmpty() || treeT.findMax().equalSegment(segmentIterable), false);
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

    public Segment getLeftNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment leftNeighbor = new Segment(p1, p1);
        return getLeftNeighborSegment(treeT, leftNeighbor, null, p.getY(), false, false);
    }

    public Segment getRightNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment rightNeighbor = new Segment(p1, p1);
        return getRightNeighborSegment(treeT, rightNeighbor, null, p.getY(), false, false);
    }

    public Segment getLeftNeighborSegment(T treeT, Segment s, Segment saved, double h, boolean sIsMin, boolean justInsert){
        if (treeT.isEmpty() || sIsMin || (treeT.isLeaf() && treeT.getData().equalSegment(s))){
            return saved;
        }
        else if (treeT.getData().equalSegment(s) && !treeT.leftIsEmpty()){
            if (treeT.getLeftT().isLeaf()){
                return saved;
            }
            else{
                T treeTLeft = treeT.getLeftT();
                while (!treeTLeft.getRightT().getData().equalSegment(s)){
                    treeTLeft = treeTLeft.getRightT();
                }
                return treeTLeft.getLeftT().findMax();
            }
        }
        else {
            if (treeT.isLeftOrRight(s, h, false) && !(treeT.isLeftOrRight(s, h, false) && treeT.isLeftOrRight(s, h, true) && treeT.isLeftOrRight(s, h + 0.001, !justInsert))){
                if ((treeT.isLeftOrRight(s, h, true) && treeT.isLeaf()) || treeT.leftIsEmpty()){
                    return saved;
                }
                else if ((treeT.getLeftT().isLeftOrRight(s, h, true) && !( treeT.getLeftT().isLeftOrRight(s, h, false)) && !(treeT.getLeftT().getData().equalSegment(s)))
                || ( treeT.getLeftT().isLeftOrRight(s, h, true) && treeT.getLeftT().isLeftOrRight(s, h, false) && !(treeT.getLeftT().getData().equalSegment(s)) && treeT.getLeftT().isLeftOrRight(s, h + 0.001, true))){
                    saved = treeT.getLeftT().getData();
                    if (treeT.getLeftT().rightIsEmpty()){
                        return saved;
                    }
                    else{
                        return getLeftNeighborSegment((T) treeT.getLeftT().getRightT(), s, saved, h, sIsMin, justInsert);
                    }
                }
                else {
                    return getLeftNeighborSegment((T) treeT.getLeftT(), s, saved, h, sIsMin, justInsert);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.rightIsEmpty()){
                    return saved;
                }
                else {
                    return getLeftNeighborSegment((T)treeT.getRightT(), s, saved, h, sIsMin, justInsert);
                }
            }
        }
    }
    public Segment getRightNeighborSegment(T treeT, Segment s, Segment saved, double h, boolean sIsMax, boolean justInsert){
        if (treeT.isEmpty() || sIsMax || (treeT.isLeaf() && treeT.getData().equalSegment(s))){
            return saved;
        }
        else if (treeT.getData().equalSegment(s) && !treeT.rightIsEmpty()){
            return treeT.getRightT().findMin();
        }
        else {
            if (treeT.isLeftOrRight(s, h, true) && !(treeT.isLeftOrRight(s, h, true) && treeT.isLeftOrRight(s, h, false) && treeT.isLeftOrRight(s, h + 0.001, justInsert))){
                if ((treeT.isLeftOrRight(s, h, false) && treeT.isLeaf()) || treeT.rightIsEmpty()){
                    return saved;
                }
                else if ((treeT.getRightT().isLeftOrRight(s, h, false) && !( treeT.getRightT().isLeftOrRight(s, h, true)) && !(treeT.getRightT().getData().equalSegment(s)))
                || (treeT.getRightT().isLeftOrRight(s, h, false) &&  treeT.getRightT().isLeftOrRight(s, h, true) && !(treeT.getRightT().getData().equalSegment(s)) && treeT.getRightT().isLeftOrRight(s, h + 0.001, false))){
                    saved = treeT.getRightT().getData();
                    if (treeT.getRightT().leftIsEmpty()){
                        return saved;
                    }
                    else{
                        return getRightNeighborSegment((T) treeT.getRightT().getLeftT(), s, saved, h, sIsMax, justInsert);
                    }
                }
                else {
                    return getRightNeighborSegment((T) treeT.getRightT(), s, saved, h, sIsMax, justInsert);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.leftIsEmpty()){
                    return saved;
                }
                else {
                    return getRightNeighborSegment((T)treeT.getLeftT(), s, saved, h, sIsMax, justInsert);
                }
            }
        }
    }
    

    public Segment getLeftMost(T treeT, Point p, ArrayList<Segment> set){
        Segment s = set.get(0);
        if (treeT.getData().equalSegment(s) ||treeT.isLeftOrRight(s, p.getY(), false)){
            if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), true) || (treeT.getData().isHorizontal() && treeT.getData().isInHorizontale(p))){
                if (treeT.leftIsEmpty() || treeT.getLeftT().isLeaf()){
                    return treeT.getData();
                }
                else {
                    return getLeftMost((T) treeT.getLeftT(), p, set);
                }
            }
            else {
                return getLeftMost((T) treeT.getLeftT(), p, set);
            }
        }
        else {
            return getLeftMost((T) treeT.getRightT(), p, set);
        }
    }

    public Segment getRightMost(T treeT, Point p, ArrayList<Segment> set){
        Segment s = set.get(0);
        if (treeT.getData().isHorizontal() && treeT.getData().isInHorizontale(p)){
            return treeT.getData();
        }
        else if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), true)){
            if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), false)){
                if (treeT.rightIsEmpty() 
                || (!treeT.getRightT().isLeftOrRight(s, p.getY(), true) && !treeT.getRightT().getData().equalSegment(s))){
                    return treeT.getData();
                }
                else {
                    return getRightMost(treeT.getRightT(), p, set);
                }
            }
            else {
                return getRightMost(treeT.getRightT(), p, set);
            }
        }
        else {
            return getRightMost(treeT.getLeftT(), p, set);
        }
    }
}