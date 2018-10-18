import java.util.ArrayList;
import java.util.Stack;

public class SaveWesteros extends SearchProblem{
	
	static Coordinates dragonstone;
	static ArrayList<Coordinates> obstacles;
	static int maxDragonglass;
	static int gridX;
	static int gridY;
	static String searchStrategy;
	
	public SaveWesteros(char[][] grid) {
		
		Coordinates jon = new Coordinates(grid.length-1,grid[0].length-1);
		int dragonglasses = 0;
		ArrayList<Coordinates> whitewalkers = new ArrayList<>();
		obstacles = new ArrayList<>();
		
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
		super.initialState = new Node(tmp, null, 0, 0, 0, null, 0);
//		super.operators = new String[]{"up", "down", "right", "left", "kill", "collect"};
		super.operators = new String[]{"kill", "collect", "up", "down", "right", "left"};
	}

	public boolean goalTest(Node node) {
		if(((SWState) node.state).whitewalkers.isEmpty())
			return true;
		return false;
	}

	@Override
	public int pathCost(String operator) {
		if(operator.equals("move"))
			return 1;
		else if(operator.equals("collect"))
			return 1;
		else if(operator.equals("kill"))
			return gridX*gridY;
		
		return 0;
	}
	
	public int h1(Node node){
		SWState state = (SWState)node.state;
		int h = (int)Math.ceil(state.whitewalkers.size()/3) * pathCost("kill");
		
		return h;
	}
	
	public int h2(Node node){
		
		if(goalTest(node))
			return 0;
		
		SWState state = (SWState)node.state;
		Coordinates jon = state.jon;
		ArrayList<Coordinates> whitewalkers = state.whitewalkers;
		int min = 1000;
		
		for(int i=0; i<whitewalkers.size(); i++){
			Coordinates whitewalker = whitewalkers.get(i);
			int distance = Math.abs(jon.x - whitewalker.x) + Math.abs(jon.y - whitewalker.y);
			if(distance < min)
				min = distance;
		}
		return min + pathCost("kill");
	}
	
	
	public static char[][] genGridStatic() {
 
		maxDragonglass = 6;
		char[][] grid = new char[4][4];
		gridX = 4;
		gridY = 4;
		
		//Example 1
		for(int i=0; i<4; i++)
		for(int j=0; j<4; j++){
			if(i==1 && j==2)
				grid[i][j] = 'D';
			else if((i==0 && j==2) || (i==1 && j==0) || (i==2 && j==1) || (i==2 && j==0))
				grid[i][j] = 'O';
			else if((i==3 && j==0) || (i==2 && j==2) || (i == 3 && j == 2) || (i==1 && j==1))
				grid[i][j] = 'W';
			else if( i== 3 && j==3)
				grid[i][j] = 'J';
			else {
				grid[i][j] = 'E';
			}
		}

		
		//Example 2
//		for(int i=0; i<4; i++)
//		for(int j=0; j<4; j++){
//			if(i==3 && j==2)
//				grid[i][j] = 'D';
//			else if((i==1 && j==1) || (i==2 && j==1))
//				grid[i][j] = 'O';
//			else if((i==0 && j==0) || (i==2 && j==3) || (i==0 && j==3))
//				grid[i][j] = 'W';
//			else if( i== 3 && j==3)
//				grid[i][j] = 'J';
//			else {
//				grid[i][j] = 'E';
//			}
//		}
		
		

		//Example 3
//		for(int i=0; i<4; i++)
//			for(int j=0; j<4; j++){
//				if(i==0 && j==0)
//					grid[i][j] = 'D';
//				else if((i == 0 && j == 2))
//					grid[i][j] = 'O';
//				else if((i==0 && j==1) || (i==2 && j==1) || (i==2 && j==2) || (i == 1 && j == 2) || (i == 1 && j == 3))
//					grid[i][j] = 'W';
//				else if( i== 3 && j==3)
//					grid[i][j] = 'J';
//				else {
//					grid[i][j] = 'E';
//				}
//			}
		
		return grid;
	}
	
	public static char[][] genGrid(){
		
		//(int)(Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
		gridX = (int)(Math.random()*1 + 5); //From 4 to 6
		gridY = (int)(Math.random()*1 + 5); //From 4 to 6
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
	
	public static String[] search(char[][] grid, String strategy, boolean visualize){
		
		Stack<Object> grids = new Stack<Object>();
		String [] result = new String[3];
		SaveWesteros sw = new SaveWesteros(grid);
		searchStrategy = strategy;
		
		Node goal = sw.GeneralSearch(sw, strategy);
		if(goal == null) 
			return result;
		String path = goal.operator;
		int cost = goal.cost;
		Node current = goal.parent;
		//String currentGrid = goal.operator +"\n";
		
		char[][] currentGrid = new char[grid.length][grid[0].length];
		
		currentGrid[dragonstone.x][dragonstone.y] = 'D';
		for(int i=0; i<obstacles.size(); i++){
			currentGrid[obstacles.get(i).x][obstacles.get(i).y] = 'O';
		}
		currentGrid[((SWState)goal.state).jon.x][((SWState)goal.state).jon.y] = 'J';
		for(int i=0; i<currentGrid.length; i++)
			for(int j=0; j<currentGrid[0].length; j++){
				if(currentGrid[i][j] == '\u0000')
					currentGrid[i][j] = 'E';
			}
		grids.push(currentGrid);
		grids.push(goal.operator);
		
		while(current != null){ 
			SWState currentState = (SWState)current.state;
			if(current.operator != null)
				path = current.operator +" "+ path;
			
			currentGrid = new char[grid.length][grid[0].length];
			
			currentGrid[dragonstone.x][dragonstone.y] = 'D';
			for(int i=0; i<obstacles.size(); i++){
				currentGrid[obstacles.get(i).x][obstacles.get(i).y] = 'O';
			}
			currentGrid[currentState.jon.x][currentState.jon.y] = 'J';
			
			if(currentState.jon.x == dragonstone.x && currentState.jon.y == dragonstone.y)
				currentGrid[currentState.jon.x][currentState.jon.y] = 'X';
			
			for(int i=0; i<currentState.whitewalkers.size(); i++){
				currentGrid[currentState.whitewalkers.get(i).x][currentState.whitewalkers.get(i).y] = 'W';
			}
			
			for(int i=0; i<currentGrid.length; i++)
				for(int j=0; j<currentGrid[0].length; j++){
					if(currentGrid[i][j] == '\u0000')
						currentGrid[i][j] = 'E';
				}
			grids.push(currentGrid);
			if(current.operator != null)
				grids.push(current.operator);
			else 
				grids.push("Start");
			
			current = current.parent;
		}
		
		if(visualize){
			while(!grids.isEmpty()){
				String operator = (String) grids.pop();
				System.out.println(operator);
				
				char[][] currGrid = (char[][]) grids.pop();
				for(int i = 0; i < currGrid.length; i++) {
					for(int j = 0; j < currGrid[0].length; j++) {
						System.out.print(currGrid[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println();
			}
			
		}
		
		result[0] = "Actions to goal: "+path;
		result[1] = "Cost: "+cost;
		result[2] = "Depth: "+goal.depth; 
		
		System.out.println("Expanded Nodes: " + sw.expandedNodes);
		
		return result;
		
	}
	
	public ArrayList<Node> expand(Node node, String[] operators) {
		ArrayList<Node> nodes = new ArrayList<>();
		for(int i = 0; i < operators.length; i++) {
			Node tmp = transitionFunction(node, operators[i]);
			if(tmp != null) 
				nodes.add(tmp);
		}
		
		return nodes;
	}
 	
	public Node transitionFunction(Node node, String operator) {
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
			if(jon.y+1 == obstacles.get(i).y && jon.x == obstacles.get(i).x)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.y+1 == currentState.whitewalkers.get(i).y && jon.x == currentState.whitewalkers.get(i).x)
				return null;
		}
		
		//repeated states
		if(node.parent != null) {
			SWState parent = ((SWState)node.parent.state);
			if(jon.y+1 == parent.jon.y && jon.x == parent.jon.x) 
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x,jon.y + 1);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		
		int cost = pathCost("move") + node.cost;

		Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "right",0);
		
		if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
			newNode.heuristic = h1(newNode);
			
		}
		else if(searchStrategy.equals("GR2") || searchStrategy.equals("AS2")){
			newNode.heuristic = h2(newNode);
		}

		switch (searchStrategy) {
		case "GR1":
		case "GR2":
			newNode.evaluate = newNode.heuristic;
			break;
		case "AS1":
		case "AS2":
			newNode.evaluate = newNode.cost + newNode.heuristic;
			break;
		default:
			newNode.evaluate = newNode.cost;
		}

		
		return newNode;
	}
	
	Node transitionFunctionLeft(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.y == 0)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.y-1 == obstacles.get(i).y && jon.x == obstacles.get(i).x)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.y-1 == currentState.whitewalkers.get(i).y && jon.x == currentState.whitewalkers.get(i).x)
				return null;
		}
		
		//repeated states
		if(node.parent != null) {
			SWState parent = ((SWState)node.parent.state);
			if(jon.y-1 == parent.jon.y && jon.x == parent.jon.x) 
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x,jon.y-1);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		
		int cost = pathCost("move") + node.cost;

		Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "left",0);
		
		if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
			newNode.heuristic = h1(newNode);
			
		}
		else if(searchStrategy.equals("GR2") || searchStrategy.equals("AS2")){
			newNode.heuristic = h2(newNode);
		}
		switch (searchStrategy) {
		case "GR1":
		case "GR2":
			newNode.evaluate = newNode.heuristic;
			break;
		case "AS1":
		case "AS2":
			newNode.evaluate = newNode.cost + newNode.heuristic;
			break;
		default:
			newNode.evaluate = newNode.cost;
		}
		
		return newNode;
	}
	
	Node transitionFunctionUp(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == 0)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.x-1 == obstacles.get(i).x && jon.y == obstacles.get(i).y)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.x-1 == currentState.whitewalkers.get(i).x && jon.y == currentState.whitewalkers.get(i).y)
				return null;
		}
		
		//repeated states
		if(node.parent != null) {
			SWState parent = ((SWState)node.parent.state);
			if(jon.y == parent.jon.y && jon.x-1 == parent.jon.x) 
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x-1,jon.y);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		
		int cost = pathCost("move") + node.cost;

		Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "up",0);
		
		if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
			newNode.heuristic = h1(newNode);
			
		}
		else if(searchStrategy.equals("GR2") ||searchStrategy.equals("AS2")){
			newNode.heuristic = h2(newNode);
		}
		
		switch (searchStrategy) {
		case "GR1":
		case "GR2":
			newNode.evaluate = newNode.heuristic;
			break;
		case "AS1":
		case "AS2":
			newNode.evaluate = newNode.cost + newNode.heuristic;
			break;
		default:
			newNode.evaluate = newNode.cost;
		}
		
		return newNode;
	}
	
	Node transitionFunctionDown(Node node){
		
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == gridX-1)
			return null;
		
		for(int i =0; i<obstacles.size();i++){
			if(jon.x+1 == obstacles.get(i).x && jon.y == obstacles.get(i).y)
				return null;
		}
		
		for(int i =0; i<currentState.whitewalkers.size();i++){
			if(jon.x+1 == currentState.whitewalkers.get(i).x && jon.y == currentState.whitewalkers.get(i).y)
				return null;
		}
		
		//repeated states
		if(node.parent != null) {
			SWState parent = ((SWState)node.parent.state);
			if(jon.y == parent.jon.y && jon.x+1 == parent.jon.x) 
				return null;
		}
		
		Coordinates newJon = new Coordinates(jon.x+1,jon.y);
		State tmp = new SWState(newJon, currentState.dragonglasses, currentState.whitewalkers);
		int cost = pathCost("move") + node.cost;

		Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "down",0);
		
		if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
			newNode.heuristic = h1(newNode);
			
		}
		else if(searchStrategy.equals("GR2") || searchStrategy.equals("AS2")){
			newNode.heuristic = h2(newNode);
		}
		
		switch (searchStrategy) {
		case "GR1":
		case "GR2":
			newNode.evaluate = newNode.heuristic;
			break;
		case "AS1":
		case "AS2":
			newNode.evaluate = newNode.cost + newNode.heuristic;
			break;
		default:
			newNode.evaluate = newNode.cost;
		}

		return newNode;
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
			
			int cost = pathCost("kill") + node.cost;

			Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "kill",0);
			
			if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
				newNode.heuristic = h1(newNode);
				
			}
			else if(searchStrategy.equals("GR2") || searchStrategy.equals("AS2")){
				newNode.heuristic = h2(newNode);
			}
			
			switch (searchStrategy) {
			case "GR1":
			case "GR2":
				newNode.evaluate = newNode.heuristic;
				break;
			case "AS1":
			case "AS2":
				newNode.evaluate = newNode.cost + newNode.heuristic;
				break;
			default:
				newNode.evaluate = newNode.cost;
			}

			return newNode;
		}

	}
	
	Node transitionFunctionCollect(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == dragonstone.x && jon.y == dragonstone.y && currentState.dragonglasses == 0){
			State tmp = new SWState(jon, maxDragonglass, currentState.whitewalkers);
			
			int cost = pathCost("collect") + node.cost;
			
			Node newNode = new Node(tmp, node, cost, 0, node.depth+1, "collect",0);
			
			if(searchStrategy.equals("GR1") || searchStrategy.equals("AS1")){
				newNode.heuristic = h1(newNode);
				
			}
			else if(searchStrategy.equals("GR2") || searchStrategy.equals("AS2")){
				newNode.heuristic = h2(newNode);
			}
			
			switch (searchStrategy) {
			case "GR1":
			case "GR2":
				newNode.evaluate = newNode.heuristic;
				break;
			case "AS1":
			case "AS2":
				newNode.evaluate = newNode.cost + newNode.heuristic;
				break;
			default:
				newNode.evaluate = newNode.cost;
			}
			
			return newNode;
		}
		
		return null;
	}
	
	
	public static void main(String[]args){
		
		char[][] grid = genGrid();
		System.out.println();
		System.out.println();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Maximum dragonglasses: "+maxDragonglass);
		
		String[] result = search(grid, "GR2", true);
		
		for(int i = 0; i < result.length; i++)
			System.out.println(result[i]);
	}
}
























