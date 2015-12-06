import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class BreathFirstPath {
	private final HashMap<Integer, Boolean> marked;
	private final HashMap<Integer, Integer> pathV;
	private final HashMap<Integer, Integer> pathW;
	private int lengthV;
	private int lengthW;
	private final Digraph graph;
	private int shortestLengthV;
	private int shortestLengthW;
    private int shortestLengthVVertex;
    private int shortestLengthWVertex;
    private int length;
    private int ancestor;
    private boolean isPathVFound;
    private boolean isPathWFound;

    /**
     * Performs BFS on two vertexes
     * @param graph Digraph representing WordNet
     */
	public BreathFirstPath(Digraph graph) {
		this.graph = graph;
		shortestLengthW = Integer.MAX_VALUE;
		shortestLengthV = Integer.MAX_VALUE;

        marked = new HashMap<>();
        pathV = new HashMap<>();
        pathW = new HashMap<>();

        lengthV = 0;
        lengthW = 0;

        length = -1;
        ancestor = -1;

        isPathVFound = false;
        isPathWFound = false;
	}

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 *
	 * @param v
	 * @param w
	 * @return
	 */
	public void bfs(int v, int w) {
		
		if (v == w) {
			ancestor = v;
			length =0;
			return;
		} 
		
		ArrayList<Integer> vArray = new ArrayList<>();
		ArrayList<Integer> wArray = new ArrayList<>();
		vArray.add(v);
		wArray.add(w);
		bfs(vArray, wArray);
	}// end of method

	/**
	 * Breath first search on two vertexes simultaneously, in order to find
	 * Shortest Ancestral Path and the length.
	 *
	 * @param v
	 * @param w
	 * @return
	 */
	public void bfs(Iterable<Integer> v, Iterable<Integer> w) {
        length = -1;
        ancestor = -1;
        lengthV = 0;
        lengthW = 0;

        marked.clear();
        pathV.clear();
        pathW.clear();

        shortestLengthW = Integer.MAX_VALUE;
        shortestLengthV = Integer.MAX_VALUE;
        shortestLengthVVertex = Integer.MAX_VALUE;
        shortestLengthWVertex = Integer.MAX_VALUE;

        isPathVFound = false;
        isPathWFound = false;

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
            if (this.ancestor == -1) {
                if (!queueV.isEmpty()) {
                    int currentV = queueV.dequeue();

                    lengthV = pathV.get(currentV) + 1;

                    if (graph.outdegree(currentV) == 0 && !isPathVFound) {
                        shortestLengthV = lengthV - 1;
                        shortestLengthVVertex = currentV;
                        isPathVFound = true;
                    }

                    for (int adjacent : graph.adj(currentV)) {
                        if (!marked.containsKey(adjacent)) {
                            queueV.enqueue(adjacent);
                            marked.put(new Integer(adjacent), true);
                            pathV.put(new Integer(adjacent), lengthV);
                        } else {

                            if (pathW.containsKey(adjacent)) {
                                isPathVFound = true;

                                // cycle found
                                if (!pathV.containsKey(adjacent)) {

                                    if (lengthV < shortestLengthV) {
                                        pathV.put(new Integer(adjacent), lengthV);
                                        shortestLengthV = lengthV;
                                        shortestLengthVVertex = adjacent;
                                    } else if (lengthV == shortestLengthV) {
                                        if (pathW.get(adjacent) < shortestLengthW) {
                                            pathV.put(new Integer(adjacent), lengthV);
                                            shortestLengthV = lengthV;
                                            shortestLengthVVertex = adjacent;
                                        }
                                    } else if (pathW.get(adjacent) < pathW.get(shortestLengthWVertex)) {
                                        pathV.put(new Integer(adjacent), lengthV);
                                        shortestLengthV = lengthV;
                                        shortestLengthVVertex = adjacent;
                                    }
                                }
                                else {
                                    shortestLengthV = pathV.get(adjacent);
                                    shortestLengthVVertex = adjacent;
                                }
                            }
                        }//end else
                    }//end for
                }// end if


                if (!queueW.isEmpty()) {
                    int currentW = queueW.dequeue();

                    lengthW = pathW.get(currentW) + 1;


                    if (graph.outdegree(currentW) == 0 && !isPathWFound) {
                        shortestLengthW = lengthW - 1;
                        shortestLengthWVertex = currentW;
                        isPathWFound = true;
                    }

                    for (int adjacent : graph.adj(currentW)) {
                        if (!marked.containsKey(adjacent)) {
                            queueW.enqueue(adjacent);
                            marked.put(new Integer(adjacent), true);
                            pathW.put(new Integer(adjacent), lengthW);
                        } else {

                            if (pathV.containsKey(adjacent)) {
                                isPathWFound = true;

                                if (!pathW.containsKey(adjacent)) {

                                    if (lengthW < shortestLengthW) {
                                        pathW.put(new Integer(adjacent), lengthW);
                                        shortestLengthW = lengthW;
                                        shortestLengthWVertex = adjacent;
                                    } else if (lengthW == shortestLengthW) {
                                        if (pathV.get(adjacent) < shortestLengthV) {
                                            pathW.put(new Integer(adjacent), lengthW);
                                            shortestLengthW = lengthW;
                                            shortestLengthWVertex = adjacent;
                                        }
                                    } else if (pathV.get(adjacent) < pathV.get(shortestLengthVVertex)) {
                                        pathW.put(new Integer(adjacent), lengthW);
                                        shortestLengthW = lengthW;
                                        shortestLengthWVertex = adjacent;
                                    }
                                }

                                else {
                                    shortestLengthW = pathW.get(adjacent);
                                    shortestLengthWVertex = adjacent;
                                }
                            }
                        }//end else
                    }//end for
                }// end if
            }// end if
        }// end while

        if (isPathWFound && isPathVFound) {
            int optionV = evaluateOption(shortestLengthVVertex);
            int optionW = evaluateOption(shortestLengthWVertex);

            if (optionV < optionW) {
                length = optionV;
                ancestor = shortestLengthVVertex;
                return;
            }
            if (optionW < optionV) {
                length = optionW;
                ancestor = shortestLengthWVertex;
                return;
            }
            // optionV = optionW
            if (optionV != Integer.MAX_VALUE || optionW != Integer.MAX_VALUE) {
                length = optionV; // choosing an arbitrary option
                ancestor = shortestLengthVVertex; // choosing an arbitrary option
                return;
            }
        }// end if

	}// end of method


    private int evaluateOption(int shortestLengthX) {
        int option = 0;

        if (pathV.containsKey(shortestLengthX))
            option += pathV.get(shortestLengthX);
        else
            return Integer.MAX_VALUE;

        if (pathW.containsKey(shortestLengthX))
            option += pathW.get(shortestLengthX);
        else
            return Integer.MAX_VALUE;

        return option;
    }

    public int getLength() {
        return length;
    }

    public int getAncestor() {
        return ancestor;
    }
}

