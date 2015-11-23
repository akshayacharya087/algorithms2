import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Scanner;

public class WordNet {

	private final ArrayList<Integer> synsetsList;
	private final ArrayList<String> nounsList;
	private final ArrayList<String> glossList;

	private final Digraph wordNet;

	/**
	 * Constructor takes the name of the two input files
	 * 
	 * @param synsets
	 * @param hypernyms
	 */
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) throw new NullPointerException();
		
		In in = new In(synsets);
			
		synsetsList = new ArrayList<Integer>();
		nounsList = new ArrayList<String>();
		glossList = new ArrayList<String>();
		
		// Filling the synsets, nounds, and gloss lists
		while (in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			synsetsList.add(Integer.parseInt(elements[0]));
			nounsList.add(elements[1]);
			glossList.add(elements[2]);
		}
		
		// Creating the digraph
		wordNet = new Digraph(synsetsList.size());
		
		in = new In(hypernyms);
		
		while(in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			
			for (int i = elements.length - 1; i > 0; i--) {
				wordNet.addEdge(Integer.parseInt(elements[i]), Integer.parseInt(elements[0]));
			}
			
		}
	}

	/**
	 * Returns all WordNet nouns
	 * 
	 * @return
	 */
	public Iterable<String> nouns() {
		return nounsList;
	}

	/**
	 * Is the word a WordNet noun?
	 * 
	 * @param word
	 * @return
	 */
	public boolean isNoun(String word) {
		if (word == null) throw new NullPointerException();
		
		int index = nounsList.indexOf(word);
		return (index != -1);
	}

	/**
	 *  Distance between nounA and nounB (defined below)
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		return 0;
	}
	
	
	/**
	 * A synset (second field of synsets.txt) that is the common ancestor of
	 * nounA and nounB in a shortest ancestral path (defined below)
	 * 
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public String sap(String nounA, String nounB) {
		return null;
	}

	/**
	 * do unit testing of this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("/resources/synsets.txt", "/resources/hypernyms.txt");
		Scanner in = new Scanner(System.in);
		String word = in.nextLine();
		
		System.out.println("Is the word " + word + " in the nouns list? " + wordNet.isNoun(word));
		
		System.out.println("Successfully finished");
	}
}
