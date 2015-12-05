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
    private boolean hasVHitAMarkedVertex;
    private boolean hasWHitAMarkedVertex;

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

        hasVHitAMarkedVertex = false;
        hasWHitAMarkedVertex = false;
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

        hasVHitAMarkedVertex = false;
        hasWHitAMarkedVertex = false;

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

					for (int adjacent : graph.adj(currentV)) {
						if (!marked.containsKey(adjacent)) {
							queueV.enqueue(adjacent);
							marked.put(new Integer(adjacent), true);
							pathV.put(new Integer(adjacent), lengthV);
						} else {


                            hasVHitAMarkedVertex = true;

							if (lengthV < shortestLengthV)  {
                                pathV.put(new Integer(adjacent), lengthV);
								shortestLengthV = lengthV;
                                shortestLengthVVertex = adjacent;
							}

                            else if (lengthV == shortestLengthV) {
                                if (pathW.containsKey(adjacent)) {
                                    if (pathW.get(adjacent) < shortestLengthW) {
                                        pathV.put(new Integer(adjacent), lengthV);
                                        shortestLengthV = lengthV;
                                        shortestLengthVVertex = adjacent;
                                    }
                                }
                            }
                            else
                            {
                                if (!pathV.containsKey(adjacent)) {
                                    pathV.put(new Integer(adjacent), lengthV);
                                }

//                                if (pathW.get(adjacent) < pathV.get(adjacent)){
//                                    shortestLengthVVertex = adjacent;
//                                }
                            }

							if (queueV.isEmpty() && (hasWHitAMarkedVertex || queueW.isEmpty())) {
													
								try {									
									
									// w is the shortest common ancestor
									if (pathW.containsKey(adjacent)) {
                                        if (shortestLengthW < shortestLengthV) {
                                            length = pathV.get(shortestLengthWVertex) + pathW.get(shortestLengthWVertex) ;
                                            ancestor = shortestLengthWVertex;
                                            return;
                                        }
                                        if (shortestLengthV < shortestLengthW) {
                                            length = pathV.get(shortestLengthVVertex) + pathW.get(shortestLengthVVertex);
                                            ancestor = shortestLengthVVertex;
                                            return;
                                        }
										return;
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

                            hasWHitAMarkedVertex = true;

							if (lengthW < shortestLengthW)  {
                                pathW.put(new Integer(adjacent), lengthW);
                                shortestLengthW = lengthW;
                                shortestLengthWVertex = adjacent;
							}

                            else if (lengthW == shortestLengthW) {
                                if (pathV.containsKey(adjacent)) {
                                    if (pathV.get(adjacent) < shortestLengthV) {
                                        pathW.put(new Integer(adjacent), lengthW);
                                        shortestLengthW = lengthW;
                                        shortestLengthWVertex = adjacent;
                                    }
                                }
                            }


                            else
                            {
                                if (!pathW.containsKey(adjacent)) {
                                    pathW.put(new Integer(adjacent), lengthW);
                                }

//                                if (pathW.get(adjacent) < shortestLengthW) {
//                                    shortestLengthWVertex = adjacent;
//                                    shortestLengthW = lengthW;
//                                }

//                                if (pathV.get(adjacent) < pathW.get(adjacent)){
//                                    shortestLengthWVertex = adjacent;
//                                }
                            }

							if (queueW.isEmpty() && (hasVHitAMarkedVertex || queueV.isEmpty())) {
													
								try {

                                    // w is the shortest common ancestor
                                    if (pathV.containsKey(adjacent)) {
                                        if (shortestLengthW < shortestLengthV) {
                                            length = pathW.get(shortestLengthWVertex) + pathV.get(shortestLengthWVertex);
                                            ancestor = shortestLengthWVertex;
                                            return;
                                        }
                                        if (shortestLengthV < shortestLengthW) {
                                            length = pathW.get(shortestLengthVVertex) + pathV.get(shortestLengthVVertex);
                                            ancestor = shortestLengthVVertex;
                                            return;
                                        }
                                        else {
                                            length = pathW.get(adjacent) + pathV.get(adjacent);
                                            ancestor = adjacent;
                                        }

                                        return;
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

        if (hasVHitAMarkedVertex || hasWHitAMarkedVertex) {
            if (!pathV.containsKey(shortestLengthWVertex) && pathW.containsKey(shortestLengthWVertex)) {
                length = pathW.get(shortestLengthWVertex);
                ancestor = shortestLengthWVertex;
                return;
            }
            if (!pathW.containsKey(shortestLengthWVertex) && pathV.containsKey(shortestLengthWVertex)) {
                length = pathV.get(shortestLengthWVertex);
                ancestor = shortestLengthWVertex;
                return;
            }
            if (pathV.containsKey(shortestLengthWVertex) && pathW.containsKey(shortestLengthWVertex)) {
                length = pathV.get(shortestLengthWVertex) + pathW.get(shortestLengthWVertex);
                ancestor = shortestLengthWVertex;
                return;
            }
            if (shortestLengthV != Integer.MAX_VALUE && shortestLengthW == Integer.MAX_VALUE) {
                if (!pathV.containsKey(shortestLengthVVertex) && pathW.containsKey(shortestLengthVVertex)) {
                    length = pathW.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
                if (!pathW.containsKey(shortestLengthVVertex) && pathV.containsKey(shortestLengthVVertex)) {
                    length = pathV.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
                if (pathV.containsKey(shortestLengthVVertex) && pathW.containsKey(shortestLengthVVertex)) {
                    length = pathV.get(shortestLengthVVertex) + pathW.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
            }
            if (shortestLengthW < shortestLengthV) {
                if (!pathV.containsKey(shortestLengthWVertex) && pathW.containsKey(shortestLengthWVertex)) {
                    length = pathW.get(shortestLengthWVertex);
                    ancestor = shortestLengthWVertex;
                    return;
                }
                if (!pathW.containsKey(shortestLengthWVertex) && pathV.containsKey(shortestLengthWVertex)) {
                    length = pathV.get(shortestLengthWVertex);
                    ancestor = shortestLengthWVertex;
                    return;
                }
                if (pathV.containsKey(shortestLengthWVertex) && pathW.containsKey(shortestLengthWVertex)) {
                    length = pathV.get(shortestLengthWVertex) + pathW.get(shortestLengthWVertex);
                    ancestor = shortestLengthWVertex;
                    return;
                }
            }
            if (shortestLengthW > shortestLengthV) {
                if (!pathV.containsKey(shortestLengthVVertex) && pathW.containsKey(shortestLengthVVertex)) {
                    length = pathW.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
                if (!pathW.containsKey(shortestLengthVVertex) && pathV.containsKey(shortestLengthVVertex)) {
                    length = pathV.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
                if (pathV.containsKey(shortestLengthVVertex) && pathW.containsKey(shortestLengthVVertex)) {
                    length = pathV.get(shortestLengthVVertex) + pathW.get(shortestLengthVVertex);
                    ancestor = shortestLengthVVertex;
                    return;
                }
            }

        }
	}// end of method


    public int getLength() {
        return length;
    }

    public int getAncestor() {
        return ancestor;
    }
}

