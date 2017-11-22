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
	 * @param maxLevelLookout the max look-ahead level
	 * @param isMAX whether player is MAX or MIN
	 * @param currentBoardState a 2d array of char representing the current board state
	 */
	public void makeTree(int maxLevelLookout, boolean isMAX, char[][] currentBoardState) {
		miniMaxBoard = new Board();
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
			// moves are in "C4,C5" format, need for conversion from letter to int, and from char to int
			int oldYPos = move.charAt(0) - 'A';
			int oldXPos = Integer.parseInt(move.substring(1,2)) - 1;
			int newYPos = move.charAt(3) - 'A';
			int newXPos = Integer.parseInt(move.substring(4,5)) - 1;
			miniMaxBoard.moveToken(oldYPos, oldXPos, newYPos, newXPos, parentNode.isMAX() ? 'G' : 'R',false,true);
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
	public String evaluateChildrenAndGetNextMove() {
		Node root = tree.getRoot();
		evaluateChildren(root);
		return root.getNextBestMove();
	}

	/**
	 * Evaluate children using the heuristics if we are at the last level.
	 * Recur over each child if we still haven't reached the last level.
	 * @param node parent node
	 */
	public void evaluateChildren(Node node) {
		List<Node> children = node.getChildren();
		boolean isMAXPlayer = node.isMAX();
		for (Node child : children) {
			// == 0 if at the last level or there are no children, i.e. the child is a winning node
			if (child.getMaxLevelLookout() == 0 || child.getChildren() == null || child.getChildren().isEmpty()) {
				calculateScore(child);
			}
			else
				evaluateChildren(child); // Recursive call
		}

		Node bestNode = findBestNode(isMAXPlayer, children, this.maxLevelLookout - node.getMaxLevelLookout()); // Find the best move among the children
		node.setScore(bestNode.getScore());
		node.setNextBestMove(bestNode.getTokenMove());
		
		// Display the move the AI chose and the scores of first level only
		if (node.equals(tree.getRoot())) {
			System.out.println("==> AI chose move [" + bestNode.getTokenMove() + "] with a score of [" + bestNode.getScore() + "]");
			String childrenScores = "";
			childrenScores += "==> Level 1 scores: ";
			for (Node child : children) {
				childrenScores += " [" + child.getScore() + "] ";
			}
			System.out.println(childrenScores);
		}
	}

	/**
	 * Method to create a list of possible next moves as strings ex. "A1,B1" implies moving A1 to B1
	 * @param parentNode
	 * @return List of String values
	 */
	public List<String> computeNextMoves(Node parentNode) {
		// Maybe make use of attribute currentBoardState?
		List<String> list = new ArrayList<>();
		
		miniMaxBoard.setBoardArr(parentNode.getCurrentState());
		Boolean isMAXPlayer = parentNode.isMAX();
		boolean isValid;
		for (int i = 0; i < miniMaxBoard.getHeight(); i++) {
			for (int j = 0; j < miniMaxBoard.getWidth(); j++) {
				if (i == 0 && j == 0) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
				}
				else if (i == 0 && j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
				}
				else if (i == 4 && j == 0 ) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
				}
				else if (i == 4 && j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
				}
				else if (i == 0) {
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
				}
				else if (i == 4) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
				}
				else if (j == 0) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal  down-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+2));
				}
				else if (j == 8) {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A')+ "" + j);
				}
				else {
					isValid = miniMaxBoard.moveToken(i, j, (i-1), j, isMAXPlayer ? 'G' : 'R',true,true); // up
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), j, isMAXPlayer ? 'G' : 'R',true,true); // down
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + (j+1));
					isValid = miniMaxBoard.moveToken(i, j, i, (j-1), isMAXPlayer ? 'G' : 'R',true,true); // left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, i, (j+1), isMAXPlayer ? 'G' : 'R',true,true); // right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j-1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal down-left
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i+1+'A') + "" + j);
					isValid = miniMaxBoard.moveToken(i, j, (i-1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal up-right
					if (isValid) list.add((char)(i+'A') + "" + (j+1) + "," + (char)(i-1+'A') + "" + (j+2));
					isValid = miniMaxBoard.moveToken(i, j, (i+1), (j+1), isMAXPlayer ? 'G' : 'R',true,true); // diagonal  down-right
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
	 * @param children list of Node that are children to the parent node
	 * @param currentLevel current look-ahead level
	 * @return the best node
	 */
    public Node findBestNode(boolean isMAX, List<Node> children, int currentLevel) {
		// Iterate through children and select the appropriate node
    	    	
    	// Node to store the highest score 
    	Node highestScoreNode = null;
    	
    	// Checking Null list
		if(children == null) {
			return null;
		}
    	
		if (isMAX) {
			for (Node n : children) {
				// Comparing
				if(highestScoreNode == null) {
					highestScoreNode = n;
				}
				if(n.getScore() >= highestScoreNode.getScore())	{
					highestScoreNode = n;
				}
			}
		}
		else {
			for (Node n : children) {
				// Find the LOWEST score using n.getScore() and compare
				// Comparing
				if(highestScoreNode == null) {
					highestScoreNode = n;
				}
				if(n.getScore() <= highestScoreNode.getScore()) {
					highestScoreNode.setScore(n.getScore());
					highestScoreNode = n;
				}
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

    