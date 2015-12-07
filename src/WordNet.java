import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordNet {

	private final HashMap<String, ArrayList<Integer>> nounsMap;	
	private final HashMap<Integer, String> synsetsMap;

	private final Digraph wordNet;	
	private final SAP sap;
	/**
	 * Constructor takes the name of the two input files
	 * 
	 * @param synsets
	 * @param hypernyms
	 */
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) throw new NullPointerException();
		
		In in = new In(synsets);
			
		nounsMap = new HashMap<>();
		synsetsMap = new HashMap<>();
		int synsetCounter = 0;
		
		// Filling the synsets, and nouns
		while (in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			int synsetId = Integer.parseInt(elements[0]);
			String[] nouns = elements[1].split(" ");
			
			// Filling nounsMap
			for (String noun : nouns) {
				if (nounsMap.containsKey(noun)) {
					nounsMap.get(noun).add(synsetId);
				}
				else {
					ArrayList<Integer> synsetsIds = new ArrayList<>();
					synsetsIds.add(synsetId);
					nounsMap.put(noun, synsetsIds);
				}
			}
			
			synsetsMap.put(new Integer(synsetId), elements[1]);
			
			
			synsetCounter++;
		}
		
		// Creating the digraph
		wordNet = new Digraph(synsetCounter);
		
		in = new In(hypernyms);
		
		while(in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			for (int i = 1; i < elements.length; i++) {
				wordNet.addEdge(Integer.parseInt(elements[0]), Integer.parseInt(elements[i]));
			}
			
		}
		
		// Creating the SAP object
		sap = new SAP(wordNet);		
	}

	/**
	 * Returns all WordNet nouns
	 * 
	 * @return
	 */
	public Iterable<String> nouns() {
		return nounsMap.keySet();
	}

	/**
	 * Is the word a WordNet noun?
	 * 
	 * @param word
	 * @return
	 */
	public boolean isNoun(String word) {		
		checkInput(word);
		return (nounsMap.containsKey(word));
	}

	/**
	 *  Distance between nounA and nounB (defined below)
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		checkInputs(nounA, nounB);
		return sap.length(nounsMap.get(nounA), nounsMap.get(nounB));
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
		checkInputs(nounA, nounB);
		int ancestor = sap.ancestor(nounsMap.get(nounA), nounsMap.get(nounB));
		return synsetsMap.get(ancestor);
	}

	/**
	 * Checks that parameters are not null, and that they belong to a rooted DAG.
	 * @param nounA
	 * @param nounB
	 */
	private void checkInputs(String nounA, String nounB) {
		if (nounA == null || nounB == null) throw new NullPointerException();
		
		if (!nounsMap.containsKey(nounA) || !nounsMap.containsKey(nounB)) throw new IllegalArgumentException();
		
	}
	
	/**
	 * Checks that the String is not null
	 * @param word
	 */
	private void checkInput(String word) {
		if (word == null) throw new NullPointerException();
	}

	/**
	 * do unit testing of this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("/resources/synsets.txt", "/resources/hypernyms.txt");
		Scanner in = new Scanner(System.in);
		String word1 = in.nextLine();
		String word2 = in.nextLine();
		System.out.println("The sap of " + word1 + " and " + word2 + " is " + wordNet.sap(word1, word2));
		System.out.println("Successfully finished");
	}
}
