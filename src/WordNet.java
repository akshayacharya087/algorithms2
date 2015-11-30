import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordNet {

	private final HashMap<String, ArrayList<Integer>> synsetsMap;	

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
			
		synsetsMap = new HashMap<>(); 		
		int synsetCounter = 0;
		
		// Filling the synsets, and nouns
		while (in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			String[] nouns = elements[1].split(" ");
			for (String noun : nouns) {
				if (synsetsMap.containsKey(noun)) {
					synsetsMap.get(noun).add(Integer.parseInt(elements[0]));
				}
				else {
					ArrayList<Integer> synsetsIds = new ArrayList<>();
					synsetsIds.add(Integer.parseInt(elements[0]));
					synsetsMap.put(noun, synsetsIds);
				}				
			}
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
	}

	/**
	 * Returns all WordNet nouns
	 * 
	 * @return
	 */
	public Iterable<String> nouns() {
		return synsetsMap.keySet();
	}

	/**
	 * Is the word a WordNet noun?
	 * 
	 * @param word
	 * @return
	 */
	public boolean isNoun(String word) {
		if (word == null) throw new NullPointerException();
		
		return (synsetsMap.containsKey(word));
	}

	/**
	 *  Distance between nounA and nounB (defined below)
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		BreathFirstPath breathFirstPath = new BreathFirstPath(wordNet);
		Result result = breathFirstPath.bfs(synsetsMap.get(nounA), synsetsMap.get(nounB));
		return result.getLength();
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
		BreathFirstPath breathFirstPath = new BreathFirstPath(wordNet);
		Result result = breathFirstPath.bfs(synsetsMap.get(nounA), synsetsMap.get(nounB));
		return "result";
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
