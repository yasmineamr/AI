import java.util.ArrayList;

public class SaveWesteros extends SearchProblem{
	
	Coordinates dragonstone;
	ArrayList<Coordinates> obstacles;
	int maxDragonglass;
	
	public SaveWesteros(char[][] grid) {
		
		Coordinates jon = new Coordinates(grid.length-1,grid[0].length-1);
		int dragonglasses = 0;
		ArrayList<Coordinates> whitewalkers = new ArrayList<>();
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == 'W') {
					whitewalkers.add(new Coordinates(i, j));
				} else if(grid[i][j] == 'O') {
					obstacles.add(new Coordinates(i, j));
				} else if(grid[i][j] == 'D') {
					dragonstone = new Coordinates(i, j);
				}
			}
		}
		
		State tmp = new SWState(jon, dragonglasses, whitewalkers);
		super.initialState = new Node(tmp, null, 0, 0, null);
		super.operators = new String[]{"up", "down", "right", "left", "kill", "collect"};
	}

	public Node transitionFunction(Node node, String operator) {
		if(((SWState)node.state).jon.y == 3 && operator.equals("right"))
			return null;
		else if (((SWState)node.state).jon.y == 0 && operator.equals("left"))
			return null;
		
		if(((SWState)node.state).jon.x == 3 && operator.equals("down"))
			return null;
		else if (((SWState)node.state).jon.x == 0 && operator.equals("up"))
			return null;
		
		if(operator.equals("kill") && ((SWState)node.state).dragonglasses == 0)
			return null;
		
		for(int i = 0; i < obstacles.size(); i++) {
			Coordinates obstacle = obstacles.get(i);
			if(((SWState)node.state).jon.x == obstacle.x && ((SWState)node.state).jon.y == obstacle.y + 1 && operator.equals("right"))
				return null;
			
			if(((SWState)node.state).jon.x == obstacle.x && ((SWState)node.state).jon.y == obstacle.y - 1 && operator.equals("left"))
				return null;
			
			if(((SWState)node.state).jon.x == obstacle.x + 1 && ((SWState)node.state).jon.y == obstacle.y && operator.equals("down"))
				return null;
			
			if(((SWState)node.state).jon.x == obstacle.x - 1 && ((SWState)node.state).jon.y == obstacle.y && operator.equals("up"))
				return null;
		}
		
		ArrayList<Coordinates> tmpwhitewalker = ((SWState)node.state).whitewalkers;
		for(int i = 0; i < tmpwhitewalker.size(); i++) {
			Coordinates whitewalker = tmpwhitewalker.get(i);
			if(((SWState)node.state).jon.x == whitewalker.x && ((SWState)node.state).jon.y == whitewalker.y + 1 && operator.equals("right"))
				return null;
			
			if(((SWState)node.state).jon.x == whitewalker.x && ((SWState)node.state).jon.y == whitewalker.y - 1 && operator.equals("left"))
				return null;
			
			if(((SWState)node.state).jon.x == whitewalker.x + 1 && ((SWState)node.state).jon.y == whitewalker.y && operator.equals("down"))
				return null;
			
			if(((SWState)node.state).jon.x == whitewalker.x - 1 && ((SWState)node.state).jon.y == whitewalker.y && operator.equals("up"))
				return null;
		}
		
		if(operator.equals("collect") && ((SWState)node.state).jon.x != dragonstone.x && ((SWState)node.state).jon.y != dragonstone.y)
			return null;
		
		if(operator.equals("right")) {
			Coordinates newJon = new Coordinates(((SWState)node.state).jon.x, ((SWState)node.state).jon.y + 1);
			State tmp = new SWState(newJon, ((SWState)node.state).dragonglasses, ((SWState)node.state).whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "right");
		}
		
		if(operator.equals("left")) {
			Coordinates newJon = new Coordinates(((SWState)node.state).jon.x, ((SWState)node.state).jon.y - 1);
			State tmp = new SWState(newJon, ((SWState)node.state).dragonglasses, ((SWState)node.state).whitewalkers);
			//costfunction
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "left");
		}
		
		if(operator.equals("up")) {
			Coordinates newJon = new Coordinates(((SWState)node.state).jon.x - 1, ((SWState)node.state).jon.y);
			State tmp = new SWState(newJon, ((SWState)node.state).dragonglasses, ((SWState)node.state).whitewalkers);
			//costfunction
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "up");
		}
		
		if(operator.equals("down")) {
			Coordinates newJon = new Coordinates(((SWState)node.state).jon.x + 1, ((SWState)node.state).jon.y);
			State tmp = new SWState(newJon, ((SWState)node.state).dragonglasses, ((SWState)node.state).whitewalkers);
			//costfunction
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "down");
		}
		
		if(operator.equals("collect")) {
			Coordinates newJon = new Coordinates(((SWState)node.state).jon.x, ((SWState)node.state).jon.y);
			State tmp = new SWState(newJon, maxDragonglass, ((SWState)node.state).whitewalkers);
			//costfunction
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "collect");
		}
		
		if(operator.equals("kill")) {
			
			return null;
		}
		
		return null;
		
	}

	public boolean goalTest(Node node) {
		if(((SWState) node.state).whitewalkers.isEmpty())
			return true;
		return false;
	}

	@Override
	public int pathCost(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public char[][] genGrid() {
 
		this.maxDragonglass = 5;
		char[][] grid = new char[4][4];
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++){
				if(i==0 && j==0)
					grid[i][j] = 'D';
				else if((i==0 && j==1) || (i==0 && j==3) || (i==3 && j==1))
					grid[i][j] = 'O';
				else if((i==0 && j==2) || (i==2 && j==0) || (i==2 && j==1) || (i==2 && j==3))
					grid[i][j] = 'W';
				else if( i== 3 && j==3)
					grid[i][j] = 'J';
				else {
					grid[i][j] = 'E';
				}
			}
		
		return grid;
	}
	
	public String[] search(char[][] grid, String strategy, boolean visualize){
		
		String [] result = new String[3];
		SaveWesteros sw = new SaveWesteros(grid);
		Node goal = super.GeneralSearch(sw, strategy);
		String path = goal.operator;
		int cost = goal.cost;
		Node current = goal.parent;
		while(current != null){
			path = current.operator +" "+ path;
			cost += current.cost;
			current = current.parent;
		}
		
		result[0] = path;
		result[1] = cost+"";
		result[2] = goal.depth+1+"";
		
		return result;
		
	}

}
























