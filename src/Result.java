/**
 * Helper class to store the common ancestor and the length of the Shortest Ancestral Path
 * @author yeremy
 *
 */
public class Result {
	
	private int ancestor;
	private int length;
	
	public Result() {
		ancestor = -1;
		length = -1;
	}
	
	public int getAncestor() {
		return ancestor;
	}
	public void setAncestor(int vertex) {
		this.ancestor = vertex;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	
}
