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
    private final Queue<Integer> queueV;
    private final Queue<Integer> queueW;
    private int optionV;
    private int optionW;

    /**
     * Performs BFS on two vertexes
     * @param graph Digraph representing WordNet
     */
	public BreathFirstPath(Digraph graph) {
		this.graph = graph;
		shortestLengthW = Integer.MAX_VALUE;
		shortestLengthV = Integer.MAX_VALUE;
        shortestLengthVVertex = Integer.MAX_VALUE;
        shortestLengthWVertex = Integer.MAX_VALUE;

        queueV = new Queue<>();
        queueW = new Queue<>();

        marked = new HashMap<>();
        pathV = new HashMap<>();
        pathW = new HashMap<>();

        lengthV = 0;
        lengthW = 0;

        length = -1;
        ancestor = -1;

        isPathVFound = false;
        isPathWFound = false;

        optionV = 0;
        optionW = 0;
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

        optionV = 0;
        optionW = 0;


        // Emptying queues
        while (!queueV.isEmpty()) {
            queueV.dequeue();
        }

        while (!queueW.isEmpty()) {
            queueW.dequeue();
        }

        // Filling the queues
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

                            // cycle found
                            if (!pathV.containsKey(adjacent)) {

                                if (pathW.containsKey(adjacent)) {
                                    isPathVFound = true;

                                    if (lengthV < shortestLengthV) {
                                        pathV.put(new Integer(adjacent), lengthV);
                                        shortestLengthV = lengthV;
                                        shortestLengthVVertex = adjacent;
                                    }
                                    else if (lengthV == shortestLengthV) {
                                        if (pathW.get(adjacent) < shortestLengthW) {
                                            pathV.put(new Integer(adjacent), lengthV);
                                            shortestLengthV = lengthV;
                                            shortestLengthVVertex = adjacent;
                                        }
                                    }
                                    else {
                                        pathV.put(new Integer(adjacent), lengthV);
                                        if (pathW.get(adjacent) == 0) {

                                            if (shortestLengthV != Integer.MAX_VALUE) {

                                                int possibleNewShortestVVertex = adjacent;

                                                int possibleNewOptionV = evaluateOption(possibleNewShortestVVertex);
                                                int currentOptionV = evaluateOption(shortestLengthWVertex);

                                                if (possibleNewOptionV < currentOptionV) {
                                                    shortestLengthV = pathV.get(adjacent);
                                                    shortestLengthVVertex = adjacent;
                                                    isPathVFound = true;
                                                }
                                            }

                                            else {
                                                shortestLengthV = pathV.get(adjacent);
                                                shortestLengthVVertex = adjacent;
                                                isPathVFound = true;
                                            }
                                        }
                                    }
                                }
                                if (!isPathWFound) {
                                    pathV.put(new Integer(adjacent), lengthV);
                                    queueV.enqueue(adjacent);
                                }
                            }

                            else {
                                if (pathV.get(adjacent) < shortestLengthV) {

                                    if (shortestLengthV != Integer.MAX_VALUE) {

                                        int possibleNewShortestVVertex = adjacent;

                                        int possibleNewOptionV = evaluateOption(possibleNewShortestVVertex);
                                        int currentOptionV = evaluateOption(shortestLengthWVertex);

                                        if (possibleNewOptionV < currentOptionV) {
                                            shortestLengthV = pathV.get(adjacent);
                                            shortestLengthVVertex = adjacent;
                                            isPathVFound = true;
                                        }
                                    }

                                    else {
                                        shortestLengthV = pathV.get(adjacent);
                                        shortestLengthVVertex = adjacent;
                                        isPathVFound = true;
                                    }
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

                            // cycle found
                            if (!pathW.containsKey(adjacent)) {

                                if (pathV.containsKey(adjacent)) {
                                    isPathWFound = true;

                                    if (lengthW < shortestLengthW) {
                                        pathW.put(new Integer(adjacent), lengthW);
                                        shortestLengthW = lengthW;
                                        shortestLengthWVertex = adjacent;
                                    }
                                    else if (lengthW == shortestLengthW) {
                                        if (pathV.get(adjacent) < shortestLengthV) {
                                            pathW.put(new Integer(adjacent), lengthW);
                                            shortestLengthW = lengthW;
                                            shortestLengthWVertex = adjacent;
                                        }
                                    }
                                    else {
                                        pathW.put(new Integer(adjacent), lengthW);
                                        if (pathV.get(adjacent) == 0) {

                                            if (shortestLengthW != Integer.MAX_VALUE) {

                                                int possibleNewShortestWVertex = adjacent;

                                                int possibleNewOptionW = evaluateOption(possibleNewShortestWVertex);
                                                int currentOptionW = evaluateOption(shortestLengthWVertex);

                                                if (possibleNewOptionW < currentOptionW) {
                                                    shortestLengthW = pathW.get(adjacent);
                                                    shortestLengthWVertex = adjacent;
                                                    isPathWFound = true;
                                                }
                                            }

                                            else {
                                                shortestLengthW = pathW.get(adjacent);
                                                shortestLengthWVertex = adjacent;
                                                isPathWFound = true;
                                            }
                                        }
                                    }

                                }
                                if (!isPathVFound) {
                                    pathW.put(new Integer(adjacent), lengthW);
                                    queueW.enqueue(adjacent);
                                }
                            }
                            else {
                                if (pathW.get(adjacent) < shortestLengthW) {

                                    // if shortest length in pathW has been already found, test which one is better
                                    if (shortestLengthW != Integer.MAX_VALUE) {

                                        int possibleNewShortestWVertex = adjacent;

                                        int possibleNewOptionW = evaluateOption(possibleNewShortestWVertex);
                                        int currentOptionW = evaluateOption(shortestLengthWVertex);

                                        if (possibleNewOptionW < currentOptionW) {
                                            shortestLengthW = pathW.get(adjacent);
                                            shortestLengthWVertex = adjacent;
                                            isPathWFound = true;
                                        }
                                    }

                                    else {
                                        shortestLengthW = pathW.get(adjacent);
                                        shortestLengthWVertex = adjacent;
                                        isPathWFound = true;
                                    }
                                }
                            }
                        }//end else
                    }//end for
                }// end if
        }// end while

        if (isPathWFound && isPathVFound) {
            optionV = evaluateOption(shortestLengthVVertex);
            optionW = evaluateOption(shortestLengthWVertex);

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

