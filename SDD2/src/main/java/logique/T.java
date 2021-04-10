package src.main.java.logique;

/**
 * Classe qui nous permet de manipuler des arbres de type T.
 * Ces arbres contiennent les segments croisés par la sweep line.
 * Les feuilles sont ordonnées selon l'ordre de gauche à droite des segments et pour 
 * chaque noeud interne, le segment qu'il contient est le segment se situant dans la
 * feuille la plus à droite de son sous arbre gauche.
 */
public class T extends AVL<Segment> {

    //Constructeurs.
    /**
     * @param data Segment.
     * @param left T.
     * @param right T.
     * Constructeur de la classe T qui spécifie la donnée de la racine head, le sous arbre gauche left 
     * et le sous arbre droit right.
     */
    public T(Segment data, T left, T right) {
        super(data, left, right);
    }


    /**
     * Constructeur par défaut.
     */
    public T() {
        super();
    }


    /** 
     * @return Segment.
     * Getteur de la racine de l'arbre qui retourne cette racine.
     */
    public Segment getDataT(){
        return getDataAVL();
    }


    /**
     * @return T.
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     */
    public T getLeftT(){
        return (T) getLeftAVL();
    }


    /**
     * @return T.
     * Getteur du sous arbre droit de l'arbre qui retourne ce sous arbre droit.
     */
    public T getRightT(){
        return (T) getRightAVL();
    }


    /**
     * @return int.
     * Getteur de la hauteur de l'arbre qui retourne cette hauteur.
     */
    public int getHeightT(){
        return getHeightAVL();
    }

    /**
     * @param new_dataT Segment.
     *  Assesseur de la racine de l'arbre qui remplace la donnée de la racine (dataT) par new_dataT.
     */
    public void setDataT(Segment new_dataT){
        setDataAVL(new_dataT);;
    }


    /**
     * @param new_leftT T.
     * Assesseur du sous arbre gauche de l'arbre qui remplace ce sous arbre gauche (leftT) par new_leftT.
     */
    public void setLeftT(T new_leftT){
        setLeftAVL(new_leftT);
    }


    /**
     * @param new_rightT T.
     * Assesseur du sous arbre droit de l'arbre qui remplace ce sous arbre droit (rightT) par new_treeT.
     */
    public void setRightT(T new_rightT){
        setRightAVL(new_rightT);;
    }
    

    /**
     * @param new_heightT int.
     * Assesseur de la hauteur de l'arbre qui remplace cette hauteur (heightT) par new_heightT.
     */
    public void setHeightT(int new_heightT){
        setHeightAVL(new_heightT);;
    }


    /** 
     * @param segment Segment.
     * @param h double.
     * @param h2 double.
     * @param now_x double.
     * @param left boolean.
     * @return boolean.
     * Retourne true si le segment en racine se trouve à gauche (si boolean left = true)
     * ou à droite (si boolean left = false) du segment en parametre, sinon retourne false.
     * Le test se fait en prenant les points appartenants à ces deux segments se trouvant à une certaine hauteur
     * et en regardant lequel de ces points à l'abscisse la plus ou la moins élevée selon si on veut savoir 
     * si un segment est à gauche ou à droite de l'autre. Cette hauteur est généralement le double h, cependant, 
     * selon le moment où l'on se trouve lorsque l'on appelle la fonction, cette hauteur peut être légèrement 
     * supérieure ou inférieure à la hauteur de la sweep line. C'est pour cela que dans le cas où l'un des 
     * segments est horizontal, on utilisera h2 qui est la hauteur réelle de la sweep line et le double now_x 
     * qui nous permettra de savoir si le segment horizontal doit être considéré comme étant à gauche ou à droite
     * de l'autre segment. Notez que pour certains branchements des conditions sont rajoutées afin de gérer les 
     * erreurs de précisions. Il est également important de remarquer que si les deux segments se croisent en un  
     * point ayant son ordonnée égale à la hauteur utilisée dans le test (h ou h2), ils sont considérés comme
     * étant à gauche et à droite l'un de l'autre.
     */
    public boolean isLeftOrRight(Segment segment, double h, double h2, double now_x, boolean left) {
        boolean verif = false;
        Segment head = this.getDataT();
        //Cas où le segment head est horizontal.
        if (head.isHorizontal()){
            //Cas où "segment" est horizontal.
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
            //Cas où "segment" est vertical.
            else if (segment.isVertical()) {
                if (left){
                    if (now_x < segment.getUpper().getX()
                    || now_x - segment.getUpper().getX() <= 0.00000001 && now_x != segment.getUpper().getX()){
                        verif = true;
                    }
                }
                else {
                    if (now_x >= segment.getUpper().getX() || segment.getUpper().getX() - now_x <= 0.00000001){
                        verif = true;
                    }
                }
            }
            //Cas général pour "segment".
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h2 - segment_param_p) / segment_pente;
                if (left) {
                    if (now_x < segment_x 
                    || (now_x - segment_x <= 0.00000001 && now_x != segment_x)){
                        verif = true;
                    }
                }
                else {
                    if (now_x >= segment_x || segment_x - now_x <= 0.00000001){
                        verif = true; 
                    }
                }
            }
        }
        //Cas où "segment" est horizontal.
        else if (segment.isHorizontal()) {
            //Cas où le segment head est vertical.
            if (head.isVertical()) {
                if (left){
                    if (now_x >= head.getUpper().getX() || head.getUpper().getX() - now_x <= 0.00000001) {
                        verif = true;
                    }
                }
                else {
                    if (now_x < head.getUpper().getX() 
                    || (now_x - head.getUpper().getX() <= 0.00000001 && now_x != head.getUpper().getX())) {
                        verif = true;
                    }
                }
            }
            //Cas général pour le segment head.
            else {
                double head_pente = head.getPente();
                double head_param_p = head.getParam_P();
                double head_x = (h2 - head_param_p) / head_pente;
                if (left) {
                    if (now_x >= head_x || head_x - now_x <= 0.00000001){
                        verif = true;
                    }
                }
                else {
                    if (now_x < head_x 
                    || (now_x - head_x <= 0.00000001 && now_x != head_x)){
                        verif = true; 
                    }
                }
            }
        }
        //Cas où le segment head est vertical.
        else if (head.isVertical()) {
            //Cas où "segment" est vertical.
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
            //Cas général pour "segment".
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
        //Cas où "segment" est vertical et head est non horizontal et non vertical.
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
        //Cas général.
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
     * @param h2 double.
     * @param now_x double.
     * Insère le segment "segment" dans l'arbre, les paramètres h, h2 et now_x sont utiles lorsque l'on doit
     * appeller la fonction isLeftOrRight afin de se "diriger" dans l'arbre. L'insertion respecte la 
     * définition des arbres de type T.
     */
    public void insertT(Segment segment, double h, double h2, double now_x) {
        if (this.isEmpty()) {
            this.insertTEmpty(segment);
        }
        else {
            if (!this.getDataT().equalSegment(segment)){
                if (this.isLeftOrRight(segment, h, h2, now_x, true)) {
                    if (this.rightIsEmpty()) {
                        this.setRightT(new T(segment, null, null));
                        this.setLeftT(new T(getDataT(), null, null));
                        this.equilibrate();
                    }
                    else {
                        this.getRightT().insertT(segment, h, h2, now_x);
                        this.equilibrate();
                    }
                }
                else {
                    if (this.leftIsEmpty()) {
                        this.setLeftT(new T(segment, null, null));
                        this.setRightT(new T(getDataT(), null, null));
                        this.setDataT(segment);
                        this.equilibrate();
                    }
                    else {
                        this.getLeftT().insertT(segment, h, h2, now_x);
                        this.setDataT(this.getLeftT().findMax());
                        this.equilibrate();
                    }
                }
            }
        }
    }

    
    /** 
     * @param segment Segment.
     * Insère le segment "segment" dans l'arbre qui est vide.
     */
    public void insertTEmpty(Segment segment) {
        this.setHeightT(this.getHeightT() + 1);
        this.setDataT(segment);
    }

    
    /** 
     * @param segment Segment.
     * @param h double.
     * @param h2 double.
     * @param now_x double.
     * Supprime le segment "segment" de l'arbre, à nouveau, les paramètres h, h2 et now_x sont utiles 
     * lorsque l'on doit appeller la fonction isLeftOrRight afin de se "diriger" dans l'arbre et la suppression 
     * respecte bien la définition des arbres de type T.
     */
    public void suppressT(Segment segment, double h, double h2, double now_x) {
        if (!this.isEmpty()) {
            if (this.getDataT().equals(segment)) {
                this.suppressTRoot(h, h2, now_x);
            }
            else if (isLeftOrRight(segment, h, h2, now_x, true)) {
                if (!rightIsEmpty() && this.getRightT().isLeaf()) {
                    if (this.getRightT().getDataT().equals(segment)) {
                        Segment s = this.getLeftT().getDataT();
                        int hauteur = this.getLeftT().getHeightT();
                        if (!this.getLeftT().leftIsEmpty()){
                            this.setRightT(this.getLeftT().getRightT());
                            this.setLeftAVL(this.getLeftT().getLeftT());
                        }
                        else {
                            this.setRightT(null);
                            this.setLeftT(null);
                        }
                        this.setDataT(s);
                        this.setHeightT(hauteur);
                    }
                }
                else {
                    if (!this.rightIsEmpty()){
                        this.getRightT().suppressT(segment, h, h2, now_x);
                        this.equilibrate();
                    }
                }
            }
            else {
                if (!this.leftIsEmpty() && !this.getLeftT().isLeaf()){
                    this.getLeftT().suppressT(segment, h, h2, now_x);
                    this.equilibrate();
                }
            }
        }
    }
    
    
    /** 
     * @param h double.
     * @param h2 double.
     * @param now_x double.
     * Supprime la racine de l'arbre, encore une fois, les paramètres h, h2 et now_x sont utiles lorsque l'on 
     * doit appeller la fonction isLeftOrRight afin de se "diriger" dans l'arbre et onrespecte bien la 
     * définition des arbres de type T.
     */
    public void suppressTRoot(double h, double h2, double now_x) {
        if(isLeaf()) {
            this.setDataT(null);
            this.setHeightT(0);
        }
        else {
            if (this.getLeftT().isLeaf()) {
                Segment s = this.getRightT().getDataT();
                Integer hauteur = this.getRightT().getHeightT();
                if (!this.getRightT().leftIsEmpty()){
                    this.setLeftT(this.getRightT().getLeftT());
                    this.setRightT(this.getRightT().getRightT());
                }
                else {
                    this.setLeftT(null);
                    this.setRightT(null);
                }
                this.setDataT(s);
                this.setHeightT(hauteur);
            }
            else {
                this.getLeftT().suppressT(this.getDataT(), h, h2, now_x); //pas ce qui est fait au cours
                this.setDataT(this.getLeftT().findMax());
                this.equilibrate();
            }
        }
    }
}