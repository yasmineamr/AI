import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public abstract class SearchProblem {
	Node initialState;
	String[] operators;
	
	static final int INF = (int)1e9;
	static int expandedNodes = 0;
	
	public abstract Node transitionFunction(Node node, String operator);
	public abstract boolean goalTest(Node node);
	public abstract int pathCost(String operator);
	public abstract int h1(Node node);
	public abstract int h2(Node node);
	public abstract ArrayList<Node> expand(Node node, String[] operators);
	
	public Node GeneralSearch(SearchProblem problem, String strategy){
		if(strategy.equals("UC") || strategy.equals("GR1") || strategy.equals("AS1") || strategy.equals("GR2") || strategy.equals("AS2") ) {
			return ucs(problem);
		}
		
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(problem.initialState);
		int depth = 0;
		while(true) {
			if(list.isEmpty()){
				if(strategy.equals("ID")){
					depth++;
					list.add(problem.initialState);
				}
				else
					return null;
			}
			Node curr = (Node) list.remove(0);
			expandedNodes++;
//			SWState state = (SWState) curr.state;
//			System.out.println(state.jon.x + " " + state.jon.y + curr.operator);

			if(problem.goalTest(curr))
				return curr;
			ArrayList<Node> result = problem.expand(curr, problem.operators);
			for(int i = 0; i < result.size(); i++) {
				switch (strategy) {
				case "BF":
					list.add(result.get(i));
					break;
				case "DF":
					list.add(0, result.get(i));
					break;	
				case "ID":
					if(result.get(i).depth <= depth)
						list.add(0, result.get(i));
					break;
				case "UC":
					list.add(result.get(i));
					Collections.sort(list);
					break;
				case "GR1":
					
					break;
				default:
					break;
				}
			}
//			for(int i = 0; i < list.size(); i++) {
//				System.out.print(list.get(i).cost+" ");
//			}
//			System.out.println();
		}		
	}
	
	public static Node bfs(SearchProblem problem) {
		Queue<Node> q = new LinkedList<Node>();
		q.add(problem.initialState);
		while(true) {
			if(q.isEmpty()) return null;
			Node curr = (Node) q.poll();
//			SWState state = (SWState) curr.state;
//			System.out.println(state.jon.x + " " + state.jon.y + curr.operator);
			if(problem.goalTest(curr))
				return curr;
			//ArrayList<Node> result = problem.expand(curr, ((SaveWesteros)problem).operators);
			ArrayList<Node> result = problem.expand(curr, problem.operators);
			for(int i = 0; i < result.size(); i++) {
				q.add(result.get(i));
			}
		}
	}
	
	public static Node dfs(SearchProblem problem) {
		Stack<Node> stack = new Stack<Node>();
		stack.add(problem.initialState);
		while(true) {
			if(stack.isEmpty()) return null;
			Node curr = (Node) stack.pop();
//			SWState state = (SWState) curr.state;
		//	System.out.println(curr.operator);
			if(problem.goalTest(curr))
				return curr;
			
			//ArrayList<Node> result = problem.expand(curr, ((SaveWesteros)problem).operators);
			ArrayList<Node> result = problem.expand(curr, problem.operators);
			for(int i = result.size()-1; i >= 0; i--) {
				stack.add(result.get(i));
			}
		}
	}
	
	public static Node dls(SearchProblem problem, int depth) {
		Stack<Node> stack = new Stack<Node>();
		stack.add(problem.initialState);
		while(true) {
			if(stack.isEmpty()) return null;
			Node curr = (Node) stack.pop();
//			SWState state = (SWState) curr.state;
//			System.out.println(curr.operator);
			if(problem.goalTest(curr))
				return curr;
			
			//ArrayList<Node> result = problem.expand(curr, ((SaveWesteros)problem).operators);
			ArrayList<Node> result = problem.expand(curr, problem.operators);
			for(int i = 0; i < result.size(); i++) {
				if(result.get(i).depth <= depth)
					stack.add(result.get(i));
			}
		}
	}
	
	public static Node ucs(SearchProblem problem) {
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(problem.initialState);
		while(true) {
			if(q.isEmpty()) return null;
			Node curr = (Node) q.poll();
			expandedNodes++;
//			SWState state = (SWState) curr.state;
//			System.out.println(state.jon.x + " " + state.jon.y + " "+ curr.operator + " " + curr.cost + " " + curr.heuristic + " " + curr.depth);
			if(problem.goalTest(curr))
				return curr;
			//ArrayList<Node> result = problem.expand(curr, ((SaveWesteros)problem).operators);
			ArrayList<Node> result = problem.expand(curr, problem.operators);
			for(int i = 0; i < result.size(); i++) {
				q.add(result.get(i));
			}
		}
	}
}




















