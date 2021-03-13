public class Q extends AVL<Point> {

    // Construteurs
    public Q(Point data, AVL<Point> left, AVL<Point> right) {
        super(data, left, right);
    }

    public Q() {
        super();
    }

    // Ajoute une donnée dans la struture Q.
    public void insertQ(Point point) {
        if (isEmpty()) {
            this.insertQEmpty(point);
        }
        else {
            Point head = this.getData();
            if (head.isUpper(point)) {
                ((Q) this.getLeftAVL()).insertQ(point);
                this.equilibrate();
            }
            else {
                if (point.isUpper(head)) {
                    ((Q) this.getRightAVL()).insertQ(point);
                    this.equilibrate();
                }
            }
        }
    }

    // Ajoute une donnée dans la struture Q qui est vide.
    public void insertQEmpty(Point point) {
        this.setData(point);
        this.setHeight(this.getHeight() + 1);
    }

    // Supprime point de la structure Q.
    public void suppressQ(Point point) {
        if (!isEmpty()) {
            Point head = this.getData();
            if (head.isUpper(point)) {
                ((Q) this.getLeftAVL()).suppressQ(point);
                this.equilibrate();
            }
            else if (point.isUpper(head)) {
                ((Q) this.getRightAVL()).suppressQ(point);
                this.equilibrate();
            }
            else {
                this.suppressQRoot();
            }
        }
    }

    // Supprime la racine de Q.
    public void suppressQRoot() {
        if (isLeaf()) {
            this.setData(null);
            this.setHeight(0);
        }
        else {
            if (this.getLeftAVL().isEmpty()) {
                AVL<Point> new_Q = this.getRightAVL();
                Point head_new_Q = new_Q.getData();
                this.setData(head_new_Q);
                this.setLeftAVL(new_Q.getLeftAVL());
                this.setRightAVL(new_Q.getRightAVL());
                this.setHeight(new_Q.getHeight());
            }
            else {
                if (this.getRightAVL().isEmpty()) {
                    AVL<Point> new_Q = this.getLeftAVL();
                    Point head_new_Q = new_Q.getData();
                    this.setData(head_new_Q);
                    this.setLeftAVL(new_Q.getLeftAVL());
                    this.setRightAVL(new_Q.getRightAVL());
                    this.setHeight(new_Q.getHeight());
                }
                else {
                    this.setData(suppressQMin());
                    this.equilibrate();
                }
            }
        }
    }

    // Supprime le minimum de Q.
    public Point suppressQMin() {
        if (this.getLeftAVL().isEmpty()) {
            Point min = this.getData();
            AVL<Point> new_Q = this.getRightAVL();
            Point head_new_Q = new_Q.getData();
            this.setData(head_new_Q);
            this.setLeftAVL(new_Q.getLeftAVL());
            this.setRightAVL(new_Q.getRightAVL());
            return min;
        }
        else {
            Point min = ((Q) this.getLeftAVL()).suppressQMin();
            this.equilibrate();
            return min;
        }
    }
}