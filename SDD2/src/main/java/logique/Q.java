package src.main.java.logique;

/**
 * Classe qui nous permet de manipuler des arbres de type Q.
 * Ces arbres contiennent nos points d'evenement.
 * L'ordre interne est le suivant : un point sera plus a gauche dans l'arbre qu'un autre si son ordonnee 
 * est plus elevee ou si les deux points ont la meme ordonnee mais que le point a une abscisse 
 * moins elevee.
 */
public class Q extends AVL<Point> {

    //Constructeurs.
    /**
     * Constructeur de la classe Q qui specifie la donnee de la racine data, le sous arbre gauche left 
     * et le sous arbre droit right.
     * @param data Point.
     * @param left Q.
     * @param right Q.
     */
    public Q(Point data, Q left, Q right) {
        super(data, left, right);
    }

    /**
     * Constructeur par defaut.
     */
    public Q() {
        super();
    }


    /** 
     * Getteur de la racine de l'arbre qui retourne cette racine.
     * @return Point.
     */
    public Point getDataQ(){
        return getDataAVL();
    }


    /**
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     * @return Q.
     */
    public Q getLeftQ(){
        return (Q) getLeftAVL();
    }


    /**
     * Getteur du sous arbre droit de l'arbre qui retourne ce sous arbre droit.
     * @return Q.
     */
    public Q getRightQ(){
        return (Q) getRightAVL();
    }


    /**
     * Getteur de la hauteur de l'arbre qui retourne cette hauteur.
     * @return int.
     */
    public int getHeightQ(){
        return getHeightAVL();
    }

    /**
     * Assesseur de la racine de l'arbre qui remplace la donnee de la racine (dataQ) par new_dataQ.
     * @param new_dataQ Point.
     */
    public void setDataQ(Point new_dataQ){
        setDataAVL(new_dataQ);
    }


    /**
     * Assesseur du sous arbre gauche de l'arbre qui remplace ce sous arbre gauche (leftQ) par new_leftQ.
     * @param new_leftQ Q.
     */
    public void setLeftQ(Q new_leftQ){
        setLeftAVL(new_leftQ);
    }


    /**
     * Assesseur du sous arbre droit de l'arbre qui remplace ce sous arbre droit (rightQ) par new_treeQ.
     * @param new_rightQ Q.
     */
    public void setRightQ(Q new_rightQ){
        setRightAVL(new_rightQ);
    }
    

    /**
     * Assesseur de la hauteur de l'arbre qui remplace cette hauteur (heightQ) par new_heightQ.
     * @param new_heightQ int.
     */
    public void setHeightQ(int new_heightQ){
        setHeightAVL(new_heightQ);
    }


    /** 
     * Insere le point "point" dans l'arbre ou si ce point est deja present dans l'arbre et si "point" est 
     * l'extremite superieure de s (dans ce cas insertionUpper vaut true sinon il vaut false), on ajoute s au 
     * isUpperOf de "point". Cela nous assure que tous les segments dont "point" est l'extremite superieure sont 
     * bien dans la liste isUpperOf de "point". Ceci se fait en respectant l'ordre des arbres de type Q.
     * @param point Point.
     * @param insertionUpper boolean.
     * @param s Segment.
     */
    public void insertQ(Point point, boolean insertionUpper, Segment s) {
        if (isEmpty()) {
            this.insertQEmpty(point);
        }
        else if (this.getDataQ().equalPoint(point)){
            if (insertionUpper){
                this.getDataQ().getIsUpperOf().add(s);
            }
        }
        else {
            Point head = this.getDataQ();
            if (point.isUpper(head)) {
                if (this.leftIsEmpty()){
                    this.setLeftQ(new Q(point, null, null));
                    this.equilibrate();
                }
                else{
                    this.getLeftQ().insertQ(point, insertionUpper, s);
                    this.equilibrate();
                }
            }
            else {
                if (head.isUpper(point)) {
                    if (this.rightIsEmpty()){
                        this.setRightQ(new Q(point, null, null));
                        this.equilibrate();
                    }
                    else{
                        this.getRightQ().insertQ(point, insertionUpper, s);
                        this.equilibrate();
                    }
                }
            }
        }
    }
    
   
    /** 
     * Insere le point "point" dans l'arbre qui est vide.
     * @param point Point.
     */
    public void insertQEmpty(Point point) {
        this.setDataQ(point);
        this.setHeightQ(this.getHeightQ() + 1);
    }

    
    /** 
     * Supprime "point" de l'arbre. Ceci se fait en respectant l'ordre des arbres de type Q.
     * @param point Point.
     */
    public void suppressQ(Point point) {
        if (!isEmpty()) {
            Point head = this.getDataQ();
            if (point.isUpper(head)) {
                if (!this.leftIsEmpty()){
                    this.getLeftQ().suppressQ(point);
                    this.equilibrate();
                }
            }
            else if (head.isUpper(point)) {
                if (!this.rightIsEmpty()){
                    this.getRightQ().suppressQ(point);
                    this.equilibrate();
                }
            }
            else {
                this.suppressQRoot();
            }
        }
    }

    /**
     * Supprime la racine de l'arbre. Ceci se fait en respectant l'ordre des arbres de type Q.
     */
    public void suppressQRoot() {
        if (this.isLeaf()) {
            this.setDataQ(null);
            this.setHeightQ(0);
        }
        else {
            if (this.leftIsEmpty()) {
                Q new_Q = this.getRightQ();
                Point head_new_Q = new_Q.getDataQ();
                this.setDataQ(head_new_Q);
                this.setLeftQ(new_Q.getLeftQ());
                this.setRightQ(new_Q.getRightQ());
                this.setHeightQ(new_Q.getHeightQ());
            }
            else {
                if (this.rightIsEmpty()) {
                    Q new_Q = this.getLeftQ();
                    Point head_new_Q = new_Q.getDataQ();
                    this.setDataQ(head_new_Q);
                    this.setLeftQ(new_Q.getLeftQ());
                    this.setRightQ(new_Q.getRightQ());
                    this.setHeightQ(new_Q.getHeightQ());
                }
                else{
                    this.setDataQ((this.getLeftQ()).suppressQMin());
                    this.equilibrate();
                }
            }
        }
    }

    
    /** 
     * Supprime le minimum (la donnee la plus a gauche) de l'arbre et retourne ce minimum. Ceci se fait en 
     * respectant l'ordre des arbres de type Q.
     * @return Point.
     */
    public Point suppressQMin() {
        if (this.leftIsEmpty()) {
            Point min = this.getDataQ();
            this.suppressQRoot();
            return min;
        }
        else {
            Point min = this.getLeftQ().suppressQMin();
            this.equilibrate();
            return min;
        }
    }

    /**
     * Affiche les donnees de l'arbre selon l'affichage "inordre" vu au cours de sddI.
     */
    public void inordreQ() {
		if (this != null) {
            if (this.getLeftQ() != null) {
                this.getLeftQ().inordreQ();
            }
            if (this.getDataQ() != null) {
                this.getDataQ().print();
            }
            if (this.getRightQ() != null) {
                this.getRightQ().inordreQ();
            }
		}
	}
}