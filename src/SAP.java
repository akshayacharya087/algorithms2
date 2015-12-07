import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;


public class SAP {

	private final Digraph digraph;
	private final BreathFirstPath breathFirstPath;

	/**
	 * Constructor takes a digraph (not necessarily a DAG)
	 * @param g
	 */
	public SAP(Digraph g) {
		digraph = g;
		breathFirstPath = new BreathFirstPath(g);
	}
	
	/**
	 *  Length of shortest ancestral path between v and w; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		checkInput(v);
		checkInput(w);
		breathFirstPath.bfs(v, w);
		return breathFirstPath.getLength();
	}
	
	 /**
	  * Checks that the input is between 0 and digraph.V()-1
	  */
	private void checkInput(int vertex) {
		if (!(vertex >= 0 && vertex <= digraph.V() -1)) throw new IndexOutOfBoundsException(); 
		
	}

	/**
	 * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		checkInput(v);
		checkInput(w);
		breathFirstPath.bfs(v, w);
		return breathFirstPath.getAncestor();
	}
	
	/**
	 * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {	
		checkInputArray(v);
		checkInputArray(w);
		
		breathFirstPath.bfs(v, w);
		return breathFirstPath.getLength();
	}
	
	/**
	 * Checks that all integers in the array are between 0 and digraph.V() - 1
	 */
	private void checkInputArray(Iterable<Integer> vertexList) {
		for (int vertex : vertexList) {
			checkInput(vertex);
		}
		
	}

	/**
	 * A common ancestor that participates in shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		breathFirstPath.bfs(v, w);
		return breathFirstPath.getAncestor();
	}
		
	
	/**
	 * Do unit testing of this class
	 * @param args
	 */
	public static void main(String args[]) {
		In in = new In("/resources/digraph5.txt");
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
        int v = 21;
        int w = 15;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    
	}
	
}
