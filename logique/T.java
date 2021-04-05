package logique;

public class T extends AVL<Segment> {

    // Construteurs
    public T(Segment head, T left, T right) {
        super(head, left, right);
    }

    public T() {
        super();
    }
    /*
    public T getRightT() {
        return (T) getRightAVL();
    }

    public void setRightT(T new_tree){
        setRightAVL(new_tree);
    }
    */

    // Retourne true si le segment en racine se trouve à gauche (si boolean left = true)
    // ou à droite (si boolean left = false) du segment en parametre.
    public boolean isLeftOrRight(Segment segment, double h, boolean left) {
        boolean verif = false;
        Segment head = this.getData();
        
        if (head.isHorizontal()){
            if (segment.isHorizontal()){
                if (left){
                    if (head.getLower().getX() <= segment.getUpper().getX() || head.getLower().getX() - segment.getUpper().getX() <= 0.00000001){
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() <= head.getUpper().getX() || segment.getLower().getX() - head.getUpper().getX() <= 0.00000001){
                        verif = true;
                    }
                }
            }
            else if (segment.isVertical()) {
                if (left){
                    if (head.getLower().getX() < segment.getUpper().getX() || head.getLower().getX() - segment.getUpper().getX() <= 0.00000001) {
                        verif = true;
                    }
                }
                else {
                    if (head.getLower().getX() >= segment.getUpper().getX() || segment.getUpper().getX() - head.getLower().getX() <= 0.00000001) {
                        verif = true;
                    }
                }
            }
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h - segment_param_p) / segment_pente;
                if (left) {
                    if (head.getLower().getX() < segment_x || head.getLower().getX() - segment_x <= 0.00000001){
                        verif = true;
                    }
                }
                else {
                    if (head.getLower().getX() >= segment_x || segment_x - head.getLower().getX() <= 0.00000001){
                        verif = true; 
                    }
                }
            }
        }
        else if (segment.isHorizontal()) {
            if (head.isVertical()) {
                if (left){
                    if (segment.getLower().getX() >= head.getUpper().getX() || head.getUpper().getX() - segment.getLower().getX() <= 0.00000001) {
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() < head.getUpper().getX() || segment.getLower().getX() - head.getUpper().getX() <= 0.00000001) {
                        verif = true;
                    }
                }
            }
            else {
                double head_pente = head.getPente();
                double head_param_p = head.getParam_P();
                double head_x = (h - head_param_p) / head_pente;
                if (left) {
                    if (segment.getLower().getX() >= head_x || head_x - segment.getLower().getX() <= 0.00000001){
                        verif = true;
                    }
                }
                else {
                    if (segment.getLower().getX() < head_x || segment.getLower().getX() - head_x <= 0.00000001){
                        verif = true; 
                    }
                }
            }
        }

        else if (head.isVertical()) {
            if (segment.isVertical()) {
                if (left) {
                    if (head.getUpper().getX() <= segment.getUpper().getX() || head.getUpper().getX() - segment.getUpper().getX() <= 0.00000001) {
                        verif = true;
                    }
                }
                else {
                    if (head.getUpper().getX() >= segment.getUpper().getX() || segment.getUpper().getX() - head.getUpper().getX() <= 0.00000001) {
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
                    if (head_x <= segment_x || head_x - segment_x <= 0.00000001) {
                        verif = true;
                    }
                }
                else {
                    if (head_x >= segment_x || segment_x - head_x <= 0.00000001) {
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
                if (head_x <= segment_x || head_x - segment_x <= 0.00000001) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x || segment_x - head_x <= 0.00000001) {
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
                if (head_x <= segment_x || head_x - segment_x <= 0.00000001) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x || segment_x - head_x <= 0.00000001) {
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
                if (this.rightIsEmpty()) {
                    this.setRightAVL(new T(segment, null, null));
                    //this.getRightAVL().setData(segment);
                    //this.getLeftAVL().setData(this.getData());
                    this.setLeftAVL(new T(getData(), null, null));
                    this.equilibrate();
                }
                else {
                    ((T) this.getRightAVL()).insertT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                if (this.leftIsEmpty()) {
                    this.setLeftAVL(new T(segment, null, null));
                    //this.getLeftAVL().setData(segment);
                    //this.getRightAVL().setData(this.getData());
                    //this.setData(segment);
                    this.setRightAVL(new T(getData(), null, null));
                    this.setData(segment);
                    this.equilibrate();
                }
                else {
                    ((T) this.getLeftAVL()).insertT(segment, h);
                    this.setData(this.getLeftAVL().findMax());
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
                if (!rightIsEmpty() && this.getRightAVL().isLeaf()) {
                    if (this.getRightAVL().getData().equals(segment)) {
                        Segment s = this.getLeftAVL().getData();
                        Integer hauteur = this.getLeftAVL().getHeight();
                        if (!this.getLeftAVL().leftIsEmpty()){
                            this.setRightAVL(this.getLeftAVL().getRightAVL());
                            this.setLeftAVL(this.getLeftAVL().getLeftAVL());
                        }
                        else {
                            this.setRightAVL(null);
                            this.setLeftAVL(null);
                        }
                        this.setData(s);
                        this.setHeight(hauteur);
                        //((T) this.getRightAVL()).suppressTRoot(h);
                        //this.suppressTRoot(h);
                    }
                }
                else {
                    ((T) this.getRightAVL()).suppressT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                //le cas suivant est inutile si arbre gauche restreint à une feuille 
                //alors la donnée est la même que la racine
                /*
                if (!this.leftIsEmpty() && this.getLeftAVL().isLeaf()) {
                    if (this.getLeftAVL().getData().equals(segment)) {
                        ((T) this.getLeftAVL()).suppressTRoot(h);
                        ((T) this.getRightAVL()).suppressTMax(h);
                        this.equilibrate();
                    }
                }
                else {
                */
                if (!this.leftIsEmpty() && !this.getLeftAVL().isLeaf()){
                    ((T) this.getLeftAVL()).suppressT(segment, h);
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
            if (this.getLeftAVL().isLeaf()) {
                Segment s = this.getRightAVL().getData();
                Integer hauteur = this.getRightAVL().getHeight();
                if (!this.getRightAVL().leftIsEmpty()){
                    this.setLeftAVL(this.getRightAVL().getLeftAVL());
                    this.setRightAVL(this.getRightAVL().getRightAVL());
                }
                else {
                    this.setLeftAVL(null);
                    this.setRightAVL(null);
                }
                this.setData(s);
                this.setHeight(hauteur);
            }
            else {
                ((T) this.getLeftAVL()).suppressT(this.getData(), h); //pas ce qui est fait au cours
                this.setData(this.getLeftAVL().findMax());
                this.equilibrate();
            }
        }
    }
}