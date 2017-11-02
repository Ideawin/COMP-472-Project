package bonzee;
import java.util.ArrayList;
import java.util.List;

public class MiniMax {
	MiniMaxTree tree;
	int maxLevelLookout;
	Board miniMaxBoard;

	/**
	 * Public constructor
	 */
	public MiniMax() {
		miniMaxBoard = new Board();
	}
	
	/**
	 * First method to call when it is the AI player's turn
	 * @param maxLevelLookout
	 */
	public void makeTree(int maxLevelLookout, boolean isMAX, char[][] currentBoardState) {
		this.maxLevelLookout = maxLevelLookout;
		tree = new MiniMaxTree();
		Node root = new Node(isMAX, maxLevelLookout, "");
		root.setCurrentState(currentBoardState);
		tree.setRoot(root);
		makeTree(root);
	}

	/**
	 * Set the tree nodes recursively
	 * @param parentNode
	 */
	public void makeTree(Node parentNode) {
		boolean isMAXPlayer = !parentNode.isMAX(); // Used to set whether the child will be MAX/MIN
		List<String> nextMoves = computeNextMoves(parentNode); // Get the next possible moves

		// Iterate through each possible moves and create a node for each, adding them as children to the parent node
		// If maxLevelLookout hasn't reached 0, then the tree can have an additional level
		for (String move : nextMoves) {
			miniMaxBoard.setBoardArr(parentNode.getCurrentState());
			miniMaxBoard.moveToken(move.charAt(0), move.charAt(1), move.charAt(3), move.charAt(4), parentNode.isMAX() ? 'G' : 'R',false);

			Node newNode = new Node(isMAXPlayer, parentNode.getMaxLevelLookout() - 1, move);
			newNode.setCurrentState(miniMaxBoard.getBoardArr());
			parentNode.addChild(newNode);
			if (newNode.getMaxLevelLookout() > 0) {
				makeTree(newNode); // Recursive call for next level
			}
		}
	}

	/**
	 * Method to evaluate children starting at the root
	 */
	public void evaluateChildren() {
		Node root = tree.getRoot();
		evaluateChildren(root);
		// MUST DISPLAY THE TREE HERE TO SHOW SCORES PROPAGATING UP!
	}

	/**
	 * Evaluate children using the heuristics if we are at the last level.
	 * Recur over each child if we still haven't reached the last level.
	 * @param node
	 */
	public void evaluateChildren(Node node) {
		List<Node> children = node.getChildren();
		boolean isMAXPlayer = node.isMAX();
		for (Node child : children) {
			// == 0 if at the last level
			if (child.getMaxLevelLookout() == 0) {
				calculateScore(child);
			}
			else
				evaluateChildren(child); // Recursive call
		}

		Node bestNode = findBestNode(isMAXPlayer, children, this.maxLevelLookout - node.getMaxLevelLookout()); // Find the best move among the children
		node.setScore(bestNode.getScore());
	}

	/**
	 * Method to create a list of possible next moves as strings ex. "A1,B1" implies moving A1 to B1
	 * @return List of String values
	 */
	public List<String> computeNextMoves(Node parentNode) {
		// TO-DO: Make a list of all possible next moves, each move being a String ex. "A1,B1"
		// Maybe make use of attribute currentBoardState?
		List<String> list = new ArrayList<>();
		
		miniMaxBoard.setBoardArr(parentNode.getCurrentState());
		Boolean isMAXPlayer = parentNode.isMAX();
		boolean isValid;
		for (int i = 0; i < miniMaxBoard.getHeight(); i++) {
			for (int j = 0; j < miniMaxBoard.getWidth(); j++) {
				if (i == 0 && j == 0) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
				}
				else if (i == 0 && j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
				}
				else if (i == 4 && j == 0 ) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
				}
				else if (i == 4 && j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
				}
				else if (i == 0) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
				}
				else if (i == 4) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
				}
				else if (j == 0) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
				}
				else if (j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
				}
				else {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
				}
			}
		}
		return list;
	}

    /**
     * Method to calculate score of a node
     * @param node one of the child node
     */
    public void calculateScore(Node node) {
    	// Start the score at 0
    	int score = 0;
    	
    	// Look for all G/R tokens through the board
    	for(int i = 0; i < node.currentState.length; i++) {
    		for(int j = 0; j < node.currentState[i].length; j++ ) {
    			if(node.currentState[i][j] == 'G') {
    				// If green, update the score to be 100 * horizontalIndex + 50 * verticalIndex
    				score += (100 * (i + 1)) + (50 * (j + 1));
    			} else if (node.currentState[i][j] == 'R') {
    				// If red, update the score to be -100 * horizontalIndex + -50 * verticalIndex
    				score -= (100 * (i + 1)) + (50 * (j + 1));
    			}
    		}
    	}
    	node.setScore(score);
    }

	/**
	 * Method to find the best next move
	 * @param isMAXPlayer a Boolean indicating whether the PARENT node is MIN or MAX
	 * @param children
	 * @return the best node
	 */
    public Node findBestNode(boolean isMAX, List<Node> children, int currentLevel) {
		// TO-DO: iterate through children and select the appropriate node
    	Node highestScoreNode = new Node(isMAX, currentLevel, null);
    	
    	//Attributes to store the Nodes
    	Node previousNode = new Node(isMAX, currentLevel, null);
    	Node currentNode = new Node(isMAX, currentLevel, null);
    	
    	//Attributes to store the Score
    	int highestScoreNew = 0;
    	int highestScoreOld = 0;
    	
    	//Checking Null list
		if(children == null)
		{
			return null;
		}
    	
		if (isMAX) {

			System.out.print("MAX Level : " + currentLevel + " ");
			
			for (Node n : children) {				
				
				// Display the scores first
				System.out.print(n.getScore() + " ");
				
				// Find the HIGHEST score using n.getScore() and compare somehow (perhaps by doing int highestScore = n.getScore(); and compare?)				
				
				//Compare the previous node with current node:
				
				currentNode = n;	
				highestScoreNew = currentNode.getScore();
												
				//Comparing: if the new score is greater than the old score then set the highestScored node to the new node (currentNode)
				if(highestScoreNew >= highestScoreOld )
				{
					highestScoreNode.setScore(currentNode.getScore());
					highestScoreNode = currentNode;
				}
				//Saving the previous node and score
				previousNode = n;
				highestScoreOld = previousNode.getScore();
			}
		}
		else {
			//System.out.print("MIN Level : " + currentLevel + " ");
			for (Node n : children) {
				// Find the LOWEST score using n.getScore() and compare somehow
				
				// Display the scores first
				System.out.print(n.getScore() + " ");
				
				//Compare the previous node with current node
				currentNode = n;	
				highestScoreNew = currentNode.getScore();
				
				//Comparing: if the new score is smaller than the old score then set highestScored node to the new node (currentNode)
				if(highestScoreNew <= highestScoreOld)
				{
					highestScoreNode.setScore(currentNode.getScore());
					highestScoreNode = currentNode;
				}
				//Saving the previous node and score
				previousNode = n;
				highestScoreOld = previousNode.getScore();
			}
		}
		return highestScoreNode;
	}
	
	/**
	 * Method to get the MiniMax board
	 * @return
	 */
	public Board getBoard() {
		return miniMaxBoard;
	}
}

    