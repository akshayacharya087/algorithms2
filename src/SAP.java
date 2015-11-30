import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {

	private Digraph digraph;
	private Queue<Integer> pathQueue;
	private HashMap<Integer, Integer> pathSetFromV;
	private HashMap<Integer, Integer> pathSetFromW;
	private HashMap<Integer, Boolean> marked;
	private Result result = null;
	private BreathFirstPath breathFirstPath;
	
	
	
	/**
	 * Constructor takes a digraph (not necessarily a DAG)
	 * @param g
	 */
	public SAP(Digraph g) {
		digraph = new Digraph(g);
		pathQueue = new Queue<>();
		pathSetFromV = new HashMap<>();
		pathSetFromW = new HashMap<>();
		marked = new HashMap<>();
		breathFirstPath = new BreathFirstPath(g);
		
	}
	
	/**
	 *  Length of shortest ancestral path between v and w; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		result = breathFirstPath.bfs(v, w);
		return result.getLength();
		
	}
	
	 
	/**
	 * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		result = breathFirstPath.bfs(v, w);
		return result.getAncestor();
		
	}
	
	/**
	 * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		result = breathFirstPath.bfs(v, w);
		return result.getLength();
	}
	
	/**
	 * A common ancestor that participates in shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		result = breathFirstPath.bfs(v, w);
		return result.getAncestor();
	}
		
	
	/**
	 * Do unit testing of this class
	 * @param args
	 */
	public static void main(String args[]) {
		 In in = new In("/resources/digraph2.txt");
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
