import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {

	/**
	 * Constructor takes a WordNet object
	 * @param wordnet
	 */
	public Outcast(WordNet wordnet) {
		
	}
	
	/**
	 * Given an array of WordNet nouns, return an oucast
	 * @param nouns
	 * @return
	 */
	public String outcast(String[] nouns) {
		return null;
	}
	
	/**
	 * Test client
	 * @param args
	 */
	public static void main(String[] args) {
		  WordNet wordnet = new WordNet(args[0], args[1]);
		    Outcast outcast = new Outcast(wordnet);
		    for (int t = 2; t < args.length; t++) {
		        In in = new In(args[t]);
		        String[] nouns = in.readAllStrings();
		        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		    }
	}
}
