public class T extends AVL<Segment> {

    // Construteurs
    public T(Segment head, AVL<Segment> left, AVL<Segment> right) {
        super(head, left, right);
    }

    public T() {
        super();
    }

    // Retourne true si le segment se trouve à gauche (si boolean left = true)
    // ou à droite (si boolean left = false) du segment en parametre.
    public boolean isLeftOrRight(Segment segment, double h, boolean left) {
        boolean verif = false;
        Segment head = this.getData();
        
        if (head.isVertical()) {
            if (segment.isVertical()) {
                if (left) {
                    if (head.getUpper().getX() <= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
                else {
                    if (head.getUpper().getX() >= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
            }
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h - segment_param_p) / segment_pente;
                double head_x = head.getUpper().getX();
                if (left) {
                    if (head_x <= segment_x) {
                        verif = true;
                    }
                }
                else {
                    if (head_x >= segment_x) {
                        verif = true;
                    }
                }
            }
        }
        
        else if (segment.isVertical()) {
            double head_pente = head.getPente();
            double head_param_p = head.getParam_P();
            double head_x = (h - head_param_p) / head_pente;
            double segment_x = segment.getUpper().getX();
            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }

        else {
            double head_pente = head.getPente();
            double segment_pente = segment.getPente();
            double head_param_p= head.getParam_P();
            double segment_param_p = segment.getParam_P();

            double head_x = (h - head_param_p) / head_pente;
            double segment_x = (h - segment_param_p) / segment_pente;

            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }
        return verif;
    }

    // Ajoute une donnée dans la struture T.
    public void insertT(Segment segment, double h) {
        if (this.isEmpty()) {
            this.insertTEmpty(segment);
        }
        else {
            if (this.isLeftOrRight(segment, h, true)) {
                if (this.getRightAVL() == null) {
                    this.setRightAVL(new AVL<Segment>(segment, null, null));
                    //this.getRightAVL().setData(segment);
                    //this.getLeftAVL().setData(this.getData());
                    this.equilibrate();
                }
                else {
                    ((T) this.getRightAVL()).insertT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                if (this.getLeftAVL().isEmpty()) {
                    this.getLeftAVL().setData(segment);
                    this.getRightAVL().setData(this.getData());
                    this.setData(segment);
                    this.equilibrate();
                }
                else {
                    ((T) this.getLeftAVL()).insertT(segment, h);
                    this.equilibrate();
                }
            }
        }
    }

    // Ajoute une donnée dans la struture T qui est vide.
    public void insertTEmpty(Segment segment) {
        this.setHeight(this.getHeight() + 1);
        this.setData(segment);
    }

    // Supprime segment de la structure T.
    public void suppressT(Segment segment, double h) {
        if (!this.isEmpty()) {
            if (this.getData().equals(segment)) {
                this.suppressTRoot(h);
            }
            else if (isLeftOrRight(segment, h, true)) {
                if (!this.getLeftAVL().isEmpty() && this.getLeftAVL().isLeaf()) {
                    if (this.getLeftAVL().getData().equals(segment)) {
                        ((T) this.getLeftAVL()).suppressTRoot(h);
                        this.suppressTRoot(h);
                    }
                }
                else {
                    ((T) this.getLeftAVL()).suppressT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                if (!this.getRightAVL().isEmpty() && this.getRightAVL().isLeaf()) {
                    if (this.getRightAVL().getData().equals(segment)) {
                        ((T) this.getRightAVL()).suppressTRoot(h);
                        ((T) this.getLeftAVL()).suppressTMax(h);
                        this.equilibrate();
                    }
                }
                else {
                    ((T) this.getRightAVL()).suppressT(segment, h);
                    this.equilibrate();
                }
            }
        }
    }

    public Segment suppressTMin(double h) {
        Segment minimum;
        if (this.getLeftAVL().isEmpty()) {
            minimum = this.getData();
            //this.setData(this.getLeftAVL().getData());
            //this.setHeight(this.getLeftAVL().getHeight());
            //this.setLeftAVL(this.getLeftAVL().getRightAVL());
            //this.setRightAVL(this.getLeftAVL().getLeftAVL());
            this.suppressTRoot(h);
        }
        else {
            minimum = ((T) this.getLeftAVL()).suppressTMin(h);
            this.equilibrate();
        }
        return minimum;
    }

    public Segment suppressTMax(double h) {
        Segment maximum;
        if (this.getRightAVL().isEmpty()) {
            maximum = this.getData();
            //this.setData(this.getRightAVL().getData());
            //this.setHeight(this.getRightAVL().getHeight());
            //this.setRightAVL(this.getRightAVL().getRightAVL());
            //this.setLeftAVL(this.getRightAVL().getRightAVL());
            this.suppressTRoot(h);
        }
        else {
            maximum = ((T) this.getRightAVL()).suppressTMax(h);
            this.equilibrate();
        }
        
        return maximum;
    }

    // Supprime la racine de T.
    public void suppressTRoot(double h) {
        if(isLeaf()) {
            this.setData(null);
            this.setHeight(0);
        }
        else {
            if (this.getLeftAVL().isEmpty()) {
                this.setData(this.getRightAVL().getData());
                this.setHeight(this.getRightAVL().getHeight());
                this.setLeftAVL(this.getRightAVL().getLeftAVL());
                this.setRightAVL(this.getRightAVL().getRightAVL());
            }
            else if (this.getRightAVL().isEmpty()) {
                this.setData(this.getLeftAVL().getData());
                this.setHeight(this.getLeftAVL().getHeight());
                this.setRightAVL(this.getLeftAVL().getRightAVL());
                this.setLeftAVL(this.getLeftAVL().getLeftAVL());
            }
            else {
                ((T) this.getLeftAVL()).suppressT(this.getData(), h);
                this.setData(this.getLeftAVL().findMax());
            }
        }
    }
}