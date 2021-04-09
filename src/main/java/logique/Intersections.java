package src.main.java.logique;

import java.util.ArrayList;

public class Intersections {

    private ArrayList<Segment> intersections;

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

    public Q constructQ(){
        Q treeQ = new Q();
        for (int i = 0; i < this.getIntersections().size(); i++){
            Segment new_segment = this.getIntersections().get(i);
            treeQ.insertQ(new_segment.getUpper(), true, new_segment); 
            treeQ.insertQ(new_segment.getLower(), false, new_segment);
        }
        return treeQ;
    }

    public ArrayList<Point> FindIntersections(){
        ArrayList<Point> solutions = new ArrayList<Point>();
        Q treeQ = constructQ();
        T treeT = new T();
        while (!treeQ.isEmpty()){
            Point nextEvent = treeQ.suppressQMin();
            HandleEventPoint(nextEvent, treeQ, treeT, solutions);
        }
        return solutions;
    }

    public void FindNewEvent(Segment s1, Segment s2, Point p, double h, Q treeQ){
        if (s1.isIntersection(s2)){
            Point new_event = s1.getIntersectionPoint(s2);
            if (new_event.getY() - Math.floor(new_event.getY()) <= 0.00000001){
                new_event.setY(Math.floor(new_event.getY()));
            }
            if (new_event.getX() - Math.floor(new_event.getX()) <= 0.00000001){
                new_event.setX(Math.floor(new_event.getX()));
            }
            if ((new_event.getY() < h) || (new_event.getY() == h && new_event.getX() > p.getX())) { 
                treeQ.insertQ(new_event, false, null);
            }
        }
    }

    public void HandleEventPoint(Point p, Q treeQ, T treeT, ArrayList<Point> solutions){
        ArrayList<Segment> justPoint = new ArrayList<Segment>();
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
            treeT.suppressT(lP.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.suppressT(cP.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
        double h = p.getY() - 0.000001;
        for (int i = 0; i< uP.size(); i++) { // on insère à hauteur h pour bien avoir l'ordre en dessous de l'intersection
            treeT.insertT(uP.get(i), h, p.getY(), p.getX());
            if (uP.get(i).getUpper().equalPoint(uP.get(i).getLower())){
                justPoint.add(uP.get(i));
            }
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.insertT(cP.get(i), h, p.getY(), p.getX());
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
                Segment s_lm = getLeftMost(treeT, p, uP.get(0));
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY() - 0.000001, p.getY(), p.getX(), true);
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, uP.get(0), null);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY() - 0.000001, p.getY(), p.getX(), true);
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
            else {
                Segment s_lm = getLeftMost(treeT, p, cP.get(0));
                Segment s_l = getLeftNeighborSegment(treeT, s_lm, null, p.getY() - 0.000001, p.getY(), p.getX(), true);
                if (s_lm != null && s_l != null){
                    FindNewEvent(s_lm, s_l, p, p.getY(), treeQ);
                }
                Segment s_rm = getRightMost(treeT, p, cP.get(0), null);
                Segment s_r = getRightNeighborSegment(treeT, s_rm, null, p.getY() - 0.000001, p.getY(), p.getX(),true);
                if (s_rm != null && s_r != null){
                    FindNewEvent(s_rm, s_r, p, p.getY(), treeQ);
                }
            }
        }
        for (int i = 0; i < justPoint.size(); i++) {
            treeT.suppressT(justPoint.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
    }
    /*
    public void addLowerAndInside(ArrayList<Segment> lP,ArrayList<Segment> cP, Point p, T treeT){
        Point p1 = new Point(p.getX(), p.getY());
        Point p2 = new Point(p.getX(), p.getY() - 1);
        Segment segmentIterable = new Segment(p1, p2);// on utilise p1 car sinon on rajouterait segment iterable aux upper de p
        //notons que le segment suivant si il est dans treeT appartient à U(p) et L(p) mais comme il est déjà dans U(p) pas besoin de le rajouter dans L(p)
        Segment segmentMemoire = new Segment(p1, p2); 
        boolean oneTimeVertical = false;
        boolean oneTimeHorizontal = false;
        boolean stop = false;
        while(!stop){
            Segment segmentLeft = getLeftNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentLeft == null 
            || (!segmentLeft.isVertical() && !segmentLeft.isHorizontal() && !segmentLeft.isIn(p)) 
            || (segmentLeft.isVertical() && !segmentLeft.isInVertical(p)) 
            || (segmentLeft.isHorizontal() && !segmentLeft.isInHorizontale(p))){
                stop = true;
            }
            else {
                if (segmentLeft.getLower().getX() == p.getX() && segmentLeft.getLower().getY() == p.getY()){
                    lP.add(segmentLeft);
                    if(segmentLeft.isVertical()){
                        oneTimeVertical = true;
                    }
                    else if (segmentLeft.isHorizontal()){
                        oneTimeHorizontal = true;
                    }
                }
                else if (!(segmentLeft.getUpper().getX() == p.getX() && segmentLeft.getUpper().getY() == p.getY())){
                    cP.add(segmentLeft);
                    if(segmentLeft.isVertical()){
                        oneTimeVertical = true;
                    }
                    else if (segmentLeft.isHorizontal()){
                        oneTimeHorizontal = true;
                    }
                }
                segmentIterable = segmentLeft;
            }
        }
        stop = false;
        segmentIterable = segmentMemoire;
        while(!stop){
            Segment segmentRight = getRightNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentRight == null 
            || (!segmentRight.isVertical() && !segmentRight.isHorizontal() && !segmentRight.isIn(p)) 
            || (segmentRight.isVertical() && !segmentRight.isInVertical(p)) 
            || (segmentRight.isHorizontal() && !segmentRight.isInHorizontale(p))){
                stop = true;
            }
            else{
                if ((!oneTimeVertical || !segmentRight.isVertical()) 
                && (!oneTimeHorizontal || !segmentRight.isHorizontal())){ //on veut empêcher de mettre la racine 2 fois (si elle respecte les conditions de la négation)
                    if (segmentRight.getLower().getX() == p.getX() && segmentRight.getLower().getY() == p.getY()){ //mais on doit pas empêcher le add de se faire les itérations suivantes
                        lP.add(segmentRight);
                    }
                    else if (!(segmentRight.getUpper().getX() == p.getX() && segmentRight.getUpper().getY() == p.getY())){
                        cP.add(segmentRight);
                    }
                }
                segmentIterable = segmentRight;
            }
        }
    }
    */

    public void addLowerAndInside(ArrayList<Segment> lP,ArrayList<Segment> cP, Point p, T treeT){
        Point p1 = new Point(p.getX(), p.getY());
        Point p2 = new Point(p.getX(), p.getY() - 1);
        Segment segmentIterable = new Segment(p1, p2);// on utilise p1 car sinon on rajouterait segment iterable aux upper de p
        //notons que le segment suivant si il est dans treeT appartient à U(p) et L(p) mais comme il est déjà dans U(p) pas besoin de le rajouter dans L(p)
        Segment segmentMemoire = new Segment(p1, p2); 
        boolean stop = false;
        while(!stop){
            Segment segmentLeft = getLeftNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentLeft == null 
            || (!segmentLeft.isVertical() && !segmentLeft.isHorizontal() && !segmentLeft.isIn(p)) 
            || (segmentLeft.isVertical() && !segmentLeft.isInVertical(p)) 
            || (segmentLeft.isHorizontal() && !segmentLeft.isInHorizontale(p))){
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
            Segment segmentRight = getRightNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentRight == null 
            || (!segmentRight.isVertical() && !segmentRight.isHorizontal() && !segmentRight.isIn(p)) 
            || (segmentRight.isVertical() && !segmentRight.isInVertical(p)) 
            || (segmentRight.isHorizontal() && !segmentRight.isInHorizontale(p))){
                stop = true;
            }
            else{
                if (!((lP.size() > 0 && segmentRight.equalSegment(lP.get(0))) || (cP.size() > 0 && segmentRight.equalSegment(cP.get(0))))){ //on veut empêcher de mettre la racine 2 fois (si elle respecte les conditions de la négation)
                    if (segmentRight.getLower().getX() == p.getX() && segmentRight.getLower().getY() == p.getY()){ //mais on doit pas empêcher le add de se faire les itérations suivantes
                        lP.add(segmentRight);
                    }
                    else if (!(segmentRight.getUpper().getX() == p.getX() && segmentRight.getUpper().getY() == p.getY())){
                        cP.add(segmentRight);
                    }
                }
                segmentIterable = segmentRight;
            }
        }
    }

    public Segment getLeftNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment leftNeighbor = new Segment(p1, p1);
        return getLeftNeighborSegment(treeT, leftNeighbor, null, p.getY(), p.getY(), p.getX(), false);
    }

    public Segment getRightNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment rightNeighbor = new Segment(p1, p1);
        return getRightNeighborSegment(treeT, rightNeighbor, null, p.getY(), p.getY(), p.getX(), false);
    }
    
    public Segment getLeftNeighborSegment(T treeT, Segment s, Segment saved, double h, double h2, double now_x, boolean justInsert){
        if (treeT.isEmpty() || (treeT.isLeaf() && treeT.getData().equalSegment(s))){
            return saved;
        }
        else if (treeT.getData().equalSegment(s) && !treeT.leftIsEmpty()){
            if (treeT.getLeftAVL().isLeaf()){
                return saved;
            }
            else{
                T treeTLeft = (T) treeT.getLeftAVL();
                while (!treeTLeft.getRightAVL().getData().equalSegment(s)){
                    treeTLeft = (T) treeTLeft.getRightAVL();
                }
                return treeTLeft.getLeftAVL().findMax();
            }
        }
        else {
            if (treeT.isLeftOrRight(s, h, h2, now_x, false) && !(treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeftOrRight(s, h + 0.000001, h2, now_x, !justInsert))){
                if ((treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeaf()) || treeT.leftIsEmpty()){
                    return saved;
                }
                else if ((((T) treeT.getLeftAVL()).isLeftOrRight(s, h, h2, now_x, true) && !(((T) treeT.getLeftAVL()).isLeftOrRight(s, h, h2, now_x, false)) && !(treeT.getLeftAVL().getData().equalSegment(s)))
                || (((T) treeT.getLeftAVL()).isLeftOrRight(s, h, h2, now_x, true) && ((T) treeT.getLeftAVL()).isLeftOrRight(s, h, h2, now_x, false) && !(treeT.getLeftAVL().getData().equalSegment(s)) && ((T) treeT.getLeftAVL()).isLeftOrRight(s, h + 0.000001, h2, now_x, true))){
                    saved = treeT.getLeftAVL().getData();
                    if (treeT.getLeftAVL().rightIsEmpty()){
                        return saved;
                    }
                    else{
                        return getLeftNeighborSegment((T) treeT.getLeftAVL().getRightAVL(), s, saved, h, h2, now_x, justInsert);
                    }
                }
                else {
                    return getLeftNeighborSegment((T) treeT.getLeftAVL(), s, saved, h, h2, now_x, justInsert);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.rightIsEmpty()){
                    return saved;
                }
                else {
                    return getLeftNeighborSegment((T)treeT.getRightAVL(), s, saved, h, h2, now_x, justInsert);
                }
            }
        }
    }
    
    public Segment getRightNeighborSegment(T treeT, Segment s, Segment saved, double h, double h2, double now_x, boolean justInsert){
        if (treeT.isEmpty() || (treeT.isLeaf() && treeT.getData().equalSegment(s))){
            return saved;
        }
        else if (treeT.getData().equalSegment(s) && !treeT.rightIsEmpty()){
            return treeT.getRightAVL().findMin();
        }
        else {
            if (treeT.isLeftOrRight(s, h, h2, now_x, true) && !(treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeftOrRight(s, h + 0.000001, h2, now_x, justInsert))){
                if ((treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeaf()) || treeT.rightIsEmpty()){
                    return saved;
                }
                else if ((((T) treeT.getRightAVL()).isLeftOrRight(s, h, h2, now_x, false) && !(((T) treeT.getRightAVL()).isLeftOrRight(s, h, h2, now_x, true)) && !(treeT.getRightAVL().getData().equalSegment(s)))
                || (((T) treeT.getRightAVL()).isLeftOrRight(s, h, h2, now_x, false) && ((T) treeT.getRightAVL()).isLeftOrRight(s, h, h2, now_x, true) && !(treeT.getRightAVL().getData().equalSegment(s)) && ((T) treeT.getRightAVL()).isLeftOrRight(s, h + 0.000001, h2, now_x, false))){
                    saved = treeT.getRightAVL().getData();
                    if (treeT.getRightAVL().leftIsEmpty()){
                        return saved;
                    }
                    else{
                        return getRightNeighborSegment((T) treeT.getRightAVL().getLeftAVL(), s, saved, h, h2, now_x, justInsert);
                    }
                }
                else {
                    return getRightNeighborSegment((T) treeT.getRightAVL(), s, saved, h, h2, now_x, justInsert);
                }
            }
            else{
                saved = treeT.getData();
                if (treeT.leftIsEmpty()){
                    return saved;
                }
                else {
                    return getRightNeighborSegment((T)treeT.getLeftAVL(), s, saved, h, h2, now_x, justInsert);
                }
            }
        }
    }
    
    
    public Segment getLeftMost(T treeT, Point p, Segment s){
        if (treeT.getData().equalSegment(s) ||treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), false)){
            if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), true) || (treeT.getData().isHorizontal() && treeT.getData().isInHorizontale(p))){
                if (treeT.leftIsEmpty() || treeT.getLeftAVL().isLeaf()){
                    return treeT.getData();
                }
                else {
                    return getLeftMost((T) treeT.getLeftAVL(), p, s);
                }
            }
            else {
                return getLeftMost((T) treeT.getLeftAVL(), p, s);
            }
        }
        else {
            return getLeftMost((T) treeT.getRightAVL(), p, s);
        }
    }
    
    public Segment getRightMost(T treeT, Point p, Segment s, Segment saved){
        if (treeT.getData().isHorizontal() && treeT.getData().isInHorizontale(p)){
            return treeT.getData();
        }
        else if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), true)){ 
            if (treeT.getData().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), false)){
                if (treeT.rightIsEmpty()){
                    return treeT.getData();
                }
                else {
                    saved = treeT.getData();
                    return getRightMost((T) treeT.getRightAVL(), p, s, saved);
                }
            }
            else {
                return getRightMost((T) treeT.getRightAVL(), p, s, saved);
            }
        }
        else {
            if(!treeT.leftIsEmpty()){
                return getRightMost((T) treeT.getLeftAVL(), p, s, saved);
            }
            else{
                return saved;
            }
        }
    }
}