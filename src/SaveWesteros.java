import java.util.ArrayList;

public class SaveWesteros extends SearchProblem{
	
	Coordinates dragonstone;
	ArrayList<Coordinates> obstacles;
	int maxDragonglass;
	int gridX;
	int gridY;
	
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
		
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.y == gridY-1 && operator.equals("right"))
			return null;
		else if (jon.y == 0 && operator.equals("left"))
			return null;
		
		if(jon.x == gridX-1 && operator.equals("down"))
			return null;
		else if (jon.x == 0 && operator.equals("up"))
			return null;
		
		if(operator.equals("kill") && currentState.dragonglasses == 0)
			return null;
		
		for(int i = 0; i < obstacles.size(); i++) {
			Coordinates obstacle = obstacles.get(i);
			if(jon.x == obstacle.x && jon.y == obstacle.y + 1 && operator.equals("right"))
				return null;
			
			if(jon.x == obstacle.x && jon.y == obstacle.y - 1 && operator.equals("left"))
				return null;
			
			if(jon.x == obstacle.x + 1 && jon.y == obstacle.y && operator.equals("down"))
				return null;
			
			if(jon.x == obstacle.x - 1 && jon.y == obstacle.y && operator.equals("up"))
				return null;
		}
		
		ArrayList<Coordinates> tmpwhitewalker = currentState.whitewalkers;
		
		for(int i = 0; i < tmpwhitewalker.size(); i++) {
			Coordinates whitewalker = tmpwhitewalker.get(i);
			if(jon.x == whitewalker.x && jon.y == whitewalker.y + 1 && operator.equals("right"))
				return null;
			
			if(jon.x == whitewalker.x && jon.y == whitewalker.y - 1 && operator.equals("left"))
				return null;
			
			if(jon.x == whitewalker.x + 1 && jon.y == whitewalker.y && operator.equals("down"))
				return null;
			
			if(jon.x == whitewalker.x - 1 && jon.y == whitewalker.y && operator.equals("up"))
				return null;
		}
		
		if(operator.equals("collect") && jon.x != dragonstone.x && jon.y != dragonstone.y)
			return null;
		
		if(operator.equals("right")) {
			Coordinates newJon = new Coordinates(jon.x,jon.y + 1);
			State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "right");
		}
		
		if(operator.equals("left")) {
			Coordinates newJon = new Coordinates(jon.x,jon.y - 1);
			State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "left");
		}
		
		if(operator.equals("up")) {
			Coordinates newJon = new Coordinates(jon.x - 1,jon.y);
			State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "up");
		}
		
		if(operator.equals("down")) {
			Coordinates newJon = new Coordinates(jon.x + 1,jon.y);
			State tmp = new SWState(newJon,currentState.dragonglasses, currentState.whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "down");
		}
		
		if(operator.equals("collect")) {
			Coordinates newJon = new Coordinates(jon.x,jon.y);
			State tmp = new SWState(newJon, maxDragonglass, currentState.whitewalkers);
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
		gridX = 4;
		gridY = 4;
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
	
	public char[][] GenGridRandom(){
		//(int)(Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
		gridX = (int)(Math.random()*3 + 4); //From 4 to 6
		gridY = (int)(Math.random()*3 + 4); //From 4 to 6
		char[][] grid = new char[gridX][gridY];
		
		
		for(int i=0;i<gridX;i++)
			for(int j=0; j<gridY; j++){
				if(i == gridX-1 && j == gridY-1)
					grid[i][j] = 'J';
				else
					grid[i][j] = 'E';
		}
		
		int obstaclesCount = 0; //Number of obstacles added so far
		int obstaclesMax = (int)(Math.random()*3 + 2); //Number of obstacles that should be added. From 2 to 4
		
		while(obstaclesCount < obstaclesMax){
			int obstacleX = (int)(Math.random()*gridX); //From 0 to gridX-1
			int obstacleY = (int)(Math.random()*gridY); //From 0 to gridY-1
			if(grid[obstacleX][obstacleY] == 'E'){
				obstaclesCount++;
				grid[obstacleX][obstacleY] = 'O';
			}
		}
		
		maxDragonglass = (int)(Math.random()*7 + 2); //From 2 to 8
		
		//Add dragonstone to grid
		boolean flag = false;
		while(!flag){
			int dragonstoneX = (int)(Math.random()*gridX); //From 0 to gridX-1
			int dragonstoneY = (int)(Math.random()*gridY); //From 0 to gridY-1
			if(grid[dragonstoneX][dragonstoneY] == 'E'){
				grid[dragonstoneX][dragonstoneY] = 'D';
				flag = true;
			}
		}
		
		int whitewalkersCount = 0; //Number of whitewalkers added so far
		int whitewalkersMax = (int)(Math.random()*4 + 2); //Number of whitewalkers that should be added. From 2 to 5
		
		while(whitewalkersCount < whitewalkersMax){
			int whitewalkerX = (int)(Math.random()*gridX); //From 0 to gridX-1
			int whitewalkerY = (int)(Math.random()*gridY); //From 0 to gridY-1
			if(grid[whitewalkerX][whitewalkerY] == 'E'){
				whitewalkersCount++;
				grid[whitewalkerX][whitewalkerY] = 'W';
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
	
	public Node transitionFunctionH(Node node, String operator) {
		if(operator.equals("right"))
			return transitionFunctionRight(node);
		
		if(operator.equals("left"))
			return transitionFunctionLeft(node);
		
		if(operator.equals("up"))
			return transitionFunctionUp(node);
		
		if(operator.equals("down"))
			return transitionFunctionDown(node);
		
		if(operator.equals("kill"))
			return transitionFunctionKill(node);
		
		if(operator.equals("collect"))
			return transitionFunctionCollect(node);
		
		return null;
	}
	
	Node transitionFunctionRight(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		if(jon.y == gridY-1)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.y+1 == obstacles.get(i).y)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.y+1 == currentState.whitewalkers.get(i).y)
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x,jon.y + 1);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		int cost = pathCost(node);
		return new Node(tmp, node, cost, node.depth+1, "right");
		
	}
	
	Node transitionFunctionLeft(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.y == 0)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.y-1 == obstacles.get(i).y)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.y-1 == currentState.whitewalkers.get(i).y)
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x,jon.y-1);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		int cost = pathCost(node);
		return new Node(tmp, node, cost, node.depth+1, "left");
	}
	
	Node transitionFunctionUp(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == 0)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.x-1 == obstacles.get(i).x)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.x-1 == currentState.whitewalkers.get(i).x)
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x-1,jon.y);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		int cost = pathCost(node);
		return new Node(tmp, node, cost, node.depth+1, "up");
	}
	
	Node transitionFunctionDown(Node node){
		
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == gridX-1)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.x+1 == obstacles.get(i).x)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.x+1 == currentState.whitewalkers.get(i).x)
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x+1,jon.y);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		int cost = pathCost(node);
		return new Node(tmp, node, cost, node.depth+1, "down");
	}
	
	Node transitionFunctionKill(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(currentState.dragonglasses == 0)
			return null;
		
		ArrayList<Integer> whitewalkersIndex = new ArrayList<>();
		
		for(int i=0; i<currentState.whitewalkers.size(); i++){
			Coordinates whitewalker = currentState.whitewalkers.get(i);
			if(jon.x == whitewalker.x && jon.y+1 == whitewalker.y)
				whitewalkersIndex.add(i);
			
			if(jon.x == whitewalker.x && jon.y-1 == whitewalker.y)
				whitewalkersIndex.add(i);
			
			if(jon.x-1 == whitewalker.x && jon.y == whitewalker.y)
				whitewalkersIndex.add(i);
			
			if(jon.x+1 == whitewalker.x && jon.y == whitewalker.y)
				whitewalkersIndex.add(i);
			
		}
		
		if(whitewalkersIndex.isEmpty())
			return null;
		else{
			ArrayList<Coordinates> newWhitewalkers = new ArrayList<>();
			for(int i=0; i<currentState.whitewalkers.size();i++){
				if(!(whitewalkersIndex.contains(i))){
					newWhitewalkers.add(currentState.whitewalkers.get(i));
				}
			}
				
			State tmp = new SWState(jon, currentState.dragonglasses-1, newWhitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "kill");
		}

	}
	
	Node transitionFunctionCollect(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == dragonstone.x && jon.y == dragonstone.y){
			State tmp = new SWState(jon, maxDragonglass, currentState.whitewalkers);
			int cost = pathCost(node);
			return new Node(tmp, node, cost, node.depth+1, "collect");
		}
		
		return null;
	}
	
	
	public static void main(String[]args){
		System.out.println("HELLO");
		
	}

}
























