
public class BSTreeSorter<D extends Comparable> {
	private D[] a;
	
//constructeur
	public BSTreeSorter(D[] anArray) {
		a = anArray;
	}

//tri du tableau a grace a un arbre binaire de recherche
	public void sort() {
		BSTree<D> t = new BSTree<D>();
		for (int i=0; i<a.length; i++) 
			t.insert(a[i]);
		t.print();
	}
}