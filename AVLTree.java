//Classe AVLTree decrivant un arbre AVL
//Elle etend la classe BSTree decrivant les arbres binaires de recherche
//Les doublons ne sont pas autorises

public class AVLTree<D extends Comparable<D>> extends BSTree<D> {

//Ajout de la hauteur dans la liste des variables d'instance
//La hauteur est ainsi stockee dans la racine de l'arbre
	private int height;
	
//constructeur
	public AVLTree() {
		super();
		height = 0;
	}
	
//On redefinit les "get" de la classe AVLTree afin d'eviter de faire du casting 
//a chaque usage. 
//En effet "getLeft" de la superclasse renvoie un BSTree et pas un AVLTree 
	public AVLTree<D> getLeft() {
		return (AVLTree<D>) super.getLeft();
	}
	public AVLTree<D> getRight() {
		return (AVLTree<D>) super.getRight();
	}
	
//"get" et "set" pour la hauteur
	public int getHeight() {
		return height;
	}
	public void setHeight(int h) {
		height = h;
	}

//On redefinit la methode insertEmpty de la classe BSTree, afin de travailler avec 
//le type AVLTree au lieu de BSTree
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new AVLTree<D>());
		setRight(new AVLTree<D>());
		height = 1;
	}
	
//Calcul la hauteur en fonction des hauteurs des sous-arbres
//Le fait d'avoir stocke la hauteur donne ici un algorithme en O(1)
	public void computeHeight() {
		if (isEmpty()) 
			height = 0;
		else 
			height = 1 + Math.max(getLeft().getHeight(),
				     getRight().getHeight());
	}
	
//Calcul de la balance
	public int balance() {
		if (isEmpty()) 
			return 0;
		else 
			return getRight().getHeight() - getLeft().getHeight();
	}

//rotation gauche
	public void leftRotation() {
		D d = getData();
		AVLTree<D> t = getRight();
		setData(t.getData());
		setRight(t.getRight());
		t.setData(d);
		t.setRight(t.getLeft());
		t.setLeft(getLeft());
		setLeft(t);
		t.computeHeight();
		computeHeight();
	}

//rotation droite
	public void rightRotation() {
		D d = getData();
		AVLTree<D> t = getLeft();
		setData(t.getData());
		setLeft(t.getLeft());
		t.setData(d);
		t.setLeft(t.getRight());
		t.setRight(getRight());
		setRight(t);
		t.computeHeight();
		computeHeight();
	}

//equilibrage
	public void equilibrate() {
	if (balance() == 2) 
		if (getRight().balance() >= 0)
			leftRotation();
		else {
			getRight().rightRotation();
			leftRotation();
		}
	else if (balance() == -2) 
		if (getLeft().balance() <= 0)
			rightRotation();
		else {
			getLeft().leftRotation();
			rightRotation();
		}
	else computeHeight();
	}
}