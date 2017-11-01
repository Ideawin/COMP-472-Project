import java.util.List;

public class MiniMax {
    MiniMaxTree tree;
    int maxLevelLookout;
    Board miniMaxBoard;
 
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
        	miniMaxBoard.moveToken(move.charAt(0), move.charAt(1), move.charAt(3), move.charAt(4), parentNode.isMAX() ? 'G' : 'R');
        	
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
    	return null;
    }
    
    /**
     * Method to calculate score of a node
     * @param node one of the child node
     */
    public void calculateScore(Node node) {
    	// TO-DO: Use the heuristics and then do node.setScore();
    }
    
    /**
     * Method to find the best next move
     * @param isMAXPlayer a Boolean indicating whether the PARENT node is MIN or MAX
     * @param children
     * @return the best node
     */
    public Node findBestNode(boolean isMAX, List<Node> children, int currentLevel) {
    	// TO-DO: iterate through children and select the appropriate node
    	if (isMAX) {
    		System.out.print("MAX Level : " + currentLevel + " ");
    		for (Node n : children) {
    			// Display the scores first
    			System.out.print(n.getScore() + " ");
    			// Find the HIGHEST score using n.getScore() and compare somehow (perhaps by doing int highestScore = n.getScore(); and compare?)
    		}
    	}
    	else {
    		System.out.print("MIN Level : " + currentLevel + " ");
    		for (Node n : children) {
    			// Find the LOWEST score using n.getScore() and compare somehow
    		}
    	}
    	return null;
    }
    
}