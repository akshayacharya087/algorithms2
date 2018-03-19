import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

	private final WordNet wordNet;
	
	/**
	 * Constructor takes a WordNet object
	 * @param wordnet
	 */	
	public Outcast(WordNet wordnet) {
		this.wordNet = wordnet;
	}
	
	/**
	 * Given an array of WordNet nouns, return an outcast
	 * @param nouns
	 * @return
	 */
	public String outcast(String[] nouns) {
		int maxDistance = Integer.MIN_VALUE;
		String outcast = null;
		int sumOfDistances = 0;
		int index = 0;
		int distance = 0;
		
		while (index < nouns.length) {
			for (int i = 0; i < nouns.length; i++) {
				if (index != i) {
					distance = wordNet.distance(nouns[index], nouns[i]);
				}
				sumOfDistances += distance;				
			}
			System.out.println("The distance for " + nouns[index] + " is " + sumOfDistances);
			if (sumOfDistances > maxDistance) {
				maxDistance = sumOfDistances;
				outcast = nouns[index];
			}
			
			sumOfDistances = 0;
			index++;
		}
				
		return outcast;
	}
	
	/**
	 * Test client
	 * @param args
	 */
	public static void main(String[] args) {
		WordNet wordnet = new WordNet("/resources/synsets.txt", "/resources/hypernyms.txt");
	    Outcast outcast = new Outcast(wordnet);		   
        In in = new In("/resources/outcast11.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println("outcastX.txt: " + outcast.outcast(nouns));		   
	}
}
