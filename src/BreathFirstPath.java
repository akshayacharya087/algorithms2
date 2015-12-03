import java.util.ArrayList;
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
	private int shortestLengthV;
	private int shortestLengthW;

	public BreathFirstPath(Digraph graph) {
		this.graph = graph;
		shortestLengthW = Integer.MAX_VALUE;
		shortestLengthV = Integer.MAX_VALUE;
	}

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 *
	 * @param v
	 * @param w
	 * @return
	 */
	public Result bfs(int v, int w) {
		
		if (v == w) { 
			Result result = new Result();
			result.setAncestor(v);
			result.setLength(0);
			return result;
		} 
		
		ArrayList<Integer> vArray = new ArrayList<>();
		ArrayList<Integer> wArray = new ArrayList<>();
		vArray.add(v);
		wArray.add(w);
		return bfs(vArray, wArray);
	}// end of method

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 *
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
							
							pathV.put(new Integer(adjacent), lengthV);
							
							if (lengthV < shortestLengthV)  {
								shortestLengthV = lengthV;
							}

							if (queueV.isEmpty()) {							
													
								try {									
									
									// w is the shortest common ancestor
									if (pathW.containsKey(adjacent)) {
										result.setLength(shortestLengthV + pathW.get(adjacent));
										result.setAncestor(adjacent);
										return result;
									}
								}
								catch (Exception e) {
									// do nothing
								}
								
							}// end if
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
			
							pathW.put(new Integer(adjacent), lengthW);
							
							if (lengthW < shortestLengthW)  {
								shortestLengthW = lengthW;
							}

							if (queueW.isEmpty()) {							
													
								try {									
									
									// v is the shortest common ancestor
									if (pathV.containsKey(adjacent)) {
										result.setLength(shortestLengthW + pathV.get(adjacent));
										result.setAncestor(adjacent);
										return result;
									}
								}
								catch (Exception e) {
									// do nothing
								}
								
							}// end if
						}//end else
					}//end for
				}// end if
			}//end if
			else break;
		} // end of while

		return result;
	}// end of method

}

