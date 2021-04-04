package logique;

public class Q extends AVL<Point> {

    // Construteurs
    public Q(Point data, AVL<Point> left, AVL<Point> right) {
        super(data, left, right);
    }

    public Q() {
        super();
    }

    
    /** 
     * @param point
     * @param insertionUpper
     * @param s
     */
    // Ajoute une donnée dans la struture Q.
    public void insertQ(Point point, boolean insertionUpper, Segment s) {
        if (isEmpty()) {
            this.insertQEmpty(point);
        }
        else {
            Point head = this.getData();
            if (insertionUpper || head.getX() == point.getX() || head.getY() == point.getY()){
                head.getIsUpperOf().add(s);
            }
            if (point.isUpper(head)) {
                if (this.leftIsEmpty()){
                    this.setLeftAVL(new Q(point, null, null));
                    this.equilibrate();
                }
                else{
                    ((Q) this.getLeftAVL()).insertQ(point, insertionUpper, s);
                    this.equilibrate();
                }
            }
            else {
                if (head.isUpper(point)) {
                    if (this.rightIsEmpty()){
                        this.setRightAVL(new Q(point, null, null));
                        this.equilibrate();
                    }
                    else{
                        ((Q) this.getRightAVL()).insertQ(point, insertionUpper, s);
                        this.equilibrate();
                    }
                }
            }
        }
    }

    
    /** 
     * @param point
     */
    // Ajoute une donnée dans la struture Q qui est vide.
    public void insertQEmpty(Point point) {
        this.setData(point);
        this.setHeight(this.getHeight() + 1);
    }

    
    /** 
     * @param point
     */
    // Supprime point de la structure Q.
    public void suppressQ(Point point) {
        if (!isEmpty()) {
            Point head = this.getData();
            if (point.isUpper(head)) {
                if (!this.leftIsEmpty()){
                    ((Q) this.getLeftAVL()).suppressQ(point);
                    this.equilibrate();
                }
            }
            else if (head.isUpper(point)) {
                if (!this.rightIsEmpty()){
                    ((Q) this.getRightAVL()).suppressQ(point);
                    this.equilibrate();
                }
            }
            else {
                this.suppressQRoot();
            }
        }
    }

    // Supprime la racine de Q.
    public void suppressQRoot() {
        if (this.isLeaf()) {
            this.setData(null);
            this.setHeight(0);
        }
        else {
            if (this.leftIsEmpty()) {
                AVL<Point> new_Q = this.getRightAVL();
                Point head_new_Q = new_Q.getData();
                this.setData(head_new_Q);
                this.setLeftAVL(new_Q.getLeftAVL());
                this.setRightAVL(new_Q.getRightAVL());
                this.setHeight(new_Q.getHeight());
            }
            else {
                if (this.rightIsEmpty()) {
                    AVL<Point> new_Q = this.getLeftAVL();
                    Point head_new_Q = new_Q.getData();
                    this.setData(head_new_Q);
                    this.setLeftAVL(new_Q.getLeftAVL());
                    this.setRightAVL(new_Q.getRightAVL());
                    this.setHeight(new_Q.getHeight());
                }
                else {
                    this.setData(((Q) this.getLeftAVL()).suppressQMin());
                    this.equilibrate();
                }
            }
        }
    }

    
    /** 
     * @return Point
     */
    // Supprime le minimum de Q.
    public Point suppressQMin() {
        if (this.leftIsEmpty()) {
            Point min = this.getData();
            /*
            if (!this.rightIsEmpty()){
                AVL<Point> new_Q = this.getRightAVL();
                Point head_new_Q = new_Q.getData();
                this.setData(head_new_Q);
                this.setLeftAVL(new_Q.getLeftAVL());
                this.setRightAVL(new_Q.getRightAVL()); 
            }
            else {
                this.setData(null);
                this.setLeftAVL(null);
                this.setRightAVL(null);
            }
            */
            this.suppressQRoot();
            return min;
        }
        else {
            Point min = ((Q) this.getLeftAVL()).suppressQMin();
            this.equilibrate();
            return min;
        }
    }

    public void inordreQ() {
		if (this != null) {
            if (this.getLeftAVL() != null) {
                ((Q) this.getLeftAVL()).inordreQ();
            }
            if (this.getData() != null) {
                ((Point) this.getData()).print();
            }
            if (this.getRightAVL() != null) {
                ((Q) this.getRightAVL()).inordreQ();
            }
		}
	}

    
    /** 
     * @return Point
     */
    public Point findMinQ(){
        Point minimum;
        if (this.leftIsEmpty()) {
            minimum = this.getData();
        }
        else {
            minimum = ((Q) this.getLeftAVL()).findMinQ();
        }
        return minimum;
    }
}