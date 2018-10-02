
public abstract class SearchProblem {
	Node initialState;
	String[] operators;
	
	public abstract Node transitionFunction(Node node, String operator);
	public abstract boolean goalTest(Node node);
	public abstract int pathCost(Node node);
	
	public static Node GeneralSearch(SearchProblem problem, String strategy){
		
		
		return null;
	}
}
