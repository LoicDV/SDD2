package src.main.java.logique;

// Import basique.
import java.lang.Math;

/**
 * Classe permettant de manipuler des AVL. 
 * Dans notre cadre, Fg est soit le type Point soit le type Segment.
 */
public class AVL<Fg> {
    
    //Variables d'instances.
    //La donnée de racine de l'arbre.
    private Fg data;
    //Les sous arbres gauche et droit de l'arbre.
    private AVL<Fg> leftAVL, rightAVL;
    //La hauteur de l'arbre.
    private int height;
    

    //Constructeurs.
    /**
     * @param data Fg.
     * @param leftAVL AVL de Fg.
     * @param rightAVL AVL de Fg.
     * Constructeur de la classe AVL qui spécifie la donnée de la racine data, le sous arbre gauche leftAVL 
     * et le sous arbre droit rightAVL.
     */
    public AVL(Fg data, AVL<Fg> leftAVL, AVL<Fg> rightAVL) {
        this.data = data;
        this.leftAVL = leftAVL;
        this.rightAVL = rightAVL;
        this.calculHeight();
    }


    /**
     * Constructeur par défaut.
     */
    public AVL() {
        this.data = null;
        this.leftAVL = null;
        this.rightAVL = null;
        this.height = 0;
    }

    
    //Assesseurs et getteurs.
    //(Attention n'utilisez les assesseurs que lorsque le changement respecte l'ordre interne de l'arbre.)
    /** 
     * @return Fg.
     * Getteur de la racine de l'arbre qui retourne cette racine.
     */
    public Fg getDataAVL() {
        return this.data;
    }

    
    /** 
     * @return AVL de Fg.
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     */
    public AVL<Fg> getLeftAVL() {
        return this.leftAVL;
    }

    
    /** 
     * @return AVL de Fg.
     * Getteur du sous arbre droit de l'arbre qui retourne ce sous arbre droit.
     */
    public AVL<Fg> getRightAVL() {
        return this.rightAVL;
    }

    
    /** 
     * @return int.
     * Getteur de la hauteur de l'arbre qui retourne cette hauteur.
     */
    public int getHeightAVL() {
        return this.height;
    }
    

    /** 
     * @param new_data Fg.
     * Assesseur de la racine de l'arbre qui remplace la donnée de la racine (data) par new_data. 
     */
    public void setDataAVL(Fg new_data) {
        this.data = new_data;
    }

    
    /** 
     * @param new_tree AVL de Fg.
     * Assesseur du sous arbre gauche de l'arbre qui remplace ce sous arbre gauche (leftAVL) par new_tree.
     */
    public void setLeftAVL(AVL<Fg> new_tree) {
        this.leftAVL = new_tree;
    }

    
    /** 
     * @param new_tree AVL de Fg.
     * Assesseur du sous arbre droit de l'arbre qui remplace ce sous arbre droit (rightAVL) par new_tree.
     */
    public void setRightAVL(AVL<Fg> new_tree) {
        this.rightAVL = new_tree;
    }

    
    /** 
     * @param new_height int.
     * Assesseur de la hauteur de l'arbre qui remplace cette hauteur (height) par new_height.
     */
    public void setHeightAVL(int new_height) {
        this.height = new_height;
    }

    
    /** 
     * @return boolean.
     * Retourne true si l'arbre est vide, false sinon.
     */
    public boolean isEmpty() {
		if (this.getDataAVL() == null && this.leftAVL == null && this.rightAVL == null) {
            return true;
        }
		return false;
    }

    
    /** 
     * @return boolean.
     * Retourne true si l'arbre est réduit à sa racine, false sinon.
     */
    public boolean isLeaf() {
        boolean verif = false;
        if (this.leftIsEmpty() && (this.rightIsEmpty())) {
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @return boolean.
     * Retourne true si le sous arbre gauche de l'arbre est vide ou bien false sinon.
     */
    public boolean leftIsEmpty() {
        boolean verif = false;
        if (this.getLeftAVL() == null || this.getLeftAVL().getDataAVL() == null) {
            verif = true;
        }
        return verif;
    }

    
    /** 
     * @return boolean.
     * Retourne true si le sous arbre droit de l'arbre est vide ou bien false sinon.
     */
    public boolean rightIsEmpty() {
        boolean verif = false;
        if (this.getRightAVL() == null || this.getRightAVL().getDataAVL() == null) {
            verif = true;
        }
        return verif;
    }
    
    
    /** 
     * @return int.
     * Calcule la hauteur de l'arbre et retourne cette hauteur.
     */
    public int calculHeight() {
        if (isEmpty()) {
            this.height = 0;
        }
        else {
            if (isLeaf()) {
                this.height = 1;
            }
            else {
                if (leftIsEmpty()) {
                    this.height = 1 + this.getRightAVL().getHeightAVL();
                }
                else {
                    if (rightIsEmpty()) {
                        this.height = 1 + this.getLeftAVL().getHeightAVL();
                    }
                    else {
                        this.height = 1 + 
                        Math.max(this.getLeftAVL().getHeightAVL(), this.getRightAVL().getHeightAVL());
                    }
                }
            }
        }
        return this.height;
    }

    
    /** 
     * @return int.
     * Calcule la balance de l'arbre et la retourne.
     */
	public int balance() {
        int x = 0;
		if (isEmpty() || isLeaf()) 
			x = 0;
        else if (getRightAVL() == null) {
            x = - getLeftAVL().getHeightAVL();
        }
        else if (getLeftAVL() == null) {
            x = getRightAVL().getHeightAVL();
        }
        else {
            x = getRightAVL().getHeightAVL() - getLeftAVL().getHeightAVL();
        }
        return x;
	}


    /**
     * Applique une rotation gauche à l'arbre.
     */
    public void leftRotation() {
		Fg data = this.getDataAVL();
		AVL<Fg> right = this.getRightAVL();
		this.setDataAVL(right.getDataAVL());
		this.setRightAVL(right.getRightAVL());
		right.setDataAVL(data);
		right.setRightAVL(right.getLeftAVL());
		right.setLeftAVL(this.getLeftAVL());
		this.setLeftAVL(right);
		right.calculHeight();
		this.calculHeight();
	}


    /**
     * Applique une rotation droite à l'arbre.
     */
	public void rightRotation() {
		Fg data = this.getDataAVL();
		AVL<Fg> left = this.getLeftAVL();
		this.setDataAVL(left.getDataAVL());
		this.setLeftAVL(left.getLeftAVL());
		left.setDataAVL(data);
		left.setLeftAVL(left.getRightAVL());
		left.setRightAVL(getRightAVL());
		setRightAVL(left);
		left.calculHeight();
		calculHeight();
	}


    /**
     * Rééquilibre notre arbre.
     */
    public void equilibrate() {
        if (balance() == 2) {
		    if (getRightAVL().balance() < 0) {
			    getRightAVL().rightRotation();
            }
		    leftRotation();
        }
	    else if (balance() == -2) {
		    if (getLeftAVL().balance() > 0) {
			    getLeftAVL().leftRotation();
            }
			rightRotation();
        }
    	else {
            calculHeight();
        }
    }

    
    /** 
     * @return Fg.
     * Retourne la donnée maximum (la plus à droite) de l'arbre.
     */
    public Fg findMax() {
        Fg maximum;
        if (this.rightIsEmpty()) {
            maximum = this.getDataAVL();
        }
        else {
            maximum = this.getRightAVL().findMax();
        }
        return maximum;
    }

    
    /** 
     * @return Fg.
     * Retourne la donnée minimum (la plus à gauche) de l'arbre.
     */
    public Fg findMin() {
        Fg minimum;
        if (this.leftIsEmpty()) {
            minimum = this.getDataAVL();
        }
        else {
            minimum = this.getLeftAVL().findMin();
        }
        return minimum;
    }


    /**
     * Affiche les données de l'arbre selon l'affichage "inordre" vu au cours de sddI.
     */
    public void inordre() {
		if (this != null) {
            if (this.getLeftAVL() != null) {
                this.getLeftAVL().inordre();
            }
            if (this.getDataAVL() != null) {
                ((Segment) this.getDataAVL()).print();
            }
            if (this.getRightAVL() != null) {
                this.getRightAVL().inordre();
            }
		}
	}
}