import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;


public class BreathFirstPath {
	private HashMap<Integer, Boolean>  marked;
	private HashMap<Integer, Integer> pathV;
	private HashMap<Integer, Integer> pathW;
	private int lengthV = 0;
	private int lengthW = 0;
	private Result result;
	
	public BreathFirstPath() {
		marked = new HashMap<>();
		result = new Result();
		pathV = new HashMap<>();
		pathW = new HashMap<>();
		
	}
	
	
	public Result bfs(Digraph graph, int v, int w) {
		marked = new HashMap<>();
		result = new Result();
		pathV = new HashMap<>();
		pathW = new HashMap<>();
		Queue<Integer> queueV = new Queue<Integer>();
		Queue<Integer> queueW = new Queue<Integer>();
		
		
		
		queueV.enqueue(v);
		queueW.enqueue(w);
		
		
		marked.put(new Integer(v), true);
		marked.put(new Integer(w), true);
		pathV.put(new Integer(v), 0);
		pathV.put(new Integer(w), 0);
		//lengthV++;
		//lengthW++;
		//marked[v] = true;
		//marked[w] = true;
		
		while (!queueV.isEmpty() && !queueW.isEmpty()) {	
			int currentV = queueV.dequeue();
			lengthV++;
			
			for (int adjacent : graph.adj(currentV)) {				
				if (!marked.containsKey(adjacent)) {
				
					queueV.enqueue(adjacent);
					marked.put(new Integer(adjacent), true);
					pathV.put(new Integer(adjacent), lengthV);
					//marked[adjacent] = true;
				}
				else {
					//lengthV++;
					pathV.put(new Integer(adjacent), lengthV);
					result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
					result.setAncestor(adjacent);
					break;
				}
			}
			
			int currentW = queueW.dequeue();
			lengthW++;
			
			
			for (int adjacent : graph.adj(currentW)) {
				if (!marked.containsKey(adjacent)) {
					queueW.enqueue(adjacent);
					marked.put(new Integer(adjacent), true);
					pathW.put(new Integer(adjacent), lengthW);
					//marked[adjacent] = true;
					
				}
				else {
					//lengthW++;
					pathW.put(new Integer(adjacent), lengthW);
					result.setLength(pathV.get(adjacent) + pathW.get(adjacent));
					result.setAncestor(adjacent);
					break;
				}
			}
		}// end of while
		
		return result;
	}// end of method
	
}
