package logique;

public class T extends AVL<Segment> {

    private Segment head;
    private T left;
    private T right;

    // Construteurs
    /**
     * @param head Segment.
     * @param left T.
     * @param right T.
     */
    public T(Segment head, T left, T right) {
        super(head, left, right);
    }

    public T() {
        super();
    }

    // Assesseur/getteur.

    /**
     * Recupere le point en racine.
     */
    public Segment getHeadT() {
        return this.head;
    }

    /**
     * Recupere le sous arbre gauche.
     */
    public T getLeftT() {
        return this.left;
    }

    /**
     * Recupere le sous arbre droit.
     */
    public T getRightT() {
        return this.right;
    }

    /** 
     * @param newSegment Segment.
     * Remplace la donnee par notre nouveau segment.
     */
    public void setHeadT(Segment newSegment) {
        this.head = newSegment;
    }

    /** 
     * @param newTreeT T.
     * Remplace notre sous-arbre gauche par le nouvel arbre T.
     */
    public void setLeftT(T newTreeT) {
        this.left = newTreeT;
    }
    
    /** 
     * @param newTreeT T.
     * Remplace notre sous-arbre droit par le nouvel arbre T.
     */
    public void setRightT(T newTreeT) {
        this.right = newTreeT;
    }
    
    /** 
     * @param segment Segment.
     * @param h double.
     * @param left boolean.
     * @return boolean.
     * Retourne true si le segment en racine se trouve a gauche (si boolean left = true)
     * ou a droite (si boolean left = false) du segment en parametre.
     */
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
                    if (head.getLower().getX() < segment.getUpper().getX() 
                    || (head.getLower().getX() - segment.getUpper().getX() <= 0.00000001 && head.getLower().getX() != segment.getUpper().getX())) {
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
                    if (head.getLower().getX() < segment_x 
                    || (head.getLower().getX() - segment_x <= 0.00000001 && head.getLower().getX() != segment_x)){
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
                    if (segment.getLower().getX() < head.getUpper().getX() 
                    || (segment.getLower().getX() - head.getUpper().getX() <= 0.00000001 && segment.getLower().getX() != head.getUpper().getX())) {
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
                    if (segment.getLower().getX() < head_x 
                    || (segment.getLower().getX() - head_x <= 0.00000001 && segment.getLower().getX() != head_x)){
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
    
    /** 
     * @param segment Segment.
     * @param h double.
     * Ajoute une donnee dans la struture T.
     */
    public void insertT(Segment segment, double h) {
        if (this.isEmpty()) {
            this.insertTEmpty(segment);
        }
        else {
            if (!this.getData().equalSegment(segment)){
                if (this.isLeftOrRight(segment, h, true)) {
                    if (this.rightIsEmpty()) {
                        this.setRightT(new T(segment, null, null));
                        this.setLeftT(new T(getData(), null, null));
                        this.equilibrate();
                    }
                    else {
                        this.getRightT().insertT(segment, h);
                        this.equilibrate();
                    }
                }
                else {
                    if (this.leftIsEmpty()) {
                        this.setLeftT(new T(segment, null, null));
                        this.setRightT(new T(getData(), null, null));
                        this.setData(segment);
                        this.equilibrate();
                    }
                    else {
                        this.getLeftT().insertT(segment, h);
                        this.setData(this.getLeftT().findMax());
                        this.equilibrate();
                    }
                }
            }
        }
    }
    
    /** 
     * @param segment Segment.
     * Ajoute une donnee dans la struture T qui est vide.
     */
    public void insertTEmpty(Segment segment) {
        this.setHeight(this.getHeight() + 1);
        this.setData(segment);
    }
    
    /** 
     * @param segment Segment.
     * @param h double.
     * Supprime segment de la structure T.
     */
    public void suppressT(Segment segment, double h) {
        if (!this.isEmpty()) {
            if (this.getData().equals(segment)) {
                this.suppressTRoot(h);
            }
            else if (isLeftOrRight(segment, h, true)) {
                if (!rightIsEmpty() && this.getRightT().isLeaf()) {
                    if (this.getRightT().getData().equals(segment)) {
                        Segment s = this.getLeftT().getData();
                        Integer hauteur = this.getLeftT().getHeight();
                        if (!this.getLeftT().leftIsEmpty()){
                            this.setRightT(this.getLeftT().getRightT());
                            this.setLeftT(this.getLeftT().getLeftT());
                        }
                        else {
                            this.setRightT(null);
                            this.setLeftT(null);
                        }
                        this.setData(s);
                        this.setHeight(hauteur);
                    }
                }
                else {
                    this.getRightT().suppressT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                if (!this.leftIsEmpty() && !this.getLeftT().isLeaf()){
                    this.getLeftT().suppressT(segment, h);
                    this.equilibrate();
                }
            }
        }
    }
    
    /** 
     * @param h double.
     * @return Segment.
     * Supprime le minimum de l'arbre T et le retourne.
     */
    public Segment suppressTMin(double h) {
        Segment minimum;
        if (this.getLeftT().isEmpty()) {
            minimum = this.getData();
            this.suppressTRoot(h);
        }
        else {
            minimum = this.getLeftT().suppressTMin(h);
            this.equilibrate();
        }
        return minimum;
    }
    
    /** 
     * @param h double.
     * @return Segment.
     * Supprime le maximum de l'arbre T et le retourne.
     */
    public Segment suppressTMax(double h) {
        Segment maximum;
        if (this.getRightT().isEmpty()) {
            maximum = this.getData();
            this.suppressTRoot(h);
        }
        else {
            maximum = this.getRightT().suppressTMax(h);
            this.equilibrate();
        }
        
        return maximum;
    }
    
    /** 
     * @param h double.
     * Supprime la racine de T.
     */
    public void suppressTRoot(double h) {
        if(isLeaf()) {
            this.setData(null);
            this.setHeight(0);
        }
        else {
            if (this.getLeftT().isLeaf()) {
                Segment s = this.getRightT().getData();
                Integer hauteur = this.getRightT().getHeight();
                if (!this.getRightT().leftIsEmpty()){
                    this.setLeftT(this.getRightT().getLeftT());
                    this.setRightT(this.getRightT().getRightT());
                }
                else {
                    this.setLeftT(null);
                    this.setRightT(null);
                }
                this.setData(s);
                this.setHeight(hauteur);
            }
            else {
                this.getLeftT().suppressT(this.getData(), h); //pas ce qui est fait au cours
                this.setData(this.getLeftT().findMax());
                this.equilibrate();
            }
        }
    }
}