//tri d'un tableau grace a un AVLTree
public class AVLTreeSorter<D extends Comparable> {
	private D[] a;
	
//constructeur
	public AVLTreeSorter(D[] anArray) {
		a = anArray;
	}

//tri
	public void sort() {
		AVLTree<D> t = new AVLTree<D>();
		for (int i=0; i<a.length; i++) 
			t.insert(a[i]);
		t.print();
	}
}