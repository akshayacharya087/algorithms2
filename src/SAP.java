import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {

	/**
	 * Constructor takes a digraph (not necessarily a DAG)
	 * @param g
	 */
	public SAP(Digraph g) {
		
	}
	
	/**
	 *  Length of shortest ancestral path between v and w; -1 if no suchp ath
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		return 0;
	}
	
	 
	/**
	 * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		return 0;
	}
	
	/**
	 * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		return 0;
	}
	
	/**
	 * A common ancestor that participates in shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return 0;
	}
	
	/**
	 * Do unit testing of this class
	 * @param args
	 */
	public static void main(String args[]) {
		 In in = new In(args[0]);
		    Digraph G = new Digraph(in);
		    SAP sap = new SAP(G);
		    while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = sap.length(v, w);
		        int ancestor = sap.ancestor(v, w);
		        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		    }
	}
	
}
