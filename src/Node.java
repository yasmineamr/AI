
public class Node implements Comparable<Node>{
	State state;
	Node parent;
	int cost;
	int heuristic;
	int depth;
	String operator;
	String strategy;
	
	public Node(State s, Node p, int c, int h, int d, String o, String st){
		state = s;
		parent = p;
		cost = c;
		heuristic = h;
		depth = d;
		operator = o;
		strategy = st;
	}


	@Override
	public int compareTo(Node n) {
		
		return cost - n.cost;
		
	}
	
	
}
