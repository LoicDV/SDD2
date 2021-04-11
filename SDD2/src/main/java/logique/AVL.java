package src.main.java.logique;

// Import basique.
import java.lang.Math;

/**
 * Classe permettant de manipuler des AVL.
 * Dans notre cadre, Fg est soit le type Point soit le type Segment.
 */
public class AVL<Fg> {

    //Variables d'instances.
    //La donnee de racine de l'arbre.
    private Fg data;
    //Les sous arbres gauche et droit de l'arbre.
    private AVL<Fg> leftAVL, rightAVL;
    //La hauteur de l'arbre.
    private int height;


    //Constructeurs.
    /**
     * Constructeur de la classe AVL qui specifie la donnee de la racine data, le sous arbre gauche leftAVL
     * et le sous arbre droit rightAVL.
     * @param data Fg.
     * @param leftAVL AVL de Fg.
     * @param rightAVL AVL de Fg.
     */
    public AVL(Fg data, AVL<Fg> leftAVL, AVL<Fg> rightAVL) {
        this.data = data;
        this.leftAVL = leftAVL;
        this.rightAVL = rightAVL;
        this.calculHeight();
    }


    /**
     * Constructeur par defaut.
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
     * Getteur de la racine de l'arbre qui retourne cette racine.
     * @return Fg.
     */
    public Fg getDataAVL() {
        return this.data;
    }


    /**
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     * @return AVL de Fg.
     */
    public AVL<Fg> getLeftAVL() {
        return this.leftAVL;
    }


    /**
     * Getteur du sous arbre gauche de l'arbre qui retourne ce sous arbre gauche.
     * @return AVL de Fg.
     */
    public AVL<Fg> getRightAVL() {
        return this.rightAVL;
    }


    /**
     * Getteur de la hauteur de l'arbre qui retourne cette hauteur.
     * @return int.
     */
    public int getHeightAVL() {
        return this.height;
    }


    /**
     * Assesseur de la racine de l'arbre qui remplace la donnee de la racine (data) par new_data.
     * @param new_data Fg.
     */
    public void setDataAVL(Fg new_data) {
        this.data = new_data;
    }


    /**
     * Assesseur du sous arbre gauche de l'arbre qui remplace ce sous arbre gauche (leftAVL) par new_tree.
     * @param new_tree AVL de Fg.
     */
    public void setLeftAVL(AVL<Fg> new_tree) {
        this.leftAVL = new_tree;
    }


    /**
     * Assesseur du sous arbre droit de l'arbre qui remplace ce sous arbre droit (rightAVL) par new_tree.
     * @param new_tree AVL de Fg.
     */
    public void setRightAVL(AVL<Fg> new_tree) {
        this.rightAVL = new_tree;
    }


    /**
     * Assesseur de la hauteur de l'arbre qui remplace cette hauteur (height) par new_height.
     * @param new_height int.
     */
    public void setHeightAVL(int new_height) {
        this.height = new_height;
    }


    /**
     * Retourne true si l'arbre est vide, false sinon.
     * @return boolean.
     */
    public boolean isEmpty() {
		if (this.getDataAVL() == null && this.leftAVL == null && this.rightAVL == null) {
            return true;
        }
		return false;
    }


    /**
     * Retourne true si l'arbre est reduit a sa racine, false sinon.
     * @return boolean.
     */
    public boolean isLeaf() {
        boolean verif = false;
        if (this.leftIsEmpty() && (this.rightIsEmpty())) {
            verif = true;
        }
        return verif;
    }


    /**
     * Retourne true si le sous arbre gauche de l'arbre est vide ou bien false sinon.
     * @return boolean.
     */
    public boolean leftIsEmpty() {
        boolean verif = false;
        if (this.getLeftAVL() == null || this.getLeftAVL().getDataAVL() == null) {
            verif = true;
        }
        return verif;
    }


    /**
     * Retourne true si le sous arbre droit de l'arbre est vide ou bien false sinon.
     * @return boolean.
     */
    public boolean rightIsEmpty() {
        boolean verif = false;
        if (this.getRightAVL() == null || this.getRightAVL().getDataAVL() == null) {
            verif = true;
        }
        return verif;
    }


    /**
     * Calcule la hauteur de l'arbre et retourne cette hauteur.
     * @return int.
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
     * Calcule la balance de l'arbre et la retourne.
     * @return int.
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
     * Applique une rotation gauche a l'arbre.
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
     * Applique une rotation droite a l'arbre.
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
     * Reequilibre notre arbre.
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
     * Retourne la donnee maximum (la plus a droite) de l'arbre.
     * @return Fg.
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
     * Retourne la donnee minimum (la plus a gauche) de l'arbre.
     * @return Fg.
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
     * Affiche les donnees de l'arbre selon l'affichage "inordre" vu au cours de sddI.
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