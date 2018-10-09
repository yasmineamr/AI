import java.util.ArrayList;
import java.util.Stack;

public class SaveWesteros extends SearchProblem{
	
	static Coordinates dragonstone;
	static ArrayList<Coordinates> obstacles;
	int maxDragonglass = 5;
	int gridX = 4;
	int gridY = 4;
	
	public SaveWesteros(char[][] grid, String strategy) {
		
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
		super.initialState = new Node(tmp, null, 0, 0, 0, null, strategy);
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
	
	public static char[][] genGrid() {
 
//		this.maxDragonglass = 5;
		char[][] grid = new char[4][4];
//		gridX = 4;
//		gridY = 4;
//		for(int i=0; i<4; i++)
//			for(int j=0; j<4; j++){
//				if(i==0 && j==0)
//					grid[i][j] = 'D';
//				else if((i==0 && j==3) || (i==3 && j==1))
//					grid[i][j] = 'O';
//				else if((i==2 && j==0) || (i==2 && j==1) || (i==2 && j==3))
//					grid[i][j] = 'W';
//				else if( i== 3 && j==3)
//					grid[i][j] = 'J';
//				else {
//					grid[i][j] = 'E';
//				}
//			}
		
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
		
//		for(int i=0; i<4; i++)
//		for(int j=0; j<4; j++){
//			if(i==0 && j==0)
//				grid[i][j] = 'D';
//			else if((i == 0 && j == 2))
//				grid[i][j] = 'O';
//			else if((i==0 && j==2) || (i==2 && j==1) || (i==2 && j==2) || (i == 1 && j == 2) || (i == 1 && j == 3))
//				grid[i][j] = 'W';
//			else if( i== 3 && j==3)
//				grid[i][j] = 'J';
//			else {
//				grid[i][j] = 'E';
//			}
//		}
		
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++){
				if(i==2 && j==1)
					grid[i][j] = 'D';
//				else if((i == 1 && j == 3))
//					grid[i][j] = 'O';
				else if((i==2 && j==2) )
					grid[i][j] = 'W';
				else if( i== 3 && j==3)
					grid[i][j] = 'J';
				else {
					grid[i][j] = 'E';
				}
			}
		
//		for(int i=0; i<4; i++)
//		for(int j=0; j<4; j++){
//			if(i==0 && j==0)
//				grid[i][j] = 'D';
//			else if((i==0 && j==3) || (i==3 && j==1) || (i == 2 && j == 2))
//				grid[i][j] = 'O';
//			else if((i==2 && j==0) || (i==2 && j==1) || (i==2 && j==3) || (i == 3 && j == 0))
//				grid[i][j] = 'W';
//			else if( i== 3 && j==3)
//				grid[i][j] = 'J';
//			else {
//				grid[i][j] = 'E';
//			}
//		}
		
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
	
	public static String[] search(char[][] grid, String strategy, boolean visualize){
		
		Stack<Object> grids = new Stack<Object>();
		String [] result = new String[3];
		SaveWesteros sw = new SaveWesteros(grid, strategy);
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
		
		while(current != null){ //changed this from current to current.operator
			SWState currentState = (SWState)current.state;
			if(current.operator != null)
				path = current.operator +" "+ path;
//			cost += current.cost;
			
			currentGrid = new char[grid.length][grid[0].length];
			
			currentGrid[dragonstone.x][dragonstone.y] = 'D';
			for(int i=0; i<obstacles.size(); i++){
				currentGrid[obstacles.get(i).x][obstacles.get(i).y] = 'O';
			}
			currentGrid[currentState.jon.x][currentState.jon.y] = 'J';
			
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
		result[2] = "Depth: "+goal.depth; //number of moves  = depth (not depth+1)
		
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
		
		int cost = 0;
		int heuristic = 0;
		if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
			
		}
		else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
			
		}
		else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
			cost = pathCost("move");
			
		}
		return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "right", node.strategy);
		
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
		int cost = 0;
		int heuristic = 0;
		if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
			
		}
		else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
			
		}
		else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
			cost = pathCost("move");
			
		}
		return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "left", node.strategy);
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
		int cost = 0;
		int heuristic = 0;
		if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
			
		}
		else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
			
		}
		else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
			cost = pathCost("move");
			
		}
		return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "up", node.strategy);
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
		int cost = 0;
		int heuristic = 0;
		
		if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
			
		}
		else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
			
		}
		else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
			cost = pathCost("move");
			
		}
		return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "down", node.strategy);
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
			int cost = 0;
			int heuristic = 0;
			if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
				
			}
			else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
				
			}
			else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
				cost = pathCost("kill");
				
			}
			return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "kill", node.strategy);
		}

	}
	
	Node transitionFunctionCollect(Node node){
		SWState currentState = ((SWState)node.state);
		Coordinates jon = currentState.jon;
		
		if(jon.x == dragonstone.x && jon.y == dragonstone.y && currentState.dragonglasses == 0){
			State tmp = new SWState(jon, maxDragonglass, currentState.whitewalkers);
			int cost = 0;
			int heuristic = 0;
			if(node.strategy.equals("GR1") || node.strategy.equals("AS1")){
				
			}
			else if(node.strategy.equals("GR2") || node.strategy.equals("AS2")){
				
			}
			else if(!(node.strategy.equals("GR1") || node.strategy.equals("GR2"))){
				cost = pathCost("collect");
				
			}
			return new Node(tmp, node, node.cost + cost, heuristic, node.depth+1, "collect", node.strategy);
		}
		
		return null;
	}
	
	
	public static void main(String[]args){
		
		char[][] grid = genGrid();
		
		String[] result = search(grid, "UC", true);
		
		for(int i = 0; i < result.length; i++)
			System.out.println(result[i]);
	}

}
























