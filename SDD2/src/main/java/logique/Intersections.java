package src.main.java.logique;

import java.util.ArrayList;

/**
 * Classe qui nous permet de resoudre le map overlay problem.
 */
public class Intersections {

    //Variable d'instance.
    //intersections est une liste qui contient les segments pour lesquels on va determiner les intersections.
    private ArrayList<Segment> intersections;

    //Constructeurs.
    /**
     * Constructeur par defaut.
     */
    public Intersections(){
        this.intersections = new ArrayList<Segment>();
    }


    /**
     * Constructeur de la classe Intersections qui specifie la liste.
     * @param intersections ArrayList de Segment.
     */
    public Intersections(ArrayList<Segment> intersections){
        this.intersections = intersections;
    }


    /**
     * Getteur de la liste intersections qui retourne cette liste.
     * @return ArrayList de Segment.
     */
    public ArrayList<Segment> getIntersections(){
        return this.intersections;
    }


    /**
     * Assesseur de la liste intersections qui la remplace par new_list.
     * @param new_list Arraylist de Segment.
     */
    public void setIntersections(ArrayList<Segment> new_list){
        this.intersections = new_list;
    }


    /**
     * Construit l'arbre treeQ qui contient les points d'evenement. On insere les upper et les lower de chaque
     * segment dans treeQ. Les upper sont inseres avec le parametre insertionUpper valant true afin de pouvoir
     * ajouter des segments dans la liste isUpperOf du upper si cela est necessaire alors que les lower sont
     * inseres avec le parametre insertionUpper valant false. On retourne ensuite l'arbre treeQ.
     * @return Q.
     */
    public Q constructQ(){
        Q treeQ = new Q();
        for (int i = 0; i < this.getIntersections().size(); i++){
            Segment new_segment = this.getIntersections().get(i);
            treeQ.insertQ(new_segment.getUpper(), true, new_segment);
            treeQ.insertQ(new_segment.getLower(), false, new_segment);
        }
        return treeQ;
    }


    /**
     * Algorithme qui va determiner les points d'intersections des segments se trouvant dans la liste intersections.
     * On commence par creer une ArrayList solutions qui contiendra les points d'intersections, on construit ensuite
     * un arbre de type Q qui contiendra les points d'evenement et qui , initialement, contient tous les upper et
     * lower des segments consideres. On initialise aussi un arbre de type T qui contiendra les segments croises
     * par la sweep line. On gere ensuite chaque point d'evenement avec HandleEventPoint. Finalement on retourne
     * la liste solutions qui contient toutes les intersections.
     * @return ArrayList de Point.
     */
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


    /**
     * Determine si les segments s1 et s2 ont une intersection et, si c'est le cas, on ajoute alors
     * cette intersection dans l'arbre de type Q (treeQ) si il est plus bas que la sweep line (le parametre h est
     * la hauteur de la sweep line) ou a la meme hauteur mais a droite du point d'evenement (le parametre p est
     * ce point d'evenement) que l'on est en train de gerer. Notez que des conditions ont ete rajoutee afin
     * d'eviter certaines erreurs de precisions.
     * @param s1 Segment.
     * @param s2 Segment.
     * @param p Point.
     * @param h double.
     * @param treeQ Q.
     */
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


    /**
     * Gere le point d'evenement p en detectant si p est un point d'intersection de plusieurs segments,
     * en inserant ou supprimant certains segment dans treeT et en detectant d'autres points d'evenements
     * si on peut les detecter a ce stade.
     * @param p Point.
     * @param treeQ Q.
     * @param treeT T.
     * @param solutions ArrayList de Point.
     */
    public void HandleEventPoint(Point p, Q treeQ, T treeT, ArrayList<Point> solutions){
        //On initialise justPoint qui contiendra les segments qui sont reduits a un point.
        ArrayList<Segment> justPoint = new ArrayList<Segment>();
        //On cree uP, lP et cP qui vont contenir les segments dont p est le upper, le lower ou un point interieur
        //grâce au isUpperOf de p et a AddLowerAndInside.
        ArrayList<Segment> uP = p.getIsUpperOf();
        ArrayList<Segment> lP = new ArrayList<Segment>();
        ArrayList<Segment> cP = new ArrayList<Segment>();
        addLowerAndInside(lP, cP, p, treeT);
        //S'il y a au moins deux segments dans l'union de uP, lP et cP p est une intersection de ces segments
        //et on le traite comme tel.
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
        //On supprime ensuite les segments dont p est le lower ou un point interieur, puis on insere
        //les segments dont le point est le upper ou un point interieur.
        //On supprime a hauteur p.getY() + 0.000001 et avec now_x valant p.getX() - 0.000001 pour bien avoir
        //l'ordre avant d'atteindre l'intersection.
        for (int i = 0; i< lP.size(); i++) {
            treeT.suppressT(lP.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.suppressT(cP.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
        //On insere a hauteur h pour bien avoir l'ordre en dessous de l'intersection.
        double h = p.getY() - 0.000001;
        for (int i = 0; i< uP.size(); i++) {
            treeT.insertT(uP.get(i), h, p.getY(), p.getX());
            if (uP.get(i).getUpper().equalPoint(uP.get(i).getLower())){
                justPoint.add(uP.get(i));
            }
        }
        for (int i = 0; i< cP.size(); i++) {
            treeT.insertT(cP.get(i), h, p.getY(), p.getX());
        }
        //On effectue differentes operations qui visent a rajouter des points d'evenement dans treeQ
        //si cela est necessaire.
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
        //on supprime les segments de justPoint de treeT.
        for (int i = 0; i < justPoint.size(); i++) {
            treeT.suppressT(justPoint.get(i), p.getY() + 0.000001, p.getY(), p.getX() - 0.000001);
        }
    }


    /**
     * Ajoute les segments de treeT qui ont p comme lower p dans lP, et ceux qui contiennent p dans leur interieur
     * a cP.
     * @param lP ArrayList de Segment.
     * @param cP ArrayList de Segment.
     * @param p Point.
     * @param treeT T.
     */
    public void addLowerAndInside(ArrayList<Segment> lP,ArrayList<Segment> cP, Point p, T treeT){
        // on utilise p1 car sinon on rajouterait segmentIterable au isUpperOf de p
        Point p1 = new Point(p.getX(), p.getY());
        Point p2 = new Point(p.getX(), p.getY() - 1);
        //On cree un segment "artificiel" segmentIterable qui contient p.
        Segment segmentIterable = new Segment(p1, p2);
        //On "sauvegarde" ce segment.
        Segment segmentMemoire = new Segment(p1, p2);
        boolean stop = false;
        //On prend le voisin gauche de segmentIterable et on regarde si il contient p.
        //On repete l'operation sur le segment jusqu'a ce qu'il n'y ait plus de voisin
        //gauche ou que le voisin gauche ne contienne pas p.
        while(!stop){
            Segment segmentLeft = getLeftNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentLeft == null
            || (!segmentLeft.isVertical() && !segmentLeft.isHorizontal() && !segmentLeft.isIn(p))
            || (segmentLeft.isVertical() && !segmentLeft.isInVertical(p))
            || (segmentLeft.isHorizontal() && !segmentLeft.isInHorizontale(p))){
                stop = true;
            }
            else {
                //Conditions qui determinent si le segment qui contient p doit etre rajoute
                //a lP ou cP.
                if (segmentLeft.getLower().getX() == p.getX() && segmentLeft.getLower().getY() == p.getY()){
                    lP.add(segmentLeft);
                }
                else if (!(segmentLeft.getUpper().getX() == p.getX() && segmentLeft.getUpper().getY() == p.getY())){
                    cP.add(segmentLeft);
                }
                segmentIterable = segmentLeft;
            }
        }
        boolean oneTime = false;
        stop = false;
        //On reinitialise segmentIterable comme etant notre segment "artificiel" de depart.
        segmentIterable = segmentMemoire;
        //On effectue le meme raisonnement que precedemment si ce n'est qu'ici on travaille avec le voisin
        //droit au lieu du voisin gauche.
        while(!stop){
            Segment segmentRight = getRightNeighborSegment(treeT, segmentIterable, null, p.getY(), p.getY(), p.getX() - 0.000001, false);
            if (segmentRight == null
            || (!segmentRight.isVertical() && !segmentRight.isHorizontal() && !segmentRight.isIn(p))
            || (segmentRight.isVertical() && !segmentRight.isInVertical(p))
            || (segmentRight.isHorizontal() && !segmentRight.isInHorizontale(p))){
                stop = true;
            }
            else{
                //Il est possible dans certains cas qu'on obtienne le meme segment en cherchant le voisin
                //gauche et le voisin droit de notre segment "artificiel", si c'est le cas ce segment a
                //deja ete ajoute dans cP ou lP. Le test suivant vise juste a veiller a ce que ce segment
                //ne soit pas ajoute une deuxieme fois.
                if (oneTime || (!((lP.size() > 0 && segmentRight.equalSegment(lP.get(0))) && !(cP.size() > 0 && segmentRight.equalSegment(cP.get(0)))))){
                    //Conditions qui determine si le segment qui contient p doit etre rajoute
                    //a lP ou cP.
                    if (segmentRight.getLower().getX() == p.getX() && segmentRight.getLower().getY() == p.getY()){
                        lP.add(segmentRight);
                    }
                    else if (!(segmentRight.getUpper().getX() == p.getX() && segmentRight.getUpper().getY() == p.getY())){
                        cP.add(segmentRight);
                    }
                }
                oneTime = true;
                segmentIterable = segmentRight;
            }
        }
    }


    /**
     * Retourne un segment qui est le voisin gauche d'un point.
     * Pour ce faire on construit un segment "artificiel" qui contient p et obtient
     * son voisin gauche grâce a getLeftNeighborSegment.
     * @param treeT T.
     * @param p Point.
     * @return Segment.
     */
    public Segment getLeftNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment leftNeighbor = new Segment(p1, p1);
        return getLeftNeighborSegment(treeT, leftNeighbor, null, p.getY(), p.getY(), p.getX(), false);
    }


    /**
     * Retourne un segment qui est le voisin droit d'un point.
     * Pour ce faire on construit un segment "artificiel" qui contient p et obtient
     * son voisin droit grâce a getRightNeighborSegment.
     * @param treeT T.
     * @param p Point.
     * @return Segment.
     */
    public Segment getRightNeighborPoint(T treeT, Point p){
        Point p1 = new Point(p.getX(), p.getY());
        Segment rightNeighbor = new Segment(p1, p1);
        return getRightNeighborSegment(treeT, rightNeighbor, null, p.getY(), p.getY(), p.getX(), false);
    }


    /**
     * Explore l'arbre treeT et retourne le voisin gauche du segment s.
     * Les parametres h, h2 et now_x sont les memes que d'habitude, justInsert vaut true si s vient
     * d'etre insere dans treeT et false sinon et saved nous sert a retenir notre "meilleur candidat"
     * pour le voisin gauche.
     * @param treeT T.
     * @param s Segment.
     * @param saved Segment.
     * @param h double.
     * @param h2 double.
     * @param now_x double.
     * @param justInsert boolean.
     * @return Segment.
     */
    public Segment getLeftNeighborSegment(T treeT, Segment s, Segment saved, double h, double h2, double now_x, boolean justInsert){
        //Dans ces cas il est impossible de continuer l'exploration, on retourne saved qui est soit
        //null si s n'a pas de voisin gauche soit le segment qui est le voisin gauche de s.
        if (treeT.isEmpty() || (treeT.isLeaf() && treeT.getDataT().equalSegment(s))){
            return saved;
        }
        //Si on trouve le segment s dans treeT et que c'est un noeud interne, on sait exactement ou se trouve son
        //voisin gauche dans treeT (s'il existe). Le branchement qui suit va donc directement chercher ce
        //voisin gauche s'il existe.
        else if (treeT.getDataT().equalSegment(s) && !treeT.leftIsEmpty()){
            //Cas a le sous arbre gauche est une feuille qui contient donc le meme segment que la racine.
            //Dans ce cas il n'y a pas de voisin gauche et on retourne saved.
            if (treeT.getLeftT().isLeaf()){
                return saved;
            }
            //Cas ou le voisin gauche existe, il s'agit de la donnee la plus a droite du sous arbre gauche
            //de l'arbre dont la racine du sous arbre droit est le segment s. On va donc directement chercher
            //ce segment.
            else{
                T treeTLeft = treeT.getLeftT();
                while (!treeTLeft.getRightT().getDataT().equalSegment(s)){
                    treeTLeft = treeTLeft.getRightT();
                }
                return treeTLeft.getLeftT().findMax();
            }
        }
        //Autres cas.
        else {
            //Cas ou la racine de treeT est a droite de s, il faut donc aller dans le sous arbre gauche.
            if (treeT.isLeftOrRight(s, h, h2, now_x, false) && !(treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeftOrRight(s, h + 0.000001, h2, now_x, !justInsert))){
                //Cas ou il est impossible d'aller plus a gauche on retourne donc saved.
                if ((treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeaf()) || treeT.leftIsEmpty()){
                    return saved;
                }
                //Cas ou la racine du sous arbre gauche est a gauche de s, cette racine est donc un candidat
                //potentiel et on explore, si c'est possible, le sous arbre droit du sous arbre gauche afin
                //d'essayer de trouver un "meilleur candidat".
                else if (((treeT.getLeftT()).isLeftOrRight(s, h, h2, now_x, true) && !(treeT.getLeftT().isLeftOrRight(s, h, h2, now_x, false)) && !(treeT.getLeftT().getDataT().equalSegment(s)))
                || (treeT.getLeftT().isLeftOrRight(s, h, h2, now_x, true) && treeT.getLeftT().isLeftOrRight(s, h, h2, now_x, false) && !(treeT.getLeftT().getDataT().equalSegment(s)) && treeT.getLeftT().isLeftOrRight(s, h + 0.000001, h2, now_x, true))){
                    saved = treeT.getLeftT().getDataT();
                    if (treeT.getLeftT().rightIsEmpty()){
                        return saved;
                    }
                    else{
                        return getLeftNeighborSegment(treeT.getLeftT().getRightT(), s, saved, h, h2, now_x, justInsert);
                    }
                }
                //Cas ou la racine du sous arbre gauche de treeT est a droite de s, il faut donc aller dans
                //le sous arbre gauche afin d'aller "encore plus a gauche" a l'etape suivante.
                else {
                    return getLeftNeighborSegment(treeT.getLeftT(), s, saved, h, h2, now_x, justInsert);
                }
            }
            //Cas ou la racine est a gauche de s, elle est donc un bon candidat et on explore le sous arbre droit
            //afin de voir si on peut trouver un "meilleur candidat".
            else{
                saved = treeT.getDataT();
                if (treeT.rightIsEmpty()){
                    return saved;
                }
                else {
                    return getLeftNeighborSegment(treeT.getRightT(), s, saved, h, h2, now_x, justInsert);
                }
            }
        }
    }


    /**
     * Explore l'arbre treeT et retourne le voisin droit du segment s.
     * Les parametres h, h2 et now_x sont les memes que d'habitude, justInsert vaut true si s vient
     * d'etre insere dans treeT et false sinon et saved nous sert a retenir notre "meilleur candidat"
     * pour le voisin gauche.
     * @param treeT T.
     * @param s Segment.
     * @param saved Segment.
     * @param h double.
     * @param h2 double.
     * @param now_x double.
     * @param justInsert boolean.
     * @return Segment.
     */
    public Segment getRightNeighborSegment(T treeT, Segment s, Segment saved, double h, double h2, double now_x, boolean justInsert){
        //Dans ces cas il est impossible de continuer l'exploration, on retourne saved qui est soit
        //null si s n'a pas de voisin droit soit le segment qui est le voisin droit de s.
        if (treeT.isEmpty() || (treeT.isLeaf() && treeT.getDataT().equalSegment(s))){
            return saved;
        }
        //Si on trouve s dans treeT, il est facile de voir que si son sous arbre droit est non vide,
        //alors son voisin droit est le segment le plus a gauche de son sous arbre droit.
        //On va donc directement chercher ce segment.
        else if (treeT.getDataT().equalSegment(s) && !treeT.rightIsEmpty()){
            return treeT.getRightT().findMin();
        }
        //Autres cas.
        else {
            //Cas ou la racine de treeT est a gauche de s, il faut donc aller dans le sous arbre droit.
            if (treeT.isLeftOrRight(s, h, h2, now_x, true) && !(treeT.isLeftOrRight(s, h, h2, now_x, true) && treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeftOrRight(s, h + 0.000001, h2, now_x, justInsert))){
                if ((treeT.isLeftOrRight(s, h, h2, now_x, false) && treeT.isLeaf()) || treeT.rightIsEmpty()){
                    return saved;
                }
                //Cas ou la racine du sous arbre droit est a droite de s, cette racine est donc un candidat
                //potentiel et on explore, si c'est possible, le sous arbre gauche du sous arbre droit afin
                //d'essayer de trouver un "meilleur candidat".
                else if ((treeT.getRightT().isLeftOrRight(s, h, h2, now_x, false) && !(treeT.getRightT().isLeftOrRight(s, h, h2, now_x, true)) && !(treeT.getRightT().getDataT().equalSegment(s)))
                || (treeT.getRightT().isLeftOrRight(s, h, h2, now_x, false) && treeT.getRightT().isLeftOrRight(s, h, h2, now_x, true) && !(treeT.getRightT().getDataT().equalSegment(s)) && treeT.getRightT().isLeftOrRight(s, h + 0.000001, h2, now_x, false))){
                    saved = treeT.getRightT().getDataT();
                    if (treeT.getRightT().leftIsEmpty()){
                        return saved;
                    }
                    else{
                        return getRightNeighborSegment(treeT.getRightT().getLeftT(), s, saved, h, h2, now_x, justInsert);
                    }
                }
                //Cas ou la racine du sous arbre droit de treeT est a gauche de s, il faut donc aller dans
                //le sous arbre droit afin d'aller "encore plus a droite" a l'etape suivante.
                else {
                    return getRightNeighborSegment(treeT.getRightT(), s, saved, h, h2, now_x, justInsert);
                }
            }
            //Cas ou la racine est a droite de s, elle est donc un bon candidat et on explore le sous arbre gauche
            //afin de voir si on peut trouver un "meilleur candidat".
            else{
                saved = treeT.getDataT();
                if (treeT.leftIsEmpty()){
                    return saved;
                }
                else {
                    return getRightNeighborSegment(treeT.getLeftT(), s, saved, h, h2, now_x, justInsert);
                }
            }
        }
    }



    /**
     * Retourne le segment le plus a gauche de uP et cP dans treeT, ou p est le point en train d'etre traite
     * par HandleEventPoint et s est un segment de uP ou cP.
     * @param treeT T.
     * @param p Point.
     * @param s Segment.
     * @return Segment.
     */
    public Segment getLeftMost(T treeT, Point p, Segment s){
        //Cas ou la racine est le segment s ou bien ou la racine est a droite de s, le segment le plus
        //a gauche de uP et cP est donc dans le sous arbre gauche.
        if (treeT.getDataT().equalSegment(s) ||treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), false)){
            //Cas ou le segment en racine contient p.
            if (treeT.getDataT().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), true) || (treeT.getDataT().isHorizontal() && treeT.getDataT().isInHorizontale(p))){
                //Cas ou on ne peut pas aller plus a gauche donc la racine est le segment recherche.
                if (treeT.leftIsEmpty() || treeT.getLeftT().isLeaf()){
                    return treeT.getDataT();
                }
                //Cas ou on peut aller plus a gauche, on est sûr qu'il y a encore au moins un segment qui contient
                //p dans le sous arbre gauche car le segment racine contient p et la feuille la plus a droite
                //de son sous arbre gauche represente le meme segment. On explore donc le sous arbre gauche.
                else {
                    return getLeftMost(treeT.getLeftT(), p, s);
                }
            }
            //Cas ou le segment racine ne contient pas p, comme ce segment est a droite de s, il faut
            //explorer le sous arbre gauche.
            else {
                return getLeftMost(treeT.getLeftT(), p, s);
            }
        }
        //Cas ou la racine est a gauche de s, les segments de uP et cP se trouvent donc dans le sous arbre
        //droit que l'on va donc explorer.
        else {
            return getLeftMost(treeT.getRightT(), p, s);
        }
    }


    /**
     * Retourne le segment le plus a droite de uP et cP dans treeT, ou p est le point en train d'etre traite
     * par HandleEventPoint, s est un segment de uP ou cP et saved nous permet de retenir notre "meilleur
     * candidat".
     * @param treeT T.
     * @param p Point.
     * @param s Segment.
     * @param saved Segment.
     * @return Segment.
     */
    public Segment getRightMost(T treeT, Point p, Segment s, Segment saved){
        //Cas ou la racine est un segment horizontal contenant p, par definition de l'ordre dans un arbre
        //de type treeT il s'agit du segment le plus a droite de uP et cP.
        if (treeT.getDataT().isHorizontal() && treeT.getDataT().isInHorizontale(p)){
            return treeT.getDataT();
        }
        //Cas ou la racine est le segment s ou bien ou la racine est a gauche de s, le segment le plus
        //a droite de uP et cP est donc dans le sous arbre gauche.
        else if (treeT.getDataT().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), true)){
            //Cas ou le segment en racine contient p.
            if (treeT.getDataT().equalSegment(s) || treeT.isLeftOrRight(s, p.getY(), p.getY(), p.getX(), false)){
                //Cas ou on ne peut pas aller plus a droite donc la racine est le segment recherche.
                if (treeT.rightIsEmpty()){
                    return treeT.getDataT();
                }
                //Cas ou on peut aller plus a droite, sans etre sûr qu'il y ait un "bon candidat" dans le sous
                //arbre droit, on pose alors saved comme etant le segment racine et on explore le sous
                //arbre droit afin de voir si on trouve un "meilleur candidat".
                else {
                    saved = treeT.getDataT();
                    return getRightMost(treeT.getRightT(), p, s, saved);
                }
            }
            //Cas ou le segment racine ne contient pas p, comme ce segment est a gauche de s, il faut
            //explorer le sous arbre droit.
            else {
                return getRightMost(treeT.getRightT(), p, s, saved);
            }
        }
        //Cas ou la racine est a droite de s, les segments de uP et cP se trouvent donc dans le sous arbre
        //gauche que l'on va donc explorer si c'est possible sinon on retourne saved qui est notre "meilleur
        //candidat".
        else {
            if(!treeT.leftIsEmpty()){
                return getRightMost(treeT.getLeftT(), p, s, saved);
            }
            else{
                return saved;
            }
        }
    }
}