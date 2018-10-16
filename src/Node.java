
public class Node implements Comparable<Node>{
	State state;
	Node parent;
	int cost;
	int heuristic;
	int depth;
	String operator;
	int evaluate;
	//String strategy;
	
	public Node(State s, Node p, int c, int h, int d, String o, int e){
		state = s;
		parent = p;
		cost = c;
		heuristic = h;
		depth = d;
		operator = o;
		evaluate = e;
		//strategy = st;
	}


	public int compareTo(Node n) {
		
		return evaluate - n.evaluate;
//		switch (strategy) {
//		case "GR1":
//		case "GR2":
//			return heuristic - n.heuristic;
//		case "AS1":
//		case "AS2":
//			return (cost + heuristic) - (n.cost + n.heuristic);
//		default:
//			return cost - n.cost;
//		}
	}
	
	
}
