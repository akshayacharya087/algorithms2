import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class BreathFirstPath {
	private HashMap<Integer, Boolean> marked;
	private HashMap<Integer, Integer> pathV;
	private HashMap<Integer, Integer> pathW;
	private int lengthV = 0;
	private int lengthW = 0;
	private Digraph graph;

	public BreathFirstPath(Digraph graph) {
		this.graph = graph;
	}

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 * 
	 * @param graph
	 * @param v
	 * @param w
	 * @return
	 */
	public Result bfs(int v, int w) {
		marked = new HashMap<>();
		Result result = new Result();
		pathV = new HashMap<>();
		pathW = new HashMap<>();
		Queue<Integer> queueV = new Queue<Integer>();
		Queue<Integer> queueW = new Queue<Integer>();

		queueV.enqueue(v);
		queueW.enqueue(w);

		marked.put(new Integer(v), true);
		marked.put(new Integer(w), true);
		pathV.put(new Integer(v), 0);
		pathW.put(new Integer(w), 0);

		while (!queueV.isEmpty() || !queueW.isEmpty()) {
			if (result.getAncestor() == -1) {
				if (!queueV.isEmpty()) {
					int currentV = queueV.dequeue();
					lengthV++;

					for (int adjacent : graph.adj(currentV)) {
						if (!marked.containsKey(adjacent)) {

							queueV.enqueue(adjacent);
							marked.put(new Integer(adjacent), true);
							pathV.put(new Integer(adjacent), lengthV);
						} else {
							// lengthV++;
							pathV.put(new Integer(adjacent), lengthV);
							result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
							result.setAncestor(adjacent);
							break;
						}
					}
				}

				if (!queueW.isEmpty()) {
					int currentW = queueW.dequeue();
					lengthW++;

					for (int adjacent : graph.adj(currentW)) {
						if (!marked.containsKey(adjacent)) {
							queueW.enqueue(adjacent);
							marked.put(new Integer(adjacent), true);
							pathW.put(new Integer(adjacent), lengthW);

						} else {
							pathW.put(new Integer(adjacent), lengthW);
							result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
							result.setAncestor(adjacent);
							break;
						}
					}
				}
			} // end of if
			else
				break;
		} // end of while

		return result;
	}// end of method

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 * 
	 * @param graph
	 * @param v
	 * @param w
	 * @return
	 */
	public Result bfs(Iterable<Integer> v, Iterable<Integer> w) {
		marked = new HashMap<>();
		Result result = new Result();
		pathV = new HashMap<>();
		pathW = new HashMap<>();
		Queue<Integer> queueV = new Queue<Integer>();
		Queue<Integer> queueW = new Queue<Integer>();

		for (int vertex : v) {
			marked.put(new Integer(vertex), true);
			pathV.put(new Integer(vertex), 0);
			queueV.enqueue(vertex);
		}

		for (int vertex : w) {
			marked.put(new Integer(vertex), true);
			pathW.put(new Integer(vertex), 0);
			queueW.enqueue(vertex);
		}

	

		while (!queueV.isEmpty() || !queueW.isEmpty()) {
			if (result.getAncestor() == -1) {
				if (!queueV.isEmpty()) {
					int currentV = queueV.dequeue();
					lengthV = pathV.get(currentV) + 1;

					for (int adjacent : graph.adj(currentV)) {
						if (!marked.containsKey(adjacent)) {
							queueV.enqueue(adjacent);
							marked.put(new Integer(adjacent), true);
							pathV.put(new Integer(adjacent), lengthV);
						} else {
							try {
								int pathWexists = pathW.get(adjacent);
								pathV.put(new Integer(adjacent), lengthV);
								result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
								result.setAncestor(adjacent);
								break;
							}
							catch (Exception e) {
								// do nothing
							}
			
						}//end else
					}//end for
				}// end if

				if (!queueW.isEmpty()) {
					int currentW = queueW.dequeue();
					lengthW = pathW.get(currentW) + 1;

					for (int adjacent : graph.adj(currentW)) {		
						if (!marked.containsKey(adjacent)) {
							queueW.enqueue(adjacent);
							marked.put(new Integer(adjacent), true);
							pathW.put(new Integer(adjacent), lengthW);

						} else {
							try {
								int pathVexists = pathV.get(adjacent);
								pathW.put(new Integer(adjacent), lengthW);
								result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
								result.setAncestor(adjacent);
								break;
							}
							catch (Exception e) {
								// do nothing
							}
						}
					}
				}
			} // end of if
			else
				break;
		} // end of while

		return result;
	}// end of method

}
