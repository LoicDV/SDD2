package src.main.java.logique;

/**
 * Classe qui nous permet de manipuler des arbres de type Q.
 * Ces arbres contiennent nos points d'évènement.
 * L'ordre interne est le suivant : un point sera plus à gauche dans l'arbre qu'un autre si son ordonnée 
 * est plus élevée ou si les deux points ont la même ordonnée mais que le point a une abscisse 
 * moins élevée.
 */
public class Q extends AVL<Point> {

    //Constructeurs.
    /**
     * @param data Point.
     * @param left Q.
     * @param right Q.
     * Constructeur de la classe Q qui spécifie la donnée de la racine data, le sous arbre gauche left 
     * et le sous arbre droit right.
     */
    public Q(Point data, Q left, Q right) {
        super(data, left, right);
    }

    /**
     * Constructeur par défaut.
     */
    public Q() {
        super();
    }


    /** 
     * @return Point.
     * Getteur de la racine de l'arbre qui retourne cette racine.
     */
    public Point getDataQ(){
        return getDataAVL();
    }


    /**
     * @return Q.
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     */
    public Q getLeftQ(){
        return (Q) getLeftAVL();
    }


    /**
     * @return Q.
     * Getteur du sous arbre droit de l'arbre qui retourne ce sous arbre droit.
     */
    public Q getRightQ(){
        return (Q) getRightAVL();
    }


    /**
     * @return int.
     * Getteur de la hauteur de l'arbre qui retourne cette hauteur.
     */
    public int getHeightQ(){
        return getHeightAVL();
    }

    /**
     * @param new_dataQ Point.
     *  Assesseur de la racine de l'arbre qui remplace la donnée de la racine (dataQ) par new_dataQ.
     */
    public void setDataQ(Point new_dataQ){
        setDataAVL(new_dataQ);
    }


    /**
     * @param new_leftQ Q.
     * Assesseur du sous arbre gauche de l'arbre qui remplace ce sous arbre gauche (leftQ) par new_leftQ.
     */
    public void setLeftQ(Q new_leftQ){
        setLeftAVL(new_leftQ);
    }


    /**
     * @param new_rightQ Q.
     * Assesseur du sous arbre droit de l'arbre qui remplace ce sous arbre droit (rightQ) par new_treeQ.
     */
    public void setRightQ(Q new_rightQ){
        setRightAVL(new_rightQ);
    }
    

    /**
     * @param new_heightQ int.
     * Assesseur de la hauteur de l'arbre qui remplace cette hauteur (heightQ) par new_heightQ.
     */
    public void setHeightQ(int new_heightQ){
        setHeightAVL(new_heightQ);
    }


    /** 
     * @param point Point.
     * @param insertionUpper boolean.
     * @param s Segment.
     * Insère le point "point" dans l'arbre ou si ce point est déjà présent dans l'arbre et si "point" est 
     * l'extrémité supérieure de s (dans ce cas insertionUpper vaut true sinon il vaut false), on ajoute s au 
     * isUpperOf de "point". Cela nous assure que tous les segments dont "point" est l'extrémité supérieure sont 
     * bien dans la liste isUpperOf de "point". Ceci se fait en respectant l'ordre des arbres de type Q.
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
     * @param point Point.
     * Insère le point "point" dans l'arbre qui est vide.
     */
    public void insertQEmpty(Point point) {
        this.setDataQ(point);
        this.setHeightQ(this.getHeightQ() + 1);
    }

    
    /** 
     * @param point Point.
     * Supprime "point" de l'arbre. Ceci se fait en respectant l'ordre des arbres de type Q.
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
     * @return Point.
     * Supprime le minimum (la donnée la plus à gauche) de l'arbre et retourne ce minimum. Ceci se fait en 
     * respectant l'ordre des arbres de type Q.
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
     * Affiche les données de l'arbre selon l'affichage "inordre" vu au cours de sddI.
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