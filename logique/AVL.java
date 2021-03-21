package logique;

import java.lang.Math;

public class AVL<Fg> {
    
    private Fg data;
    private AVL<Fg> leftAVL, rightAVL;
    private int height;
    
    // Constructeurs.
    public AVL(Fg data, AVL<Fg> leftAVL, AVL<Fg> rightAVL) {
        this.data = data;
        this.leftAVL = leftAVL;
        this.rightAVL = rightAVL;
        this.calculHeight();
    }

    public AVL() {
        this.data = null;
        this.leftAVL = null;
        this.rightAVL = null;
        this.height = 0;
    }

    // Assesseur/getteur.
    public Fg getData() {
        return this.data;
    }

    public AVL<Fg> getLeftAVL() {
        return this.leftAVL;
    }

    public AVL<Fg> getRightAVL() {
        return this.rightAVL;
    }

    public int getHeight() {
        return this.height;
    }

    // Attention utiliser seulement si le changement respecte l'ordre interne de l'arbre.
    public void setData(Fg new_data) {
        this.data = new_data;
    }

    public void setLeftAVL(AVL<Fg> new_tree) {
        this.leftAVL = new_tree;
    }

    public void setRightAVL(AVL<Fg> new_tree) {
        this.rightAVL = new_tree;
    }

    public void setHeight(int new_height) {
        this.height = new_height;
    }

    // Retourne true si la liste est vide, false sinon.
    public boolean isEmpty() {
        if (this.getData() == null && this.leftAVL == null && this.rightAVL == null) {
            return true;
        }
        return false;
    }

    // Retourne vrai si l'arbre gauche et droite sont vides, false sinon.
    public boolean isLeaf() {
        boolean verif = false;
        if ((this.getLeftAVL() == null) && (this.getRightAVL() == null)) {
            verif = true;
        }
        return verif;
    }

    // Teste si l'arbre gauche est vide.
    public boolean leftIsEmpty() {
        boolean verif = false;
        if (this.getLeftAVL() == null) {
            verif = true;
        }
        return verif;
    }

    // Teste si l'arbre droit est vide.
    public boolean rightIsEmpty() {
        boolean verif = false;
        if (this.getRightAVL() == null) {
            verif = true;
        }
        return verif;
    }
    
    // Calcule la hauteur d'un arbre.
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
                    this.height = 1 + this.getRightAVL().calculHeight();
                }
                else {
                    if (rightIsEmpty()) {
                        this.height = 1 + this.getLeftAVL().calculHeight();
                    }
                    else {
                        this.height = 1 + 
                        Math.max(this.getLeftAVL().calculHeight(), this.getRightAVL().calculHeight());
                    }
                }
            }
        }
        return this.height;
    }

    //Calcul de la balance
    public int balance() {
        int x = 0;
        if (isEmpty() || isLeaf()) {
            x = 0;
        }
        else if (this.getRightAVL() == null) {
            x = - this.getLeftAVL().getHeight();
        }
        else if (this.getLeftAVL() == null) {
            x = this.getRightAVL().getHeight();
        }
        else {
            x = this.getRightAVL().getHeight() - this.getLeftAVL().getHeight();
        }
        return x;
    }

    // Applique la rotation gauche.
    public void leftRotation() {
        Fg data = this.getData();
        AVL<Fg> right = this.getRightAVL();
        this.setData(right.getData());
        this.setRightAVL(right.getRightAVL());
        right.setData(data);
        right.setRightAVL(right.getLeftAVL());
        right.setLeftAVL(this.getLeftAVL());
        this.setLeftAVL(right);
        right.calculHeight();
        this.calculHeight();
    }

    //rotation droite
    public void rightRotation() {
        Fg data = this.getData();
        AVL<Fg> left = this.getLeftAVL();
        this.setData(left.getData());
        this.setLeftAVL(left.getLeftAVL());
        left.setData(data);
        left.setLeftAVL(left.getRightAVL());
        left.setRightAVL(getRightAVL());
        setRightAVL(left);
        left.calculHeight();
        calculHeight();
    }

    // Rééquilibre notre AVL.
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

    public Fg findMax() {
        Fg maximum;
        if (this.getRightAVL().isEmpty()) {
            maximum = this.getData();
        }
        else {
            maximum = this.getRightAVL().findMax();
        }
        return maximum;
    }

    public Fg findMin() {
        Fg minimum;
        if (this.getLeftAVL().isEmpty()) {
            minimum = this.getData();
        }
        else {
            minimum = this.getLeftAVL().findMin();
        }
        return minimum;
    }

    public void inordre() {
        if (this != null) {
            if (this.getLeftAVL() != null) {
                this.getLeftAVL().inordre();
            }
            if (this.getData() != null) {
                ((Segment) this.getData()).print();
            }
            if (this.getRightAVL() != null) {
                this.getRightAVL().inordre();
            }
        }
    }
}