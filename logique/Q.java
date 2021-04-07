package logique;

public class Q extends AVL<Point> {

    private Point data;
    private Q left;
    private Q right;

    // Construteurs
    /**
     * @param data Point.
     * @param left Q
     * @param right Q
     */
    public Q(Point data, Q left, Q right) {
        super(data, left, right);
    }

    public Q() {
        super();
    }

    // Assesseur/getteur.

    /**
     * Recupere le point en racine.
     */
    public Point getDataQ() {
        return this.data;
    }

    /**
     * Recupere le sous arbre gauche.
     */
    public Q getLeftQ() {
        return this.left;
    }

    /**
     * Recupere le sous arbre droit.
     */
    public Q getRightQ() {
        return this.right;
    }

    /** 
     * @param newPoint Point.
     * Remplace la donnee par notre nouveau point.
     */
    public void setDataQ(Point newPoint) {
        this.data = newPoint;
    }

    /** 
     * @param newTreeQ Q.
     * Remplace notre sous-arbre gauche par le nouvel arbre Q.
     */
    public void setLeftQ(Q newTreeQ) {
        this.left = newTreeQ;
    }

    
    /** 
     * @param newTreeQ Q.
     * Remplace notre sous-arbre droit par le nouvel arbre Q.
     */
    public void setRightQ(Q newTreeQ) {
        this.right = newTreeQ;
    }

    /** 
     * @param point Point.
     * @param insertionUpper boolean.
     * @param s Segment.
     * Ajoute une donnee dans la struture Q.
     */
    public void insertQ(Point point, boolean insertionUpper, Segment s) {
        if (isEmpty()) {
            this.insertQEmpty(point);
        }
        else {
            Point head = this.getData();
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
     * Ajoute une donnee dans la struture Q qui est vide.
     */
    public void insertQEmpty(Point point) {
        this.setData(point);
        this.setHeight(this.getHeight() + 1);
    }

    
    /** 
     * @param point
     * Supprime point de la structure Q.
     */
    public void suppressQ(Point point) {
        if (!isEmpty()) {
            Point head = this.getData();
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
     * Supprime la racine de Q.
     */ 
    public void suppressQRoot() {
        if (this.isLeaf()) {
            this.setData(null);
            this.setHeight(0);
        }
        else {
            if (this.leftIsEmpty()) {
                Q new_Q = this.getRightQ();
                Point head_new_Q = new_Q.getData();
                this.setData(head_new_Q);
                this.setLeftQ(new_Q.getLeftQ());
                this.setRightQ(new_Q.getRightQ());
                this.setHeight(new_Q.getHeight());
            }
            else {
                if (this.rightIsEmpty()) {
                    Q new_Q = this.getLeftQ();
                    Point head_new_Q = new_Q.getData();
                    this.setData(head_new_Q);
                    this.setLeftQ(new_Q.getLeftQ());
                    this.setRightQ(new_Q.getRightQ());
                    this.setHeight(new_Q.getHeight());
                }
                else {
                    this.setData(this.getLeftQ().suppressQMin());
                    this.equilibrate();
                }
            }
        }
    }

    /** 
     * @return Point.
     * Supprime le minimum de Q.
     */
    public Point suppressQMin() {
        if (this.leftIsEmpty()) {
            Point min = this.getData();
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
     * Execute l'affichage inordre sur l'arbre Q.
     */
    public void inordreQ() {
		if (this != null) {
            if (this.getLeftQ() != null) {
                this.getLeftQ().inordreQ();
            }
            if (this.getData() != null) {
                this.getData().print();
            }
            if (this.getRightQ() != null) {
                this.getRightQ().inordreQ();
            }
		}
	}

    /** 
     * @return Point.
     * Trouve le point le plus a gauche de l'arbre (minimum des points suivant la definition de Q).
     */
    public Point findMinQ(){
        Point minimum;
        if (this.leftIsEmpty()) {
            minimum = this.getData();
        }
        else {
            minimum = this.getLeftQ().findMinQ();
        }
        return minimum;
    }
}