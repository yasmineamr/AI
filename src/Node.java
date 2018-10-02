
public class Node {
	State state;
	Node parent;
	int cost;
	int depth;
	String operator;
	
	public Node(State s, Node p, int c, int d, String o){
		state = s;
		parent = p;
		cost = c;
		depth = d;
		operator = o;
	}
}
